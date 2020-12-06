import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class BoardGUI {
    @FXML public Button nextTurnBtn;
    @FXML public Button nextTurnButton;
    @FXML private Canvas canvas;
    @FXML public BorderPane baseBoarderPane;
    @FXML public Label playerTurnTag;
    @FXML ImageView RotationImage;
    @FXML private MenuBar menuButton;

    /**
     * BoardX is the board width
     * BoardY is the height of the board
     */
    private int boardX;
    private int boardY;
    private String img;
    private String[] playerImages = {"head1.png","head2.png","head3.png","head4.png"};
    private Image[] statusEffects = {new Image("fireEffect.png"), new Image("iceEffect.png")};
    private Image fixedImage = new Image("F.png");
    /**
     * Turnprogression means
     * -1: turn not started
     * 0: push in tile
     * 1: play action tile
     * 2: move player
     * 3: turn is finished waiting on end turn
     */
    private int turnProgression = -1;
    //-1 is not selected
    private int handIndex = -1;
    //double move holder
    private boolean doubleMove = false;
    private boolean backTrack = false;
    private Game game;

    private HBox nextTurnHBox = new HBox();
    private HBox tileHandHBox = new HBox();

    /**
     * The variables relating to the canvas resizing
     * the padding changes to make the boxX=boxY but they could be not equal until updated
     */
    private double xPad,yPad,boxX,boxY;
    /**
     * MouseX and mouseY represent the click location of the canvas offset by 1
     * 1 - the boardX/boardY is the board tiles and 0 or boardX/boardY+1 are for the triangles at the end
     */
    private double mouseX,mouseY;

    /**
     * This handles drawing the canvas on the board
     */
    @FXML
    private void drawCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //clear the board
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        gc.clearRect(0, 0, width, height);

        yPad = 0;
        xPad = 0;
        //work out padding
        if (height > width){
            yPad += (height - width)/2;
        } if (width > height){
            xPad += (width - height)/2;
        }
        //set the box size
        boxX = (width-(xPad*2)) /  (boardX + 2);
        boxY = (height-(yPad*2)) / (boardY + 2);
        xPad += boxX;
        yPad += boxY;
        //draw verticle lines for grid
        for (int i = 1; i < boardX; i++){
            gc.moveTo((boxX*i)+xPad,yPad);
            gc.lineTo((boxX*i)+xPad,height-yPad);
            gc.stroke();
        }
        //draw horizontal lines for grid
        for (int i = 1; i < boardY; i++){
            gc.moveTo(xPad,(boxY*i)+yPad);
            gc.lineTo(width-xPad,(boxY*i)+yPad);
            gc.stroke();
        }
        //Draw tiles on board
        for (int i = 0; i < boardX; i++){
            for (int j = 0; j < boardY; j++){
                //check if selected
                if ((i == mouseX-1) && (j == mouseY-1)){
                    gc.setFill(Color.RED);
                    gc.fillRect((boxX*i)+xPad+1,(boxY*j)+yPad+1,boxX-2,boxY-2);
                }
                //get tile
                FloorTile t = (FloorTile) this.game.getBoard().getTile(i,j);
                //if tile isn't null draw it
                if (t != null) {
                    Image tileImage = new Image(t.getImageLocation());
                    Rotate rotation = new Rotate(t.getRotation(),(boxX*(i+0.5))+xPad,(boxY*(j+0.5))+yPad);
                    gc.save();
                    gc.setTransform(rotation.getMxx(), rotation.getMyx(), rotation.getMxy(), rotation.getMyy(), rotation.getTx(), rotation.getTy());
                    gc.drawImage(tileImage,(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                    gc.restore();
                    //draw status effects
                    if (t.getIsOnFire()){
                        gc.drawImage(statusEffects[0],(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                    } else if (t.getIsFrozen()){
                        gc.drawImage(statusEffects[1],(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                    }
                    if (t.getIsTileFixed()){
                        gc.drawImage(fixedImage,(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                    }
                } else {
                    //if tile is null draw a blue square
                    gc.setFill(Color.BLUE);
                    gc.fillRect((boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                }
            }
        }
        if (turnProgression == 0) {
            gc.setFill(Color.YELLOW);
            //draw selectable columns with triangle buttons
            for (int i = 0; i < (boardX); i++) {
                if (this.game.getBoard().isColumnPushable(i)) {
                    //add down facing triangle
                    //check if selected
                    if (i == mouseX - 1 && 0 == mouseY) {
                        gc.setFill(Color.RED);
                        gc.fillPolygon(new double[]{(boxX * i) + xPad, (boxX * i) + xPad + (boxX / 2), (boxX * (i + 1)) + xPad},
                                new double[]{yPad - boxY, yPad, yPad - boxY}, 3);
                        gc.setFill(Color.YELLOW);
                    }
                    gc.fillPolygon(new double[]{(boxX * i) + xPad + 2, (boxX * i) + xPad + (boxX / 2), (boxX * (i + 1)) + xPad - 2},
                            new double[]{yPad - boxY + 2, yPad - 2, yPad - boxY + 2}, 3);
                    //add up facing triangle
                    //check if selected
                    if (i == mouseX - 1 && boardY + 1 == mouseY) {
                        gc.setFill(Color.RED);
                        gc.fillPolygon(new double[]{(boxX * i) + xPad, (boxX * i) + xPad + (boxX / 2), (boxX * (i + 1)) + xPad},
                                new double[]{(boxY * (boardY + 2)), (boxY * (boardY + 1)), (boxY * (boardY + 2))}, 3);
                        gc.setFill(Color.YELLOW);
                    }
                    gc.fillPolygon(new double[]{(boxX * i) + xPad + 2, (boxX * i) + xPad + (boxX / 2), (boxX * (i + 1)) + xPad - 2},
                            new double[]{(boxY * (boardY + 2)) - 2, (boxY * (boardY + 1)) + 2, (boxY * (boardY + 2)) - 2}, 3);
                }
            }
            //draw selectable rows with triangles
            for (int i = 0; i < (boardY); i++) {
                if (this.game.getBoard().isRowPushable(i)) {
                    //add right facing triangle
                    //check if selected
                    if (0 == mouseX && i == mouseY - 1) {
                        gc.setFill(Color.RED);
                        gc.fillPolygon(new double[]{xPad - boxX, xPad, xPad - boxX},
                                new double[]{(boxY * i) + yPad, (boxY * i) + yPad + (boxY / 2), (boxY * (i + 1)) + yPad}, 3);
                        gc.setFill(Color.YELLOW);
                    }
                    gc.fillPolygon(new double[]{xPad - boxX + 2, xPad - 2, xPad - boxX + 2},
                            new double[]{(boxY * i) + yPad + 2, (boxY * i) + yPad + (boxY / 2), (boxY * (i + 1)) + yPad - 2}, 3);
                    //add right facing triangle#
                    //check if selected
                    if (boardX + 1 == mouseX && i == mouseY - 1) {
                        gc.setFill(Color.RED);
                        gc.fillPolygon(new double[]{xPad + (boxX * (boardX + 1)), xPad + (boxX * boardX), xPad + (boxX * (boardX + 1))},
                                new double[]{(boxY * i) + yPad, (boxY * i) + yPad + (boxY / 2), (boxY * (i + 1)) + yPad}, 3);
                        gc.setFill(Color.YELLOW);
                    }
                    gc.fillPolygon(new double[]{xPad + (boxX * (boardX + 1)) - 2, xPad + (boxX * boardX) + 2, xPad + (boxX * (boardX + 1)) - 2},
                            new double[]{(boxY * i) + yPad + 2, (boxY * i) + yPad + (boxY / 2), (boxY * (i + 1)) + yPad - 2}, 3);
                }
            }
        }
        //draw players
        gc.setFill(Color.NAVY);
        ArrayList<Player> players = this.game.getPlayers();
        for (int i = 0; i < players.size(); i++){
            int x = players.get(i).getX();
            int y = players.get(i).getY();
            int subx = i % 2;
            int suby = 0;
            if (i > 1){
                suby = 1;
            }
            Image playerImage = new Image(playerImages[i]);
            gc.drawImage(playerImage,(boxX*x)+xPad+((boxX/4)*(subx+1)),(boxY*y)+yPad+((boxY/4)*(suby+1)),boxX/4,boxY/4);
        }
    }

    /**
     * must be initialize otherwise the data won't exist
     * sets default board size and starts the rendering
     */
    @FXML
    public void initialize(){
        boardX = 5;
        boardY = 5;
        canvas.setOnMouseClicked(event -> {
            canvasClickEventHandler(event.getX(),event.getY());
            drawCanvas();
        });
    }

    /**
     * Sets the references in this class to correct values
     * @param game the current game instance
     */
    public void setGame(Game game) {
        this.game = game;
        this.boardX = game.getBoard().getWidth();
        this.boardY = game.getBoard().getLength();

        startTurn();
        //TODO: handle if current player can't move update the turn progression

        setCards(game.getTurn().getHand());
        drawCanvas();
    }

    /**
     * Checks if the mouse click was in range then updates mouseX and mouseY to the new values
     * @param x the x coord of the click event
     * @param y the y coord of the click event
     */
    public void canvasClickEventHandler(double x,double y){
        //check if in board range
        if ((x > xPad-boxX) && (y > yPad-boxY)){
            if ((x < (xPad+(boxX*(boardX+1)))) && (y < (yPad+(boxY*(boardY+1))))){
                x = x - xPad+boxX;
                y = y - yPad+boxY;
                int xTimes = (int) (x / boxX);
                int yTimes = (int) (y / boxY);
                //System.out.println("x: "+xTimes+" y: "+yTimes);
                mouseX = xTimes;
                mouseY = yTimes;
                if (turnProgression == 0 && handIndex != -1){
                    playerPushInTile();
                } else if (backTrack){
                    playerPlayBackTrack();
                } else if (turnProgression == 1 && handIndex != -1){
                    playerPlayAction();
                } else if (turnProgression == 2 || turnProgression == 1){
                    playerMove();
                }

            }
        }
        drawCanvas();
        setCards(this.game.getTurn().getHand());
    }

    /**
     * Handles when a player pushes in a tile
     */
    public void playerPushInTile(){
        //check if tile can be pushed in
        //get selected tile
        FloorTile t = (FloorTile) this.game.getTurn().getHand().get(this.handIndex);
        //get selected rotation
        int rotation = 0;
        //push in the tile
        int index = 1;
        boolean forward = true;
        //if row
        if (mouseX == 0 || mouseX == boardX+1) {
            if (mouseY > 0 && mouseY < boardY+1) {
                if (mouseX == boardX+1){
                    forward = false;
                }
                if (this.game.getBoard().isRowPushable((int)mouseY -1)) {
                    FloorTile tile = game.getBoard().pushInRow(t, (int) mouseY - 1, forward);
                    this.game.getBag().addTile(tile);
                    pushPlayersOnRow((int)mouseY - 1, forward);
                }
            }
        } else if (mouseY == 0 || mouseY == boardY+1) {
            if (mouseX > 0 && mouseX < boardX+1) {
                if (mouseY == boardY+1){
                    forward = false;
                }
                if (this.game.getBoard().isColumnPushable((int)mouseX-1)) {
                    FloorTile tile = this.game.getBoard().pushInColumn(t, (int) mouseX - 1, forward);
                    this.game.getBag().addTile(tile);
                    pushPlayersOnColumn((int)mouseX - 1, forward);
                }
            }
        }
        drawCanvas();
        turnProgression = 1;
        this.game.getTurn().getHand().remove(this.handIndex);
        handIndex = -1;
    }

    /**
     * move all players on row and loop them if they get pushed off
     * @param index the row index starts at 0
     * @param left true if pushing from the left
     */
    private void pushPlayersOnRow(int index, boolean left){
        for (Player p : this.game.getPlayers()){
            if (p.getY() == index){
                int newX;
                if (left) {
                    newX = (p.getX() + 1) % boardX;
                } else {
                    newX = (p.getX() +(boardX - 1) % boardX);
                }
                p.movePlayer(new int[]{newX,p.getY()});
            }
        }
    }

    /**
     * Move all players on column and loop them if they get pushed off
     * @param index the column index starts at 0
     * @param top true if pushing from the top
     */
    private void pushPlayersOnColumn(int index, boolean top){
        for (Player p : this.game.getPlayers()){
            if (p.getX() == index){
                int newY;
                if (top) {
                    newY = (p.getX() + 1) % boardY;
                } else {
                    newY = (p.getX() +(boardY - 1) % boardY);
                }
                p.movePlayer(new int[]{p.getX(),newY});
            }
        }
    }

    /**
     * Handles the player playing an action tile
     */
    public void playerPlayAction(){
        //if action tile can be played
        //get selected action tile
        ActionTile t = (ActionTile)this.game.getTurn().getHand().get(this.handIndex);
        //play selected tile at mouseX-1 and mouseY-1
        try {
            ActionTileFloor tile = (ActionTileFloor)t;
            tile.action(this.game.getBoard(), (int)mouseX, (int)mouseY, this.game.getPlayers().size());
        } catch (Exception e){
            //not a action floor tile
            if (t instanceof DoubleMoveTile){
                DoubleMoveTile tile = (DoubleMoveTile)t;
                this.doubleMove = true;
            } else {
                BackTrackTile tile = (BackTrackTile)t;
                this.backTrack = true;
            }
        }
        game.playActionSound(t.getTILETYPE());
        drawCanvas();
        isAbleToMove(this.game.getTurn());
        this.game.getTurn().getHand().remove(this.handIndex);
        handIndex = -1;
    }

    /**
     * Handles when the player plays a backtrack meeting
     */
    private  void playerPlayBackTrack(){
        for (Player p : this.game.getPlayers()){
            //TODO: check if they can be sent back
            if (p.getX() == mouseX -1 && p.getY() == mouseY -1){
                p.movePlayer(p.getPreviousPosition2());
            }
        }
    }

    /**
     * Handles the start of a player's turn give them the card
     */
    public void startTurn(){
        Player p = game.getTurn();
        Tile drawnTile = game.getBag().getRandomTile();
        p.addToHand(drawnTile);
        drawCanvas();
        try {
            ActionTile actionTile = (ActionTile)drawnTile;
            //choose to play it
            playerTurnTag.setText("Do you want to play the action Tile");
            turnProgression = 1;
        } catch (Exception e){
            FloorTile floorTile = (FloorTile)drawnTile;
            //must play this now
            if (canPlaceTile()) {
                playerTurnTag.setText("You must place the floor Tile");
                turnProgression = 0;
            } else {
                if (p.getHand().size() > 1){
                    playerTurnTag.setText("Do you want to play the action Tile");
                    turnProgression = 1;
                } else {
                    //allow them to move
                    isAbleToMove(p);
                    if (turnProgression == 2) {
                        playerTurnTag.setText("You can move");
                    } else {
                        playerTurnTag.setText("You can't move, end your turn");
                    }
                }
                //remove tile from hand
                this.game.getBag().addTile(floorTile);
                p.getHand().remove(floorTile);
            }
        }
    }

    /**
     * Checks if the player can push in a tile
     * @return true if they can
     */
    private boolean canPlaceTile(){
        for (int i = 0; i < boardX; i++){
            if(this.game.getBoard().isColumnPushable(i)){
                return true;
            }
        }
        for (int i = 0; i < boardY; i++){
            if(this.game.getBoard().isRowPushable(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Handle when the player selects the
     * @param index
     */
    public void handClicked(int index){
        Player p = game.getTurn();
        this.handIndex = index;
        Tile selectedTile = p.getHand().get(index);
        try {
            ActionTile actionTile = (ActionTile)selectedTile;
            //choose to play it
            playerTurnTag.setText("Click on a tile to play this on");
            this.turnProgression = 1;
        } catch (Exception e){
            FloorTile floorTile = (FloorTile)selectedTile;
            //must play this now
            playerTurnTag.setText("Set the rotation and click a triangle to push in from");
            RotationImage.setImage(new Image(floorTile.getImageLocation()));
            this.turnProgression = 0;
            drawCanvas();
        }
        setCards(this.game.getTurn().getHand());
    }

    /**
     * handles when the player move on the board
     */
    public void playerMove(){
        Player p = this.game.getTurn();
        int[] newPos = canMoveTo(p, mouseX-1, mouseY-1);
        if (newPos != null) {
            p.movePlayer(newPos);
            if (!this.doubleMove) {
                turnProgression = 3;
            } else {
                this.doubleMove = false;
            }
        }
        //check if player on the Goal tile
        if (this.game.getBoard().getTile(p.getX(),p.getY()) instanceof GoalTile){

            //TODO: Test this works, works different to clicking a button. Maybe get Gus to look at since he designed scene changing system?

            System.out.println("Winner: " + p.getProfile());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));
            Parent winningMenuFXMLParent = null;
            try {
                winningMenuFXMLParent = (Parent)loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene winningMenuFXMLScene = new Scene(winningMenuFXMLParent);
            Stage primaryStage = (Stage) ( playerTurnTag.getScene().getWindow());
            // This line gets the stage the 'Play' button's action event came from
            primaryStage.setScene(winningMenuFXMLScene);
            WinScreenGUI controller = (WinScreenGUI)loader.getController();
            controller.setGame(this.game);
            primaryStage.show();
        }
        drawCanvas();
    }

    /**
     * Check if the tile is accessible and that it isn't currently occupied
     * @param p the player we want to move
     * @param x the new x coordinate starts at 0
     * @param y the new y coordinate starts at 0
     * @return returns the position if they can move to it
     */
    public int[] canMoveTo(Player p, double x, double y){
        int[] newPos = {(int) x, (int) y};
        if (x >= 0 && x < boardX && y >= 0 && y < boardY) {
            if(this.game.getBoard().isAccessibleFrom(p.getX(),p.getY(),newPos[0],newPos[1])) {
                if (isNotOccupied(newPos[0],newPos[1])) {
                    return newPos;
                }
            }
        }
        return null;
    }

    /**
     * Checks if player is on that tile
     * @param x the X coordinate starts at 0
     * @param y the Y coordinate starts at 0
     * @return true if no player on tile
     */
    private boolean isNotOccupied(int x, int y){
        for (Player p : this.game.getPlayers()){
            if (p.getX() == x && p.getY() == y){
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the player is able to move then sets the turn progression appropriately
     * @param p the player that we are checking
     */
    public void isAbleToMove(Player p) {
        if (canMoveTo(p, p.getX() + 1, p.getY()) != null
        || canMoveTo(p, p.getX() - 1, p.getY()) != null
        || canMoveTo(p, p.getX(), p.getY() + 1) != null
        || canMoveTo(p, p.getX(), p.getY() - 1) != null) {
            turnProgression = 2;
        } else {
            turnProgression = 3;
        }
    }

    /**
     * Decrements the number of status turns remaining
     */
    public void reduceStatusTurns(){
        for (int i = 0; i < boardX; i++){
            for (int j = 0; j < boardY; j++){
                FloorTile t = this.game.getBoard().getTile(i,j);
                if (t.getIsOnFire() || t.getIsFrozen()){
                    int turns = t.getStatusTurnsRemaining();
                    t.setStatusTurnsRemaining(turns-1);
                }
            }
        }
    }

    /**
     * Event handler for next turn button
     * @param actionEvent
     * @throws IOException
     */
    public void nextTurnButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        System.out.println(turnProgression);
        if (turnProgression == -1){
            //Then the turn has not started, but the cards should be shown.
            startTurn();
            reduceStatusTurns();
            setCards(this.game.getTurn().getHand());
            nextTurnButton.setText("End Turn");
        } else if (turnProgression == 3) {
            //The turn has ended
            RotationImage.setRotate(0);
            this.turnProgression = -1;
            this.handIndex = -1;
            nextTurnButton.setText("Show Cards");
            System.out.println(this.game.getTurn().getProfile());
            this.game.nextTurn();
            System.out.println(this.game.getTurn().getProfile());
            hideCards();
        } else if(turnProgression != 0) {
            isAbleToMove(this.game.getTurn());
            if (turnProgression == 3) {
                nextTurnButtonAction(actionEvent);
            }
        }

        //reset image rotation
        RotationImage.setImage(null);
    }

    /**
     * Hides the cards at then end of the turn
     */
    public void hideCards() {
        Game.playMenuSound();
        baseBoarderPane.setBottom(null);
        nextTurnHBox = new HBox();
        Text nextPlayer = new Text(game.getTurn().getProfile() + "'s Turn");
        nextPlayer.setStyle("-fx-font-size: 32px; -fx-fill: white;");
        nextTurnHBox.setStyle("-fx-background-color: #303030;");
        nextTurnHBox.getChildren().add(nextPlayer);
        baseBoarderPane.setBottom(nextTurnHBox);
    }

    /**
     * Gets the game
     * @return the current game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Rotate the selected tile left 90 degrees
     * @param actionEvent the button click
     */
    public void leftRotateButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        if (turnProgression == 0) {
            FloorTile tempTile = (FloorTile) this.game.getTurn().getHand().get(this.handIndex);

            try {

                tempTile.setRotation((tempTile.getRotation() + 270) % 360);

            } catch (Exception e) {
                try {
                    tempTile.setRotation(0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            RotationImage.setRotate(tempTile.getRotation());
        }
    }

    /**
     * Rotate the tile right 90 degrees
     * @param actionEvent the button click
     */
    public void rightRotateButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        if (turnProgression == 0) {
            FloorTile tempTile = (FloorTile) this.game.getTurn().getHand().get(this.handIndex);

            try {

                tempTile.setRotation((tempTile.getRotation() + 90) % 360);

            } catch (Exception e) {
                try {
                    tempTile.setRotation(0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            RotationImage.setRotate(tempTile.getRotation());

        }
    }

    /**
     * Sets the hand to the current players hand
     * @param deck the current players hand
     */
    public void setCards(ArrayList<Tile> deck)  {
        baseBoarderPane.setBottom(null);
        tileHandHBox = new HBox();
        if (deck.size() == 0) {
            Image borderImage = new Image("invSlot.png", 64,64,true,true);
            tileHandHBox.getChildren().add(new ImageView(borderImage));
            baseBoarderPane.setBottom(tileHandHBox);
        } else {
            for(Tile card : deck) {
                Image cardImage = new Image(card.getImageLocation(), 40,40,true,true);
                Image borderImage = new Image("invSlot.png", 64,64,true,true);
                StackPane cardStack = new StackPane();
                cardStack.getChildren().addAll(new ImageView(borderImage), new ImageView(cardImage));
                cardStack.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handClicked((int) (cardStack.getBoundsInParent().getMinX())/64);
                });
                tileHandHBox.getChildren().add(cardStack);
                baseBoarderPane.setBottom(tileHandHBox);
            }
        }

    }

    /**
     * called when the user clicks the save game button. First plays the menu sound and then checks if the player is at
     * the end of their turn. If so it will load the save game option. If not it'll tell the user to finish their turn.
     * @param actionEvent
     */

    public void saveGameButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();

        if(turnProgression == -1) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
            Parent SaveMenuFXMLParent = null;
            try {
                SaveMenuFXMLParent = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene SaveMenuFXMLScene = new Scene(SaveMenuFXMLParent);
            Stage primaryStage = new Stage();
            // This line gets the stage the 'Play' button's action event came from
            primaryStage.setScene(SaveMenuFXMLScene);
            SaveMenuGUI controller = (SaveMenuGUI) loader.getController();
            controller.setGame(this.game);
            primaryStage.show();

        } else {

            playerTurnTag.setText("Please Finish your turn before you save");

        }

    }

    /**
     * Exits the user from the game and puts them on the main menu
     * @param actionEvent
     */

    public void exitButtonAction(ActionEvent actionEvent) {
        game.resetPlayers();
        Game.playMenuSound();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent mainMenuFXMLParent = null;
        try {
            mainMenuFXMLParent = (Parent)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage = (Stage) baseBoarderPane.getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        MainMenuGUI controller = (MainMenuGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();

    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class BoardGUI {
    @FXML public Button nextTurnBtn;
    @FXML private Canvas canvas;
    @FXML public BorderPane baseBoarderPane;
    @FXML public Label playerTurnTag;

    private int boardX;
    private int boardY;
    private String img;
    private String[] playerImages = {"head1.png","head2.png","head3.png","head4.png"};
    private Image[] statusEffects = {new Image("fireEffect.png"), new Image("iceEffect.png")};
    private Image fixedImage = new Image("F.png");
    private int turnProgression = 2;
    private int handIndex = -1;

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
        gc.setFill(Color.YELLOW);
        //draw selectable columns with triangle buttons
        for (int i = 0; i < (boardX); i++){
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
        for (int i = 0; i < (boardY); i++){
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
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        (event) -> {
                            drawCanvas();
                        })
        );
        boardX = 5;
        boardY = 5;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setOnMouseClicked(event -> {
            canvasClickEventHandler(event.getX(),event.getY());
            drawCanvas();
        });
    }

    public void setGame(Game game) throws IOException {
        this.game = game;
        this.boardX = game.getBoard().getWidth();
        this.boardY = game.getBoard().getLength();

        startTurn();
        //TODO: handle if current player can't move update the turn progression

        setCards(game.getTurn().getHand());
        //TODO: actually move this to somewhere good
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
                if (turnProgression == 0){
                    playerPushInTile();
                } if (turnProgression == 1){
                    playerPlayAction();
                } if (turnProgression == 2){
                    playerMove();
                }
                drawCanvas();
            }
        }
    }

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
                this.game.getBoard().pushInRow(t, (int)mouseY-1, forward);
            }
        } else if (mouseY == 0 || mouseY == boardY+1) {
            if (mouseX > 0 && mouseX < boardX+1) {
                this.game.getBoard().pushInColumn(t, (int)mouseX-1, forward);
            }
        }
        this.game.getTurn().getHand().remove(this.handIndex);
        this.turnProgression = 2;
    }

    public void playerPlayAction(){
        //if action tile can be played
        //get selected action tile
        ActionTile t = (ActionTile)this.game.getTurn().getHand().get(this.handIndex);
        //play selected tile at mouseX-1 and mouseY-1
        try {
            ActionTileFloor tile = (ActionTileFloor)t;
            tile.action(this.game.getBoard(), (int)mouseX, (int)mouseY);
        } catch (Exception e){
            //not a action floor tile
            if (t.getTILETYPE().equals("DoubleMoveTile")){
                DoubleMoveTile tile = (DoubleMoveTile)t;
                tile.action(this.game.getTurn());
            } else {
                BackTrackTile tile = (BackTrackTile)t;
                //TODO: implement this
            }
        }
        this.turnProgression = 2;
    }

    //used at the start of a players turn
    public void startTurn(){
        Player p = game.getTurn();
        Tile drawnTile = game.getBag().getRandomTile();
        p.addToHand(drawnTile);
        //System.out.println(p.getHand().size());
        try {
            ActionTile actionTile = (ActionTile)drawnTile;
            //choose to play it
            playerTurnTag.setText("Do you want to play the action Tile");
        } catch (Exception e){
            FloorTile floorTile = (FloorTile)drawnTile;
            //must play this now
            playerTurnTag.setText("You must place the floor Tile");
        }
    }

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
            this.turnProgression = 0;
        }
    }

    public void playerMove(){
        Player p = this.game.getTurn();
        int[] newPos = {(int) mouseX - 1, (int) mouseY - 1};
        if (mouseX != 0 && mouseX != boardX+1 && mouseY !=0 && mouseY != boardY+1)
        if(this.game.getBoard().isAccessibleFrom(p.getX(),p.getY(),newPos[0],newPos[1])) {
            p.movePlayer(newPos);
        }
    }

    public void nextTurnButtonAction(ActionEvent actionEvent) throws IOException {
        Text nextPlayer = new Text(game.getTurn().getProfile() + "'s Turn");
        nextPlayer.setStyle("-fx-font-size: 32px; -fx-fill: white;");
        nextTurnHBox.setStyle("-fx-background-color: #303030;");
        nextTurnHBox.getChildren().add(nextPlayer);
        baseBoarderPane.setBottom(nextTurnHBox);
    }

    public Game getGame() {
        return game;
    }

    public void leftRotateButtonAction(ActionEvent actionEvent) {

        //TODO: Connect to currently selected tile
        FloorTile tempTile = null;

        try {

            tempTile.setRotation(tempTile.getRotation() - 90);

        } catch (Exception e) {
            try {
                tempTile.setRotation(0);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public void rightRotateButtonAction(ActionEvent actionEvent) {

        FloorTile tempTile = null;
        //TODO: Connect to currently selected tile

        try {

            tempTile.setRotation(tempTile.getRotation() + 90);

        } catch (Exception e) {
            try {
                tempTile.setRotation(360);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public void setCards(ArrayList<Tile> deck)  {
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

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;

public class BoardGUI {
    @FXML private Canvas canvas;

    private int boardX;
    private int boardY;
    private String img;
    private String[] playerImages = {"head1.png","head2.png","head3.png","head4.png"};

    private Game game;

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
                        //gc.drawImage(fireEffect,(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
                    } else if (t.getIsFrozen()){
                        //gc.drawImage(iceEffect,(boxX*i)+xPad+2,(boxY*j)+yPad+2,boxX-4,boxY-4);
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
        for (int i = 1; i < (boardX-1); i++){
            //TODO: add check for is usable
            //add down facing triangle
            //check if selected
            if (i == mouseX-1 && 0 == mouseY){
                gc.setFill(Color.RED);
                gc.fillPolygon(new double[]{(boxX*i)+xPad,(boxX*i)+xPad+(boxX/2),(boxX*(i+1))+xPad},
                        new double[]{yPad-boxY,yPad,yPad-boxY}, 3);
                gc.setFill(Color.YELLOW);
            }
            gc.fillPolygon(new double[]{(boxX*i)+xPad+2,(boxX*i)+xPad+(boxX/2),(boxX*(i+1))+xPad-2},
                    new double[]{yPad-boxY+2,yPad-2,yPad-boxY+2}, 3);
            //add up facing triangle
            //check if selected
            if (i == mouseX-1 && boardY+1 == mouseY){
                gc.setFill(Color.RED);
                gc.fillPolygon(new double[]{(boxX*i)+xPad,(boxX*i)+xPad+(boxX/2),(boxX*(i+1))+xPad},
                        new double[]{(boxY*(boardY+2)),(boxY*(boardY+1)),(boxY*(boardY+2))}, 3);
                gc.setFill(Color.YELLOW);
            }
            gc.fillPolygon(new double[]{(boxX*i)+xPad+2,(boxX*i)+xPad+(boxX/2),(boxX*(i+1))+xPad-2},
                    new double[]{(boxY*(boardY+2))-2,(boxY*(boardY+1))+2,(boxY*(boardY+2))-2}, 3);
        }
        //draw selectable rows with triangles
        for (int i = 1; i < (boardY-1); i++){
            //TODO: add check for is usable
            //add right facing triangle
            //check if selected
            if (0 == mouseX && i == mouseY-1){
                gc.setFill(Color.RED);
                gc.fillPolygon(new double[]{xPad-boxX,xPad,xPad-boxX},
                        new double[]{(boxY*i)+yPad,(boxY*i)+yPad+(boxY/2),(boxY*(i+1))+yPad},3);
                gc.setFill(Color.YELLOW);
            }
            gc.fillPolygon(new double[]{xPad-boxX+2,xPad-2,xPad-boxX+2},
                    new double[]{(boxY*i)+yPad+2,(boxY*i)+yPad+(boxY/2),(boxY*(i+1))+yPad-2},3);
            //add right facing triangle#
            //check if selected
            if (boardX+1 == mouseX && i == mouseY-1){
                gc.setFill(Color.RED);
                gc.fillPolygon(new double[]{xPad+(boxX*(boardX+1)),xPad+(boxX*boardX),xPad+(boxX*(boardX+1))},
                        new double[]{(boxY*i)+yPad,(boxY*i)+yPad+(boxY/2),(boxY*(i+1))+yPad},3);
                gc.setFill(Color.YELLOW);
            }
            gc.fillPolygon(new double[]{xPad+(boxX*(boardX+1))-2,xPad+(boxX*boardX)+2,xPad+(boxX*(boardX+1))-2},
                    new double[]{(boxY*i)+yPad+2,(boxY*i)+yPad+(boxY/2),(boxY*(i+1))+yPad-2},3);
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
            //gc.fillRect((boxX*x)+xPad+((boxX/4)*(subx+1)),(boxY*y)+yPad+((boxY/4)*(suby+1)),boxX/4,boxY/4);
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

    public void addReferences(String image,int x,int y){
        img = image;
        boardX = x;
        boardY = y;
    }

    public void setGame(Game game){
        this.game = game;
        this.boardX = game.getBoard().getWidth();
        this.boardY = game.getBoard().getLength();
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
            }
        }
    }
}

/**
 * This class controls each individial player.
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
import java.util.ArrayList;
public class Player {


    /** Represents current position with X and Y
     */
    private int[][] CurrentPosition = new int[1][1];
    /** Contains x and y of the previous position */
    private int[] PreviousPosition = new int[2];
    /** Contains x and y of the second previous position */
    private int[] PreviousPosition2 = new int[2];
    /** Contains a hand of cards */
    private ArrayList<Tile> HandOfCards;
    /** Contains the current profile */
    private Profile currentProfile;
    /** Stores the current name */
    private String name;

    public Player(int x, int y, String name){
        CurrentPosition[0][0] = x;
        this.name = name;
    }

    public int[] getPreviousPosition() {
        return PreviousPosition;
    }

    public int[] getPreviousPosition2(){
        return PreviousPosition2;
    }

    public void setPreviousPosition(int x, int y) {
        PreviousPosition[0] = x;
        PreviousPosition[1] = y;
    }

    public void setPreviousPosition2(int x, int y) {
        PreviousPosition2[0] = x;
        PreviousPosition2[1] = y;
    }

}

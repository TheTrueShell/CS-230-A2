import java.util.ArrayList;

public class Player {
    /**
     * This class controls each individial player.
     * @author Rory Durrant, Joel Lawless
     * @version 0.0.1
     */

    /** Represents current position with X and Y
     */
    private int[][] CurrentPosition;
    /** Contains a list of previous positions */
    private int[][] PreviousPositions;
    /** Contains a hand of cards */
    private ArrayList<Tile> HandOfCards;
    /** Contains the current profile */
    private Profile currentProfile;

    public Player(int x, int y, String name){

    }

    public int[][] getPreviousPositions() {
        return PreviousPositions;
    }


}

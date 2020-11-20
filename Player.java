/**
 * This class controls each individial player.
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
import com.sun.xml.internal.bind.v2.TODO;

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
    //private String name;

    /**
     *
     * @param x - Represents the X coordinate of the player.
     * @param y - Represents the Y coordinate of the player
     * @param name - Represents the name of the player.
     */
    public Player(int x, int y, String name){
        CurrentPosition[0][0] = x;
        findCurrentProfile(name);
    }

    private Profile findCurrentProfile(String name){
        //TODO
        return null;
    }

    /**
     * @return - Returns previous position in a x=[0], y=[1] format.
     */
    public int[] getPreviousPosition() {
        return PreviousPosition;
    }

    /**
     * @return - Returns previous position in a x=[0], y=[1] format.
     */
    public int[] getPreviousPosition2(){
        return PreviousPosition2;
    }

    /**
     * Sets the position the player was last in.
     * @param x - Represents the X coordinate of the player.
     * @param y - Represents the Y coordinate of the player
     */
    public void setPreviousPosition(int x, int y) {
        PreviousPosition[0] = x;
        PreviousPosition[1] = y;
    }

    /**
     * Sets the position the player was in 2 turns ago.
     * @param x Represents the X coordinate of the player.
     * @param y - Represents the Y coordinate of the player
     */
    public void setPreviousPosition2(int x, int y) {
        PreviousPosition2[0] = x;
        PreviousPosition2[1] = y;
    }

    /**
     * @return - Returns an ArrayList which consists of the current hand.
     */
    public ArrayList<Tile> getHand(){
        return HandOfCards;
    }

    /**
     * Draws a random card from the bag.
     */
    public void drawCard(){
        //TODO - CHECK BAG FUNCTION
        HandOfCards.add(null);
    }
}

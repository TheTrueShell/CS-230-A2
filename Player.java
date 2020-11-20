/**
 * This class controls each individial player.
 *
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */

import java.util.ArrayList;

public class Player {


    /**
     * Represents current position with X and Y
     */
    private int[] CurrentPosition = new int[2];
    /**
     * Contains x and y of the previous position
     */
    private int[] PreviousPosition = new int[2];
    /**
     * Contains x and y of the second previous position
     */
    private int[] PreviousPosition2 = new int[2];
    /**
     * Contains a hand of cards
     */
    private ArrayList<Tile> HandOfCards;
    /**
     * Contains the current profile
     */
    private String currentProfile;
    /** Stores the current name */
    //private String name;

    /**
     * @param x       - Represents the X coordinate of the player.
     * @param y       - Represents the Y coordinate of the player
     * @param name - Represents the name of the profile the player is linked to.
     */
    public Player(int x, int y, String name) {
        CurrentPosition[0] = x;
        CurrentPosition[1] = y;
        currentProfile = name;
    }

    /**
     * @return - Returns current profile associated with this player.
     */
    public String getProfile() {
        return currentProfile;
    }

    /**
     * @return - Returns X position of player.
     */
    public int getX() {
        return CurrentPosition[0];
    }

    /**
     * @return - Returns Y position of player.
     */
    public int getY() {
        return CurrentPosition[1];
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
    public int[] getPreviousPosition2() {
        return PreviousPosition2;
    }

    /**
     * Sets the position the player was last in.
     *
     * @param x - Represents the X coordinate of the player.
     * @param y - Represents the Y coordinate of the player
     */
    public void setPreviousPosition(int x, int y) {
        PreviousPosition[0] = x;
        PreviousPosition[1] = y;
    }

    /**
     * Sets the position the player was in 2 turns ago.
     *
     * @param x Represents the X coordinate of the player.
     * @param y - Represents the Y coordinate of the player
     */
    public void setPreviousPosition2(int x, int y) {
        PreviousPosition2[0] = x;
        PreviousPosition2[1] = y;
    }

    public int getNumOfTiles() {
        return HandOfCards.size();
    }

    /**
     * @return - Returns an ArrayList which consists of the current hand.
     */
    public ArrayList<Tile> getHand() {
        return HandOfCards;
    }

    /**
     * Draws a random card from the bag.
     */
    public void addToHand(Tile t) {
        HandOfCards.add(t);
    }
}

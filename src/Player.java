import java.util.ArrayList;

/**
 * This class controls each individual player.
 *
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
public class Player {
    /**
     * Represents current position with X and Y
     */
    private int[] CurrentPosition = new int[2];
    /**
     * Contains x and y of the previous position
     */
    private int[] PreviousPosition ;
    /**
     * Contains x and y of the second previous position
     */
    private int[] PreviousPosition2;
    /**
     * Contains a hand of cards
     */
    private final ArrayList<Tile> HandOfCards = new ArrayList<>();
    /**
     * Contains the current profile
     */
    private final String currentProfile;

    /**
     * @param x    Represents the X coordinate of the player.
     * @param y    Represents the Y coordinate of the player
     * @param name Represents the name of the profile the player is linked to.
     */
    public Player(int x, int y, String name) {
        CurrentPosition[0] = x;
        CurrentPosition[1] = y;
        PreviousPosition = new int[] {x, y};
        PreviousPosition2 = new int[] {x, y};
        currentProfile = name;
    }

    /**
     * @return Returns current profile associated with this player.
     */
    public String getProfile() {
        return currentProfile;
    }

    /**
     * @param coords the coords of the player on the board.
     */
    public void movePlayer(int[] coords) {
        this.PreviousPosition2 = this.PreviousPosition;
        this.PreviousPosition = CurrentPosition;
        CurrentPosition = coords;
    }

    /**
     * @return Returns X position of player.
     */
    public int getX() {
        return CurrentPosition[0];
    }

    /**
     * @return Returns Y position of player.
     */
    public int getY() {
        return CurrentPosition[1];
    }

    /**
     * @return Returns previous position in a x=[0], y=[1] format.
     */
    public int[] getPreviousPosition() {
        return PreviousPosition;
    }

    /**
     * @return Returns previous position in a x=[0], y=[1] format.
     */
    public int[] getPreviousPosition2() {
        return PreviousPosition2;
    }

    /**
     * Sets the position the player was last in.
     *
     * @param x Represents the X coordinate of the player.
     * @param y Represents the Y coordinate of the player
     */
    public void setPreviousPosition(int x, int y) {
        PreviousPosition[0] = x;
        PreviousPosition[1] = y;
    }

    /**
     * Sets the position the player was in 2 turns ago.
     *
     * @param x Represents the X coordinate of the player.
     * @param y Represents the Y coordinate of the player
     */
    public void setPreviousPosition2(int x, int y) {
        PreviousPosition2[0] = x;
        PreviousPosition2[1] = y;
    }

    public int getNumOfTiles() {
        return HandOfCards.size();
    }

    /**
     * @return Returns an ArrayList which consists of the current hand.
     */
    public ArrayList<Tile> getHand() {
        return HandOfCards;
    }

    /**
     * Draws a random card from the bag.
     * @param t the tile to add
     */
    public void addToHand(Tile t) {
        HandOfCards.add(t);
    }

}

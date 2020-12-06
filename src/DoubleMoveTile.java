/**
 * DoubleMoveTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the DoubleMoveTile. This allows the player to perform a second action.
 */

public class DoubleMoveTile extends ActionTile {

    private static String imageLocation = "DoubleMove.png";

    /**
     * Constructor for doubleMoveTile. creates the object and sets the tileType to DoubleMoveTile
     */

    public DoubleMoveTile() {

        super.setTileType("DoubleMoveTile");

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}

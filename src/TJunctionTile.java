/**
 * ActionTile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class implements the ActionTile superclass.
 */

public class TJunctionTile extends FloorTile {

    private static final String imageLocation = "T-Junction.png";

    /**
     * Constructor for TJuctionTile. Inherits from FloorTile.java.
     * Sets the rotation and uses default values for Rest
     *
     * @param tileRotation the rotation of the tile
     * @throws Exception if setRotation fails
     */

    public TJunctionTile(int tileRotation) throws Exception {

        setTileType("TJunctionTile");

        boolean[] sides = {true, true, true, false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }

    /**
     * Getter for the Image location of the tile.
     *
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}
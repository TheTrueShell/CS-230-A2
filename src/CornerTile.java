/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class CornerTile extends FloorTile {

    private static final String imageLocation = "Corner.png";

    /**
     * Constructor for CornerTile, inherits from FloorTile.
     * Sets tile rotation and then uses default value for rest.
     * @param tileRotation the rotation of the tile
     * @throws Exception if setRotation errors
     */

    public CornerTile(int tileRotation) throws Exception {
        setTileType("CornerTile");
        boolean[] sides  = {true,true,false,false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);
        //setImageLocation("");

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }

}

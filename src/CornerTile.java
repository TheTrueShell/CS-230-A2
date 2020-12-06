/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class CornerTile extends FloorTile {

    private static String imageLocation = "Corner.png";

    /**
     * Constructor for CornerTile, inherits from FloorTile.
     * Sets tilerotation and then uses default value for rest.
     * @param tileRotation
     * @throws Exception
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
     * Constructor for CornerTile, inherits from FloorTile.
     * Sets tilerotation and ImageLocation, then uses default value for rest.
     * @param tileRotation
     * @throws Exception
     */

    public CornerTile (int tileRotation, String ImageLocation) throws Exception {

        boolean[] sides  = {true,true,false,false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);
        //setImageLocation(ImageLocation);

    }
    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }

}

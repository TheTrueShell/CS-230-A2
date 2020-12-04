/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class StraightTile extends FloorTile {

    private final String TILETYPE = "StraightTile";
    private static final String imageLocation = "Straight.png";

    /**
     * Constructor for StraightTile. Inherits most of implementation for FloorTile.java.
     * Sets the rotation and then uses default for rest of values
     * @param tileRotation
     * @throws Exception
     */

    public StraightTile(int tileRotation) throws Exception {
        boolean[] sides  = {true,false,true,false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }
    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}
/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class TJunctionTile extends FloorTile {

    private final String TILETYPE = "TJunctionTile";

    /**
     * Constructor for TJuctionTile. Inherits from FloorTile.java.
     * Sets the rotation and uses default values for Rest
     * @param tileRotation
     * @throws Exception
     */

    public TJunctionTile(int tileRotation) throws Exception {

        boolean[] sides  = {false,true,true,true};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }

}
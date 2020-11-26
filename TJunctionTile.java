/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class TJunctionTile extends FloorTile {

    private final String TILETYPE = "TJunctionTile";

    public TJunctionTile(int tileRotation) throws Exception {

        boolean[] sides  = {false,true,true,true};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }

}
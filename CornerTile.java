/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class CornerTile extends FloorTile {

    private final String TILETYPE = "CornerTile";

    public CornerTile(int tileRotation) throws Exception {
        boolean[] sides  = {true,true,false,false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);
        setImageLocation("");

    }

    public CornerTile (int tileRotation, String ImageLocation) throws Exception {

        boolean[] sides  = {true,true,false,false};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);
        setImageLocation(ImageLocation);

    }

}

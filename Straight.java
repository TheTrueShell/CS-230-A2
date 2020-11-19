/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class Straight extends FloorTile {

    public Straight(int tileRotation) throws Exception {
        boolean[] sides  = {true,false,false,true};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }

}
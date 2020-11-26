/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public class GoalTile extends FloorTile {

    private final String TILETYPE = "GoalTile";


    /**
     * Constructor for GoalTile. Inherits from FloorTile.
     * sets the rotation, and sets default values for rest.
     * @param tileRotation
     * @throws Exception
     */

    public GoalTile(int tileRotation) throws Exception {

        boolean[] sides  = {true,true,true,true};
        setAccessibleSides(sides);
        setRotation(tileRotation);
        setIsFrozen(false);
        setIsFrozen(false);
        setTileFixed(false);

    }

}
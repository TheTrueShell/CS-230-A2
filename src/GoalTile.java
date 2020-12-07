/**
 * ActionTile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class implements the ActionTile superclass.
 */

public class GoalTile extends FloorTile {

    private static final String imageLocation = "Goal.png";


    /**
     * Constructor for GoalTile. Inherits from FloorTile.
     * sets the rotation, and sets default values for rest.
     *
     * @param tileRotation
     * @throws Exception
     */

    public GoalTile(int tileRotation) throws Exception {

        setTileType("GoalTile");

        boolean[] sides = {true, true, true, true};
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
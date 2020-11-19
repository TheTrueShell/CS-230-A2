/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public abstract class GoalTile extends FloorTile {

    public GoalTile(int tileRotation) {
        super(tileRotation);
    }

    public abstract boolean hasPlayerOn();

}
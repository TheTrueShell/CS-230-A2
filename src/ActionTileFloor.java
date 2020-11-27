/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public abstract class ActionTileFloor extends ActionTile {

    public abstract void action(FloorTile tile) throws Exception;

}
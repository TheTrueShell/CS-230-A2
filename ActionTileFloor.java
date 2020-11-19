/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public abstract class ActionTileFloor extends ActionTile {

    private boolean isPlayable;
    public abstract void action(FloorTile tile) throws Exception;

    public boolean getIsPlayable(){

       return isPlayable;

    }

    public void setPlayable(boolean playable) {

        this.isPlayable = playable;

    }

}
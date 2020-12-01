/**
 * ActionTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the ActionTile superclass.
 */

public abstract class ActionTile extends Tile {

    private boolean isPlayable = false;
    private final String TILETYPE = "ActionTile";

    public boolean getIsPlayable(){

       return isPlayable;

    }

    public void setPlayable(boolean playable) {

        this.isPlayable = playable;

    }

    @Override
    public String toString() {

        return (getTILETYPE() + "\n" + getIsPlayable());

    }

}
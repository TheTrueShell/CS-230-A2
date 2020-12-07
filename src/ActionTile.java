/**
 * ActionTile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class implements the ActionTile superclass.
 */

public abstract class ActionTile extends Tile {

    private boolean isPlayable = false;

    /**
     * Returns the current state of isPlayable which dictates if the action can be played.
     *
     * @return boolean isPlayable
     */

    public boolean getIsPlayable() {

        return isPlayable;

    }

    /**
     * Sets the stat of isPlayable which dictates if the action can be played
     *
     * @param playable is it playable
     */

    public void setPlayable(boolean playable) {

        this.isPlayable = playable;

    }

    /**
     * Returns the string version of the action tile. first displaying the tile type then the isPlayable state.
     *
     * @return String tileType + newline + isPlayable
     */

    @Override
    public String toString() {

        return (getTILETYPE() + "\n" + getIsPlayable());

    }

}
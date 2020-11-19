/**
 * DoubleMoveTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the DoubleMoveTile. This allows the player to perform a second action.
 */

public abstract class DoubleMoveTile extends ActionTilePlayer {


    @Override
    public void action(Player player) {

        player.replay(); //Talk to others about how to implement on friday

    }

}

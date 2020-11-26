/**
 * BackTrackTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the BackTrack tiles. These tiles take the effected player back a turn.
 */

public class BackTrackTile extends ActionTile {

    private final String TILETYPE = "BackTrackTile";

    public void action(Player player) {

        player.movePlayer(player.getPreviousPosition2()); //May need try catch around it at somepoint

    }

}

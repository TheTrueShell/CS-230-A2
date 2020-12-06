/**
 * BackTrackTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the BackTrack tiles. These tiles take the effected player back a turn.
 */

public class BackTrackTile extends ActionTile {

    private static String imageLocation = "BackTrack.png";

    public BackTrackTile() {

        super.setTileType("BackTrackTile");

    }

    public void action(Player player) {

        player.movePlayer(player.getPreviousPosition2()); //May need try catch around it at somepoint

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}

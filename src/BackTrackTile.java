/**
 * BackTrackTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the BackTrack tiles. These tiles take the effected player back a turn.
 */

public class BackTrackTile extends ActionTile {

    private static String imageLocation = "BackTrack.png";

    /**
     * Constructor for BacktrackTile, setting the tiletype to BackTrackTile
     */

    public BackTrackTile() {

        super.setTileType("BackTrackTile");

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}

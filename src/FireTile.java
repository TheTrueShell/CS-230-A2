/**
 * FireTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the Firetile Class.This tile will cause a fire to appear wherever the user requests.
 */

public class FireTile extends ActionTileFloor {

    private final String TILETYPE = "FireTile";
    private static String imageLocation = "Fire.png";

    public FireTile() {

        super.setPlayable(false);

    }

    @Override
    public void action(FloorTile tile) throws Exception {

        if (super.getIsPlayable()) {
            tile.setIsOnFire(true);
            System.out.println("Set " + tile.toString() + " on fire");
        } else {

            throw new Exception("Cannot use this fire card yet");

        }

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}

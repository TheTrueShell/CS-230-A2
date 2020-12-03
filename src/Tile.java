/**
 * Tile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 *  This class is the superclass for all possible tiles.
 */
public abstract class Tile {

    protected int drawnOnTurn;
    private final String TILETYPE = "Tile";
    private static String imageLocation;

    public String getTILETYPE(){
        return TILETYPE;
    }

    /**
     * Setter for image Location of the tile
     * @param imageLocation
     */

    public void setImageLocation(String imageLocation) {

        this.imageLocation = imageLocation;

    }

    /**
     * Getter for the Image location of the tile.
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}
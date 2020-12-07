/**
 * Tile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class is the superclass for all possible tiles.
 */
public abstract class Tile {

    private static String imageLocation;
    protected int drawnOnTurn;
    private String tileType = "Tile";

    /**
     * sets the tile type to the inputted string
     *
     * @param tileType the type of tile
     */

    public void setTileType(String tileType) {

        this.tileType = tileType;

    }

    /**
     * returns the tileType var
     *
     * @return String tileType
     */

    public String getTILETYPE() {
        return tileType;
    }

    /**
     * Getter for the Image location of the tile.
     *
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }

    /**
     * Setter for image Location of the tile
     *
     * @param imageLocation the image's location
     */

    public void setImageLocation(String imageLocation) {

        Tile.imageLocation = imageLocation;

    }
}
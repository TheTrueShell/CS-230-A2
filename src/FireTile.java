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
    public void action(Board board, int x, int y) throws Exception {

        for( int i = -1; i < x + 1; i++) {

            for( int z = -1; z < y + 1; z++) {

                try {

                    board.getTile(i,z).setIsOnFire(true);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    System.out.println("Fire outside of array");

                }


            }

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

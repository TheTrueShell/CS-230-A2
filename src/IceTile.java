/**
 * IceTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the IceTile class. This tile freezes floor tiles
 */
public class IceTile extends ActionTileFloor {

    private final String TILETYPE = "IceTile";
    private static String imageLocation = "Ice.png";

    public IceTile() {

        super.setPlayable(false);

    }

    @Override
    public void action(Board board, int x, int y) throws Exception {

        for( int i = -1; i < x + 1; i++) {

            for( int z = -1; z < y + 1; z++) {

                try {

                    board.getTile(i,z).setIsFrozen(true);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    System.out.println("Ice outside of array");

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

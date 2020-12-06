/**
 * IceTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the IceTile class. This tile freezes floor tiles
 */
public class IceTile extends ActionTileFloor {

    private static String imageLocation = "Ice.png";

    public IceTile() {

        super.setPlayable(false);
        super.setTileType("IceTile");

    }

    @Override
    public void action(Board board, int x, int y, int numOfPlayers) throws Exception {

        for( int i = x-2; i < x + 1; i++) {

            for( int z = y-2; z < y + 1; z++) {

                try {

                    board.getTile(i,z).setIsFrozen(true);
                    //melts at the start of your next turn
                    board.getTile(i,z).setStatusTurnsRemaining(numOfPlayers);

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

/**
 * IceTile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class implements the IceTile class. This tile freezes floor tiles
 */
public class IceTile extends ActionTileFloor {

    private static final String imageLocation = "Ice.png";

    /**
     * Constructor for IceTile. Sets playable to false and sets the tile type to IceTile
     */

    public IceTile() {

        super.setPlayable(false);
        super.setTileType("IceTile");

    }

    /**
     * Recursively goes through the 3x3 grid setting the isFrozen to true for those tiles.
     *
     * @param board the board to effect
     * @param x the x coord of the ice action
     * @param y the y coord of the ice action
     * @param numOfPlayers the number of players it effects
     */

    @Override
    public void action(Board board, int x, int y, int numOfPlayers) {

        for (int i = x - 2; i < x + 1; i++) {

            for (int z = y - 2; z < y + 1; z++) {

                try {

                    board.getTile(i, z).setIsFrozen(true);
                    //melts at the start of your next turn
                    board.getTile(i, z).setStatusTurnsRemaining(numOfPlayers);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    System.out.println("Ice outside of array");

                }


            }

        }


    }

    /**
     * Getter for the Image location of the tile.
     *
     * @return String imageLocation
     */
    public String getImageLocation() {

        return imageLocation;

    }
}

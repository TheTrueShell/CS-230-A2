/**
 * FireTile.java
 *
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 * - no copyright
 * This class implements the Firetile Class.This tile will cause a fire to appear wherever the user requests.
 */

public class FireTile extends ActionTileFloor {

    private static final String imageLocation = "Fire.png";

    /**
     * FireTile constructor. Creates the object and sets the playable state to false and the tile type to "FireTile"
     */

    public FireTile() {

        super.setPlayable(false);
        super.setTileType("FireTile");

    }

    /**
     * makes the area effect on the board when the fire tile is placed
     *
     * @param board        board object
     * @param x            x coordinate to be placed onto
     * @param y            y coordinate to be placed onto
     * @param numOfPlayers
     * @throws Exception if the Fire is outside of the array
     */
    @Override
    public void action(Board board, int x, int y, int numOfPlayers)
            throws Exception {

        for (int i = x - 2; i < x + 1; i++) {

            for (int z = y - 2; z < y + 1; z++) {

                try {

                    board.getTile(i, z).setIsOnFire(true);
                    //goes out after the end of your next turn
                    board.getTile(i, z)
                            .setStatusTurnsRemaining(numOfPlayers + 2);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    System.out.println("Fire outside of array");

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

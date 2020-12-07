import java.util.ArrayList;
import java.util.Random;

/**
 * The bag class consists of the tiles that will be available to be played during the game.
 *
 * @author Aryan Dusi, Gus Rendle
 * @version 1.0
 */
public class Bag {
    private final ArrayList<Tile> tiles = new ArrayList<>();

    /**
     * Gets a random tile from the bag
     *
     * @return Random the Tile from the bag to be returned
     */
    public Tile getRandomTile() {
        int r = new Random().nextInt(tiles.size());
        return tiles.get(r);
    }

    /**
     * Adds a tile to the bag
     *
     * @param tile The tile to be added
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Gets all the tiles
     */
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }
}

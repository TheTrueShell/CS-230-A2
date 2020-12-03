/**
 * This class is used to model the Board which: consists of Tiles and is played on
 *
 * @author Aryan Dusi, Gus Rendle
 * @version 1.0
 */
public class Board {
    FloorTile[][] board;

    /**
     * Create a new Board with a given width and length
     *
     * @param width  width of Board to be made
     * @param length length of Board to be made
     */
    public Board(int width, int length) {
        this.board = new FloorTile[width][length];
    }

    /**
     * Return The length of the Board
     *
     * @return The length of the Board
     */
    public int getLength() {
        return board[0].length;
    }

    /**
     * Return the width of the Board
     *
     * @return The width of the Board
     */
    public int getWidth() {
        return board.length;
    }

    /**
     * Gets the tile at the specified location
     *
     * @param x x co-ord of the tile
     * @param y y co-ord of the tile
     * @return the tile at (x,y)
     */
    public FloorTile getTile(int x, int y) {
        return board[x][y];
    }

    /**
     * Inserts a tile onto the Board in a position specified by x and y
     *
     * @param tile The Tile that will be inserted onto the Board
     * @param x    The x coordinate for the Tile
     * @param y    The y coordinate for the Tile
     */
    public void insertTile(FloorTile tile, int x, int y) {
        board[x][y] = tile;
    }

}

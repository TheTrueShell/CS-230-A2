/**
 * This class is used to model the Board which: consists of Tiles and is played on
 *
 * @author Aryan Dusi, Gus Rendle
 * @version 1.0
 */
public class Board {
    Tile[][] board;
    private int playersInGame;
    private Player turn;

    /**
     * Create a new Board with a given width and length
     *
     * @param width  width of Board to be made
     * @param length length of Board to be made
     */
    public Board(int width, int length) {
        this.board = new Tile[width - 1][length - 1];
    }

    /**
     * Return The length of the Board
     *
     * @return The length of the Board
     */
    public int getLength() {
        return board[0].length + 1;
    }

    /**
     * Return the width of the Board
     *
     * @return The width of the Board
     */
    public int getWidth() {
        return board.length + 1;
    }

    /**
     * Inserts a tile onto the Board in a position specified by x and y
     *
     * @param tile The Tile that will be inserted onto the Board
     * @param x    The x coordinate for the Tile
     * @param y    The y coordinate for the Tile
     */
    public void insertTile(Tile tile, int x, int y) {
        board[x - 1][y - 1] = tile;
    }

}
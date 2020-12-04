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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int fixedTiles = 0;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getLength(); y++) {
                if (((FloorTile) board[x][y]).getIsTileFixed()) {
                    fixedTiles++;
                    sb.insert(0,x + "," + y + "," + board[x][y].toString() + "\n" );
                } else {
                    sb.append(x + "," + y + "," + board[x][y].toString() + "\n");
                }
            }
        }
        sb.insert(0,fixedTiles + "\n" );
        return sb.toString();
    }

    public boolean isAccessibleFrom(int currentX,int currentY,int newX,int newY){
        boolean[] currrentAccessibleSides = board[currentX][currentY].getAccessibleSides();
        boolean[] targetAccessibleSides = board[newX][newY].getAccessibleSides();
        //check that it is an adjacent tile
        if (currentX - newX > -2 && currentX - newX < 2){
            if (currentY - newY > -2 && currentY - newY < 2){
                //check that it isn't diagonal
                if (currentX == newX){
                    //if above
                    if (currentY - newY == -1){
                        if (currrentAccessibleSides[0] && targetAccessibleSides[2]) {
                            return true;
                        }
                    } else if (currrentAccessibleSides[2] && targetAccessibleSides[0]){
                        return true;
                    }
                }else if (currentY == newY){
                    //if to the left
                    if (currentX - newX == -1){
                        if (currrentAccessibleSides[3] && targetAccessibleSides[1]) {
                            return true;
                        }
                    } else if (currrentAccessibleSides[1] && targetAccessibleSides[3]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

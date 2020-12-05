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

    /**
     * Checks if a tile is accessible from another tile
     * @param currentX the starting tiles x
     * @param currentY the starting tiles y
     * @param newX the tile we want to get tos x
     * @param newY the tile we want to get tos y
     * @return
     */
    public boolean isAccessibleFrom(int currentX,int currentY,int newX,int newY){
        boolean[] currrentAccessibleSides = board[currentX][currentY].getAccessibleSides();
        boolean[] targetAccessibleSides = board[newX][newY].getAccessibleSides();
        if (board[newX][newY].getIsOnFire()){
            return false;
        }
        //check that it is an adjacent tile
        if (currentX - newX > -2 && currentX - newX < 2){
            if (currentY - newY > -2 && currentY - newY < 2){
                //check that it isn't diagonal
                if (currentX == newX){
                    //if above
                    if (currentY - newY == 1){
                        if (currrentAccessibleSides[0] && targetAccessibleSides[2]) {
                            return true;
                        }
                    } else if (currrentAccessibleSides[2] && targetAccessibleSides[0]){
                        return true;
                    }
                }else if (currentY == newY){
                    //if to the left
                    if (currentX - newX == 1){
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

    /**
     * Returns true if a tile can be pushed in on that row
     * @param index the row index
     * @return true if you can push in a tile
     */
    public boolean isRowPushable(int index)
    { for(int i=0;i<this.getLength();i++){
        if (board[i][index].getIsFrozen() == true || board[i][index].getIsTileFixed()==true) {
            return false;
        }
    }
        return true;
    }

    /**
     * Returns true if a tile can be pushed in on that column
     * @param index the column index
     * @return true if you can push in a tile
     */
    public boolean isColumnPushable(int index)
    { for (int i=0;i<this.getWidth();i++) {
        if (board[index][i].getIsFrozen() == true || board[index][i].getIsTileFixed() == true) {
            return false;
        }
    }
        return true;
    }

    /**
     * inserts a given floorTile onto the board in the position: x,y
     * @param tile tile to be inserted
     * @param index of tile to be inserted from the left
     * @param top determines whether the tile is inserted from the top or bottom
     */
    public FloorTile pushInColumn(FloorTile tile,int index, boolean top) {
        if (top) {
            for(int i=this.getLength()-1;i>0;i--)
            {
                FloorTile temp = board[index][i];
                board[index][i]=board[index][i-1];
                board[index][i-1] = temp;
            }
            FloorTile returnTile = board[index][0];
            this.insertTile(tile,index,0);
            return returnTile;
        } else {
            for(int i=0;i<this.getLength()-1;i++)
            {
                FloorTile temp = board[index][i];
                board[index][i]=board[index][i+1];
                board[index][i+1] = temp;
            }
            FloorTile returnTile = board[index][this.getLength()-1];
            this.insertTile(tile,index,this.getLength()-1);
            return returnTile;
        }
    }

    /**
     * inserts a given floorTile onto the board in the position: x,y
     * @param tile tile to be inserted
     * @param index of tile to be inserted from the left
     * @param left determines whether the tile is inserted form the left side or right side.
     */
    public FloorTile pushInRow(FloorTile tile,int index,boolean left)
    {
        if (left) {
            for(int i=this.getWidth()-1;i>0;i--)
            {
                FloorTile temp = board[i][index];
                board[i][index]=board[i-1][index];
                board[i-1][index] = temp;
            }
            FloorTile returnTile = board[0][index];
            this.insertTile(tile,0,index);
            return returnTile;
        } else {
            for(int i=0;i<this.getWidth()-1;i++)
            {
                FloorTile temp = board[i][index];
                board[i][index]=board[i+1][index];
                board[i+1][index] = temp;
            }
            FloorTile returnTile = board[this.getLength()-1][index];
            this.insertTile(tile,this.getLength()-1,index);
            return returnTile;
        }

    }
}






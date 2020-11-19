/**
 * FloorTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * - no copyright
 *  This class implements the FloorTile superclass and some basic methods.
 */

public abstract class FloorTile extends Tile {

    private boolean isTileFixed;
    private boolean isFrozen;
    private boolean isOnFire;
    private int tileRotation; //In 360 degrees
    private boolean[] acessibleSides = new boolean[4]; // Top. right, bottom, Left
    public abstract void play();

    public FloorTile(int tileRotation) throws Exception {

        try {
        this.setRotation(tileRotation);
        } catch (Exception ex) {

            throw new Exception(ex.getMessage());

        } 

    }

    public void setRotation(int rotation) throws Exception {

        if (rotation % 90 != 0) {

            throw new Exception("Cannot Rotate tile outside of 90 degree increments");

        } else if (rotation > 360 || rotation < 0) {

            throw new Exception("Cannot set rotate above 360 degrees or less than 0 degrees");

        } else {

            this.tileRotation = rotation;

        }
    }

}
/**
 * FloorTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * - no copyright
 *  This class implements the FloorTile superclass and some basic methods.
 */

public abstract class FloorTile extends Tile {

    protected boolean isTileFixed;
    protected boolean isFrozen;
    protected boolean isOnFire;
    protected int tileRotation; //In 360 degrees
    protected boolean[] accessibleSides; // Top. right, bottom, Left

    public boolean canBeMovedOnto() {

        if ( isTileFixed ) {

            return false;

        } else {

            return true;
        }

    }

    public void setTileFixed(boolean isTileFixed) {

        this.isTileFixed = isTileFixed;

    }


    public void setIsFrozen (boolean isFrozen) {

        this.isFrozen = isFrozen;

    }

    public void setIsOnFire (boolean isFire) {

        this.isOnFire = isFire;

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

    public boolean getIsFrozen() {

        return this.isFrozen;

    }

    public boolean getIsOnFire() {

        return this.isOnFire;

    }

    public int getRotation() {

        return this.tileRotation;

    }





}
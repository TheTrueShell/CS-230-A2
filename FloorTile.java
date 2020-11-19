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

    public FloorTile(int tileRotation, boolean isTileFixed) throws Exception {

        try {
        this.setRotation(tileRotation);
        } catch (Exception ex) {

            throw new Exception(ex.getMessage());

        }
        setTileFixed(isTileFixed);
        setIsFrozen(false);
        setIsOnFire(false);

    }

    public FloorTile(int tileRotation, boolean isTileFixed, boolean isFozen, boolean isOnFire) throws Exception {

        try {
            this.setRotation(tileRotation);
        } catch (Exception ex) {

            throw new Exception(ex.getMessage());

        }
        setTileFixed(isTileFixed);
        setIsFrozen(isFrozen);
        setIsOnFire(isOnFire);

    }

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
import java.awt.*;

/**
 * FloorTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * - no copyright
 *  This class implements the FloorTile superclass and some basic methods.
 */

public abstract class FloorTile extends Tile {

    private boolean isTileFixed; // Can the tile be moved from its current position.
    private boolean isFrozen;
    private boolean isOnFire;
    private int tileRotation; //In 360 degrees
    private boolean[] accessibleSides = new boolean[4]; // Top. right, bottom, Left
    private final String TILETYPE = "FloorTile";

    /**
     * Check if the current tile can have a player move onto it
     * @return boolean true if the tile can be moved onto, false if the tile cannot be moved onto
     */
    public boolean canBeMovedOnto() {

        if ( isTileFixed ) {

            return false;

        } else {

            return true;
        }

    }

    /**
     * Setter for the boolean determining whether or not the tile can be moved.
     * @param isTileFixed
     */

    public void setTileFixed(boolean isTileFixed) {

        this.isTileFixed = isTileFixed;

    }

    /**
     * Setter for the boolean determining whether or not the tile is frozen.
     * @param isFrozen
     */

    public void setIsFrozen (boolean isFrozen) {

        this.isFrozen = isFrozen;

    }

    /**
     *Setter for the boolean determining whether or not the tile is on fire.
     * @param isFire
     */

    public void setIsOnFire (boolean isFire) {

        this.isOnFire = isFire;

    }

    /**
     * Setter for the array of booleans determining which sides of the tile are accessible to the player
     * @param accessibleSides
     */

    public void setAccessibleSides(boolean[] accessibleSides) {

        this.accessibleSides = accessibleSides;

    }



    /**
     * Sets the rotation of the Tile. Checks that the rotation inputted is a multiple of 90 and is below 360.
     * @param rotation Takes a integer from 0 - 360 degrees
     * @throws Exception throws errors if input is not a multiple of 90 or is above 360 degrees
     */

    public void setRotation(int rotation) throws Exception {

        if (rotation % 90 != 0) {

            throw new Exception("Cannot Rotate tile outside of 90 degree increments");

        } else if (rotation > 360 || rotation < 0) {

            throw new Exception("Cannot set rotate above 360 degrees or less than 0 degrees");

        } else {

            this.tileRotation = rotation;
            int x = rotation / 90;
            for (int i = 0; i < x; i++) {
                setRotateAccessibleSides();
            }

        }
    }

    /**
     * Sets the rotation of the accessible sides to the player. As the tile rotates the entrances must also rotate
     */

    //As the model of the tile rotates so will the possible entrances and exits

    public void setRotateAccessibleSides() {

        for(int i = 0; i < accessibleSides.length; i++) {

           boolean[] accessibleSidestemp =  accessibleSides;

            if (accessibleSides[i] == true) {

                accessibleSidestemp[i] = false;

                // If we have reached the end of the array we still want to rotate the first spot.
                //The whole tile will rotate clockwise in increments of 90 degrees

                try { accessibleSidestemp[i+1] = true; }
                catch (ArrayIndexOutOfBoundsException ex) {

                    accessibleSidestemp[0] = true;

                }

            } else {

                accessibleSidestemp[i] = true;

                try { accessibleSidestemp[i+1] = false; }
                catch (ArrayIndexOutOfBoundsException ex) {

                    accessibleSidestemp[0] = false;

                }
            }

            accessibleSides = accessibleSidestemp;

        }

    }


    /**
     * Getter for the boolean array of accessible sides of the tile.
     * @return boolean array accessibleSlides.
     */

    public boolean[] getAccessibleSides() {

        return accessibleSides;

    }

    /**
     * Getter for the boolean determining if the tile is frozen or not.
     * @return boolean isFrozen
     */

    public boolean getIsFrozen() {

        return this.isFrozen;

    }

    /**
     * Getter for the boolean determining if the tile is on fire or not.
     * @return boolean IsOnFire
     */

    public boolean getIsOnFire() {

        return this.isOnFire;

    }

    /**
     * Getter for the integer determining the rotation of the tile.
     * @return Integer TileRotation
     */

    public int getRotation() {

        return this.tileRotation;

    }


    public String getTILETYPE() {

        return TILETYPE;

    }

    public boolean getIsTileFixed() {
        return isTileFixed;
    }
    /**
     * To String method for the Floor Tile classes
     * @return String FloorTile
     */

    @Override
    public String toString() {

        return getTILETYPE() + "," + getRotation() + "," + getIsOnFire() + "," + getIsFrozen();

    }

}
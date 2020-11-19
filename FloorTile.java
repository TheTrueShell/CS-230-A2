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

    public void setAcessibleSides(boolean[] acessibleSides) {

        this.acessibleSides = acessibleSides;

    }

    public boolean[] getAcessibleSides() {

        return this.acessibleSides;

    }

    public void setRotation(int rotation) throws Exception {

        if (rotation % 90 != 0) {

            throw new Exception("Cannot Rotate tile outside of 90 degree increments");

        } else if (rotation > 360 || rotation < 0) {

            throw new Exception("Cannot set rotate above 360 degrees or less than 0 degrees");

        } else {

            this.tileRotation = rotation;
            int x = rotation / 90;
            for (int i = 0; i < x; i++) {
                roateAcessibleSides();
            }

        }
    }

    //As the model of the tile rotates so will the possible enterances and exits

    public void roateAcessibleSides() {

        for(int i = 0; i < acessibleSides.length; i++) {

           boolean[] acessibleSidestemp =  acessibleSides;

            if (acessibleSides[i] == true) {

                acessibleSidestemp[i] = false;

                try { acessibleSidestemp[i+1] = true; }
                catch (ArrayIndexOutOfBoundsException ex) {

                    acessibleSidestemp[0] = true;

                }

            } else {

                acessibleSidestemp[i] = true;

                try { acessibleSidestemp[i+1] = false; }
                catch (ArrayIndexOutOfBoundsException ex) {

                    acessibleSidestemp[0] = false;

                }
            }

            acessibleSides = acessibleSidestemp;

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
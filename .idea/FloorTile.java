public abstract class FloorTile {

    private boolean isTileFixed;
    private boolean isFrozen;
    private boolean isOnFire;
    private int tileRotation; //In 360 degrees
    private boolean[] acessibleSides = new boolean[4]; // Top. right, bottom, Left

}
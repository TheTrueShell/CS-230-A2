/**
 * IceTile.java
 * @author William Aodan Telford and Deividas Prokopovicius
 * @version 0.1.0
 *  - no copyright
 *  This class implements the IceTile class. This tile freezes floor tiles
 */
public class IceTile extends ActionTile {

    @Override
    public void action(FloorTile tile) throws Exception {

        if (super.getIsPlayable()) {
            tile.setIsFrozen(true);
            System.out.println("Set " + tile.toString() + " to frozen");
        } else {

            throw new Exception("Cannot use this freeze card yet");

        }

    }


}

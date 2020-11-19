/**
 * This class controls the basiscs of the individual player profiles.
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
public class Profile {

    private String Name;
    private int GamesPlayed;
    private int GamesWon;
    private int GamesLost;

    /** */
    public Profile(String name){
        this.Name = name;
    }

    public int getGamesPlayed() {
        return GamesPlayed;
    }

    public int getGamesLost() {
        return GamesLost;
    }

    public int getGamesWon() {
        return GamesWon;
    }

    public String getName() {
        return Name;
    }
}

/**
 * This class controls the basics of the individual player profiles.
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
public class Profile {

    private String Name;

    private int GamesPlayed;

    private int GamesWon;

    private int GamesLost;

    /**
     * Create a new profile with the name given as parameter.
     * @param name the name of the profile
     *  */
    public Profile(String name){
        this.Name = name;
    }

    /**
     * returns each of the variables.
     */
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

    /**
     * Increments each of the variables by 1.
     */
    public void updateGamesPlayed() {GamesPlayed++ ;}

    public void updateGamesLost() {GamesLost++ ;}

    public void updateGamesWon() {GamesWon++ ;}


}

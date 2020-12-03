import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
/**
 * This class controls the basics of the individual player profiles.
 *
 * @author Rory Durrant, Joel Lawless
 * @version 0.0.1
 */
public class Profile {

    private final String Name;

    private int GamesPlayed;

    private int GamesWon;

    private int GamesLost;

    /**
     * Create a new profile with the name given as parameter.
     *
     * @param name the name of the profile
     */
    public Profile(String name) {
        this.Name = name;
    }

    /**
     * Create a profile and populate all attributes
     *
     * @param name        the name of the profile
     * @param gamesPlayed the number of games they have played
     * @param gamesWon    the number of games won
     * @param gamesLost   the number of games lost
     */
    public Profile(String name, int gamesPlayed, int gamesWon, int gamesLost) {
        this.Name = name;
        this.GamesPlayed = gamesPlayed;
        this.GamesWon = gamesWon;
        this.GamesLost = gamesLost;
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
    public void updateGamesPlayed() {
        GamesPlayed++;
    }

    public void updateGamesLost() {
        GamesLost++;
    }

    public void updateGamesWon() {
        GamesWon++;
    }

    /**
     * Decrements GamesPlayed
     */
    public void removeGamesPlayed() {
        GamesPlayed--;
    }

    /**
     * edit each of the variables
     */

    public void editGamesWon(int wins) {
        this.GamesWon = wins;
    }

    public void editGamesPlayed(int played) {
        this.GamesWon = played;
    }

    public void editGamesLost (int lost) {
        this.GamesWon = lost;
    }

    /**
     * prints out the profiles
     */

    public void listProfiles() {
        String filePath = "profiles.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            reader.lines().forEach(System.out::println);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}

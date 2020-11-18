import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is used to get the message of the day
 * @author Benjamin Rockley
 * @version 1.0
 */
public class MessageOfTheDay {

    private static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/puzzle";
    private static final String SOLUTION_URL = "http://cswebcat.swansea.ac.uk/message?solution=";

    /**
     * This is a helper function to get information from the server
     * @param uri The site that you want to perform a GET query to
     * @return The contents of the GET request
     * @throws Exception Server error
     */
    private static String querySite(String uri) throws Exception {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    /**
     * Decodes the puzzle into the solution
     * @param puzzle the puzzle that is returned from the server
     * @return the solution that is valid  for 60 seconds
     */
    private static String decode(String puzzle){
        StringBuilder shift = new StringBuilder(puzzle);
        //shift all character in string
        for (int i =0; i < puzzle.length(); i++){
            char character = puzzle.charAt(i);
            int value;
            if ((i % 2) == 0){
                value = (int) character + (25-i)- (int)'A';
            }else {
                value = (int) character + (i+1)- (int)'A';
            }
            character = (char) ((value % 26) + (int)'A');
            shift.setCharAt(i,character);
        }
        puzzle = "CS-230" + shift.toString();
        puzzle = puzzle + puzzle.length();
        return puzzle;
    }

    /**
     * This will get the message of the day from the server
     * @return The message of the day
     */
    public static String getMessage(){
        String puzzle;
        try {
            puzzle = querySite(PUZZLE_URL);
        } catch (Exception e) {
            return "ERROR: couldn't contact server";
        }
        String code = decode(puzzle);
        String result;
        try {
            result = querySite(SOLUTION_URL + code);
        } catch (Exception e) {
            result = "ERROR: server error";
        }

        return result;
    }

    public static void main(String[] args) throws Exception{
        String puzzle = querySite(PUZZLE_URL);
        String code = decode(puzzle);
        System.out.println(code);
        System.out.println(puzzle);
        System.out.println(getMessage());
    }
}
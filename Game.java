import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main entry point to the game.
 * It creates and runs the game.
 * @author Benjamin Rockley, Mohammed T
 * @version 0.1
 */
public class Game {
    //TODO:borad being played
    private Board board;
    //TODO:current profile avalible
    private ArrayList<Profile> profiles = new ArrayList<Profile>();


    //create board
    public void createBoard(int length, int width){
        this.board = new Board(length,width);
    }

    /**
     * Saves the current game to gameInProgrees.txt
     */
    public void saveBoard(){
        //TODO: save to file
        String filename = "gameInProgress.txt";
        try{
            File file = new File(filename);
            file.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        //write to the file
        try {
            FileWriter fileWriter = new FileWriter("profiles.txt");
            fileWriter.write(this.board.getLength()+","+this.board.getWidth()+"\n");
            fileWriter.write(this.board.getNumberOfFixedtiles()+"\n");
            //wrtie fixed tiles
            //TODO: getFixed tiles not specified but is a pain to do here
            for (Tile t : this.board.getFixedTiles()){
                //TODO: maybe move to FloorTile.toString() Method
                fileWriter.write(t.getX()+","+t.getY()+","+t.getTileType+","+t.getRotation()+","
                        +t.isOnFire()+","+t.isFrozen()+"\n");
            }
            //write non fixedTiles
            //TODO: check that this is implemented in the board class
            for (Tile t : this.board.getNonFixedTiles()){
                //TODO: maybe move to FloorTile.toString() Method
                fileWriter.write(t.getX()+","+t.getY()+","+t.getTileType+","+t.getRotation()+","
                        +t.isOnFire()+","+t.isFrozen()+"\n");
            }
            fileWriter.write(this.board.getPlayersInGame()+"\n");
            fileWriter.write(this.board.getTurn().getName());
            //x,y,profile,numoftilesinhand
            //write the players to the file
            for (Player p : this.board.getPlayers()){
                fileWriter.write(p.getX()+","+p.getY()+","+p.getProfile().getName()+","+p.getNumOfTiles()+"\n");
                for (Tile t : p.getHand()){
                    fileWriter.write(t.getTileType()+"\n");
                }
                //write previous positions
                fileWriter.write(p.getPreviousPosition()[0]+","+p.getPreviousPosition()[1]+"\n");
                fileWriter.write(p.getPreviousPosition2()[0]+","+p.getPreviousPosition2()[1]+"\n");
            }
            //TODO:Change this from a list of strings to a count of types
            //eg. numStraight,numCorner,numT,numGoal,numIce,numFire,numDouble,numBacktrack
            for (Tile t : this.board.getBag().getTiles()){
                fileWriter.write(t.getTileType()+"\n");
            }
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * load game in progress from file
     * @throws FileNotFoundException if board doesn't exist
     */
    public void loadBoard() throws FileNotFoundException {
        File myObj = new File("gameInProgress.txt");
        Scanner myReader = new Scanner(myObj);
        //TODO: handle if the file isn't thr correct length
        int boardX = myReader.nextInt();
        int boardY = myReader.nextInt()
        this.board = new Board(boardX,boardY);
        int fixedNum = myReader.nextInt();
        //load fixed tiles
        for (int i = 0; i < fixedNum; i++){
            int x = myReader.nextInt();
            int y = myReader.nextInt();
            Tile t = fromType(myReader.next());
            int r = myReader.nextInt();
            t.setIsOnFire(myReader.nextBoolean());
            t.setIsFrozen(myReader.nextBoolean());
            t.setIsFixed(true);
            //TODO:Check that this is correct format
            this.board.insertTile(t,x,y,r);
        }
        //load non fixed tiles
        for (int i = 0; i < ((boardX * boardY) - fixedNum); i++){
            int x = myReader.nextInt();
            int y = myReader.nextInt();
            Tile t = fromType(myReader.next());
            int r = myReader.nextInt();
            t.setIsOnFire(myReader.nextBoolean());
            t.setIsFrozen(myReader.nextBoolean());
            t.setIsFixed(false);
            //TODO:Check that this is correct format
            this.board.insertTile(t,x,y,r);
        }
        //load players
        int playersInGame = myReader.nextInt();
        String turnName = myReader.next();
        for (int i = 0; i < playersInGame; i++){
            int x = myReader.nextInt();
            int y = myReader.nextInt();
            String name = myReader.next();
            Player p = new Player(x,y,name);
            int numOfTiles = myReader.nextInt();
            for (int j = 0; j < numOfTiles; j++){
                Tile t = formType(myReader.next());
                //TODO: see how they implement this for it to work
                p.addToHand(t);
            }
            //TODO: again check implementation in player class
            player.setPreviousPosition(myReader.nextInt(),myReader.nextInt());
            player.setPreviousPosition2(myReader.nextInt(),myReader.nextInt());
        }
        //load bag
        //TODO: change if we change how this is stored
        while (myReader.hasNext()){
            Tile t = fromType(myReader.next());
            this.board.getBag.insertTile(t);
        }
        myReader.close();
    }

    //load from preset
    public void loadPreset(){
        //TODO: Discuss, should it be in board not game?
    }

    //delete board
    public void deleteBoard(){
        //TODO: delete game from player profiles, needs profile to exist
        //for (Player p : this.board.getPlayers()){
        //  p.setGamesPlayed(p.getgamesPlayer()-1);
        //}
        this.board = null;
    }

    //create profile
    public void createProfile(String name){
        //TODO: Waiting on profile to exist
        Profile profile = new Profile(name);
        this.profiles.add(profile);
        saveProfiles();
    }

    //remove profile
    public void removeProfile(Profile profile){
        profiles.remove(profile);
        saveProfiles();
    }

    /**
     * Save the profiles list to "profiles.txt"
     */
    public void saveProfiles(){
        //try to create file
        try{
            File file = new File("profiles.txt");
            file.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        //write to the file
        try {
            FileWriter fileWriter = new FileWriter("profiles.txt");
            for (Profile profile : this.profiles) {
                //TODO:Test if this works once class exists
                //should create a line in the form: name,gamesplayed,gamesWon,gamesLost
                fileWriter.write(profile.getName()+","+profile.getGamesPlayed()+","
                        +profile.getGamesWon()+","+profile.getGamesLost()+"\n");
            }
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * converts a string to a tile
     * @param type the type of tile you want
     * @return an instance of the tile class
     */
    private Tile fromType(String type){
        if (type.equals("ICE")){
            return new IceTile();
        }if (type.equals("FIRE")){
            return new FireTile();
        }if (type.equals("DOUBLEMOVE")){
            return new DoubleMoveTile();
        }if (type.equals("BACKTRACK")){
            return new BackTrackTile();
        }if (type.equals("CORNER")){
            return new CornerTile();
        }if (type.equals("TJUNCTION")){
            return new TJunctionTile();
        }if (type.equals("STRAIGTH")){
            return new StraightTile();
        }if (type.equals("GOAL")){
            return new GoalTile();
        }
    }
}

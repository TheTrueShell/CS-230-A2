import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
//GUI
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the main entry point to the game.
 * It creates and runs the game.
 * @author Benjamin Rockley, Mohammed T
 * @version 0.2
 */
public class Game extends Application {
    private Bag gameBag;

    // GUI
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuGUI.fxml"));
        primaryStage.setTitle("Labyrinth");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



    private Board board;
    private ArrayList<Profile> profiles = new ArrayList<Profile>();

    public static void main(String[] args){
        launch(args); //starts GUI
        //Game test = new Game();
        //test.createProfile("Test");
        //test.saveProfiles();
    }

    public Game(){
        loadProfiles();
    }

//    /**
//     * Saves the current game to gameInProgrees.txt
//     */
//    public void saveBoard(){
//        //TODO: save to file
//        String filename = "gameInProgress.txt";
//        try{
//            File file = new File(filename);
//            file.createNewFile();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        //write to the file
//        try {
//            FileWriter fileWriter = new FileWriter("profiles.txt");
//            fileWriter.write(this.board.getLength()+","+this.board.getWidth()+"\n");
//            fileWriter.write(this.board.getNumberOfFixedtiles()+"\n");
//            //wrtie fixed tiles
//            //TODO: getFixed tiles not specified but is a pain to do here
//            for (FloorTile t : this.board.getFixedTiles()){
//                //TODO: maybe move to FloorTile.toString() Method
//                //x and y are in board not
//                //get tile type needs to be  new method
//                int x = this.board.getTileX(t);
//                int y = this.board.getTileY(t);
//                fileWriter.write(x+","+y+","+t.getTileType+","+t.getRotation()+","
//                        +t.getIsOnFire()+","+t.getIsFrozen()+"\n");
//            }
//            //write non fixedTiles
//            //TODO: check that this is implemented in the board class
//            for (FloorTile t : this.board.getNonFixedTiles()){
//                //TODO: maybe move to FloorTile.toString() Method
//                int x = this.board.getTileX(t);
//                int y = this.board.getTileY(t);
//                fileWriter.write(x+","+y+","+t.getTileType+","+t.getRotation()+","
//                        +t.getIsOnFire()+","+t.getIsFrozen()+"\n");
//            }
//            fileWriter.write(this.board.getPlayersInGame()+"\n");
//            //TODO: get turn doesn't exist
//            fileWriter.write(this.board.getTurn().getName());
//            //x,y,profile,numoftilesinhand
//            //write the players to the file
//            for (Player p : this.board.getPlayers()){
//                fileWriter.write(p.getX()+","+p.getY()+","+p.getProfile().getName()+","+p.getNumOfTiles()+"\n");
//                for (Tile t : p.getHand()){
//                    fileWriter.write(t.getTileType()+"\n");
//                }
//                //write previous positions
//                fileWriter.write(p.getPreviousPosition()[0]+","+p.getPreviousPosition()[1]+"\n");
//                fileWriter.write(p.getPreviousPosition2()[0]+","+p.getPreviousPosition2()[1]+"\n");
//            }
//            //TODO:Change this from a list of strings to a count of types
//            //eg. numStraight,numCorner,numT,numGoal,numIce,numFire,numDouble,numBacktrack
//            //TODO: bag doesn't have get tiles method
//            for (Tile t : this.board.getBag().getTiles()){
//                //TODO: ask if they can add this feature
//                fileWriter.write(t.getTileType()+"\n");
//            }
//            fileWriter.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    /**
     * load game in progress from file
     * @throws FileNotFoundException if board doesn't exist
     */
    public void loadBoard() throws FileNotFoundException {
        //remove board if there
        deleteBoard();

        File myObj = new File("gameInProgress.txt");
        Scanner myReader = new Scanner(myObj);
        //TODO: handle if the file isn't thr correct length
        int boardX = myReader.nextInt();
        int boardY = myReader.nextInt();
        this.board = new Board(boardX,boardY);
        int fixedNum = myReader.nextInt();
        //load fixed tiles
        for (int i = 0; i < fixedNum; i++){
            int x = myReader.nextInt();
            int y = myReader.nextInt();
            String type = myReader.next();
            //rotation
            int r = myReader.nextInt();
            FloorTile t = floorFromType(type,r);
            t.setIsOnFire(myReader.nextBoolean());
            t.setIsFrozen(myReader.nextBoolean());
            t.setTileFixed(true);
            this.board.insertTile(t,x,y);
        }
        //load non fixed tiles
        for (int i = 0; i < ((boardX * boardY) - fixedNum); i++){
            int x = myReader.nextInt();
            int y = myReader.nextInt();
            String type = myReader.next();
            //rotation
            int r = myReader.nextInt();
            FloorTile t = floorFromType(type,r);
            t.setIsOnFire(myReader.nextBoolean());
            t.setIsFrozen(myReader.nextBoolean());
            t.setTileFixed(false);
            this.board.insertTile(t,x,y);
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
                //creates with roation 0 if floor tile
                Tile t = fromType(myReader.next());
                p.addToHand(t);
            }
            p.setPreviousPosition(myReader.nextInt(),myReader.nextInt());
            p.setPreviousPosition2(myReader.nextInt(),myReader.nextInt());
        }
        //load bag
        //TODO: change if we change how this is stored
        while (myReader.hasNext()){
            //issue exists as rotation is not guaranteed
            Tile t = fromType(myReader.next());
            //no get bag
            //this.board.getBag.insertTile(t);
        }
        myReader.close();
    }

    /**
     * Load a preset from file
     * @param preset the preset file name
     * @param players String of profile names
     * @throws Exception If preset doesn't exist
     */
    public void loadPreset(String preset,ArrayList<String> players) throws Exception{
        //TODO: Discuss, should it be in board not game?
        File presetFile = new File(preset);
        Scanner presetReader = new Scanner(presetFile);
        int width = presetReader.nextInt();
        int height = presetReader.nextInt();
        this.board = new Board(width,height);
        for (int i = 0; i < presetReader.nextInt(); i++){
            int x = presetReader.nextInt();
            int y = presetReader.nextInt();
            String type = presetReader.next();
            int rotation = presetReader.nextInt();
            FloorTile t = floorFromType(type,rotation);
            t.setIsOnFire(presetReader.nextBoolean());
            t.setIsFrozen(presetReader.nextBoolean());
            this.board.insertTile(t,x,y);
        }
        for (int i = 0; i < 4; i++){
            int x = presetReader.nextInt();
            int y = presetReader.nextInt();
            Player p = new Player(x,y,players.get(i));
            //TODO: board doesn't have players
            //board.addPlayer(p);
        }
    }

    /**
     * Deletes current game and updates the profiles to reflect this
     */
    public void deleteBoard(){
        //TODO: ask if they want to add game canceled
        //for (Player p : this.board.getPlayers()){
        //  p.getProfile().removeGamesPlayed();
        //}
        this.board = null;
    }

    /**
     * load profiles into class from file
     */
    private void loadProfiles(){
        File profileFile;
        boolean created = false;
        //try to create the file
        try{
            profileFile = new File("profiles.txt");
            created = profileFile.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (created){
            return;
        }
        profileFile = new File("profiles.txt");
        Scanner profileReader;
        try {
            profileReader = new Scanner(profileFile);
            while (profileReader.hasNext()){
                String name = profileReader.next();
                int gamesPlayed = profileReader.nextInt();
                int gamesWon = profileReader.nextInt();
                int gamesLost = profileReader.nextInt();
                profiles.add(new Profile(name,gamesPlayed,gamesWon,gamesLost));
            }
        } catch (FileNotFoundException e) {
            //this will never be reached as the file is always there
            e.printStackTrace();
        }
    }

    /**
     * Creates a new profile with name
     * @param name the name of the profile
     */
    public void createProfile(String name){
        Profile profile = new Profile(name);
        this.profiles.add(profile);
        saveProfiles();
    }

    /**
     * Removes a profile from the profile list then saves to save this change
     * @param profile the profile to be deleted
     */
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
            boolean success = file.createNewFile();
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
     * Creates a tile from a type with defaults values and no rotation
     * @param type the type of tile
     * @return new tile of type
     */
    private Tile fromType(String type){
        if (type.equals("ICE")){
            return new IceTile();
        }if (type.equals("FIRE")){
            return new FireTile();
        }
        if (type.equals("DOUBLEMOVE")){
        //abstacrt and can't be called
            return new DoubleMoveTile();
        }
        if (type.equals("BACKTARCK")){
            return new BackTrackTile();
        }if (type.equals("CORNER")){
            try {
                return new CornerTile(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("TJUNCTION")){
            try {
                return new TJunctionTile(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("STRAIGTH")){
            try {
                return new StraightTile(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //does goal tile need a roation?
        try {
            return new GoalTile(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * converts a string to a actionTile
     * @param type the type of tile you want
     * @return an instance of the tile class
     */
    private ActionTile actionFromType(String type){
        if (type.equals("ICE")){
            return new IceTile();
        }if (type.equals("FIRE")){
            return new FireTile();
        }
        if (type.equals("DOUBLEMOVE")){
            //abstacrt and can't be called
            return new DoubleMoveTile();
        }
        return new BackTrackTile();
    }

    /**
     * convert string into floorTile
     * @param type the tiles type
     * @param rotation the rotation of the tile in degrees must be in 90 degree increments
     * @return a new tile of type
     */
    private FloorTile floorFromType(String type, int rotation){
        //this should resolve any issues
        //should this be in the constructor?
        if (rotation % 90 != 0){
            rotation = 0;
        }
        if (type.equals("CORNER")){
            try {
                return new CornerTile(rotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("TJUNCTION")){
            try {
                return new TJunctionTile(rotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("STRAIGTH")){
            try {
                return new StraightTile(rotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //does goal tile need a roation?
        try {
            return new GoalTile(rotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //it will never reach here
        return null;
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
//GUI
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * This class is the main entry point to the game.
 * It creates and runs the game.
 * @author Benjamin Rockley, Mohammed T, Music Implementation William Aodan Telford
 * @version 1.0
 */
public class Game extends Application {
    private static final String PROFILES_PATH = "profiles.txt";
    private static final String MUSIC_FILE_PATH = "sounds/theme3.mp3";
    private static final File MENU_SOUND_FILE_PATH = new File("sounds/menuSound.mp3");
    private static final File FIRE_SOUND_FILE_PATH = new File("sounds/fireSound.mp3");
    private static final File ICE_SOUND_FILE_PATH = new File("sounds/iceSound.mp3");


    private Bag gameBag;
    private ArrayList<Player> players;
    private Board board;
    private Player turn;
    private ArrayList<Profile> profiles = new ArrayList<Profile>();
    private static double musicVolume = 0.1;
    private static double menuSoundVolume = 0.5;

    //Instantiating Sounds
    private static Media media = new Media(new File(MUSIC_FILE_PATH.toString()).toURI().toString());
    private static MediaPlayer mediaPlayer  = new MediaPlayer(media);;
    private static AudioClip menuSound = new AudioClip(Paths.get(MENU_SOUND_FILE_PATH.toString()).toUri().toString());


    // GUI
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent root = (Parent)loader.load();

        playMusic();
        primaryStage.setTitle("Labyrinth");
        primaryStage.setScene(new Scene(root, 600, 400));
        MainMenuGUI controller = (MainMenuGUI)loader.getController();
        controller.setGame(new Game());
        primaryStage.show();

    }

    /**
     * Reads the final music file path and creates a mediaplayer. Setting it to autoplay
     */

    public static void playMusic() {

        //Instantiating MediaPlayer class
        mediaPlayer.setVolume(musicVolume);
        mediaPlayer.setAutoPlay(true);

    }

    /**
     * Sets the music volume and then plays the background music
     */

    public static void playMenuSound() {

        //Instantiating MediaPlayer class
        menuSound.setVolume(menuSoundVolume);
        menuSound.play();

    }

    /**
     * Determines the action tile and then plays the associated sound.
     * @param tileType
     */

    public void playActionSound(String tileType) {

        if(tileType.equals("FireTile")) {

            AudioClip fire = new AudioClip(Paths.get(FIRE_SOUND_FILE_PATH.getPath()).toUri().toString());
            fire.setVolume(menuSoundVolume);
            fire.play();

        } else if(tileType.equals("IceTile")) {


            AudioClip ice = new AudioClip(Paths.get(ICE_SOUND_FILE_PATH.getPath()).toUri().toString());
            ice.setVolume(menuSoundVolume);
            ice.play();

        }

    }

    public static void main(String[] args){
        launch(args); //starts GUI
        //Game test = new Game();
        //test.createProfile("Test");
        //test.saveProfiles();
    }

    public Game(){
        loadProfiles();
        this.players = new ArrayList<Player>();
        this.gameBag = new Bag();
    }

    /**
     * Saves the current game to gameInProgrees.txt
     */
    public void saveBoard(String filePath){

        File file = null;

        try{
            file = new File(filePath);
            file.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        //write to the file
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.board.getLength()+","+this.board.getWidth()+"\n");
            fileWriter.write(this.board.toString());
            fileWriter.write(this.players.size()+"\n");
            fileWriter.write(this.turn.getProfile()+"\n");
            //x,y,profile,numoftilesinhand
            //write the players to the file
            for (Player p : this.players){
                fileWriter.write(p.getX()+","+p.getY()+","+p.getProfile()+","+p.getNumOfTiles()+"\n");
                for (Tile t : p.getHand()){
                    fileWriter.write(t.getTILETYPE()+"\n");
                }
                //write previous positions
                fileWriter.write(p.getPreviousPosition()[0]+","+p.getPreviousPosition()[1]+"\n");
                fileWriter.write(p.getPreviousPosition2()[0]+","+p.getPreviousPosition2()[1]+"\n");
            }
            for (Tile t : this.gameBag.getTiles()){
                fileWriter.write(t.getTILETYPE()+"\n");
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
    public void loadBoard(String filePath) throws FileNotFoundException {
        //remove board if there
        deleteBoard();

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);
        //TODO: handle if the file isn't the correct length
        Scanner lineReader = new Scanner(myReader.next()).useDelimiter(",");
        int boardX = lineReader.nextInt();
        int boardY = lineReader.nextInt();
        this.board = new Board(boardX,boardY);
        lineReader = new Scanner(myReader.next()).useDelimiter(",");
        int fixedNum = lineReader.nextInt();
        //load fixed tiles
        for (int i = 0; i < fixedNum; i++){
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            int x = lineReader.nextInt();
            int y = lineReader.nextInt();
            String type = lineReader.next();
            //rotation
            int r = lineReader.nextInt();
            FloorTile t = floorFromType(type,r);
            t.setIsOnFire(lineReader.nextBoolean());
            t.setIsFrozen(lineReader.nextBoolean());
            t.setTileFixed(true);
            this.board.insertTile(t,x,y);
        }
        //load non fixed tiles
        for (int i = 0; i < ((boardX * boardY) - fixedNum); i++){
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            int x = lineReader.nextInt();
            int y = lineReader.nextInt();
            String type = lineReader.next();
            //rotation
            int r = lineReader.nextInt();
            FloorTile t = floorFromType(type,r);
            t.setIsOnFire(lineReader.nextBoolean());
            t.setIsFrozen(lineReader.nextBoolean());
            t.setTileFixed(false);
            this.board.insertTile(t,x,y);
        }
        //clear players
        this.players = new ArrayList<Player>();
        //load players
        lineReader = new Scanner(myReader.next()).useDelimiter(",");
        int playersInGame = lineReader.nextInt();
        lineReader = new Scanner(myReader.next()).useDelimiter(",");
        String turnName = lineReader.next();
        for (int i = 0; i < playersInGame; i++){
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            int x = lineReader.nextInt();
            int y = lineReader.nextInt();
            String name = lineReader.next();
            Player p = new Player(x,y,name);
            int numOfTiles = lineReader.nextInt();
            for (int j = 0; j < numOfTiles; j++){
                lineReader = new Scanner(myReader.next()).useDelimiter(",");
                //creates with roation 0 if floor tile
                Tile t = fromType(lineReader.next());
                p.addToHand(t);
            }
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            p.setPreviousPosition(lineReader.nextInt(),lineReader.nextInt());
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            p.setPreviousPosition2(lineReader.nextInt(),lineReader.nextInt());
            this.players.add(p);
        }
        for (int i = 0; i < this.players.size(); i++){
            if (this.players.get(i).getProfile().equals(turnName)){
                this.turn = this.players.get(i);
            }
        }
        //load bag
        while (myReader.hasNext()){
            lineReader = new Scanner(myReader.next()).useDelimiter(",");
            //issue exists as rotation is not guaranteed
            Tile t = fromType(lineReader.next());
            this.gameBag.addTile(t);
        }
        myReader.close();
        lineReader.close();
    }

    /**
     * Load a preset from file
     * @param preset the preset file name
     * @throws Exception If preset doesn't exist
     */
    public void loadPreset(String preset) throws Exception{
        File presetFile = new File(preset);
        Scanner presetReader = new Scanner(presetFile);
        Scanner lineReader = new Scanner(presetReader.next()).useDelimiter(",");
        int width = lineReader.nextInt();
        int height = lineReader.nextInt();
        this.board = new Board(width,height);
        lineReader = new Scanner(presetReader.next()).useDelimiter(",");
        int numOfFixed = lineReader.nextInt();
        for (int i = 0; i < numOfFixed; i++){
            lineReader = new Scanner(presetReader.next()).useDelimiter(",");
            int x = lineReader.nextInt();
            int y = lineReader.nextInt();
            String type = lineReader.next();
            int rotation = lineReader.nextInt();
            FloorTile t = floorFromType(type,rotation);
            t.setIsOnFire(lineReader.nextBoolean());
            t.setIsFrozen(lineReader.nextBoolean());
            t.setTileFixed(true);
            this.board.insertTile(t,x,y);
        }
        for (int i = 0; i < 4; i++){
            lineReader = new Scanner(presetReader.next()).useDelimiter(",");
            int x = lineReader.nextInt();
            int y = lineReader.nextInt();
            if (i < this.players.size()) {
                Player p = new Player(x, y, this.players.get(i).getProfile());
                this.players.set(i,p);
            }
        }
        this.turn = this.players.get(0);
        this.gameBag = new Bag();
        lineReader = new Scanner(presetReader.next()).useDelimiter(",");
        //corner,straight,tjunction,ice,fire,double,back
        int cornerTiles = lineReader.nextInt();
        int straightTiles = lineReader.nextInt();
        int tjunctionTiles = lineReader.nextInt();
        int iceTiles = lineReader.nextInt();
        int fireTiles = lineReader.nextInt();
        int doublemoveTiles = lineReader.nextInt();
        int backtrackTiles = lineReader.nextInt();
        for (int i = 0; i < cornerTiles; i++){
            this.gameBag.addTile(new CornerTile(0));
        }
        for (int i = 0; i < straightTiles; i++){
            this.gameBag.addTile(new StraightTile(0));
        }
        for (int i = 0; i < tjunctionTiles; i++){
            this.gameBag.addTile(new TJunctionTile(0));
        }
        for (int i = 0; i < iceTiles; i++){
            this.gameBag.addTile(new IceTile());
        }
        for (int i = 0; i < fireTiles; i++){
            this.gameBag.addTile(new FireTile());
        }
        for (int i = 0; i < doublemoveTiles; i++){
            this.gameBag.addTile(new DoubleMoveTile());
        }
        for (int i = 0; i < backtrackTiles; i++){
            this.gameBag.addTile(new BackTrackTile());
        }
        //populate board this is the only added randomness to the board
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                if (this.board.getTile(i,j) == null){
                    FloorTile t = randomFloorTile();
                    this.board.insertTile(t,i,j);
                }
            }
        }
    }

    /**
     * Deletes current game and updates the profiles to reflect this
     */
    public void deleteBoard(){
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
            profileFile = new File(PROFILES_PATH);
            created = profileFile.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (created){
            return;
        }
        profileFile = new File(PROFILES_PATH);
        Scanner profileReader;
        try {
            profileReader = new Scanner(profileFile);
            while (profileReader.hasNext()){
                Scanner lineReader = new Scanner(profileReader.next()).useDelimiter(",");
                String name = lineReader.next();
                int gamesPlayed = lineReader.nextInt();
                int gamesWon = lineReader.nextInt();
                int gamesLost = lineReader.nextInt();
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
            File file = new File(PROFILES_PATH);
            boolean success = file.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        //write to the file
        try {
            FileWriter fileWriter = new FileWriter(PROFILES_PATH);
            for (Profile profile : this.profiles) {
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
     * Get the profiles
     */
    public ArrayList<Profile> getProfiles(){
        return this.profiles;
    }

    /**
     * Get the profile with the matching name
     * @param name
     */
    private Profile getProfile(String name){
        for (Profile p : this.profiles){
            if (p.getName().equals(name)){
                return p;
            }
        }
        //returns null if no profile matching it was found
        //should never get here
        return null;
    }

    /**
     * adds a new player with the profile name
     * @param profile
     */
    public void addPlayer(String profile){
        if (players.size() < 5) {
            this.players.add(new Player(0, 0, profile));
        }
    }

    /**
     * Remove a player from game
     * @param name the name of player profile
     */
    public void removePlayer(String name){
        for (int i = 0; i < this.players.size(); i++){
            if (this.players.get(i).getProfile().equals(name)){
                this.players.remove(i);
            }
        }
    }

    /**
     * Reset Players
     */
    public void resetPlayers(){
        this.players = new ArrayList<Player>();
    }

    /**
     * get all the players currently in game
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the current players turn
     */
    public Player getTurn(){
        return this.turn;
    }

    /**
     * Sets the turn to the next player
     */
    public void nextTurn() {
        int index = this.players.indexOf(this.turn);
        turn = this.players.get((index + 1) % this.players.size());
    }

    /**
     * gets the board
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * gets the bag
     */
    public Bag getBag(){
        return this.gameBag;
    }

    /**
     * Creates a tile from a type with defaults values and no rotation
     * @param type the type of tile
     * @return new tile of type
     */
    private Tile fromType(String type){
        if (type.equals("IceTile")){
            return new IceTile();
        }if (type.equals("FireTile")){
            return new FireTile();
        }
        if (type.equals("DoubleMoveTile")){
        //abstacrt and can't be called
            return new DoubleMoveTile();
        }
        if (type.equals("BackTrackTile")){
            return new BackTrackTile();
        }if (type.equals("CornerTile")){
            try {
                return new CornerTile(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("TJunctionTile")){
            try {
                return new TJunctionTile(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("StraightTile")){
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
        if (type.equals("CornerTile")){
            try {
                return new CornerTile(rotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("TJunctionTile")){
            try {
                return new TJunctionTile(rotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }if (type.equals("StraightTile")){
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

    /**
     * generates a random floor tile with random rotation
     * used for populating a new board
     * @return the floor tile
     */
    private FloorTile randomFloorTile(){
        int rotation = new Random().nextInt(3) * 90;
        int type = new Random().nextInt(3);
        FloorTile out;
        if (type == 0){
            try {
                out = new CornerTile(rotation);
                return out;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } if (type == 1){
            try {
                out = new StraightTile(rotation);
                return out;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                out = new TJunctionTile(rotation);
                return out;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //should never get here as we make sure to generate correct parameters
        return null;
    }

    /**
     * Sets the music's volume
     * @param newMusicVolume
     */
    public static void setMusicVolume(double newMusicVolume) {

        musicVolume = newMusicVolume;
        mediaPlayer.setVolume(musicVolume);

    }

    /**
     * Sets the sound effect volume
     * @param newMenuVolume
     */

    public static void setMenuSoundVolume(double newMenuVolume) {

        menuSoundVolume = newMenuVolume;
        menuSound.setVolume(menuSoundVolume);

    }

    /**
     * Gets the current music volume
     * @return
     */

    public static double getMusicVolume() {

        return musicVolume;

    }

    /**
     * Gets the current sound effect volume
     * @return
     */

    public static double getMenuSoundVolume() {

        return menuSoundVolume;

    }


}

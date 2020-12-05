import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author William Aodan Telford
 * @Version 0.1
 */

public class LoadGameGUI {

    private final String PATH_TO_SAVE_GAMES = "saves";

    @FXML
    private ListView gameList;
    @FXML
    private Label loadGameLabel;


    private Game game;
    private ArrayList<String> games;

    @FXML
    public void initialize(){

        //Getting the folder saves
        File directoryPath = new File(PATH_TO_SAVE_GAMES);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");


        for(File file : filesList) {

            gameList.getItems().add(file);

        }


    }



    public void setGame(Game game){
        this.game = game;
    }

    @FXML
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent mainMenuFXMLParent = (Parent)loader.load();
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        MainMenuGUI controller = (MainMenuGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    /**
     * Loads the selected Game
     * @param actionEvent
     */

    public void loadGameButtonAction(ActionEvent actionEvent) {

        File selectedGame = (File) gameList.getSelectionModel().getSelectedItem();
        System.out.println(selectedGame);
        boolean loadLevel = false;

        //TODO: Catch error when a file is selected that isn't in a save game format.

        if(selectedGame != null) {

            try {
                game.loadBoard(selectedGame.getPath());
                loadLevel = true;

            } catch (FileNotFoundException e) {
                loadGameLabel.setText("Invalid save game. Please choose another.");
            }
        } else {

            loadGameLabel.setText("Invalid save game. Please choose another.");

        }

        if(loadLevel) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
            Parent boardGUIFXMLParent = null;
            try {
                boardGUIFXMLParent = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene boardGUIFXMLScene = new Scene(boardGUIFXMLParent);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // This line gets the stage the 'Play' button's action event came from
            primaryStage.setScene(boardGUIFXMLScene);
            BoardGUI controller = (BoardGUI) loader.getController();
            controller.setGame(this.game);
            primaryStage.show();

        }

    }
}

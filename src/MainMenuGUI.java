import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main Menu Scene's controller.
 *
 * @author Gus Rendle
 * @version 1.1
 */
public class MainMenuGUI {
    @FXML
    private Label messageOfDay;
    private Game game;

    /**
     * load the message of the day
     */
    @FXML
    public void initialize() {
        messageOfDay.setText(MessageOfTheDay.getMessage());
    }

    /**
     * The method for when the 'Play' button is clicked.
     * It switches to the BoardGUI Scene
     *
     * @param actionEvent the event of the 'Play' button being pressed
     * @throws IOException if the .fxml file cannot be found
     */
    public void playButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Parent boardGUIParent = loader.load();
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        GameMenuGUI controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    /**
     * Called when the profiles button is pushed. Loads ProfileMenu.fxml
     *
     * @param actionEvent the action event of the object
     * @throws IOException if it cannot find the fxml file
     */

    public void profilesButtonAction(ActionEvent actionEvent)
            throws IOException {
        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("ProfileMenu.fxml"));
        Parent boardGUIParent = loader.load();
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        ProfileMenu controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    /**
     * Called when the settings button is pushed. Loads SettingsMenu.fxml
     *
     * @param actionEvent the action event of the object
     * @throws IOException if it cannot find the fxml file
     */

    public void settingsButtonAction(ActionEvent actionEvent)
            throws IOException {
        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        Parent boardGUIParent = loader.load();
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        SettingMenuGUI controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    /**
     * Called when the quit button is pushed. exits the game.
     *
     * @param actionEvent the action event of the object
     */

    public void quitButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        System.exit(0);
    }

    /**
     * Set the game to the passed one
     *
     * @param game the game instance
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public void leaderBoardButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();
        LeaderBoardGUI.setGame(game);
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("LeaderBoard.fxml"));
        Parent leaderBoardFXMLParent = null;
        try {
            leaderBoardFXMLParent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene leaderBoardFXMLScene = new Scene(leaderBoardFXMLParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(leaderBoardFXMLScene);
        LeaderBoardGUI controller = loader.getController();
        LeaderBoardGUI.setGame(this.game);
        primaryStage.show();


    }
}
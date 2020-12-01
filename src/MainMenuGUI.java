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
 * @author Gus Rendle
 * @version 1.1
 */
public class MainMenuGUI {
    @FXML
    private Label messageOfDay;

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
     * @param actionEvent  the event of the 'Play' button being pressed
     * @throws IOException if the .fxml file cannot be found
     */
    public void playButtonAction(ActionEvent actionEvent) throws IOException {
        Parent boardGUIParent = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        primaryStage.show();
    }

    public void profilesButtonAction(ActionEvent actionEvent) throws IOException {
        Parent boardGUIParent = FXMLLoader.load(getClass().getResource("ProfileMenu.fxml"));
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        primaryStage.show();
    }

    public void settingsButtonAction(ActionEvent actionEvent) throws IOException {
        Parent boardGUIParent = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));
        Scene boardGUIScene = new Scene(boardGUIParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIScene);
        primaryStage.show();
    }

    public void quitButtonAction(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuGUI {

    private Game game;

    /**
     * Runs when the user clicks new Game. It loads the javafx for the new Game menu
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void newGameButtonAction(ActionEvent actionEvent)
            throws IOException {
        Game.playMenuSound();
        Board newGame = new Board(10, 10);
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("CreateGameMenu.fxml"));
        Parent mainMenuFXMLParent = loader.load();
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        CreateGameMenuGUI controller =
                loader.getController();
        controller.setGame(this.game);
        primaryStage.show();


    }

    /**
     * Runs When the load game is clicked. It loads the javafx for loading game menu
     *
     * @param actionEvent
     * @throws IOException
     */

    @FXML
    public void loadGameButtonAction(ActionEvent actionEvent)
            throws IOException {

        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("LoadGame.fxml"));
        Parent selectMenuFXMLParent = loader.load();
        Scene selectMenuFXMLScene = new Scene(selectMenuFXMLParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(selectMenuFXMLScene);
        LoadGameGUI controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();

    }

    /**
     * Runs when the back button is clicked. loads the javafx for the main menu
     *
     * @param actionEvent
     * @throws IOException
     */

    @FXML
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent mainMenuFXMLParent = loader.load();
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        MainMenuGUI controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    public void setGame(Game game) {
        this.game = game;
    }

}

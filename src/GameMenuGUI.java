import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuGUI {
    private Game game;
    @FXML public AnchorPane mainMenuPanel;

    @FXML
    public void newGameButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        Board newGame = new Board(10,10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateGameMenu.fxml"));
        Parent mainMenuFXMLParent = (Parent)loader.load();
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        CreateGameMenuGUI controller = (CreateGameMenuGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();


    }

    @FXML
    public void loadGameButtonAction(ActionEvent actionEvent) throws IOException {

        Game.playMenuSound();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent selectMenuFXMLParent = (Parent)loader.load();
        Scene selectMenuFXMLScene = new Scene(selectMenuFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(selectMenuFXMLScene);
        SettingMenuGUI controller = (SettingMenuGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();

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

    public void setGame(Game game){
        this.game = game;
    }

}

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
    @FXML public AnchorPane mainMenuPanel;

    @FXML
    public void newGameButtonAction(ActionEvent actionEvent) throws IOException {
        Board newGame = new Board(10,10);
    }

    @FXML
    public void loadGameButtonAction(ActionEvent actionEvent) throws IOException {

    }

    @FXML
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Parent mainMenuFXMLParent = FXMLLoader.load(getClass().getResource("MainMenuGUI.fxml"));
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        primaryStage.show();
    }

}

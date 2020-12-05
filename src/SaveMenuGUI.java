import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author William Aodan Telford
 * @version 0.2
 */

public class SaveMenuGUI {
    private Game game;

    @FXML
    TextField saveFile;

    public void saveButtonAction(ActionEvent actionEvent) {

        game.saveBoard("saves/" + saveFile.getText());

    }

    /**
     * Loads the board back for the user.
     * @param actionEvent
     */

    public void backButtonAction(ActionEvent actionEvent) {


        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    public void setGame(Game game){
        this.game = game;
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author William Aodan Telford
 * @version 0.2
 */

public class SaveMenuGUI {
    private static final String FILE_SAVED_TEXT = "The game was saved";
    @FXML
    TextField saveFile;
    @FXML
    Label saveGameLabel;
    private Game game;

    /**
     * Called when save button is pushed. Saves the file using the string given in the input box.
     *
     * @param actionEvent
     */

    public void saveButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();

        game.saveBoard("saves/" + saveFile.getText() + ".txt");
        saveGameLabel.setText(FILE_SAVED_TEXT);

    }

    /**
     * Loads the board back for the user.
     *
     * @param actionEvent
     */

    public void backButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();

        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();

    }

    /**
     * Sets the Game to allow the saveBoard implementation.
     *
     * @param game
     */

    public void setGame(Game game) {
        this.game = game;
    }
}

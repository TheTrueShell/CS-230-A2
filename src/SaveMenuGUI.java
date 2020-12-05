import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveMenuGUI {
    private Game game;

    public void saveButtonAction(ActionEvent actionEvent) {



    }

    public void backButtonAction(ActionEvent actionEvent) {

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

    public void setGame(Game game){
        this.game = game;
    }
}

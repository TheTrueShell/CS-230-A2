import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainMenuGUI {
    @FXML public BorderPane mainMenuPanel;

    @FXML
    public void playButtonAction(ActionEvent actionEvent) throws IOException {
        BorderPane BoardPanel = FXMLLoader.load(getClass().getResource("BoardGUI.fxml"));
        mainMenuPanel.getChildren().setAll(BoardPanel);
    }
}

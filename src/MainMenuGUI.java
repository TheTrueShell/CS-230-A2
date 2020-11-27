import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainMenuGUI {
    public AnchorPane mainMenuPanel;
    public Button openBoardBtn;

    public void openBoard(ActionEvent actionEvent) throws IOException {
        AnchorPane BoardPanel = FXMLLoader.load(getClass().getResource("BoardGUI.fxml"));
        mainMenuPanel.getChildren().setAll(BoardPanel);
    }
}

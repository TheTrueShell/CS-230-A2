import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BoardGUI {
    @FXML
    private Label header;
    @FXML
    public void initialize(){
        header.setText("Text generated at runtime");
    }
}

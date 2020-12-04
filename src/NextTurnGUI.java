import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NextTurnGUI {
    public Text nextTurnText;


    public void setNextTurnText(String txt) {
        nextTurnText.setText(txt);
    }

}

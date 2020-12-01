import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfileMenu {
    private Game game;

    @FXML
    public void initialize(){
        //get profiles
    }

    public void setGame(Game game){
        this.game = game;
    }
}

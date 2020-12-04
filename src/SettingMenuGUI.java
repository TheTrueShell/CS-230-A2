import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingMenuGUI {

    @FXML
    Slider musicSlider;

    private Game game;

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

    public void volumeButtonAction(ActionEvent actionEvent) {

        double musicVolume = (musicSlider.getValue() / 100);
        Game.setMusicVolume(musicVolume);

    }
}

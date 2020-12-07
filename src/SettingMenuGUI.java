import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @Author William Aodan Telford
 * Version 1.0
 */

public class SettingMenuGUI {

    @FXML
    Slider musicSlider;
    @FXML
    Slider soundsSlider;

    private Game game;

    /**
     * Initializes the sliders to make sure they are in the same place as the music volume
     */

    @FXML
    public void initialize() {

        musicSlider.setValue(Game.getMusicVolume() * 100);
        soundsSlider.setValue(Game.getMenuSoundVolume() * 100);

    }

    /**
     * Called when the back button is pressed. loads the main menu
     *
     * @param actionEvent the action event of the object
     * @throws IOException if it cannot find the fxml file
     */

    @FXML
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Game.playMenuSound();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent mainMenuFXMLParent = loader.load();
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage =
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        MainMenuGUI controller = loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }

    /**
     * Sets the game
     *
     * @param game the game to set
     */

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Called when the Volume button is pressed. Sets the music volume to the value of the slider
     *
     * @param actionEvent the action event of the object
     */

    public void volumeButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();

        double musicVolume = (musicSlider.getValue() / 100);
        Game.setMusicVolume(musicVolume);

    }

    /**
     * Called when the sounds effect button is pressed. Sets the sounds volume to the value of the slider.
     *
     * @param actionEvent the action event of the object
     */

    public void menuVolumeButtonAction(ActionEvent actionEvent) {

        Game.playMenuSound();
        double menuVolume = (soundsSlider.getValue() / 100);
        Game.setMenuSoundVolume(menuVolume);

    }
}

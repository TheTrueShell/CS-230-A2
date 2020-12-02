import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Main Menu Scene's controller.
 * @author Gus Rendle
 * @version 1.1
 */
public class CreateGameMenuGUI {
    @FXML

    private Game game;
    private ArrayList<Profile> Profiles;
    private ArrayList<String> presets;
    @FXML
    private ChoiceBox playerOneProfile;
    @FXML
    private ChoiceBox mapPreset;

    @FXML
    public void initialize(){
        File dir = new File("presets");
        this.presets = new ArrayList<String>(Arrays.asList(dir.list()));
        List<String> presetList = this.presets.stream()
                .map(s -> s.replaceFirst("^preset_","").replace(".txt",""))
                .collect(Collectors.toList());
        ObservableList<String> observablePresetList = FXCollections.observableArrayList(presetList);
        mapPreset.setItems(observablePresetList);
    }

    public void profilesActionMenu(ActionEvent actionEvent) throws IOException {



    }

    @FXML
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
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



    /**
     * Set the game to the passed one
     * @param game the game instance
     */
    public void setGame(Game game){
        this.game = game;
        Profiles = game.getProfiles();
        ObservableList<String> names = FXCollections.observableArrayList();

        for(Profile p : Profiles ) {

            names.add(p.getName());

        }

        System.out.println(Profiles.size());
        playerOneProfile.setItems(names);

    }
}

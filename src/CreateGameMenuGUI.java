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

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Create Game Menu Scene's controller.
 * @author William Aodan Telford
 * @version 1.1
 */
public class CreateGameMenuGUI {
    @FXML

    private Game game;
    private ArrayList<Profile> Profiles;
    @FXML
    private ChoiceBox playerOneProfile;
    @FXML
    private ChoiceBox playerTwoProfile;
    @FXML
    private ChoiceBox playerThreeProfile;
    @FXML
    private ChoiceBox playerFourProfile;

    private ObservableList<String> names = FXCollections.observableArrayList();


    public void profilesActionMenuOne(ActionEvent actionEvent) throws IOException {

        playerOneProfile.getValue();

    }

    public void profilesActionMenuTwo(ActionEvent actionEvent) throws IOException {



    }

    public void profilesActionMenuThree(ActionEvent actionEvent) throws IOException {



    }

    public void profilesActionMenuFour(ActionEvent actionEvent) throws IOException {



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

        for(Profile p : Profiles ) {

            names.add(p.getName());

        }

        names.add("No Player");

        addNames(names);
        updateNames();

    }


    public void addNames(ObservableList<String> newNames) {

        this.names = newNames;

    }

    public void updateNames() {

        playerOneProfile.setItems(this.names);
        playerTwoProfile.setItems(this.names);
        playerThreeProfile.setItems(this.names);
        playerFourProfile.setItems(this.names);

    }


}

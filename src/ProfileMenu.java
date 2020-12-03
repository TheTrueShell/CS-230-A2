import com.sun.xml.internal.ws.api.server.EndpointReferenceExtensionContributor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;

import javax.swing.*;
import java.io.IOException;
import java.util.Observable;

public class ProfileMenu {
    private Game game;

    private boolean deleteClicked;
    private boolean addClicked;
    private boolean editClicked;


    @FXML
    private Button Ok;

    @FXML
    private TextField Input;

    @FXML
    private Label Label;

    @FXML
    private Label Label2;

    @FXML
    private TextField Input2;

    @FXML
    private ListView profilesList;

    @FXML
    public void initialize(){
        //get profiles
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

    @FXML
    public void addButtonAction(ActionEvent actionEvent) throws IOException {

        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(false);
        Input2.setVisible(false);

        addClicked = true;
        deleteClicked = false;
        editClicked = false;
    }

    @FXML
    public void editButtonAction(ActionEvent actionEvent) throws IOException {

        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(true);
        Input2.setVisible(true);

        editClicked = true;
        addClicked = false;
        deleteClicked = false;
    }

    @FXML
    public void deleteButtonAction(ActionEvent actionEvent) throws IOException {
        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(false);
        Input2.setVisible(false);

        deleteClicked = true;
        addClicked = false;
        editClicked = false;
    }

    @FXML
    public void okButtonAction(ActionEvent actionEvent) throws IOException {
        if (addClicked == true) {
            String name = Input.getText();
            game.createProfile(name);
        }

        if (deleteClicked == true) {
            String deletedName = Input.getText();
            Profile p = null;
            for (Profile profile :game.getProfiles()) {
                if(profile.getName().equals(deletedName)) {
                    p  = profile;
                    break;
                }
            }
            game.removeProfile(p);
        }


        if(editClicked == true) {
            String currentName = Input.getText();
            String newName = Input2.getText();
            Profile p = null;
            for (Profile profile: game.getProfiles()) {
                if(profile.getName().equals(currentName)) {
                    p = profile;
                    break;
                }

            }
            p.setName(newName);
            game.saveProfiles();
        }



        Ok.setVisible(false);
        Input.setVisible(false);
        Label.setVisible(false);
        Label2.setVisible(false);
        Input2.setVisible(false);

        deleteClicked = false;
        addClicked = false;
        editClicked = false;
    }

    @FXML
    public void setGame(Game game){
        this.game = game;
        for (Profile p : game.getProfiles()){
            String profileData = p.getName() + " W:" + p.getGamesWon() + " L:" + p.getGamesLost();
            profilesList.getItems().add(profileData);
        }

        Ok.setVisible(false);
        Input.setVisible(false);
        Label.setVisible(false);
        Label2.setVisible(false);
        Input2.setVisible(false);
    }
}

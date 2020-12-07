import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is a menu screen is used to manage profiles
 *
 * @author Aryan Dusi
 * @version 0.0.1
 */
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

    /**
     * Used to go back to main menu of the game
     *
     * @param actionEvent event of the back button pressed
     * @throws IOException if the mainMenu fxml can't be found
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
     * When the add profile button is clicked
     *
     * @param actionEvent event of the add button being clicked
     */
    @FXML
    public void addButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();

        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(false);
        Input2.setVisible(false);

        addClicked = true;
        deleteClicked = false;
        editClicked = false;
    }

    /**
     * When the edit profile button is clicked
     *
     * @param actionEvent the event of clicking edit
     */
    @FXML
    public void editButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();

        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(true);
        Input2.setVisible(true);

        editClicked = true;
        addClicked = false;
        deleteClicked = false;
    }

    /**
     * When the delete button has been clickec
     *
     * @param actionEvent the event of clicking delete
     */
    @FXML
    public void deleteButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        Ok.setVisible(true);
        Input.setVisible(true);
        Label.setVisible(true);
        Label2.setVisible(false);
        Input2.setVisible(false);

        deleteClicked = true;
        addClicked = false;
        editClicked = false;
    }

    /**
     * When the ok button has been clicked, it does the corresponding action to the previous button click
     *
     * @param actionEvent the event of clicking the ok button
     */
    @FXML
    public void okButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        if (addClicked) {
            String name = Input.getText();
            game.createProfile(name);
        }

        if (deleteClicked) {
            String deletedName = Input.getText();
            Profile p = null;
            for (Profile profile : game.getProfiles()) {
                if (profile.getName().equals(deletedName)) {
                    p = profile;
                    break;
                }
            }
            game.removeProfile(p);
        }


        if (editClicked) {
            String currentName = Input.getText();
            String newName = Input2.getText();
            Profile p = null;
            for (Profile profile : game.getProfiles()) {
                if (profile.getName().equals(currentName)) {
                    p = profile;
                    break;
                }

            }
            p.setName(newName);
            game.saveProfiles();
            for (int i = 0; i < game.getProfiles().size(); i++) {
                Profile profile = game.getProfiles().get(i);
                String profileData =
                        profile.getName() + " W:" + profile.getGamesWon() +
                                " L:" + profile.getGamesLost();
                profilesList.getItems().set(i, profileData);
            }
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

    /**
     * calls the setGame menu to preset the profile menu to have an invisible form and the profiles list to be preloaded
     *
     * @param game calls object game to set the game
     */
    @FXML
    public void setGame(Game game) {
        this.game = game;
        for (Profile p : game.getProfiles()) {
            String profileData = p.getName() + " W:" + p.getGamesWon() + " L:" +
                    p.getGamesLost();
            profilesList.getItems().add(profileData);
        }

        Ok.setVisible(false);
        Input.setVisible(false);
        Label.setVisible(false);
        Label2.setVisible(false);
        Input2.setVisible(false);
    }
}

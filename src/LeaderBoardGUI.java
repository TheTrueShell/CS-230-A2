import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class controls the win screen
 *
 * @author William Aodan Telford
 * @version 0.0.1
 */
public class LeaderBoardGUI {
    @FXML
    private Label playerWin;
    @FXML
    private ListView leaderBoard;


    private static Game game;
    @FXML
    public void initialize() {

        ArrayList<Profile> profiles = game.getProfiles();

        ObservableList<String> displayProfiles = FXCollections.observableArrayList();
        for(Profile p : profiles ) {

            displayProfiles.add(p.getName() + ": " + " Games played: " + p.getGamesPlayed() + " Games Won: " + p.getGamesWon() + " Games Lost: " + p.getGamesLost() );

        }

        leaderBoard.setItems(displayProfiles);


    }


    /**
     * Sets the game
     * @param newgame
     */

    public static void setGame(Game newgame){
        game = newgame;
    }

    public void mainMenuButtonAction(ActionEvent actionEvent) {
        Game.playMenuSound();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
        Parent mainMenuFXMLParent = null;
        try {
            mainMenuFXMLParent = (Parent)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainMenuFXMLScene = new Scene(mainMenuFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(mainMenuFXMLScene);
        MainMenuGUI controller = (MainMenuGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();

    }
}

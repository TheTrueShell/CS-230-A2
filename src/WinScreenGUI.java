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

/**
 * This class controls the win screen
 *
 * @author Mohammed T, Aryan Dusi
 * @version 0.0.1
 */
public class WinScreenGUI {
    @FXML
    private Label playerWin;

    @FXML
    private TextField playerLost;

    private Game game;

    /**
     * upon creation of the winScreen form, the stats of the players are updated and the outcome is displayed
     * @param winner the player who won
     * @param losers an arraylist of the player(s) that lost
     */
    public WinScreenGUI(Player winner, ArrayList<Player> losers) {
        String winnerProfile = winner.getProfile();
        playerWin.setText(winnerProfile);

        String losersOutput ="";
        for (int i=0;i<losers.size();i++) {
            losersOutput =losersOutput+ losers.get(i).toString()+", ";
        }
        playerLost.setText(losersOutput);


        ArrayList<Profile> profiles = game.getProfiles();
        for(int i =0;i<profiles.size();i++){
        if(profiles.get(i).getName() == winnerProfile) {
        profiles.get(i).updateGamesPlayed();
        profiles.get(i).updateGamesWon();
        game.saveProfiles();
        }
        else{
        profiles.get(i).updateGamesPlayed();
        profiles.get(i).updateGamesLost();
        game.saveProfiles();
        }
        }

    }

    /**
     * Sets the game
     * @param game
     */

    public void setGame(Game game){
        this.game = game;
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

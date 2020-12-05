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
public class WinScreen{
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
    public WinScreen(Player winner,ArrayList<Player> losers) {
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

}

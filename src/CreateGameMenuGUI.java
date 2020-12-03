import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
 * @Co-authors William Aodan Telford, Ben Rockley
 * @version 1.1
 */
public class CreateGameMenuGUI {
    @FXML
    private Game game;

    private ArrayList<Profile> Profiles;
    private ArrayList<String> presets;
    @FXML
    private ComboBox<String> playerOneProfile;
    @FXML
    private ChoiceBox<String> mapPreset;
    @FXML
    private ComboBox<String> playerTwoProfile;
    @FXML
    private ComboBox<String> playerThreeProfile;
    @FXML
    private ComboBox<String> playerFourProfile;

    private String playerOneCurrentSelection = "No Player";
    private String playerTwoCurrentSelection ="No Player";
    private String playerThreeCurrentSelection = "No Player";
    private String playerFourCurrentSelection = "No Player";

    private ObservableList<String> playerOneNames = FXCollections.observableArrayList();
    private ObservableList<String> playerTwoNames = FXCollections.observableArrayList();
    private ObservableList<String> playerThreeNames = FXCollections.observableArrayList();
    private ObservableList<String> playerFourNames = FXCollections.observableArrayList();

    private ObservableList<String> names = FXCollections.observableArrayList();


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

        String chosenNameOne = (String) playerOneProfile.getValue();
        String chosenNameTwo = (String) playerTwoProfile.getValue();
        String chosenNameThree = (String) playerThreeProfile.getValue();
        String chosenNameFour = (String) playerFourProfile.getValue();
        System.out.println(chosenNameOne);
        reloadProfileNames();
        updateNames();


        for(int i = 0; i < names.size(); i++) {

            if(names.get(i) == chosenNameOne || names.get(i) == chosenNameTwo || names.get(i)
                            == chosenNameThree || names.get(i) == chosenNameFour) {

                names.remove(i);
                updateNames();

            }

        }


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
    public void newGameButtonAction(ActionEvent actionEvent) throws IOException {
        if (playerOneProfile.getSelectionModel().getSelectedItem() != null) {
            this.game.addPlayer(playerOneProfile.getValue());
        }
        try {
            this.game.loadPreset("presets/preset_" + mapPreset.getValue() + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
        Parent boardGUIFXMLParent = (Parent)loader.load();
        Scene boardGUIFXMLScene = new Scene(boardGUIFXMLParent);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // This line gets the stage the 'Play' button's action event came from
        primaryStage.setScene(boardGUIFXMLScene);
        BoardGUI controller = (BoardGUI)loader.getController();
        controller.setGame(this.game);
        primaryStage.show();
    }


    /**
     * Set the game to the passed one
     * @param game the game instance
     */
    public void setGame(Game game){
        this.game = game;
        reloadProfileNames();
        initialiseNames();
        updateNames();

    }

    private void initialiseNames() {

        for(String s : names) {

            playerOneNames.add(s);
            playerTwoNames.add(s);
            playerThreeNames.add(s);
            playerFourNames.add(s);

        }

    }


    public void reloadProfileNames() {
        Profiles = game.getProfiles();

        for(Profile p : Profiles ) {

            names.add(p.getName());

        }

        names.add("No Player");

    }

    public void updateNames() {

        playerOneProfile.setItems(playerOneNames);
        playerTwoProfile.setItems(playerTwoNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);

    }

    public void playerOneProfileAction(ActionEvent actionEvent) {


        playerOneCurrentSelection = playerOneProfile.getValue();

        playerTwoNames.remove(playerOneCurrentSelection);
        playerThreeNames.remove(playerOneCurrentSelection);
        playerFourNames.remove(playerOneCurrentSelection);

        playerTwoProfile.setItems(playerTwoNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);
        System.out.println("PlayerOne Changed");



    }

    public void playerTwoProfileAction(ActionEvent actionEvent) {


        playerTwoCurrentSelection = playerTwoProfile.getValue();

        playerOneNames.remove(playerTwoCurrentSelection);
        playerThreeNames.remove(playerTwoCurrentSelection);
        playerFourNames.remove(playerTwoCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);

    }


    public void playerThreeProfileAction(ActionEvent actionEvent) {

        playerThreeCurrentSelection = playerThreeProfile.getValue();

        playerOneNames.remove(playerThreeCurrentSelection);
        playerTwoNames.remove(playerThreeCurrentSelection);
        playerFourNames.remove(playerThreeCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerTwoProfile.setItems(playerTwoNames);
        playerFourProfile.setItems(playerFourNames);

    }

    public void playerFourProfileAction(ActionEvent actionEvent) {

        playerFourCurrentSelection = playerFourProfile.getValue();

        playerOneNames.remove(playerFourCurrentSelection);
        playerThreeNames.remove(playerFourCurrentSelection);
        playerTwoNames.remove(playerFourCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerTwoProfile.setItems(playerTwoNames);

    }


}

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
 *
 * @author Gus Rendle
 * @version 1.4
 * @Co-authors William Aodan Telford, Ben Rockley
 */
public class CreateGameMenuGUI {

    String NOPLAYERDISPLAYSTRING = "No Player";

    @FXML
    private Game game;

    @FXML
    private Label topOfScreenLabel;

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

    // The current Profile that has been selected by each player
    private String playerOneCurrentSelection = NOPLAYERDISPLAYSTRING;
    private String playerTwoCurrentSelection = NOPLAYERDISPLAYSTRING;
    private String playerThreeCurrentSelection = NOPLAYERDISPLAYSTRING;
    private String playerFourCurrentSelection = NOPLAYERDISPLAYSTRING;


    // The lists of profile names that can be used by each player
    private final ObservableList<String> playerOneNames =
            FXCollections.observableArrayList();
    private final ObservableList<String> playerTwoNames =
            FXCollections.observableArrayList();
    private final ObservableList<String> playerThreeNames =
            FXCollections.observableArrayList();
    private final ObservableList<String> playerFourNames =
            FXCollections.observableArrayList();

    // Master list of all Profile Names
    private final ObservableList<String> names = FXCollections.observableArrayList();

    /**
     * Initialized the presets list once the FXML has been loaded.
     */

    @FXML
    public void initialize() {
        File dir = new File("presets");
        this.presets = new ArrayList<String>(Arrays.asList(dir.list()));
        List<String> presetList = this.presets.stream()
                .map(s -> s.replaceFirst("^preset_", "").replace(".txt", ""))
                .collect(Collectors.toList());
        ObservableList<String> observablePresetList =
                FXCollections.observableArrayList(presetList);
        mapPreset.setItems(observablePresetList);
    }


    /**
     * Loads the main menu when the back button is clicked
     *
     * @param actionEvent
     * @throws IOException
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
     * Creates a new Game using the profiles and preset selected
     *
     * @param actionEvent
     * @throws IOException
     */

    @FXML
    public void newGameButtonAction(ActionEvent actionEvent)
            throws IOException {
        Game.playMenuSound();
        if ((mapPreset.getSelectionModel().getSelectedItem() != null)
                && (playerOneProfile.getValue() != NOPLAYERDISPLAYSTRING)
                && (playerTwoProfile.getValue() != NOPLAYERDISPLAYSTRING)) {

            if (playerOneProfile.getSelectionModel().getSelectedItem() !=
                    null) {
                this.game.addPlayer(playerOneProfile.getValue());
            }
            if (playerTwoProfile.getSelectionModel().getSelectedItem() !=
                    null) {
                this.game.addPlayer(playerTwoProfile.getValue());
            }
            if (playerThreeProfile.getSelectionModel().getSelectedItem() != null
                    && playerThreeProfile.getValue() != NOPLAYERDISPLAYSTRING) {
                this.game.addPlayer(playerThreeProfile.getValue());
            }
            if (playerFourProfile.getSelectionModel().getSelectedItem() != null
                    && playerFourProfile.getValue() != NOPLAYERDISPLAYSTRING) {
                this.game.addPlayer(playerFourProfile.getValue());
            }


            try {
                this.game.loadPreset(
                        "presets/preset_" + mapPreset.getValue() + ".txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
            Parent boardGUIFXMLParent = loader.load();
            Scene boardGUIFXMLScene = new Scene(boardGUIFXMLParent);
            Stage primaryStage =
                    (Stage) ((Node) actionEvent.getSource()).getScene()
                            .getWindow();
            // This line gets the stage the 'Play' button's action event came from
            primaryStage.setScene(boardGUIFXMLScene);
            BoardGUI controller = loader.getController();
            controller.setGame(this.game);
            primaryStage.show();
        } else {

            topOfScreenLabel
                    .setText("Please choose a profile for player one and two." +
                            " Please also choose a board preset!");

        }
    }


    /**
     * Set the game to the passed one and starts the combobuttons
     *
     * @param game the game instance
     */
    public void setGame(Game game) {
        this.game = game;
        reloadProfileNames();
        initialiseNames();
        updateNames();

    }

    /**
     * Adds all the profile names from the profiles.txt to the lists of usable profiles.
     */

    private void initialiseNames() {

        for (String s : names) {

            playerOneNames.add(s);
            playerTwoNames.add(s);
            playerThreeNames.add(s);
            playerFourNames.add(s);

        }

    }

    /**
     * Reloads all the possible profile names into names.
     */

    public void reloadProfileNames() {
        Profiles = game.getProfiles();

        for (Profile p : Profiles) {

            names.add(p.getName());

        }

        names.add(NOPLAYERDISPLAYSTRING);

    }

    /**
     * Updates the names available to each player
     */

    public void updateNames() {

        playerOneProfile.setItems(playerOneNames);
        playerTwoProfile.setItems(playerTwoNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);

    }

    /**
     * Checks that if checkName can be found within the given ObservableList
     *
     * @param playerNames
     * @param checkName
     * @return boolean true or false depending on if checkName can be found.
     */

    public boolean isInPlayerXNames(ObservableList<String> playerNames,
                                    String checkName) {

        for (String s : playerNames) {

            if (s == checkName) {

                return true;

            }

        }

        return false;

    }

    /**
     * Activates when user selects new item from the player One combo box.
     * First searches to see if the previous selection needs to be added back to the usable profiles
     * Second gets the new Value and removes it from the usable profiles
     * Third Checks that the NOPLAYERDISPLAYSTRING option hasn't been removed.
     *
     * @param actionEvent
     */

    public void playerOneProfileAction(ActionEvent actionEvent) {

        if (!isInPlayerXNames(playerTwoNames, playerOneCurrentSelection)
                ||
                !isInPlayerXNames(playerThreeNames, playerOneCurrentSelection)
                ||
                !isInPlayerXNames(playerFourNames, playerOneCurrentSelection)) {

            playerTwoNames.add(playerOneCurrentSelection);
            playerThreeNames.add(playerOneCurrentSelection);
            playerFourNames.add(playerOneCurrentSelection);

        }


        playerOneCurrentSelection = playerOneProfile.getValue();

        playerTwoNames.remove(playerOneCurrentSelection);
        playerThreeNames.remove(playerOneCurrentSelection);
        playerFourNames.remove(playerOneCurrentSelection);

        playerTwoProfile.setItems(playerTwoNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);
        //System.out.println("PlayerOne Changed");

        addNoPlayerOption();


    }

    /**
     * Activates when user selects new item from the player Two combo box.
     * First searches to see if the previous selection needs to be added back to the usable profiles
     * Second gets the new Value and removes it from the usable profiles
     * Third Checks that the NOPLAYERDISPLAYSTRING option hasn't been removed.
     *
     * @param actionEvent
     */

    public void playerTwoProfileAction(ActionEvent actionEvent) {

        if (!isInPlayerXNames(playerOneNames, playerTwoCurrentSelection)
                ||
                !isInPlayerXNames(playerThreeNames, playerTwoCurrentSelection)
                ||
                !isInPlayerXNames(playerFourNames, playerTwoCurrentSelection)) {

            playerOneNames.add(playerTwoCurrentSelection);
            playerThreeNames.add(playerTwoCurrentSelection);
            playerFourNames.add(playerTwoCurrentSelection);

        }

        playerTwoCurrentSelection = playerTwoProfile.getValue();

        playerOneNames.remove(playerTwoCurrentSelection);
        playerThreeNames.remove(playerTwoCurrentSelection);
        playerFourNames.remove(playerTwoCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerFourProfile.setItems(playerFourNames);

        addNoPlayerOption();

    }

    /**
     * Activates when user selects new item from the player Three combo box.
     * First searches to see if the previous selection needs to be added back to the usable profiles
     * Second gets the new Value and removes it from the usable profiles
     * Third Checks that the NOPLAYERDISPLAYSTRING option hasn't been removed.
     *
     * @param actionEvent
     */

    public void playerThreeProfileAction(ActionEvent actionEvent) {

        if (!isInPlayerXNames(playerOneNames, playerThreeCurrentSelection)
                ||
                !isInPlayerXNames(playerTwoNames, playerThreeCurrentSelection)
                || !isInPlayerXNames(playerFourNames,
                playerThreeCurrentSelection)) {

            playerOneNames.add(playerThreeCurrentSelection);
            playerTwoNames.add(playerThreeCurrentSelection);
            playerFourNames.add(playerThreeCurrentSelection);

        }

        playerThreeCurrentSelection = playerThreeProfile.getValue();

        playerOneNames.remove(playerThreeCurrentSelection);
        playerTwoNames.remove(playerThreeCurrentSelection);
        playerFourNames.remove(playerThreeCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerTwoProfile.setItems(playerTwoNames);
        playerFourProfile.setItems(playerFourNames);

        addNoPlayerOption();

    }

    /**
     * Activates when user selects new item from the player Four combo box.
     * First searches to see if the previous selection needs to be added back to the usable profiles
     * Second gets the new Value and removes it from the usable profiles
     * Third Checks that the NOPLAYERDISPLAYSTRING option hasn't been removed.
     *
     * @param actionEvent
     */

    public void playerFourProfileAction(ActionEvent actionEvent) {

        if (!isInPlayerXNames(playerOneNames, playerFourCurrentSelection)
                || !isInPlayerXNames(playerTwoNames, playerFourCurrentSelection)
                || !isInPlayerXNames(playerThreeNames,
                playerFourCurrentSelection)) {

            playerOneNames.add(playerFourCurrentSelection);
            playerThreeNames.add(playerFourCurrentSelection);
            playerTwoNames.add(playerFourCurrentSelection);

        }

        playerFourCurrentSelection = playerFourProfile.getValue();

        playerOneNames.remove(playerFourCurrentSelection);
        playerThreeNames.remove(playerFourCurrentSelection);
        playerTwoNames.remove(playerFourCurrentSelection);

        playerOneProfile.setItems(playerOneNames);
        playerThreeProfile.setItems(playerThreeNames);
        playerTwoProfile.setItems(playerTwoNames);

        addNoPlayerOption();

    }

    /**
     * Checks to see if the NOPLAYERDISPLAYSTRING option has been removed. If it has it readds it to the possible names list.
     */

    public void addNoPlayerOption() {

        if (!isInPlayerXNames(playerOneNames, NOPLAYERDISPLAYSTRING)) {

            playerOneNames.add(NOPLAYERDISPLAYSTRING);

        }
        if (!isInPlayerXNames(playerTwoNames, NOPLAYERDISPLAYSTRING)) {

            playerTwoNames.add(NOPLAYERDISPLAYSTRING);

        }
        if (!isInPlayerXNames(playerThreeNames, NOPLAYERDISPLAYSTRING)) {

            playerThreeNames.add(NOPLAYERDISPLAYSTRING);

        }
        if (!isInPlayerXNames(playerFourNames, NOPLAYERDISPLAYSTRING)) {

            playerFourNames.add(NOPLAYERDISPLAYSTRING);

        }

    }


}

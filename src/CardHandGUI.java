import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class CardHandGUI {

    public HBox deckHBox;

    public void setCards(ArrayList<Tile> deck) {
        for(Tile card : deck) {
            Image cardImage = new Image(card.getImageLocation(), 64,64,true,true);
            deckHBox.getChildren().add(new ImageView(cardImage));
        }
    }
}

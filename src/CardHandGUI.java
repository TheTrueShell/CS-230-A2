import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class CardHandGUI {

    public HBox deckHBox;

    public void setCards(ArrayList<Tile> deck) {
        for(Tile card : deck) {
            Image cardImage = new Image(card.getImageLocation(), 40,40,true,true);
            Image borderImage = new Image("invSlot.png", 64,64,true,true);
            StackPane cardStack = new StackPane();
            cardStack.getChildren().addAll(new ImageView(borderImage), new ImageView(cardImage));
            deckHBox.getChildren().add(cardStack);


        }
    }
}

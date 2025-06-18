package vue._components.bookCard;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;

public abstract class BaseBookCardComponent extends BorderPane {
    protected Livre livre;
    protected int quantite;

    public BaseBookCardComponent(Livre livre, int quantite) {
        super();

        this.livre = livre;
        this.quantite = quantite;

        this.setMinWidth(250);
        this.setPrefWidth(250);

        this.setStyle("-fx-border-color: black");
        this.setPadding(new Insets(10));

        // Positions des widgets

        ImageView top = this.top();
        this.setTop(top);

        VBox center = this.center();
        this.setCenter(center);
    }

    public int getQuantite() {
        return this.quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void miseAJourAffichage() {
        VBox center = this.center();
        this.setCenter(center);
    }

    private ImageView top() {
        ImageView bookImage = new ImageView("./images/unknownBook.png");
        bookImage.setFitHeight(60);
        bookImage.setFitWidth(60);
        return bookImage;
    }

    private VBox center() {
        VBox vboxDetails = new VBox();
        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vboxDetails.getChildren().addAll(
            labelTitre,
            new Label(livre.joinNomAuteurs()),
            new Label(String.format("%.2f€ - %d en stock", livre.getPrix(), this.quantite))
        );
        return vboxDetails;
    }
}

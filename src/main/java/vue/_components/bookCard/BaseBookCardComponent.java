package vue._components.bookCard;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;

/** La base d'une carte pour un livre */
public abstract class BaseBookCardComponent extends BorderPane {
    /** Un livre */
    protected Livre livre;
    /** La quantité en stock du livre */
    protected int quantite;

    /**
     * Initialliser une carte pour un livre.
     * @param livre Un livre.
     * @param quantite La quantité du livre présent dans les stocks magasin.
     */
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

    /**
     * Obtenir la quantité du livre en stock.
     * @return La quantité du livre en stock.
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Définir la quantité du livre en stock.
     * @param quantite La nouvelle quantité du livre en stock.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Mettre à jour l'affichage de la pane.
     */
    public void miseAJourAffichage() {
        VBox center = this.center();
        this.setCenter(center);
    }

    /**
     * Obtenir la pane du dessus, affichant la couverture du livre. 
     * @return La pane du dessus, affichant la couverture du livre. 
     */
    private ImageView top() {
        ImageView bookImage = new ImageView("./images/unknownBook.png");
        bookImage.setFitHeight(60);
        bookImage.setFitWidth(60);
        return bookImage;
    }

    /**
     * Obtenir la pane du milieu, affichant les informations du livre (titre, auteur, prix, stock). 
     * @return La pane du milieu, affichant les informations du livre (titre, auteur, prix, stock). 
     */
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

package vue._components.bookCard;

import controleurs.customers.ControleurInfoLivre;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;

/** La carte d'un livre à afficher au vendeur */
public class SellerBookCardComponent extends BaseBookCardComponent {
    /** Le nombre de ventes dans le magasin du vendeur */
    private int nbVentes;

    /**
     * Initialiser une carte d'un livre à afficher au client.
     * @param livre Un livre. 
     * @param quantite La quantité du livre présent dans le magasin.
     * @param nbVentes Le nombre de ventes dans le magasin du vendeur.
     */
    public SellerBookCardComponent(Livre livre, int quantite, int nbVentes) {
        super(livre, quantite);

        this.nbVentes = nbVentes;

        this.initComponents();
    }

    /**
     * Obtenir la pane du milieu, affichant les informations du livre (titre, auteur, prix, stock). 
     * @return La pane du milieu, affichant les informations du livre (titre, auteur, prix, stock). 
     */
    @Override
    protected VBox center() {
        VBox vboxDetails = new VBox();
        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vboxDetails.getChildren().addAll(
            labelTitre,
            new Label(livre.joinNomAuteurs()),
            new Label(String.format("%.2f€ - %d en stock - %d ventes dans le magasin", livre.getPrix(), this.quantite, this.nbVentes))
        );
        return vboxDetails;
    }

    /**
     * Obtenir le composant en bas de la carte, avec le bouton d'informations
     * @return Le composant de bas de carte.
     */
    protected HBox bottom() {
        HBox hboxBoutons = new HBox();
        hboxBoutons.setMaxHeight(Double.MAX_VALUE);

        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton d'info
        Button infoButton = new Button("Informations");
        infoButton.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(infoButton, Priority.ALWAYS);
        infoButton.setOnAction(new ControleurInfoLivre(this.livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView(getClass().getResource("/images/info.png").toExternalForm());
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().add(infoButton);
        return hboxBoutons;
    }
}

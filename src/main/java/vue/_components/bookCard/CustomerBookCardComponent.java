package vue._components.bookCard;

import controleurs.customers.ControleurAjouterPanier;
import controleurs.customers.ControleurInfoLivre;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;

/** La carte d'un livre à afficher au client */
public class CustomerBookCardComponent extends BaseBookCardComponent {
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser une carte d'un livre à afficher au client.
     * @param livre Un livre. 
     * @param quantite La quantité du livre présent dans le magasin client.
     * @param modele Le modèle de données.
     */
    public CustomerBookCardComponent(Livre livre, int quantite, ChaineLibrairie modele) {
        super(livre, quantite);

        this.modele = modele;

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
            new Label(String.format("%.2f€ - %d en stock", livre.getPrix(), this.quantite))
        );
        return vboxDetails;
    }

    /**
     * Obtenir le composant en bas de la carte, avec le bouton "Ajouter au panier" et d'informations
     * @return Le composant de bas de carte.
     */
    @Override
    protected HBox bottom() {
        HBox hboxBoutons = new HBox();
        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton ajout panier
        Button addPanierButton = new Button("Ajouter au panier");
        if (this.quantite == 0) {
            addPanierButton.setDisable(true);
        } else {
            addPanierButton.setOnAction(new ControleurAjouterPanier(this, this.modele, this.livre));
        }

        addPanierButton.setPrefHeight(30);
        addPanierButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(addPanierButton, Priority.ALWAYS);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);
        infoButton.setOnAction(new ControleurInfoLivre(this.livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView(getClass().getResource("/images/info.png").toExternalForm());
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(addPanierButton, infoButton);
        return hboxBoutons;
    }
}

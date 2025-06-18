package vue._components.bookCard;

import controleurs.customers.ControleurAjouterPanier;
import controleurs.customers.ControleurInfoLivre;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        HBox bottom = this.bottom();
        this.setBottom(bottom);
    }

    @Override
    /**
     * Mettre à jour l'affichage de la pane.
     */
    public void miseAJourAffichage() {
        super.miseAJourAffichage();

        HBox bottom = this.bottom();
        this.setBottom(bottom);
    }

    /**
     * Obtenir le composant en bas de la carte, avec le bouton "Ajouter au panier" et d'informations
     * @return Le composant de bas de carte.
     */
    private HBox bottom() {
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
        ImageView infoIcon = new ImageView("./images/info.png");
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(addPanierButton, infoButton);
        return hboxBoutons;
    }
}

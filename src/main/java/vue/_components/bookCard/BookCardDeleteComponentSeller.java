package vue._components.bookCard;

import controleurs.customers.ControleurAjouterPanier;
import controleurs.customers.ControleurInfoLivre;
import controleurs.seller.ControleurSupprimerLivre;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.seller.SellerDeleteBookView;

/** La carte d'un livre à afficher au client */
public class BookCardDeleteComponentSeller extends BaseBookCardComponent {
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La vue 
    */
    private SellerDeleteBookView vue;

    /**
     * Initialiser une carte d'un livre à afficher au client.
     * @param livre Un livre. 
     * @param quantite La quantité du livre présent dans le magasin client.
     * @param modele Le modèle de données.
     */
    public BookCardDeleteComponentSeller(Livre livre, int quantite, ChaineLibrairie modele, SellerDeleteBookView vue) {
        super(livre, quantite);

        this.modele = modele;
        this.vue = vue;
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
     * Obtenir le composant en bas de la carte, avec le bouton "Supprimer" et d'informations
     * @return Le composant de bas de carte.
     */
    private HBox bottom() {
        HBox hboxBoutons = new HBox();
        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton Supprimer
        Button supprimer = new Button("Supprimer");
        if (this.quantite == 0) {
            supprimer.setDisable(true);
        } else {
            supprimer.setOnAction(new ControleurSupprimerLivre(this, this.modele, this.livre));
        }

        supprimer.setPrefHeight(30);
        supprimer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(supprimer, Priority.ALWAYS);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);
        infoButton.setOnAction(new ControleurInfoLivre(this.livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView("./images/info.png");
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(supprimer, infoButton);
        return hboxBoutons;
    }

    /**
     * Supprimer la carte du livre.
     */
    public void supprimerCarte() {
        Pane parent = (Pane) getParent();
        parent.getChildren().remove(this);
    }

    public SellerDeleteBookView getVue() {
        return this.vue;
    }
}

package vue._components.bookCard;

import controleurs.customers.ControleurInfoLivre;
import controleurs.seller.ControleurSupprimerLivre;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.seller.SellerDeleteBookListPane;

/** La carte d'un livre à afficher au vendeur pour le supprimer*/
public class SellerBookRemoveCardComponent extends SellerBookInfoCardComponent {
    /** La pane de la liste de livres */
    private SellerDeleteBookListPane sellerDeleteBookPane;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser une carte d'un livre à afficher au vendeur pour le supprimer.
     * @param livre Un livre. 
     * @param quantite La quantité du livre présent dans le magasin.
     * @param nbVentes Le nombre de ventes dans le magasin du vendeur.
     * @param sellerDeleteBookPane La pane de la liste de livres
     * @param modele Le modèle
     */
    public SellerBookRemoveCardComponent(Livre livre, int quantite, int nbVentes, SellerDeleteBookListPane sellerDeleteBookPane, ChaineLibrairie modele) {
        super(livre, quantite, nbVentes);

        this.sellerDeleteBookPane = sellerDeleteBookPane;
        this.modele = modele;

        this.initComponents();
    }

    /**
     * Obtenir le livre de la carte.
     * @return Le livre de la carte.
     */
    public Livre getLivre() {
        return this.livre;
    }

    /**
     * Obtenir le composant en bas de la carte, avec le bouton d'informations et le bouton de suppression.
     * @return Le composant de bas de carte.
     */
    @Override
    protected HBox bottom() {
        HBox hboxBoutons = new HBox();
        hboxBoutons.setMaxHeight(Double.MAX_VALUE);

        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton Supprimer
        Button supprimer = new Button("Supprimer");
        if (this.quantite == 0) {
            supprimer.setDisable(true);
        } else {
            supprimer.setOnAction(new ControleurSupprimerLivre(this.sellerDeleteBookPane, this.modele, this.livre));
        }

        supprimer.setPrefHeight(30);
        supprimer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(supprimer, Priority.ALWAYS);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);
        infoButton.setOnAction(new ControleurInfoLivre(this.livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView(getClass().getResource("/images/info.png").toExternalForm());
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(supprimer, infoButton);
        return hboxBoutons;
    }
}

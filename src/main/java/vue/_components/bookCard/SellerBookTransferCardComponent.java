package vue._components.bookCard;

import controleurs.seller.ControleurTransfererLivre;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue._components.numberField.IntegerField;
import vue._components.numberField.IntegerFieldDisableButton;
import vue.seller.SellerScene;
import vue.seller.SellerTransferBookPane;

/** La carte d'un livre à afficher au client */
public class SellerBookTransferCardComponent extends BaseBookCardComponent {
    /** Le modèle */
    private ChaineLibrairie modele;
    /** La scène du vendeur */
    private SellerScene sellerScene;
    /** La pane de transfert de livres */
    private SellerTransferBookPane sellerTransferBook;

    /** Le NumberField */
    private IntegerField quantiteField;

    /**
     * Initialiser une carte d'un livre à afficher au client.
     * @param livre Un livre. 
     * @param quantite La quantité du livre présent dans le magasin client.
     * @param modele Le modèle de données.
     * @param sellerScene La scène du vendeur.
     * @param sellerTransferBook La pane de transfert de livres.
     */
    public SellerBookTransferCardComponent(Livre livre, int quantite, ChaineLibrairie modele, SellerScene sellerScene, SellerTransferBookPane sellerTransferBook) {
        super(livre, quantite);

        this.modele = modele;
        this.sellerScene = sellerScene;
        this.sellerTransferBook = sellerTransferBook;

        this.initComponents();
    }

    /**
     * Obtenir le champ de saisie de la quantité du livre.
     * @return Le champ de saisie de la quantité du livre.
     */
    public IntegerField getQuantiteField() {
        return this.quantiteField;
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
        Button transferButton = new Button("Transférer");
        transferButton.setDisable(true);
        transferButton.setOnAction(new ControleurTransfererLivre(this.sellerScene, this.sellerTransferBook, this.modele, this.livre, this));

        this.quantiteField = new IntegerFieldDisableButton(transferButton);
        this.quantiteField.setPromptText("Quantité");

        transferButton.setPrefHeight(30);
        transferButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(transferButton, Priority.ALWAYS);

        hboxBoutons.getChildren().addAll(this.quantiteField, transferButton);
        return hboxBoutons;
    }
}

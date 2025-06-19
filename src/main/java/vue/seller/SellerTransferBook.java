package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.seller.ControleurChangerMagasinSeller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.alerts.AlertErreurException;

/**
 * La vue pour transférer un livre vers un autre magasin.
 */
public class SellerTransferBook extends VBox {
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le textfield du titre du livre */
    private TextField titreLivre;
    /** Le textfield de la quantité du livre */
    private TextField qte;

    /** La scène du vendeur */
    private SellerScene sellerScene;

    /**
     * Initialiser la vue pour transférer un livre vers un autre magasin.
     * @param modele Le modèle de données.
     */
    public SellerTransferBook(ChaineLibrairie modele) {
        this.modele = modele;
        this.titreLivre = new TextField();
        this.qte= new TextField(getAccessibleText());

        this.getChildren().addAll(this.getCenter());
    }

    /**
     * Obtenir la liste des magasins pour le vendeur.
     * @param vendeur Le vendeur
     * @return La VBox contenant la liste des magasins.
     */
    private VBox getMagasinsList(Vendeur vendeur) {
        VBox magasinsVBox = new VBox();
        magasinsVBox.setSpacing(10);

        // Label
        Label magasinLabel = new Label("Votre magasin");
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // Combo-box avec liste magasin
        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            new AlertErreurException("La liste des magasins n'a pas pu être récupérée.", e.getMessage());
        }

        ComboBox<Magasin> magasinComboBox = new ComboBox<>();
        magasinComboBox.getItems().addAll(listeMagasins);
        magasinComboBox.setValue(vendeur.getMagasin());
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);

        magasinComboBox.setOnAction(new ControleurChangerMagasinSeller(this.sellerScene, this.modele));

        magasinsVBox.getChildren().addAll(magasinLabel, magasinComboBox);
        return magasinsVBox;
    }

    // public ComboBox<String> getComboBox(){
    //     ComboBox<String> comboBoxChoixMag = new ComboBox<>();

    //     List<Magasin> listeMagasins = new ArrayList<>();
    //     try {
    //         listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
    //     } catch (SQLException e) {
    //         new AlertErreurException("Impossible de récupérer les magasins.", e.getMessage());
    //     }

    //     for (Magasin magasin : listeMagasins) {
    //         comboBoxChoixMag.getItems().add(magasin.getNom());
    //     }
    // return comboBoxChoixMag;
    // }

    /**
     * Obtenir le centre de la vue, contenant les champs pour transférer un livre.
     * @return La VBox contenant les champs pour transférer un livre.
     */
    public VBox getCenter(){
        VBox vBox = new VBox();

        Label titre = new Label("Titre");
        VBox vboxTitre = new VBox();
        vboxTitre.getChildren().addAll(titre, this.titreLivre);

        Label quantite= new Label("Quantité");
        VBox vBoxQte= new VBox();
        vBoxQte.getChildren().addAll(quantite, this.qte);

        HBox hboxChamp= new HBox();

        hboxChamp.getChildren().addAll(vboxTitre, vBoxQte);

        Label madDest= new Label("Magasin de destination");
        vBox.getChildren().addAll(hboxChamp,madDest, this.getMagasinsList(null));
        return vBox;
    }
}
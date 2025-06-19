package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.BaseListElementsWithSearchPane;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookTransferCardComponent;

public class SellerTransferBookPane extends BaseListElementsWithSearchPane<Livre> {
    private ChaineLibrairie modele;
    private SellerScene sellerScene;

    private ComboBox<Magasin> magasinComboBox;

    public SellerTransferBookPane(List<Livre> listeLivres, SellerScene sellerScene, ChaineLibrairie modele,
            Magasin magasin) {

        super("Transférer un livre", listeLivres, 2, 2, "Rechercher un livre...");
        
        this.modele = modele;
        this.sellerScene = sellerScene;

        this.addComponents();
    }

    public ComboBox<Magasin> getMagasinComboBox() {
        return this.magasinComboBox;
    }

    public VBox getHeaderPane() {
        VBox headerBox = new VBox();
        headerBox.setSpacing(10);

        Label titreMagasin = new Label("Magasin de destination");

        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            new AlertErreurException("La liste des magasins n'a pas pu être récupérée.", e.getMessage());
        }

        Magasin vendeurMagasin = this.modele.getVendeurActuel().getMagasin();
        if (listeMagasins.contains(vendeurMagasin)) {
            listeMagasins.remove(vendeurMagasin);
        }

        this.magasinComboBox = new ComboBox<>();
        magasinComboBox.getItems().addAll(listeMagasins);
        magasinComboBox.setPromptText("Sélectionner un magasin");
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);
        
        headerBox.getChildren().addAll(titreMagasin, magasinComboBox);

        return headerBox;
    }

    public BorderPane getElementComponent(Livre livre) {
        Vendeur vendeur = this.modele.getVendeurActuel();
        Magasin magasin = vendeur.getMagasin();

        int quantiteStock = 0;
        try {
            quantiteStock = this.modele.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
        } catch (SQLException e) {
            new AlertErreurException("Le stock dans le magasin n'a pas pu être récupéré.", e.getMessage());
        }

        SellerBookTransferCardComponent cardComponent = new SellerBookTransferCardComponent(livre, quantiteStock, this.modele, this.sellerScene, this);
        return cardComponent;
    }
}
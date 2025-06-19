package controleurs.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.alerts.AlertErreur;
import vue._components.alerts.AlertErreurException;
import vue._components.alerts.AlertInfo;
import vue._components.bookCard.SellerBookTransferCardComponent;
import vue._components.numberField.NumberField;
import vue.seller.SellerScene;
import vue.seller.SellerTransferBookPane;

public class ControleurTransfererLivre implements EventHandler<ActionEvent>{
    private SellerScene sellerScene;
    private SellerTransferBookPane sellerTransferBook;
    private ChaineLibrairie modele;
    private Livre livre;
    private SellerBookTransferCardComponent sellerBookTransferCardComponent;

    public ControleurTransfererLivre(SellerScene sellerScene, SellerTransferBookPane sellerTransferBook, ChaineLibrairie modele, Livre livre, SellerBookTransferCardComponent sellerBookTransferCardComponent){
        this.sellerScene = sellerScene;
        this.sellerTransferBook = sellerTransferBook;
        this.modele = modele;
        this.livre = livre;
        this.sellerBookTransferCardComponent = sellerBookTransferCardComponent;
    }

    @Override
    public void handle(ActionEvent event) {
        ComboBox<Magasin> magasinComboBox = this.sellerTransferBook.getMagasinComboBox();
        Magasin magasinChoisi = magasinComboBox.getValue();
        if (magasinChoisi == null) {
            new AlertErreur("Veuillez sélectionner un magasin pour transférer le livre.");
            return; 
        }

        Vendeur vendeur = this.modele.getVendeurActuel();
        Magasin vendeurMagasin = vendeur.getMagasin();

        NumberField quantiteField = this.sellerBookTransferCardComponent.getQuantiteField();
        int quantiteTransfert = quantiteField.getValeur();

        try {
            this.modele.getLivreBD().transfertLivre(this.livre, vendeurMagasin, magasinChoisi, quantiteTransfert);
        } catch (SQLException e) {
            new AlertErreurException("Le transfert du livre a échoué.", e.getMessage());
            return;
        }

        new AlertInfo("Transfert réussi", "Le livre a été transféré avec succès vers le magasin " + magasinChoisi.getNom() + ".");

        // Mise à jour page
        List<Livre> livresMagasin = new ArrayList<>();
        try {
            livresMagasin = this.modele.getLivreBD().obtenirLivreEnStockMagasin(vendeurMagasin);
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer la liste des livres.", e.getMessage());
            return;
        }

        this.sellerScene.showTransfer(livresMagasin, vendeurMagasin);
    }
}
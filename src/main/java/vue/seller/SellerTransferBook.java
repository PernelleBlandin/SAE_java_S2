package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurAcceuil;
import controleurs.seller.ControleurChangerMagasinSeller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.BaseListElementsWithSearchPane;
import vue._components.TitleAndBackButtonPane;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookTransfertComponent;

public class SellerTransferBook extends BaseListElementsWithSearchPane<Livre> {
    private ChaineLibrairie modele;
    private TextField titreLivre;
    private TextField qte;

    private SellerScene sellerScene;

    



    public SellerTransferBook(List<Livre> listeLivres, int nbLignes, SellerScene sellerScene, ChaineLibrairie modele, Magasin magasin) {

        super("Livres en stock", listeLivres, nbLignes, 1, "Rechercher un livre...");
        this.modele = modele;
        this.titreLivre = new TextField();
        this.qte= new TextField();
        this.sellerScene= sellerScene;

        this.addComponents();
        this.sellerScene= sellerScene;

        //this.getChildren().addAll(this.getCenter());


    }

    /** TODO refaire docs
     * Obtenir la liste des magasins, avec un titre et un combo-box pour le changer.
     * @param client Un client.
     * @return La liste des magasins et le titre de la VBox.
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
        vBox.getChildren().addAll(hboxChamp,madDest);


        //adminMagStock
        // int currentStock = 0;
        // try {
        //     currentStock = this.modele.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
        // } catch (SQLException e) {
        //     // On ignore
        // }
        
        List<Livre> listelivres = new ArrayList<>();
        try {
            listelivres = this.modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            new AlertErreurException("La liste des livres n'a pas pu être récupérée.", e.getMessage());
        }

        for (int i = 0; i < listelivres.size() && i < 4; i++) {
            Livre livre = listelivres.get(i);
            BorderPane bookCard = this.sellerScene.createOrGetCardComponent(livre);
            vBox.getChildren().add(bookCard);
        }
            return vBox;
        }
    
    
    /**
     * Mettre à jour l'affichage de la page.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(0, this.getListeLivresPane());
    }

    public BorderPane getHeaderPane() {
    return new TitleAndBackButtonPane(this.getTitre(), new ControleurAcceuil(this.sellerScene));
}
    public BorderPane getElementComponent(Livre livre) {
        return this.sellerScene.createOrGetCardComponent(livre);
    }

    public SellerBookTransfertComponent getElementComponent(Livre livre int quantite, int nbVentes, SellerTransferBook sellerTransferBook, ChaineLibrairie modele){
        Vendeur vendeur = this.modele.getVendeurActuel();
        Magasin magasin = vendeur.getMagasin();

        SellerBookTransfertComponent bookCard = new SellerBookTransfertComponent(livre, nbLignes, nbColonnes, null, modele);
        return bookCard;
    }


}
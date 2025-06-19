package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue._components.alerts.AlertErreurException;

public class SellerTransferBook extends VBox{
    private ChaineLibrairie modele;
    private TextField titreLivre;
    private TextField qte;

    public SellerTransferBook(ChaineLibrairie modele) {
        this.modele = modele;
        this.titreLivre = new TextField();
        this.qte= new TextField(getAccessibleText());
    }

    public ComboBox<String> getComboBox(){
        ComboBox<String> comboBoxChoixMag = new ComboBox<>();

        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les magasins.", e.getMessage());
        }

        for (Magasin magasin : listeMagasins) {
            comboBoxChoixMag.getItems().add(magasin.getNom());
        }
        return comboBoxChoixMag;
    }

    public VBox getCenter(){
        VBox vBox = new VBox();
        Label titre = new Label("Titre");

        Label quantite= new Label("Quantité");

        HBox hboxChamp= new HBox();
        hboxChamp.getChildren().addAll(this.titreLivre, this.qte);
        

        Label madDest= new Label("Magasin de destination");
        
        vBox.getChildren().addAll(titre, qte, hboxChamp, this.getComboBox());
        return vBox;
    }
}
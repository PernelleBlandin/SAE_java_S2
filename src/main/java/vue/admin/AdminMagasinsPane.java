package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.admin.ControleurBoutonMagasinStock;
import controleurs.admin.ControleurBoutonMagasinVendeur;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;

public class AdminMagasinsPane extends VBox {
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    public AdminMagasinsPane(AdminScene adminScene, ChaineLibrairie modele) {
        this.adminScene = adminScene;
        this.modele = modele;

        this.setSpacing(50);

        this.getChildren().addAll(
                this.titre(),
                this.listeMagasins());
    }

    private Label titre() {
        Label titre = new Label("Magasins");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre;
    }

    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale pour
     * voir les magasins.
     * 
     * @return laListe La VBox
     */
    private VBox listeMagasins() {
        VBox laListe = new VBox(10);

        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            // TODO: handle exception
        }

        for (Magasin magasin : listeMagasins) {
            Image imgStock = new Image("/images/book.png");
            ImageView viewStock = new ImageView(imgStock);
            Image imgVendeurs = new Image("/images/multiple_sellers_silhouette.png");
            ImageView viewVendeurs = new ImageView(imgVendeurs);
            Image imgPoubelle = new Image("/images/trashcan.png");
            ImageView viewPoubelle = new ImageView(imgPoubelle);

            viewVendeurs.setFitHeight(35);
            viewVendeurs.setFitWidth(35);
            viewPoubelle.setFitHeight(35);
            viewPoubelle.setFitWidth(35);
            viewStock.setFitHeight(35);
            viewStock.setFitWidth(35);

            BorderPane ligneMag = new BorderPane();
            HBox lesBtn = new HBox(10);
            Label leMag = new Label(magasin.toString());

            Button btnVendeursDuMag = new Button();
            btnVendeursDuMag.setGraphic(viewVendeurs);
            btnVendeursDuMag.setOnAction(new ControleurBoutonMagasinVendeur(this.adminScene, magasin));

            Button btnSupprimerMag = new Button();
            btnSupprimerMag.setGraphic(viewPoubelle);
            // TODO: Contrôleur

            Button btnStock = new Button();
            btnStock.setGraphic(viewStock);
            btnStock.setOnAction(new ControleurBoutonMagasinStock(this.adminScene, magasin));

            lesBtn.getChildren().addAll(btnVendeursDuMag, btnSupprimerMag, btnStock);

            ligneMag.setLeft(leMag);
            ligneMag.setRight(lesBtn);

            laListe.getChildren().add(ligneMag);
        }

        return laListe;
    }
}
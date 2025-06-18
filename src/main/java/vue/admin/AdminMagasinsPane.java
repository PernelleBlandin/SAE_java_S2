package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.admin.ControleurBoutonMagasinStock;
import controleurs.admin.ControleurBoutonMagasinVendeur;
import javafx.geometry.Insets;
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
import vue._components.alerts.AlertErreurException;

/** La pane du menu magasin */
public class AdminMagasinsPane extends VBox {
    /** La scène administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane pour afficher les magasins de la chaîne de librairie.
     * @param adminScene La scène de la page administrateur. 
     * @param modele Le modèle de données.
     */
    public AdminMagasinsPane(AdminScene adminScene, ChaineLibrairie modele) {
        this.adminScene = adminScene;
        this.modele = modele;

        this.setSpacing(50);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
                this.titre(),
                this.listeMagasins());
    }

    /**
     * Obtenir le titre du menu.
     * @return Le label du titre du menu.
     */
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
            new AlertErreurException("Impossible de récupérer les magasins.", e);
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

            // Vendeurs
            Button btnVendeursDuMag = new Button();
            btnVendeursDuMag.setGraphic(viewVendeurs);
            btnVendeursDuMag.setOnAction(new ControleurBoutonMagasinVendeur(this.adminScene, magasin));

            // Stock
            Button btnStock = new Button();
            btnStock.setGraphic(viewStock);
            btnStock.setOnAction(new ControleurBoutonMagasinStock(this.adminScene, magasin));

            // Supprimer
            Button btnSupprimerMag = new Button();
            btnSupprimerMag.setGraphic(viewPoubelle);
            // TODO: Contrôleur

            lesBtn.getChildren().addAll(btnVendeursDuMag, btnStock, btnSupprimerMag);

            ligneMag.setLeft(leMag);
            ligneMag.setRight(lesBtn);

            laListe.getChildren().add(ligneMag);
        }

        return laListe;
    }
}
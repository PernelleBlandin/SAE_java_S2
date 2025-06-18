package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.alerts.AlertErreurException;

public class AdminMagasinsStockPane extends VBox {
    private AdminScene fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin pris en paramètre */
    private Magasin magasin;

    public AdminMagasinsStockPane(AdminScene fenetrePrin, ChaineLibrairie modele, Magasin magasin) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
        this.magasin = magasin;

        this.setSpacing(50);

        this.getChildren().addAll(
            this.titre(), 
            this.barreRecherche(), 
            this.listeLivres()
        );
    }

    private Label titre() {
        Label titre = new Label("Stock - " + this.magasin.getNom());
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre;
    }

    private TextField barreRecherche() {
        TextField barreRecherche = new TextField();
        barreRecherche.setPromptText("Rechercher");
        return barreRecherche;
    }

    private VBox listeLivres() {
        VBox laListe = new VBox(10);

        List<Livre> listeLivres = new ArrayList<>();
        try {
            listeLivres = this.modele.getLivreBD().obtenirLivreDejaEnStockMagasin(magasin);
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les livres.", e);
        }

        for (Livre livre: listeLivres) {
            Image imgInfo = new Image("/images/info.png");
            ImageView viewInfo = new ImageView(imgInfo);
            Image imgSauvegarde = new Image("/images/disquette.png");
            ImageView viewSauvegarde = new ImageView(imgSauvegarde);
            viewInfo.setFitHeight(35);
            viewInfo.setFitWidth(35);

            BorderPane ligneLivre = new BorderPane();
            Label leLivre = new Label(livre.getTitre() + " - " + livre.getAuteurs());
            HBox lesBtn = new HBox(10);

            Button btnInfo = new Button();
            btnInfo.setGraphic(viewInfo);
            TextField majNbExemplaire = new TextField();
            majNbExemplaire.setPromptText("Future quantité totale");
            Button btnSauvegarde = new Button();
            btnSauvegarde.setGraphic(viewSauvegarde);

            lesBtn.getChildren().addAll(btnInfo, majNbExemplaire, btnSauvegarde);

            // btnInfo.setOnAction(new ControleurInfo());
            // btnSauveNouveauStock.setOnAction(new ControleurSauveNouveauStock());

            ligneLivre.setLeft(leLivre);
            ligneLivre.setRight(lesBtn);

            laListe.getChildren().add(ligneLivre);
        }

        return laListe;
    }
}
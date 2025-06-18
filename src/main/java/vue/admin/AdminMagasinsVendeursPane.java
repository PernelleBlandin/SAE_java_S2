package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import modeles.Vendeur;
import vue._components.alerts.AlertErreurException;

public class AdminMagasinsVendeursPane extends VBox {
    private AdminScene fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin pris en paramètre */
    private Magasin magasin;

    public AdminMagasinsVendeursPane(AdminScene fenetrePrin, ChaineLibrairie modele, Magasin magasin) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
        this.magasin = magasin;

        this.setSpacing(50);

        this.getChildren().addAll(
            this.titre(),
            this.listeVendeurs()
        );
    }

    private Label titre() {
        Label titre = new Label("Vendeurs de " + this.magasin.getNom());
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre;
    }

    private VBox listeVendeurs() {
        VBox laListe = new VBox(10);

        List<Vendeur> listeVendeurs = new ArrayList<>();
        try {
            listeVendeurs = this.modele.getVendeurBD().obtenirListeVendeurParMagasin(this.magasin.getId());
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les vendeurs.", e);
        }

        for (Vendeur vendeur: listeVendeurs) {
            Image imgPoubelle = new Image("/images/trashcan.png");
            ImageView viewPoubelle = new ImageView(imgPoubelle);
            viewPoubelle.setFitHeight(35);
            viewPoubelle.setFitWidth(35);

            BorderPane ligneVendeur = new BorderPane();
            Label leVendeur = new Label(vendeur.toString());
            HBox lesBtn = new HBox(10);

            Button btnSupprimerVendeur = new Button();
            btnSupprimerVendeur.setGraphic(viewPoubelle);

            // btnVendeursDuMag.setOnAction(new ControleurVoirVendeurs(mag));
            // btnSupprimerMag.setOnAction(new ControleurSupprMag(mag));

            ligneVendeur.setLeft(leVendeur);
            ligneVendeur.setRight(btnSupprimerVendeur);

            laListe.getChildren().add(ligneVendeur);
        }

        return laListe;
    }
}

package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.admin.ControleurBoutonRetourMagasin;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.TitleAndBackButtonPane;
import vue._components.alerts.AlertErreurException;

/** Pane affichant la liste des vendeurs d'un magasin */
public class AdminMagasinsVendeursPane extends VBox {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin pris en paramètre */
    private Magasin magasin;

    /**
     * Initialiser la pane affichant la liste des vendeurs d'un magasin.
     * @param adminScene La scène de la page administrateur.
     * @param modele Le modèle de données.
     * @param magasin Un magasin.
     */
    public AdminMagasinsVendeursPane(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;

        this.setSpacing(50);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
            this.titre(),
            this.listeVendeurs()
        );
    }

    /**
     * Obtenir le titre du menu avec un bouton "Retour".
     * @return Le titre du menu avec un bouton "Retour".
     */
    private BorderPane titre() {
        return new TitleAndBackButtonPane("Vendeurs de " + this.magasin.getNom(), new ControleurBoutonRetourMagasin(this.adminScene));
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

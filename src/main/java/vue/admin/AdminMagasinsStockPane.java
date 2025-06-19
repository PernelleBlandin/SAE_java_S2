package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.admin.ControleurBoutonEnregistrerStock;
import controleurs.admin.ControleurBoutonRetourMagasin;
import controleurs.customers.ControleurInfoLivre;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.SearchBar;
import vue._components.TitleAndBackButtonPane;
import vue._components.alerts.AlertErreurException;
import vue._components.numberField.NumberFieldDisableButton;

/** Pane des stocks d'un magasin. */
public class AdminMagasinsStockPane extends VBox {
    /** La scène adminsitrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin pris en paramètre */
    private Magasin magasin;

    /**
     * Initialiser la pane des stocks d'un magasin.
     * @param adminScene La scène administrateur.
     * @param modele Le modèle.
     * @param magasin Le magasin.
     */
    public AdminMagasinsStockPane(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;

        this.setSpacing(50);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
            this.titre(), 
            this.barreRecherche(), 
            this.listeLivres()
        );
    }

    /**
     * Obtenir le titre du menu avec un bouton "Retour".
     * @return Le titre du menu avec un bouton "Retour".
     */
    private BorderPane titre() {
        return new TitleAndBackButtonPane("Stock - " + this.magasin.getNom(), new ControleurBoutonRetourMagasin(this.adminScene));
    }

    /**
     * Obtenir la barre de recherche des livres.
     * @return La barre de recherche des livres.
     */
    private SearchBar barreRecherche() {
        SearchBar barreRecherche = new SearchBar("Rechercher ...");
        return barreRecherche;
    }

    /**
     * Obtenir le widget avec la liste des livres.
     * @return Le widget avec la liste des livres.
     */
    private VBox listeLivres() {
        VBox laListe = new VBox(10);

        List<Livre> listeLivres = new ArrayList<>();
        try {
            listeLivres = this.modele.getLivreBD().obtenirLivreDejaEnStockMagasin(magasin);
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les livres.", e.getMessage());
        }

        for (Livre livre: listeLivres) {
            // Info
            ImageView viewInfo = new ImageView(getClass().getResource("/images/info.png").toExternalForm());
            viewInfo.setFitHeight(35);
            viewInfo.setFitWidth(35);

            Button btnInfo = new Button();
            btnInfo.setGraphic(viewInfo);
            btnInfo.setOnAction(new ControleurInfoLivre(livre));

            // Sauvegarde

            ImageView viewSauvegarde = new ImageView(getClass().getResource("/images/disquette.png").toExternalForm());
            viewSauvegarde.setFitHeight(35);
            viewSauvegarde.setFitWidth(35);

            Button btnSauvegarde = new Button();
            btnSauvegarde.setDisable(true);
            btnSauvegarde.setGraphic(viewSauvegarde);

            // Number Input

            int currentStock = 0;
            try {
                currentStock = this.modele.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
            } catch (SQLException e) {
                // On ignore
            }

            NumberFieldDisableButton majNbExemplaire = new NumberFieldDisableButton(btnSauvegarde, currentStock);
            majNbExemplaire.setPrefHeight(35);
            majNbExemplaire.setPromptText("Quantité");

            BorderPane ligneLivre = new BorderPane();
            Label leLivre = new Label(livre.getTitre() + " - " + livre.joinNomAuteurs());
            HBox lesBtn = new HBox(10);

            lesBtn.getChildren().addAll(btnInfo, majNbExemplaire, btnSauvegarde);

            btnSauvegarde.setOnAction(new ControleurBoutonEnregistrerStock(this.adminScene, this.modele, magasin, livre, majNbExemplaire));

            ligneLivre.setLeft(leLivre);
            ligneLivre.setRight(lesBtn);

            laListe.getChildren().add(ligneLivre);
        }

        return laListe;
    }
}
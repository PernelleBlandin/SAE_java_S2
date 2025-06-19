package vue.admin;

import java.sql.SQLException;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.BaseListBooksPane;
import vue._components.TitleAndBackButtonPane;
import vue._components.numberField.NumberFieldDisableButton;

public class AdminMagasinsStockPane extends BaseListBooksPane {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    private ChaineLibrairie modele;
    private Magasin magasin;
    public AdminMagasinsStockPane(String titre, List<Livre> listeLivres, int nbLignes, AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        super(titre, listeLivres, nbLignes, 1);

        this.adminScene = adminScene;
        this.magasin = magasin;
        this.modele = modele;

        this.addComponents();
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public BorderPane getHeaderPane() {
        return new TitleAndBackButtonPane(this.getTitre(), new ControleurBoutonRetourMagasin(this.adminScene));
    }

    public BorderPane getBookComponent(Livre livre) {
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
        majNbExemplaire.setPrefHeight(40);
        majNbExemplaire.setPromptText("Quantité");

        BorderPane ligneLivre = new BorderPane();
        ligneLivre.setPadding(new Insets(5));
        ligneLivre.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        Label leLivre = new Label(livre.getTitre() + " - " + livre.joinNomAuteurs());
        leLivre.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        
        HBox lesBtn = new HBox(10);
        lesBtn.getChildren().addAll(btnInfo, majNbExemplaire, btnSauvegarde);

        btnSauvegarde.setOnAction(new ControleurBoutonEnregistrerStock(this.adminScene, this.modele, magasin, livre, majNbExemplaire));

        ligneLivre.setLeft(leLivre);
        ligneLivre.setRight(lesBtn);

        return ligneLivre;
    }
}

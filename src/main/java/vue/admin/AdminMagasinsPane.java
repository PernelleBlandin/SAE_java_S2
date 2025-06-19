package vue.admin;

import java.util.List;

import controleurs.admin.ControleurBoutonMagasinStock;
import controleurs.admin.ControleurBoutonMagasinVendeur;
import controleurs.admin.ControleurBoutonSupprimerMagasin;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue._components.BaseListElementsWithSearchPane;

/** Liste des magasins à afficher à l'administrateur */
public class AdminMagasinsPane extends BaseListElementsWithSearchPane<Magasin> {
    /**  La scène admin */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane des magasins pour l'admin.
     * @param listeMagasins La liste des magasins à afficher.
     * @param adminScene La scène de l'admin.
     * @param modele Le modèle de la librairie.
     */
    public AdminMagasinsPane(List<Magasin> listeMagasins, AdminScene adminScene, ChaineLibrairie modele) {
        super("Magasins", listeMagasins, 5, 1, "Rechercher un magasin...");

        this.adminScene = adminScene;
        this.modele = modele;

        this.addComponents();
    }

    @Override
    /**
     * Ajouter les composants à la pane.
     */
    public void addComponents() {
        this.getChildren().addAll(
            this.getHeaderPane(),
            this.searchBar,
            this.listeElements.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()
        );
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public Label getHeaderPane() {
        Label titre = new Label("Magasins");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre;
    }

    /**
     * Obtenir le composant représentant un magasin.
     * @param magasin Le magasin à représenter.
     * @return Un BorderPane contenant le nom du magasin et les boutons pour gérer le magasin
     */
    public BorderPane getElementComponent(Magasin magasin) {
        ImageView viewStock = new ImageView(getClass().getResource("/images/book.png").toExternalForm());
        ImageView viewVendeurs = new ImageView(getClass().getResource("/images/multiple_sellers_silhouette.png").toExternalForm());
        ImageView viewPoubelle = new ImageView(getClass().getResource("/images/trashcan.png").toExternalForm());

        viewVendeurs.setFitHeight(35);
        viewVendeurs.setFitWidth(35);
        viewPoubelle.setFitHeight(35);
        viewPoubelle.setFitWidth(35);
        viewStock.setFitHeight(35);
        viewStock.setFitWidth(35);

        BorderPane ligneMag = new BorderPane();
        ligneMag.setPadding(new Insets(10, 10, 10, 10));
        ligneMag.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        HBox lesBtn = new HBox(10);
        Label leMag = new Label(magasin.toString());

        // Vendeurs
        Button btnVendeursDuMag = new Button();
        btnVendeursDuMag.setGraphic(viewVendeurs);
        btnVendeursDuMag.setOnAction(new ControleurBoutonMagasinVendeur(this.adminScene, magasin));

        // Stock
        Button btnStock = new Button();
        btnStock.setGraphic(viewStock);
        btnStock.setOnAction(new ControleurBoutonMagasinStock(this.adminScene, this.modele, magasin));

        // Supprimer
        Button btnSupprimerMag = new Button();
        btnSupprimerMag.setGraphic(viewPoubelle);
        btnSupprimerMag.setOnAction(new ControleurBoutonSupprimerMagasin(this.adminScene, this.modele, magasin));

        lesBtn.getChildren().addAll(btnVendeursDuMag, btnStock, btnSupprimerMag);

        ligneMag.setLeft(leMag);
        ligneMag.setRight(lesBtn);

        return ligneMag;
    }    
}
package vue._components;

import java.sql.SQLException;
import java.util.List;

import controleurs.admin.ControleurBoutonEnregistrerStock;
import controleurs.customers.ControleurInfoLivre;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import vue.SceneGestionStockInterface;
import vue._components.numberField.NumberFieldDisableButton;

/** Pane pour l'affichage du stock magasin */
public abstract class BaseMagasinsStockPane extends BaseListElementsWithSearchPane<Livre> {
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin */
    private Magasin magasin;

    /** La scène de gestion de stock */
    private SceneGestionStockInterface scene;

    /**
     * Constructeur de la pane des livres en stock d'un magasin.
     * @param listeLivres La liste des livres à afficher dans la pane.
     * @param nbLignes Le nombre de lignes à afficher dans la liste.
     * @param adminScene La scène de la page administrateur.
     * @param modele Le modèle de la librairie.
     * @param magasin Le magasin pour lequel on affiche les stocks des livres.
     */
    public BaseMagasinsStockPane(List<Livre> listeLivres, int nbLignes, ChaineLibrairie modele, SceneGestionStockInterface scene, Magasin magasin) {
        super("Livres en stock", listeLivres, nbLignes, 1, "Rechercher un livre à modifier...");

        this.magasin = magasin;
        this.scene = scene;
        this.modele = modele;
    }

    /**
     * Obtenir le magasin. 
     * @return Le magasin.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Obtenir la scène de la page.
     * @return La scène de la page de gestion des stocks.
     */
    public SceneGestionStockInterface getPageScene() {
        return this.scene;
    }

    /**
     * Obtenir le titre.
     * @return Le titre de la pane.
     */
    public abstract Node getHeaderPane();

    /**
     * Obtenir le composant d'un élément de la liste.
     * @param livre Le livre à afficher.
     * @return Un BorderPane contenant le titre du livre, un bouton pour afficher les informations
     */
    public BorderPane getElementComponent(Livre livre) {
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

        btnSauvegarde.setOnAction(new ControleurBoutonEnregistrerStock(this.scene, this.modele, magasin, livre, majNbExemplaire));

        ligneLivre.setLeft(leLivre);
        ligneLivre.setRight(lesBtn);

        return ligneLivre;
    }
}

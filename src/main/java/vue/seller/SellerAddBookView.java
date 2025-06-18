package vue.seller;

import java.sql.SQLException;

import controleurs.ControleurDeconnexion;
import controleurs.customers.ControleurCustomerRecherche;
import controleurs.seller.ControleurAcceuilVendeur;
import controleurs.seller.ControleurPage;
import controleurs.seller.ControleurValider;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Vendeur;
import vue.AppIHM;
import vue._components.SearchBar;
/**
 * La vue de "ajouter un livre"
 */
public class SellerAddBookView{

    /**
     * La vue principal
     */
    private AppIHM app;
    /**
     * Le modèle
     */
    private ChaineLibrairie modele;

    /**
     * La scène de la vue
     */
    private Scene scene;

    /**
     * La scène principal
     */
    private BorderPane root;
    /**
     * VBox Central
     */
    private HBox center;
    /**
     * VBox a gauche
     */
    private VBox left;
    private TextField idLivre = new TextField();
    private TextField titreLivre = new TextField();
    private TextField prix = new TextField();  // TODO : autoriser seulement des int/Integer
    private TextField anneePubli = new TextField();
    private TextField nomAuteur = new TextField();
    private TextField deces = new TextField();
    private TextField naissance = new TextField();
    private TextField idAuteur = new TextField();
    private TextField editeur = new TextField();
    private TextField classification = new TextField();
    private TextField idClassification = new TextField();
    private TextField nbPages = new TextField();
    private int cpt = 0;

    /**
     * Initialiser la vue de l'accueil d'un vendeur.
     *
     * @param app La vue principal.
     * @param modele Le modèle.
     */
    public SellerAddBookView(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        HBox header = this.getHeader();
        this.root.setTop(header);

        this.center = this.getCenter();
        this.root.setCenter(center);

        this.left = this.getLeft();
        this.root.setLeft(left);

        this.scene = new Scene(this.root);
    }

       /**
     * Obtenir le header du menu vendeur.
     *
     * @return Le header du menu vendeur.
     */
    public HBox getHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView("/images/logo.png");
        logo.setFitWidth(304.6);
        logo.setFitHeight(91.2);

        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setGraphic(logo);
        buttonLogo.setOnAction(new ControleurAcceuilVendeur(this.app));

        TextField searchBar = new SearchBar("Rechercher un livre...");

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));

        header.getChildren().addAll(buttonLogo, searchBar, deconnexionButton);

        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 0));

        return header;
    }

     /**
     * Obtenir l'élement central.
     *
     * @return L'élement central de la page.
     */
    public HBox getCenter() {
        VBox centerLeft = new VBox();
        VBox centerRight = new VBox();
        HBox center = new HBox();

        Label titre1 = new Label("Identifiant du livre :");
    
        Label titre2 = new Label("Titre du livre :");

        Label titre3 = new Label("Nombre de pages :");

        Label titre4 = new Label("Année de publication :");

        Label titre5 = new Label("Nom de l'auteur :");

        Label titre6 = new Label("Date de naissance de l'auteur :");

        Label titre7 = new Label("Date de décès de l'auteur (-1 si toujours en vie) :");

        Label titre8 = new Label("Identifiant de l'auteur :");

        Label titre9 = new Label("Nom de l'éditeur :");

        Label titre10 = new Label("Nom de classification :");

        Label titre11 = new Label("Identifiant de classification :");

        if(this.cpt == 0) {
            idAuteur.setDisable(true);
            idClassification.setDisable(true);
        }
        Button valider = new Button("Valider");
        valider.setOnAction(new ControleurValider(this));

        centerLeft.getChildren().addAll(
            titre1, this.idLivre,
            titre2, this.titreLivre,
            titre3, this.nbPages,
            titre4, this.anneePubli,
            titre5, this.nomAuteur,
            titre6, this.naissance
        );
        centerLeft.setSpacing(10);
        centerLeft.setPadding(new Insets(10, 20, 10, 20));
        centerRight.getChildren().addAll(
            titre7, this.deces,
            titre8, this.idAuteur,
            titre9, this.editeur,
            titre10, this.classification,
            titre11, this.idClassification,
            valider
        );
        centerRight.setSpacing(10);
        centerRight.setPadding(new Insets(10, 20, 10, 20));
        center.getChildren().addAll(centerLeft, centerRight);
        center.setSpacing(10);
        center.setPadding(new Insets(20, 20, 10, 20));
        return center;
    }

    public String classificationExistante(String titre){
        String res = null;
        try {
            res = this.modele.getLivreBD().getIdDewey(titre);
        } catch (SQLException e) {
            // TODO erreur handle exception
        }
        return res;
    }

    /**
     * Obtenir l'élement de gauche.
     *
     * @return L'élement a gauche de la page.
     */
    public VBox getLeft() {
        VBox left = new VBox(70);

        Button ajouterLivre = new Button("Ajouter un livre");
        ajouterLivre.setOnAction(new ControleurPage(this.app));
        ajouterLivre.setStyle("-fx-background-color: #808080");

        Button supprimerLivre = new Button("Supprimer un livre");
        supprimerLivre.setOnAction(new ControleurPage(this.app));

        Button majQteLivre = new Button("Mettre à jour la quantité d'un livre");
        majQteLivre.setOnAction(new ControleurPage(this.app));

        Button transfertLivre = new Button("Transférer un livre");
        transfertLivre.setOnAction(new ControleurPage(this.app));

        Button rpClient = new Button("Agir en tant que client");
        rpClient.setOnAction(new ControleurPage(this.app));

        left.getChildren().addAll(ajouterLivre, supprimerLivre, majQteLivre, transfertLivre, rpClient);
        left.setPadding(new Insets(30, 50, 0, 15));
        left.setAlignment(Pos.TOP_CENTER);
        return left;
    }

    /**
     * Mettre à jour l'affichage
     */
    public void miseAJourAffichage() {
        this.center = this.getCenter();
        this.left = this.getLeft();
        this.root.setLeft(this.left);
        this.root.setCenter(this.center);
        this.cpt++;
    }
    
    /**
     * Obtenir la scène
     *
     * @return La scène
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Obtenir la scène
     *
     * @return La scène
     */
    public AppIHM getApp() {
        return this.app;
    }

    public void reset(){
        this.cpt = 0;
        this.idLivre.setText("");
        this.titreLivre.setText("");
        this.prix.setText("");
        this.anneePubli.setText("");
        this.nbPages.setText("");
        this.nomAuteur.setText("");
        this.naissance.setText("");
        this.deces.setText("");
        this.idAuteur.setText("");
        this.editeur.setText("");
        this.classification.setText("");
        this.idClassification.setText("");
    }

    public Alert popUpLivreAjoute() {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Livre Express");
        alerte.setHeaderText("Livre ajouté avec succès");
        return alerte;
    }

    public TextField getIdLivre(){ 
        return this.idLivre;
    }

    public TextField getTitreLivre(){
        return this.titreLivre;
    }

    public TextField getPrix(){
        return this.prix;
    }

    public TextField getAnneePubli(){
        return this.anneePubli;
    }

    public TextField getNomAuteur(){
        return this.nomAuteur;
    }

    public TextField getNaissance(){
        return this.naissance;
    }

    public TextField getDeces(){
        return this.deces;
    }

    public TextField getIdAuteur(){
        return this.idAuteur;
    }
    
    public TextField getEditeur(){
        return this.editeur;
    }

    public TextField getClassification(){
        return this.classification;
    }

    public TextField getIdClassification(){
        return this.idClassification;
    }
    
    public TextField getNbPages() {
        return this.nbPages;
    }
    
    public ChaineLibrairie getModele(){
        return this.modele;
    }
}

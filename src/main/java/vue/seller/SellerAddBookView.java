package vue.seller;

import java.sql.SQLException;

import controleurs.ControleurDeconnexion;
import controleurs.ControleurPage;
import controleurs.seller.ControleurAcceuilVendeur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

        SearchBar searchBar = new SearchBar("Rechercher un livre...");

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
        Vendeur vendeur = this.modele.getVendeurActuel();

        Label titre1 = new Label("Identifiant du livre :");
        TextField idLivre  =  new TextField();

        Label titre2 = new Label("Titre du livre :");
        TextField titreLivre = new TextField();

        Label titre3 = new Label("Nombre de pages :");
        TextField prix = new TextField();
        // TODO autoriser seulement des int/Integer
        
        Label titre4 = new Label("Année de publication");
        TextField anneePubli = new TextField();

        Label titre5 = new Label("Nom, année naissance et décès (-1 si vivant) de l'auteur\n(séparés par des virgules)");
        TextField auteur = new TextField();

        Label titre6 = new Label("Identifiant de l'auteur");

        TextField idAuteur = new TextField();
        idAuteur.setDisable(true);
        if(auteur.getText()!=null){
            String[] infoAuteurs = auteur.getText().split(",");
            if(infoAuteurs.length==3){
                for (int i = 0; i < infoAuteurs.length; i++) {
                    infoAuteurs[i] = infoAuteurs[i].trim();
                }
                if (infoAuteurs.length != 3) {
                    // TODO erreur handle exception
                }
                String auteurNom = infoAuteurs[0];
                int naissance = Integer.parseInt(infoAuteurs[1]);
                int deces = Integer.parseInt(infoAuteurs[2]);
                String auteurExistant;
                try {
                    auteurExistant = this.modele.getLivreBD().getIdAuteur(auteurNom);
                    if (auteurExistant == null){
                        idAuteur.setDisable(true);
                    }else{
                        idAuteur.setDisable(false);
                    }
                }catch (SQLException e){
                    // TODO erreur base de donnée
                }
            }
        }

        Label titre7 = new Label("Nom de l'éditeur");
        TextField editeur = new TextField();

        Label titre8 = new Label("Nom de classification");
        TextField classification = new TextField();

        Label titre9 = new Label("Identifiant de classification");
        TextField idClassification = new TextField();
        
        String classificationExistante;
        if(classification.getText()!=null){
            try {
                classificationExistante = this.modele.getLivreBD().getIdDewey(titre8.getText());
                if (classificationExistante == null) {
                    idClassification.setDisable(true);
                }
                else{
                    idClassification.setDisable(false);
                }
            } catch (SQLException e) {
                // TODO erreur handle exception
            }
        }

        Button valider = new Button("Valider");

        centerLeft.getChildren().addAll(titre1, idLivre, titre2, titreLivre, titre3, prix, titre4, anneePubli, titre5, auteur, titre7, editeur, titre8, classification);
        centerLeft.setSpacing(10);
        centerLeft.setPadding(new Insets(10, 20, 10, 20));
        centerRight.getChildren().addAll(titre6, idAuteur, titre9, idClassification, valider);
        centerRight.setSpacing(10);
        centerRight.setPadding(new Insets(10, 20, 10, 20));
        center.getChildren().addAll(centerLeft, centerRight);
        center.setSpacing(10);
        center.setPadding(new Insets(20, 20, 10, 20));
        return center;
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
}
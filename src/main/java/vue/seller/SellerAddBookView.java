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
    private TextField auteur = new TextField();
    private TextField idAuteur = new TextField();
    private TextField editeur = new TextField();
    private TextField classification = new TextField();
    private TextField idClassification = new TextField();

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
        Vendeur vendeur = this.modele.getVendeurActuel();

        Label titre1 = new Label("Identifiant du livre :");
    
        Label titre2 = new Label("Titre du livre :");

        Label titre3 = new Label("Nombre de pages :");

        Label titre4 = new Label("Année de publication");

        Label titre5 = new Label("Nom, année naissance et décès (-1 si vivant) de l'auteur\n(séparés par des virgules)");

        Label titre6 = new Label("Identifiant de l'auteur");

        this.idAuteur.setDisable(true);
        String auteurNom = null;
        int naissance = -1;
        int deces = -1;
        if(auteur.getText()!=null){
            String[] infoAuteurs = auteur.getText().split(",");
            if(infoAuteurs.length==3){
                for (int i = 0; i < infoAuteurs.length; i++) {
                    infoAuteurs[i] = infoAuteurs[i].trim();
                }
                if (infoAuteurs.length != 3) {
                    // TODO erreur handle exception
                }
                auteurNom = infoAuteurs[0];
                naissance = Integer.parseInt(infoAuteurs[1]);
                deces = Integer.parseInt(infoAuteurs[2]);
                String auteurExistant;
                try {
                    auteurExistant = this.modele.getLivreBD().getIdAuteur(auteurNom);
                    if (auteurExistant == null){
                        this.idAuteur.setDisable(false);
                    }
                }catch (SQLException e){
                    // TODO erreur base de donnée
                }
            }
        }

        Label titre7 = new Label("Nom de l'éditeur");

        Label titre8 = new Label("Nom de classification");

        Label titre9 = new Label("Identifiant de classification");
        this.idClassification.setDisable(true);
        String classificationExistante;
        if(classification.getText()!=null && classification.getText().length()!=0){
            try {
                classificationExistante = this.modele.getLivreBD().getIdDewey(this.classification.getText());
                if (classificationExistante == null) {
                    this.idClassification.setDisable(false);
                }
            } catch (SQLException e) {
                // TODO erreur handle exception
            }
        }

        Button valider = new Button("Valider");
        valider.setOnAction(new ControleurValider(this));
        if (this.idLivre.getText().length() != 0 && this.titreLivre.getText().length() != 0 && this.prix.getText().length() != 0 && this.anneePubli.getText().length() != 0 && this.auteur.getText().length() != 0 && this.editeur.getText().length() != 0 && this.classification.getText().length() != 0) {
            String isbn = this.idLivre.getText();
            String titre = this.titreLivre.getText();
            int nbPages = 0;
            int anneeDePublication = 0;
            double prix = 0.0;
            try {
                nbPages = Integer.parseInt(this.prix.getText());
                anneeDePublication = Integer.parseInt(this.anneePubli.getText());
                prix = Double.parseDouble(this.prix.getText());
            } catch (NumberFormatException e) {
                // TODO erreur handle exception
            }
            String editeurNom = this.editeur.getText();
            String classificationNom = this.classification.getText();
            String idClassifications = this.idClassification.getText();
            String idAuteur = this.idAuteur.getText();
            try {
                if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && this.modele.getLivreBD().getIdDewey(this.classification.getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, idClassifications, naissance, deces);
                    popUpLivreAjoute().showAndWait();
                    reset();
                } else

                if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && this.modele.getLivreBD().getIdDewey(this.classification.getText()) != null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, naissance, deces);
                    popUpLivreAjoute().showAndWait();
                    reset();
                } else 
                
                if (this.modele.getLivreBD().getIdAuteur(auteurNom) != null && this.modele.getLivreBD().getIdDewey(this.classification.getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idClassifications);
                    popUpLivreAjoute().showAndWait();
                    reset();
                } else {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom);
                    popUpLivreAjoute().showAndWait();
                    reset();
                }
            } catch (SQLException e) {
                // TODO erreur handle exception
            }
        }

        centerLeft.getChildren().addAll(titre1, this.idLivre, titre2, this.titreLivre, titre3, this.prix, titre4, this.anneePubli, titre5, this.auteur, titre7, this.editeur, titre8, this.classification);
        centerLeft.setSpacing(10);
        centerLeft.setPadding(new Insets(10, 20, 10, 20));
        centerRight.getChildren().addAll(titre6, this.idAuteur, titre9, this.idClassification, valider);
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
        this.idLivre.setText("");
        this.titreLivre.setText("");
        this.prix.setText("");
        this.anneePubli.setText("");
        this.auteur.setText("");
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
}
package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurAcceuilVendeur;
import controleurs.ControleurDeconnexion;
import controleurs.ControleurVoirPlusSeller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue.AppIHM;
import vue.BibliothequeComposants;
import vue.MAJVueInterface;
import vue._components.BookCardComponentSeller;

/**
 * La vue de l'accueil d'un vendeur
 */
public class SellerHomeView implements MAJVueInterface {

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
    private VBox center;
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
    public SellerHomeView(AppIHM app, ChaineLibrairie modele) {
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
        Button boutonLogo = new Button();
        boutonLogo.setGraphic(logo);
        boutonLogo.setOnAction(new ControleurAcceuilVendeur(this.app));

        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setGraphic(logo);

        TextField searchBar = BibliothequeComposants.getSearchBar("Rechercher un livre...");
        HBox.setHgrow(searchBar, Priority.ALWAYS);

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));

        header.getChildren().addAll(buttonLogo, searchBar, deconnexionButton);

        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 0));

        return header;
    }

    /**
     * Obtenir l'élement de gauche.
     *
     * @return L'élement a gauche de la page.
     */
    public VBox getLeft() {
        VBox left = new VBox(70);

        Button ajouterLivre = new Button("Ajouter un livre");
        // ajouterLivre.setOnAction(new ControleurAjouterLivre(this.app));

        Button supprimerLivre = new Button("Supprimer un livre");
        // supprimerLivre.setOnAction(new ControleurSupprimerLivre(this.app));

        Button majQteLivre = new Button("Mettre à jour la quantité d'un livre");
        // majQteLivre.setOnAction(new ControleurMajQteLivre(this.app));

        Button transfertLivre = new Button("Transférer un livre");
        // transfertLivre.setOnAction(new ControleurTransfertLivre(this.app));

        Button rpClient = new Button("Agir en tant que client");
        // rpClient.setOnAction(new ControleurAgirCommeClient(this.app));

        left.getChildren().addAll(ajouterLivre, supprimerLivre, majQteLivre, transfertLivre, rpClient);
        left.setPadding(new Insets(30, 50, 0, 15));

        return left;
    }

    /**
     * Obtenir l'élement central.
     *
     * @return L'élement central de la page.
     */
    public VBox getCenter() {
        VBox center = new VBox();

        Vendeur vendeur = this.modele.getVendeurActuel();

        // Début
        Label welcome = new Label(String.format("Bienvenue %s ! 👋", vendeur.toString()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);

        Label magasinLabel = new Label("Votre magasin");
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        ComboBox<Magasin> magasinComboBox = new ComboBox<>();
        magasinComboBox.setValue(vendeur.getMagasin());
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);

        // Meilleures ventes
        VBox meilleuresVentesVBox = this.getMeilleuresVentes();

        center.getChildren().addAll(welcome, magasinLabel, magasinComboBox, meilleuresVentesVBox);

        center.setSpacing(10);
        center.setPadding(new Insets(10, 20, 10, 20));

        return center;
    }

    /**
     * Obtenir le titre d'une section, avec un bouton "Voir tout".
     *
     * @param title Le titre de la section.
     * @return La section, avec son titre et un bouton "Voir tout".
     */
    private GridPane getSectionTitle(String title) {
        List<Livre> listeLivres = new ArrayList<>();
        try {
            listeLivres = this.modele.getLivreBD().obtenirLivresMeilleuresVentes();
        } catch (SQLException e) {
            // TODO: handle exception
        }
        GridPane section = new GridPane();
        section.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        section.add(titleLabel, 0, 0);

        Button viewAllButton = new Button("Voir tout");
        viewAllButton.setOnAction(new ControleurVoirPlusSeller(this, listeLivres));
        GridPane.setHalignment(viewAllButton, HPos.RIGHT);
        section.add(viewAllButton, 1, 0);
        return section;
    }

    /**
     * Obtenir la VBox des meilleures ventes.
     *
     * @return La VBox des meilleures ventes.
     */
    private VBox getMeilleuresVentes() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        GridPane recommendationsVBox = this.getSectionTitle("Meilleures Ventes");

        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.getLivreBD().obtenirLivresMeilleuresVentes();
        } catch (SQLException e) {
            // TODO: handle exception
        }

        HBox topLivresVentes = new HBox();
        topLivresVentes.setSpacing(20);

        for (int i = 0; i < livres.size() && i < 5; i++) {
            Livre livre = livres.get(i);
            BorderPane bookCard = new BookCardComponentSeller(livre, 3);
            HBox.setHgrow(bookCard, Priority.ALWAYS);
            topLivresVentes.getChildren().add(bookCard);
        }
        vbox.getChildren().addAll(recommendationsVBox, topLivresVentes);
        return vbox;
    }

    public void showListBooks(String titre, List<Livre> listeLivres) {
        SellerListBook vue = new SellerListBook(this, this.modele, titre, listeLivres );
        this.app.getPrimaryStage().setScene(vue.getScene());
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
     * Obtenir la scène de l'accueil d'un vendeur.
     *
     * @return La scène de l'accueil d'un vendeur.
     */
    public Scene getScene() {
        return this.scene;
    }

    public AppIHM getApp() {
        return this.app;
    }
}

package vue.customers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurDeconnexion;
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
import modeles.Client;
import modeles.Livre;
import modeles.Magasin;
import vue.AppIHM;
import vue.BibliothequeComposants;
import vue.MAJVueInterface;

/** La vue de l'accueil d'un client */
public class CustomerHomeView implements MAJVueInterface {
    /** La vue principal */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    /** VBox Central */
    private VBox center;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param app La vue principal.
     * @param modele Le modèle.
     */
    public CustomerHomeView(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        HBox header = this.getHeader();
        this.root.setTop(header);

        this.center = this.getCenter();
        this.root.setCenter(center);
        
        this.scene = new Scene(this.root);
    }

    /**
     * Obtenir le header du menu client.
     * @return Le header du menu client.
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
        
        TextField searchBar = BibliothequeComposants.getSearchBar("Rechercher un livre...");
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        
        Button panierButton = new Button("Panier");
        panierButton.setMinSize(120, 50);
        
        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));
        
        header.getChildren().addAll(buttonLogo, searchBar, panierButton, deconnexionButton);
        
        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 20));

        return header;
    }

    /**
     * Obtenir l'élement central.
     * @return L'élement central de la page.
     */
    public VBox getCenter() {
        VBox center = new VBox();

        Client client = this.modele.getClientActuel();

        // Début
        Label welcome = new Label(String.format("Bienvenue %s ! 👋", client.toString()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);

        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            // TODO: handle exception
        }

        Label magasinLabel = new Label("Votre magasin");
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        ComboBox<Magasin> magasinComboBox = new ComboBox<>();
        magasinComboBox.getItems().addAll(listeMagasins);
        magasinComboBox.setValue(client.vendeur());
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);

        // Recommendations
        VBox recommendationsVBox = this.getRecommendations();

        // Meilleures ventes
        VBox meilleuresVentesVBox = this.getMeilleuresVentes();

        center.getChildren().addAll(welcome, magasinLabel, magasinComboBox, recommendationsVBox, meilleuresVentesVBox);
        
        center.setSpacing(10);
        center.setPadding(new Insets(10, 20, 10, 20));

        return center;
    }

    /**
     * Obtenir le titre d'une section, avec un bouton "Voir tout".
     * @param title Le titre de la section.
     * @return La section, avec son titre et un bouton "Voir tout".
     */
    private GridPane getSectionTitle(String title) {
        GridPane section = new GridPane();
        section.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        section.add(titleLabel, 0, 0);
        
        Button viewAllButton = new Button("Voir tout");
        GridPane.setHalignment(viewAllButton, HPos.RIGHT);
        section.add(viewAllButton, 1, 0);
        
        return section;
    }

    /**
     * Obtenir la VBox des recommendations.
     * @return La VBox des recommendations.
     */
    private VBox getRecommendations() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        GridPane recommendationsVBox = this.getSectionTitle("Livre Express vous recommande");

        Client client = this.modele.getClientActuel();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.onVousRecommande(client);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        HBox livresRecommendes = new HBox();
        livresRecommendes.setSpacing(20);

        try {
            for (int i = 0; i < livres.size() && i < 5; i++) {
                Livre livre = livres.get(i);
                BorderPane bookCard = BibliothequeComposants.getBookCard(livre, this.modele, this);
                
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                livresRecommendes.getChildren().add(bookCard);
            }
        } catch (SQLException e) {
            // TODO: handle exception
        }

        vbox.getChildren().addAll(recommendationsVBox, livresRecommendes);
        return vbox;
    }

    /**
     * Obtenir la VBox des meilleures ventes.
     * @return La VBox des meilleures ventes.
     */
    private VBox getMeilleuresVentes() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        GridPane recommendationsVBox = this.getSectionTitle("Meilleures Ventes");

        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.getLivreBD().obtenirLivresMeilleuresVentes(5);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        HBox topLivresVentes = new HBox();
        topLivresVentes.setSpacing(20);

        try {
            for (int i = 0; i < livres.size() && i < 5; i++) {
                Livre livre = livres.get(i);
                BorderPane bookCard = BibliothequeComposants.getBookCard(livre, this.modele, this);
                
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                topLivresVentes.getChildren().add(bookCard);
            }
        } catch (SQLException e) {
            // TODO: handle exception
        }

        vbox.getChildren().addAll(recommendationsVBox, topLivresVentes);
        return vbox;
    }

    /**
     * Mettre à jour l'affichage
     */
    public void miseAJourAffichage() {
        this.center = this.getCenter();
        this.root.setCenter(this.center);
    }

    /**
     * Obtenir la scène de l'accueil d'un client.
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}

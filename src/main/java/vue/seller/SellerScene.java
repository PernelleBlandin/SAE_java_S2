package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import controleurs.ControleurAcceuil;
import controleurs.ControleurDeconnexion;
import controleurs.ControleurRecherche;
import controleurs.seller.ControleurMenuVendeur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue.AppIHM;
import vue.SceneListBooksInterface;
import vue._components.MenuAsidePane;
import vue._components.SearchBar;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookInfoCardComponent;

/** La scène du client */
public class SellerScene implements SceneListBooksInterface {
    /** La vue principale */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    /** La liste des "cartes" des livres */
    private HashMap<Livre, SellerBookInfoCardComponent> bookCards;

    /** Le header */
    private HBox header;

    /** Le pane de la page d'accueil */
    private SellerHomePane homePane;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param app La vue principale.
     * @param modele Le modèle.
     */
    public SellerScene(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");
        
        this.bookCards = new HashMap<>();

        this.header = this.getHeader();
        this.root.setTop(this.header);

        // Pour des questions de performance, on l'initialise qu'une seule fois
        this.homePane = new SellerHomePane(this, this.modele);
        this.root.setCenter(this.homePane);

        List<String> nomsMenu = new ArrayList<>(Arrays.asList(
            "Ajouter un livre",
            "Supprimer un livre",
            "Mettre à jour la quantité d'un livre",
            "Transférer un livre",
            "Agir en tant que client"
        ));
        this.root.setLeft(new MenuAsidePane(nomsMenu, new ControleurMenuVendeur(this, this.modele)));

        this.scene = new Scene(this.root);
    }

    /**
     * Obtenir le header du menu client.
     * @return Le header du menu client.
     */
    public HBox getHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        
        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(3046 / 15);
        logo.setFitHeight(912 / 15);
        
        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setOnAction(new ControleurAcceuil(this));
        buttonLogo.setGraphic(logo);
        
        TextField searchBar = new SearchBar("Rechercher un livre...");
        searchBar.setOnKeyTyped(new ControleurRecherche(this, this.modele));
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        
        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(70, 30);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app, this.modele));
        
        header.getChildren().addAll(buttonLogo, searchBar, deconnexionButton);
        
        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 20));

        return header;
    }

    /**
     * Afficher la page d'accueil du client.
     */
    public void showHome() {
        this.homePane.miseAJourAffichage();
        this.root.setCenter(this.homePane);
    }

    public void showAddBook() {
        SellerAddBookPane sellerAddBookPane = new SellerAddBookPane(this.modele);
        this.root.setCenter(sellerAddBookPane);
    }

    public void showDeleteBook(List<Livre> listeLivres) {
        SellerDeleteBookListPane sellerDeleteBookPane = new SellerDeleteBookListPane(listeLivres, this, this.modele);
        this.root.setCenter(sellerDeleteBookPane);
    }

    /**
     * Afficher la page affichant la liste de livres.
     * @param titre Le titre du menu.
     * @param listeLivres Une liste de livres.
     */
    public void showListBooks(String titre, List<Livre> listeLivres) {
        SellerListBooksPane sellerListBooksPane = new SellerListBooksPane(titre, listeLivres, this);
        this.root.setCenter(sellerListBooksPane);
    }

    /**
     * Créer ou obtenir la "carte" d'un livre, affichant les informations de ce dernier.
     * @param livre Un livre.
     * @return La carte du livre.
     */
    public SellerBookInfoCardComponent createOrGetCardComponent(Livre livre) {
        if (this.bookCards.containsKey(livre)) {
            return this.bookCards.get(livre);
        }

        Vendeur vendeur = this.modele.getVendeurActuel();
        Magasin magasin = vendeur.getMagasin();

        int quantiteStock = 0;
        try {
            quantiteStock = this.modele.getMagasinBD().obtenirStockLivre(vendeur.getMagasin().getId(), livre.getISBN());
        } catch (SQLException e) {
            new AlertErreurException("Le stock dans le magasin n'a pas pu être récupéré.", e.getMessage());
        }

        int nbVentes = 0;
        try {
            nbVentes = this.modele.getLivreBD().getNombreVentesLivreMagasin(livre.getISBN(), magasin.getId());
        } catch (SQLException e) {
            new AlertErreurException("Le nombre de ventes du livre n'a pas pu être récupéré.", e.getMessage());
        }

        SellerBookInfoCardComponent bookCard = new SellerBookInfoCardComponent(livre, quantiteStock, nbVentes);
        this.bookCards.put(livre, bookCard);

        return bookCard;
    }

    /**
     * Obtenir la scène de l'accueil d'un client.
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}


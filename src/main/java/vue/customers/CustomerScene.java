package vue.customers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import controleurs.ControleurDeconnexion;
import controleurs.customers.ControleurAcceuilClient;
import controleurs.customers.ControleurBoutonPanier;
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
import modeles.Client;
import modeles.DetailLivre;
import modeles.Livre;
import modeles.LivreIntrouvableException;
import vue.AppIHM;
import vue._components.SearchBar;
import vue._components.bookCard.CustomerBookCardComponent;

public class CustomerScene {
    /** La vue principal */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    private HashMap<Livre, CustomerBookCardComponent> bookCards;

    /** Le header */
    private HBox header;

    private CustomerHomePane homePane;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param app La vue principal.
     * @param modele Le modèle.
     */
    public CustomerScene(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");
        
        this.bookCards = new HashMap<>();

        this.header = this.getHeader();
        this.root.setTop(this.header);

        // Pour des questions de performance, on l'initialise qu'une seule fois
        this.homePane = new CustomerHomePane(this, this.modele);
        this.root.setCenter(this.homePane);

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
        logo.setFitWidth(3046 / 15);
        logo.setFitHeight(912 / 15);
        
        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setOnAction(new ControleurAcceuilClient(this));
        buttonLogo.setGraphic(logo);
        
        TextField searchBar = new SearchBar("Rechercher un livre...");
        HBox.setHgrow(searchBar, Priority.ALWAYS);

        Button panierButton = new Button("Panier");
        panierButton.setOnAction(new ControleurBoutonPanier(this));
        panierButton.setMinSize(70, 30);
        
        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(70, 30);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));
        
        header.getChildren().addAll(buttonLogo, searchBar, panierButton, deconnexionButton);
        
        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 20));

        return header;
    }

    public void showHome() {
        this.homePane.miseAJourAffichage();
        this.root.setCenter(this.homePane);
    }

    /**
     * Afficher le panier du client.
     */
    public void showPanier() {
        CustomerPanierPane customerPanierPane = new CustomerPanierPane(this, this.modele);
        this.root.setCenter(customerPanierPane);
    }

    /**
     * Afficher la page affichant la liste de livres.
     * @param titre Le titre du menu.
     * @param listeLivres Une liste de livres.
     */
    public void showListBooks(String titre, List<Livre> listeLivres) {
        CustomerListBooksPane customerListBookPane = new CustomerListBooksPane(titre, listeLivres, this);
        this.root.setCenter(customerListBookPane);
    }

    public CustomerBookCardComponent createOrGetCardComponent(Livre livre) {
        if (this.bookCards.containsKey(livre)) {
            return this.bookCards.get(livre);
        }

        Client client = this.modele.getClientActuel();

        int quantiteStock = 0;
        try {
            quantiteStock = this.modele.getMagasinBD().obtenirStockLivre(client.getMagasin().getId(), livre.getISBN());
            if (quantiteStock >= 1) {
                DetailLivre detailLivre = client.getPanier().getDetailLivre(livre);
                quantiteStock -= detailLivre.getQuantite();
            }
        } catch (LivreIntrouvableException e) {
            // On ignore l'exception ici
        } catch (SQLException e) {
            // TODO: handle exception
        }

        CustomerBookCardComponent bookCard = new CustomerBookCardComponent(livre, quantiteStock, this.modele);
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


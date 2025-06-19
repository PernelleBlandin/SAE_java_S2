package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controleurs.ControleurAcceuil;
import controleurs.ControleurDeconnexion;
import controleurs.admin.ControleurMenuAdmin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue.AppIHM;
import vue.SceneInterface;
import vue._components.MenuAsidePane;
import vue._components.alerts.AlertErreurException;

/** La scène pour la page administrateur */
public class AdminScene implements SceneInterface {
    /** La vue principale */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;

    /**
     * Initialiser la vue de l'accueil d'un administrateur.
     * 
     * @param app    La vue principale.
     * @param modele Le modèle.
     */
    public AdminScene(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        this.root.setTop(this.getHeader());

        List<String> nomsMenu = new ArrayList<>(Arrays.asList("Tableau de bord", "Magasins & vendeurs", "Exporter des factures"));
        this.root.setLeft(new MenuAsidePane(nomsMenu, new ControleurMenuAdmin(this)));

        this.showHome();

        this.scene = new Scene(this.root);
    }

    /**
     * Obtenir le header du menu client.
     * 
     * @return Le header du menu client.
     */
    public BorderPane getHeader() {
        BorderPane header = new BorderPane();
        header.setPadding(new Insets(10, 10, 10, 10));

        // Logo
        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(3046 / 15);
        logo.setFitHeight(912 / 15);

        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setOnAction(new ControleurAcceuil(this));
        buttonLogo.setGraphic(logo);
        header.setLeft(buttonLogo);

        // Text

        Label labelConnexion = new Label("Connecté en tant qu'administrateur");
        labelConnexion.setFont(Font.font("Arial", FontWeight.BOLD, 18)); 
        header.setCenter(labelConnexion);

        // Déconnexion

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(70, 30);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app, this.modele));
        header.setRight(deconnexionButton);
        BorderPane.setAlignment(deconnexionButton, Pos.CENTER);
       
        return header;
    }

    /**
     * Afficher la page d'accueil (statistiques).
     */
    public void showHome() {
        AdminStatsPane statsPane = new AdminStatsPane(this.modele);
        this.root.setCenter(statsPane);
    }

    /**
     * Afficher la page pour exporter des factures.
     */
    public void showExportFactures() {
        AdminFacturesPane facturesPane = new AdminFacturesPane(this.modele);
        this.root.setCenter(facturesPane);
    }

    /**
     * Afficher la page pour afficher les magasins.
     */
    public void showMagasins() {
        List<Magasin> magasins = new ArrayList<>();  
        try {
            magasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer la liste des magasins", e.getMessage());
            return;
        }  

        AdminMagasinsPane magasinsPane = new AdminMagasinsPane(magasins, this, this.modele);
        this.root.setCenter(magasinsPane);
    }

    /**
     * Afficher la page pour gérer les vendeurs d'un magasin.
     * 
     * @param magasin Un magasin
     */
    public void showVendeurs(Magasin magasin) {
        List<Vendeur> listeVendeurs = new ArrayList<>();
        try {
            listeVendeurs = this.modele.getVendeurBD().obtenirListeVendeurParMagasin(magasin.getId());
        } catch (Exception e) {
            new AlertErreurException("Erreur lors de la récupération des vendeurs du magasin.", e.getMessage());
            return;
        }

        AdminMagasinsVendeursPane vendeursPane = new AdminMagasinsVendeursPane(listeVendeurs, this, this.modele, magasin);
        this.root.setCenter(vendeursPane);
    }

    /**
     * Afficher la page pour gérer le stock d'un magasin.
     * 
     * @param listeLivres La liste des livres à afficher
     * @param magasin Un magasin
     */
    public void showStock(List<Livre> listeLivres, Magasin magasin) {
        AdminMagasinsStockPane stockPane = new AdminMagasinsStockPane(listeLivres, 6, this, this.modele, magasin);
        this.root.setCenter(stockPane);
    }

    // public void showDemandeInfoVendeur(Magasin magasin) {
    //     AdminDemandeInfoVendeurPane demandeInfoVendeurPane = new AdminDemandeInfoVendeurPane(this, this.modele, magasin);
    //     this.root.setCenter(demandeInfoVendeurPane);
    // }
    
    /**
     * Obtenir la scène de l'accueil d'un client.
     * 
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}

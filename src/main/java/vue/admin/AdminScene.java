package vue.admin;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue.AppIHM;
import vue.SceneInterface;

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
        this.root.setLeft(this.getAside());

        this.showHome();

        this.scene = new Scene(this.root);
    }

    /**
     * Obtenir le header du menu client.
     * 
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
        buttonLogo.setOnAction(new ControleurAcceuil(this));
        buttonLogo.setGraphic(logo);

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(70, 30);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app, this.modele));

        header.getChildren().addAll(buttonLogo, deconnexionButton);

        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 20));

        return header;
    }

    /**
     * Obtenir l'élement de côté.
     * 
     * @return L'élement de côté de la page.
     */
    public VBox getAside() {
        VBox aside = new VBox(100);
        aside.setPadding(new Insets(30, 50, 0, 50));

        List<String> nomsMenu = new ArrayList<>(
                Arrays.asList("Tableau de bord", "Magasins & vendeurs", "Exporter des factures"));
        for (String menu : nomsMenu) {
            Button button = new Button(menu);
            aside.getChildren().add(button);

            button.setOnAction(new ControleurMenuAdmin(this));
        }
        return aside;
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
        AdminFacturesPane facturesPane = new AdminFacturesPane(this, this.modele);
        this.root.setCenter(facturesPane);
    }

    /**
     * Afficher la page pour afficher les magasins.
     */
    public void showMagasins() {
        AdminMagasinsPane magasinsPane = new AdminMagasinsPane(this, this.modele);
        this.root.setCenter(magasinsPane);
    }

    /**
     * Afficher la page pour gérer les vendeurs d'un magasin.
     * 
     * @param magasin Un magasin
     */
    public void showVendeurs(Magasin magasin) {
        AdminMagasinsVendeursPane vendeursPane = new AdminMagasinsVendeursPane(this, this.modele, magasin);
        this.root.setCenter(vendeursPane);
    }

    /**
     * Afficher la page pour gérer le stock d'un magasin.
     * 
     * @param magasin Un magasin
     */
    public void showStock(Magasin magasin) {
        AdminMagasinsStockPane stockPane = new AdminMagasinsStockPane(this, this.modele, magasin);
        this.root.setCenter(stockPane);
    }

    /**
     * Obtenir la scène de l'accueil d'un client.
     * 
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}

package vue.customers;

import controleurs.ControleurDeconnexion;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modeles.ChaineLibrairie;
import vue.AppIHM;
import vue.BibliothequeComposants;

/** La vue de l'accueil d'un client */
public class CustomerHomeView {
    /** La vue principal */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param app La vue principal.
     * @param modele Le modèle.
     */
    public CustomerHomeView(AppIHM app, ChaineLibrairie modele) {
        this.app = app;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setPrefSize(1920, 1080);

        HBox header = this.getHeader();
        root.setTop(header);
        
        this.scene = new Scene(root);
    }

    /**
     * Obtenir le header du menu client.
     * @return Le header du menu client.
     */
    public HBox getHeader() {
        HBox root = new HBox();

        ImageView logo = new ImageView("/images/logo.png");
        logo.setFitWidth(304.6);
        logo.setFitHeight(91.2);

        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;");
        buttonLogo.setGraphic(logo);

        TextField searchBar = BibliothequeComposants.getSearchBar("Rechercher un livre...");

        Button panierButton = new Button("Panier");

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));

        root.getChildren().addAll(buttonLogo, searchBar, panierButton, deconnexionButton);

        return root;
    }

    /**
     * Obtenir la scène de l'accueil d'un client.
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}

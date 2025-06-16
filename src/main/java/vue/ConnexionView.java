package vue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.ChaineLibrairie;

/**
 * La vue de l'accueil du jeu.
 */
public class ConnexionView {
    /** La vue principal */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie chaineLibrairie;

    private Scene scene;

    /**
     * Initialiser la vue de l'accueil.
     * @param app La vue principal.
     * @param chaineLibrairie Le modèle de jeu.
     */
    public ConnexionView(AppIHM app, ChaineLibrairie chaineLibrairie) {
        this.app = app;
        this.chaineLibrairie = chaineLibrairie;

        GridPane root = new GridPane();
        root.setPrefSize(1920, 1080);

        root.setStyle("-fx-background-image: url(\"/images/connexionBackground.jpg\"); -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-size: cover;");

        VBox connexionBox = this.getConnexionBox();
        root.getChildren().add(connexionBox);
        
        this.scene = new Scene(root);
    }

    private VBox getConnexionBox() {
        VBox root = new VBox();

        root.getChildren().add(new Label("Connexion"));

        VBox vboxChoixUtilisateurs = new VBox();
        List<String> utilisateurs = new ArrayList<>(Arrays.asList("Client", "Vendeur", "Administrateur"));
        for (String utilisateur: utilisateurs) {
            vboxChoixUtilisateurs.getChildren().add(new Button(utilisateur));
        }

        root.getChildren().add(vboxChoixUtilisateurs);

        return root;
    }

    /**
     * Obtenir la scène de connexion.
     * @return La scène de connexion.
     */
    public Scene getScene() {
        return this.scene;
    }
}

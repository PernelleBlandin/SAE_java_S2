package vue.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controleurs.ControleurBoutonsConnexion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import vue.AppIHM;

/**
 * La vue de l'accueil du jeu.
 */
public class ConnexionView {
    /** La vue principale */
    private AppIHM app;

    /**
     * La scène de la page de connexion.
     */
    private Scene scene;

    /**
     * Initialiser la vue de l'accueil.
     * @param app La vue principale.
     */
    public ConnexionView(AppIHM app) {
        this.app = app;

        StackPane root = new StackPane();

        ImageView image = new ImageView(getClass().getResource("/images/connexionBackground.jpg").toExternalForm());

        // https://stackoverflow.com/questions/65052979/how-can-i-make-responsive-imageview-in-fxml-javafx
        image.fitWidthProperty().bind(root.widthProperty());
        image.fitHeightProperty().bind(root.heightProperty());

        VBox connexionBox = this.getConnexionBox();
        StackPane.setMargin(connexionBox, new Insets(200, 300, 200, 300));
        
        root.getChildren().addAll(image, connexionBox);
        
        this.scene = new Scene(root);
    }

    /**
     * Obtenir la VBox de connexion, permettant de choisir le mode de connexion (Client/Vendeur/Administrateur).
     * @return La VBox de connexion.
     */
    private VBox getConnexionBox() {
        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px;");
        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(400);
        logo.setFitHeight(200);
        Label connexionLabel = new Label("Bienvenue !");
        connexionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        root.getChildren().addAll(logo, connexionLabel);

        VBox vboxChoixUtilisateurs = new VBox();
        vboxChoixUtilisateurs.setAlignment(Pos.CENTER);

        List<String> utilisateurs = new ArrayList<>(Arrays.asList("Client", "Vendeur", "Administrateur"));
        for (String utilisateur: utilisateurs) {
            Button button = new Button(utilisateur);
            button.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
            button.setMaxWidth(Double.MAX_VALUE);

            vboxChoixUtilisateurs.getChildren().add(button);
            button.setOnAction(new ControleurBoutonsConnexion(this.app));
        }

        vboxChoixUtilisateurs.setSpacing(10);
        VBox.setMargin(vboxChoixUtilisateurs, new Insets(100, 200, 100, 200));
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

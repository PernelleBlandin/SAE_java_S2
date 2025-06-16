package vue.customers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import vue.AppIHM;

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

        VBox header = this.getHeader();
        root.setTop(header);
        
        this.scene = new Scene(root);
    }

    /**
     * Obtenir le header du menu client.
     * @return Le header du menu client.
     */
    public VBox getHeader() {
        VBox root = new VBox();

        root.getChildren().addAll(new Label("test"));

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

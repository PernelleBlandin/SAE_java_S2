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
public class SellerHomeView implements MAJVueInterface {
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
    public SellerHomeView(AppIHM app, ChaineLibrairie modele) {
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
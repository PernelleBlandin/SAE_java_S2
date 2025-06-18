package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import controleurs.ControleurDeconnexion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Vendeur;
import modeles.Livre;
import modeles.Magasin;

import vue.AppIHM;
import vue.BibliothequeComposants;
import vue.MAJVueInterface;

/** La vue de l'accueil d'un admin */
public class AdminView {
    
    /** La vue principale */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    
    /**HBox du header*/
    private HBox header;
    
    /** VBox du cote */
    private VBox aside;



    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param app La vue principal.
     * @param modele Le modèle.
     */
    public AdminView(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        this.header = this.getHeader();
        this.root.setTop(header);

        this.aside = this.getAside();
        this.root.setLeft(aside);

        
        this.modeFacture();

        this.scene = new Scene(this.root);
    }

public Scene getScene() {
    return this.scene;
    
}

    /**
     * Obtenir le header du menu admin.
     * @return Le header du menu admin.
     */
    public HBox getHeader() {
        HBox header = new HBox(40);
        header.setAlignment(Pos.CENTER_LEFT);
        
        ImageView logo = new ImageView("/images/logo.png");
        logo.setFitWidth(304.6);
        logo.setFitHeight(91.2);
        
        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setGraphic(logo);

        Label titre = new Label("Connecté en tant qu'administrateur");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));
        
        header.getChildren().addAll(buttonLogo, titre, deconnexionButton);

        header.setSpacing(100);
        header.setPadding(new Insets(10, 20, 10, 20));

        return header;
        }

    /**
     * Obtenir l'élement de cote.
     * @return L'élement de cote de la page.
     */
    public VBox getAside(){
        VBox aside = new VBox(100);
        aside.setPadding(new Insets(30,50,0,50));

        List<String> nomsMenu = new ArrayList<>(Arrays.asList("Tableau de bord", "Magasins & vendeurs", "Exporter des factures"));

        Button boutonTabBord = new Button("Tableau de bord");
        Button boutonMagVendeur = new Button("Les Magasins (Gestion vendeurs/stock)");
        Button boutonExporterFacture = new Button("Exporter des factures");


        
        //ControleurTabBord stats = new ControleurTabBord(this.modele);
        //this.boutonTabBord.setOnAction(stats);

        //ControleurMagVendeur gestionMagVendeur = new ControleurMagVendeur(this.modele);
        //this.boutonMagvendeur.setOnAction(GestionMagVendeur);

        //ControleurExporterFacture exportFact = new ControleurExporterFacture(this.modele);
        //this.boutonExporterFacture.setOnAction(exportFact);

        
        
        
        aside.getChildren().addAll(boutonMagVendeur, boutonExporterFacture, boutonTabBord);

        return aside;
        
    }


   

    public void modeStat() {
        FenetreStat fenetreStat = new FenetreStat(this, modele);
        this.root.setCenter(fenetreStat);
    }

    public void modeFacture() {
        FenetreFacture fenetreFacture = new FenetreFacture(this, modele);
        this.root.setCenter(fenetreFacture);
    }

    public void modeGestionMagasins() {
        FenetreGestionMagasins fenetreGestionMagasins = new FenetreGestionMagasins(this, modele);
        this.root.setCenter(fenetreGestionMagasins);
    }

    public void modeGestionVendeurs(Magasin magasin) {
        FenetreGestionVendeurs fenetreGestionVendeurs = new FenetreGestionVendeurs(this, modele, magasin);
        this.root.setCenter(fenetreGestionVendeurs);
    }

    public void modeStock(Magasin magasin) {
        FenetreStock fenetreStock = new FenetreStock(this, modele, magasin);
        this.root.setCenter(fenetreStock);
    }
    

}


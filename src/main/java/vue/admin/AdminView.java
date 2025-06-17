package vue.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import controleurs.ControleurDeconnexion;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
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

        HBox header = this.getHeader();
        this.root.setTop(header);

        this.aside = this.getAside();
        this.root.setCenter(fenetreStat());
        
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
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        
        ImageView logo = new ImageView("/images/logo.png");
        logo.setFitWidth(304.6);
        logo.setFitHeight(91.2);
        
        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;"); // Pour retirer le background gris derrière le bouton
        buttonLogo.setGraphic(logo);

        Label titre = new Label("Connecté en tant qu'administrateur");

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));
        
        header.getChildren().addAll(buttonLogo, titre, deconnexionButton);

        header.setSpacing(10);
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

        List<String> nomsMenu = new ArrayList<>(Arrays.asList("Tableau de bord", "Magasins & vendeurs", "Exporter des factures", "Gérer des stocks"));

        Button boutonTabBord = new Button("Tableau de bord");
        Button boutonMagVendeur = new Button("Magasins & vendeurs");
        Button boutonExporterFacture = new Button("Exporter des factures");
        Button boutonGereStock = new Button("Gérer des stocks");


        
        //ControleurTabBord stats = new ControleurTabBord(this.modele);
        //this.boutonTabBord.setOnAction(stats);

        //ControleurMagVendeur gestionMagVendeur = new ControleurMagVendeur(this.modele);
        //this.boutonMagvendeur.setOnAction(GestionMagVendeur);

        //ControleurExporterFacture exportFact = new ControleurExporterFacture(this.modele);
        //this.boutonExporterFacture.setOnAction(exportFact);

        //ControleurGereStock gestionStock = new ControleurGereStock(this.modele);
        //this.boutonExporterFacture.setOnAction(GestionStock);
        
        
        
        aside.getChildren().addAll(boutonMagVendeur, boutonExporterFacture ,boutonGereStock, boutonTabBord);

        return aside;
        
    }

    public TilePane fenetreStat() {
        TilePane centre = new TilePane();

        //BarChart
        // CategoryAxis xAxis = new CategoryAxis();
        // NumberAxis yAxis= new NumberAxis();

        // XYChart.Series series= new XYChart.Series<>();
        // Map<String, Map<Integer, Integer>> dataBar = modele.getStatistiquesBD().getNbLivresParMagasinParAn();
        // for(String key: dataBar.keySet()){
        //    series.getData().add(new XYChart.Data("key",  dataBar.get(key)) {
            
        //    }) 
        // }

        BarChart <String, Number> barChart= new BarChart<>(xAxis, yAxis);

        //PieChart

        //AreaChart

        //LineChart

        //BarChart

        //PieChart

        //PieChart

        //LineChart
        
        }

        
        
        

    public void modeStat() {
        this.root.setCenter(fenetreStat());
    }

    public void modeFacture() {
        this.root.setCenter(fenetreFact());
    }

    public void modeGestionMagasins() {
        this.root.setCenter(fenetreGestionMagasins());
    }

    public void modeGestionVendeurs() {
        this.root.setCenter(fenetregestionVendeurs());
    }

    public void modeStocksSelectMag() {
        this.root.setCenter(fenetreStockSelectMag());
    }

    public void modeStockUnMag() {
        this.root.setCenter(fenetreStockParMag());
    }
    

}


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
import javafx.scene.chart.*;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
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

        VBox aside = this.getAside();
        this.root.setLeft(aside);

        this.root.setCenter(fenetreGestionMagasins()); 
        
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

/**
     * TilePane à mettre au centre du BorderPane quand on change sa partie centrale en mode statistique.
     * @return centre Le TilePane
     */
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

        //BarChart <String, Number> barChart= new BarChart<>(xAxis, yAxis);

        //PieChart
        PieChart pieChartParTheme= new PieChart();
        pieChartParTheme.setTitle("Chiffre d'affaire 2024 par thème");
        try{
            Map <String, Double> dataParTheme = this.modele.getStatistiquesBD().getCA2024ParTheme();
            for(String key: dataParTheme.keySet()){
                PieChart.Data categorie=new PieChart.Data(key, dataParTheme.get(key));
                pieChartParTheme.getData().add(categorie);
        }
        //pieChartParTheme.setLegendSide(side.RIGHT);
        }catch (SQLException e){
            // TODO: handle exception
            
        }

        //         AreaChart
        // AreaChart areaChartCAParChart = new AreaChart();


        //        //LineChart  Map<String, Map<Integer, Double>> 
        // NumberAxis xAxisLine = new NumberAxis();
        // NumberAxis yAxisLine = new NumberAxis();
        // //xAxis.setLabel(null);

        // LineChart lineCompLigneMagasin = new LineChart(xAxisLine, yAxisLine);
        // XYChart.Series series3 = new XYChart.Series<>();

        

//BarChart  Map<String, Integer> getTop10EditeursNbAuteurs()
    //     NumberAxis xAxisBar = new NumberAxis();
    //     CategoryAxis yAxisBar = new CategoryAxis();
    //     BarChart barChartTopDix = new BarChart<>(xAxisBar, yAxisBar);
    //     XYChart.Series<String, Number> series4 = new XYChart.Series<>();
    //     barChartTopDix.setTitle("Dix éditeurs les plus important en nombre d'auteurs");
    //     xAxisBar.setTickLabelRotation(90);
    //     try{
    //         Map<String, Integer> dataTopDix= this.modele.getStatistiquesBD().getTop10EditeursNbAuteurs();
    //         for(String key: dataTopDix.keySet()){
    //             BarChart.Data categorie=new XYChart.Data(dataTopDix.get(key), key);
    //             series4.getData().add(categorie);
    //     }
    //     barChartTopDix.getData().addAll(series4);
    // }catch (SQLException e){
    //         // TODO: handle exception
            
    //     }


        //PieChart  Map<String, Integer> getQteLivresGoscinyOrigineClients()
        PieChart pieChartGosciny= new PieChart();
        pieChartGosciny.setTitle("Quantité de livres de René Gosciny achetés\n en fonction de l'origine des clients");
        try{
            Map<String, Integer> dataGosciny = this.modele.getStatistiquesBD().getQteLivresGoscinyOrigineClients();
            for(String key: dataGosciny.keySet()){
                PieChart.Data categorie2=new PieChart.Data(key, dataGosciny.get(key));
                pieChartGosciny.getData().add(categorie2);
            }
        //pieChartParTheme.setLegendSide(side.RIGHT);
        }catch (SQLException e){
            // TODO: handle exception
            
        }

        //PieChart

        //LineChart
        
        centre.getChildren().addAll(pieChartParTheme,pieChartGosciny);
        return centre;
        }

    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale en mode facture.
     * @return centre La VBox
     */
    //A finir d'implementer avec le popup et récup les donner de la fact
    public VBox fenetreFacture() {
        VBox centre = new VBox(40);

        Label sousTitre = new Label("Exporter des factures");


        HBox lesTF = new HBox(30);
        TextField moisTF = new TextField();
        moisTF.setPromptText("Mois");

        TextField anneeTF = new TextField();
        anneeTF.setPromptText("Année");
        lesTF.getChildren().addAll(moisTF, anneeTF);
        lesTF.setAlignment(Pos.CENTER);
        

        Button btnExporter = new Button("Exporter");

        centre.getChildren().addAll(sousTitre, lesTF, btnExporter);
        centre.setAlignment(Pos.TOP_CENTER);

        return centre;
    }
        
    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale pour voir les magasins.
     * @return lesMag La VBox
     */
    public VBox fenetreGestionMagasins() {
        VBox lesMag = new VBox(10);

        Label titre = new Label("Magasins");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lesMag.getChildren().addAll(titre);

        
        try { 
            for(Magasin unMag : this.modele.getMagasinBD().obtenirListeMagasin()) {
            Image imgVendeurs = new Image("/images/multiple_sellers_silhouette.png");
            ImageView viewVendeurs = new ImageView(imgVendeurs);
            Image imgPoubelle = new Image("/images/trashcan.png");
            ImageView viewPoubelle = new ImageView(imgPoubelle);
            viewVendeurs.setFitHeight(35);
            viewVendeurs.setFitWidth(35);
            viewPoubelle.setFitHeight(35);
            viewPoubelle.setFitWidth(35);

            BorderPane ligneMag = new BorderPane();
            HBox lesBtn = new HBox(10);
            Label leMag = new Label(unMag.getNom() + " - (" + unMag.getVille() + ")");

            Button btnVendeursDuMag = new Button();
            btnVendeursDuMag.setGraphic(viewVendeurs);
            Button btnSupprimerMag = new Button();
            btnSupprimerMag.setGraphic(viewPoubelle);

            lesBtn.getChildren().addAll(btnVendeursDuMag, btnSupprimerMag);

            //btnVendeursDuMag.setOnAction(new ControleurVoirVendeurs(mag));
            //btnSupprimerMag.setOnAction(new ControleurSupprMag(mag));
        
            ligneMag.setLeft(leMag);
            ligneMag.setRight(lesBtn);

            lesMag.getChildren().addAll(titre, ligneMag);

        }

            
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        

        return lesMag;
    }
        

    public void modeStat() {
        this.root.setCenter(fenetreStat());
    }

    public void modeFacture() {
        this.root.setCenter(fenetreFacture());
    }

    public void modeGestionMagasins() {
        this.root.setCenter(fenetreGestionMagasins());
    }

    // public void modeGestionVendeurs() {
    //     this.root.setCenter(fenetreGestionVendeurs());
    // }

    // public void modeStocksSelectMag() {
    //     this.root.setCenter(fenetreStockSelectMag());
    // }

    // public void modeStockUnMag() {
    //     this.root.setCenter(fenetreStockParMag());
    // }
    

}


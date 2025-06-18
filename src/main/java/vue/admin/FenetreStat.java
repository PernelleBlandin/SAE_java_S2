package vue.admin;

import java.sql.SQLException;
import java.util.Map;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.dsaRMD160;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;



public class FenetreStat extends BorderPane {

    /** La fenêtre principale AdminView */
    private AdminView fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;

    public FenetreStat(AdminView fenetrePrin, ChaineLibrairie modele) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;

        this.setCenter(this.fenetreStat());
    }


    //GRAPH TABLEAU DE BORD


    //1)BarChart Map<String, Map<Integer, Integer>> getNbLivresParMagasinParAn()
    // BarChart
        // CategoryAxis xAxis = new CategoryAxis();
        // NumberAxis yAxis= new NumberAxis();


        // XYChart.Series series= new XYChart.Series<>();
        // Map<String, Map<Integer, Integer>> dataBar = modele.getStatistiquesBD().getNbLivresParMagasinParAn();
        // for(String key: dataBar.keySet()){
        //    series.getData().add(new XYChart.Data("key",  dataBar.get(key)) {
           
        //    })
        // }


        // BarChart <String, Number> barChart= new BarChart<>(xAxis, yAxis);


    public PieChart graphCA2024ParTheme() {
    //2)PieChart
        PieChart pieChartParTheme= new PieChart();
        pieChartParTheme.setTitle("Chiffre d'affaire 2024 par thème");
        try{
            Map <String, Double> dataParTheme = this.modele.getStatistiquesBD().getCA2024ParTheme();
            for(String key: dataParTheme.keySet()){
                PieChart.Data categorie=new PieChart.Data(key, dataParTheme.get(key));
                pieChartParTheme.getData().add(categorie);
        }
       
        }catch (SQLException e){
            // TODO: handle exception
           
        }return pieChartParTheme;
    }


    //3) AreaChart Map<String, Map<Integer, Double>> getEvolutionCAParMoisParMagasin2024()


    //    AreaChart
        // AreaChart areaChartCAParChart = new AreaChart();




       
    //4) LineChartMap<String, Map<Integer, Double>> getComparaisonVentesLigneMagasin()


    //        //LineChart  Map<String, Map<Integer, Double>>
        // NumberAxis xAxisLine = new NumberAxis();
        // NumberAxis yAxisLine = new NumberAxis();
        // //xAxis.setLabel(null);


        // LineChart lineCompLigneMagasin = new LineChart(xAxisLine, yAxisLine);
        // XYChart.Series series3 = new XYChart.Series<>();




    public BarChart graphTop10EditeursNbAuteurs(){
        //5) BarChart  Map<String, Integer> getTop10EditeursNbAuteurs()
        NumberAxis xAxisBar = new NumberAxis();
        CategoryAxis yAxisBar = new CategoryAxis();
        BarChart barChartTopDix = new BarChart<>(xAxisBar, yAxisBar);
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        barChartTopDix.setTitle("Dix éditeurs les plus important en nombre d'auteurs");
        xAxisBar.setTickLabelRotation(90);
        try{
            Map<String, Integer> dataTopDix= this.modele.getStatistiquesBD().getTop10EditeursNbAuteurs();
            for(String key: dataTopDix.keySet()){
                BarChart.Data categorie=new XYChart.Data(dataTopDix.get(key), key);
                series4.getData().add(categorie);
        }
        barChartTopDix.getData().addAll(series4);
    }catch (SQLException e){
            // TODO: handle exception
           
    }return barChartTopDix;
    }


    //6)
    public PieChart graphQteLivresGoscinyOrigineClients(){
        PieChart pieChartGosciny= new PieChart();
        pieChartGosciny.setTitle("Quantité de livres de René Gosciny achetés\n en fonction de l'origine des clients");
        pieChartGosciny.setPrefWidth(500);
        pieChartGosciny.setPrefHeight(400);
        try{
            Map<String, Integer> dataGosciny = this.modele.getStatistiquesBD().getQteLivresGoscinyOrigineClients();
            System.out.println("Data Gosciny "+ dataGosciny);
            for(String key: dataGosciny.keySet()){
                PieChart.Data categorie2=new PieChart.Data(key, dataGosciny.get(key));
                pieChartGosciny.getData().add(categorie2);
            }
        //pieChartParTheme.setLegendSide(side.RIGHT);
        }catch (SQLException e){
            // TODO: handle exception
           
        }return pieChartGosciny;
    }


    //7)  PieChart Map<String, Double> getValeurStockParMagasin()
    public PieChart graphValeurStockParMagasin(){
    PieChart pieChartValStockMag= new PieChart();
        pieChartValStockMag.setTitle("Valeur du stock par magasin");
        pieChartValStockMag.setPrefWidth(500);
        pieChartValStockMag.setPrefHeight(400);
        try{
            Map<String, Double> dataStockMag = this.modele.getStatistiquesBD().getValeurStockParMagasin();
            for(String key: dataStockMag.keySet()){
                double valeurData= dataStockMag.get(key);
                PieChart.Data dataStock=new PieChart.Data(key,valeurData );
                pieChartValStockMag.getData().add(dataStock);

                Tooltip tooltip= new Tooltip(key+ " : "+ String.format("%.2f €", valeurData));
                Tooltip.install(dataStock.getNode(), tooltip);
            }

            

        }catch (SQLException e){
            // TODO: handle exception
           
        }return pieChartValStockMag;

    }
    //8)  LineChart Map<String, Double>> getEvolutionCATotalParClient()
    //
    public VBox fenetreStat() {
    
    ComboBox<String> comboBoxChoix = new ComboBox<>();
    comboBoxChoix.getItems().addAll(
        "Nombre de livres vendus par magasin",
        "CA 2024 par thème", 
        "Evolution CA des magasins par mois en 2024",
        "Evolution chiffre d'affaire, comparaison ventes en ligne et en magasin",
        "Valeur du stock par magasin",
        "Evolution CA total par client",
        "10 éditeurs les plus importants en nombres d'auteurs",
        "Quantité de livres de René Goscinny achetées en fonction de l'origine des clients"
    );

    VBox afficheChart = new VBox();
    afficheChart.setAlignment(Pos.CENTER);

    comboBoxChoix.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            String valCombo = comboBoxChoix.getValue();
            if (valCombo != null) {
                switch (valCombo) {
                    case "Nombre de livres vendus par magasin":
                        //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                        break;
                    case "CA 2024 par thème":
                        afficheChart.getChildren().setAll(graphCA2024ParTheme());
                        break;
                    case "Evolution CA des magasins par mois en 2024":
                        //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                        break;
                    case "Evolution chiffre d'affaire, comparaison ventes en ligne et en magasin":
                        //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                        break;
                    case "10 éditeurs les plus importants en nombres d'auteurs":
                        afficheChart.getChildren().setAll(graphTop10EditeursNbAuteurs());
                        break;
                    case "Quantité de livres de René Goscinny achetées en fonction de l'origine des clients":
                        afficheChart.getChildren().setAll(graphQteLivresGoscinyOrigineClients());
                        break;
                    case "Valeur du stock par magasin":
                        afficheChart.getChildren().setAll(graphValeurStockParMagasin());
                        break;
                    case "Evolution CA total par client":
                        //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                        break;
                        
                    // Ajoutez les autres cas ici
                    default:
                        System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                        break;
                }
            }
        }
    });
    
    VBox affichage = new VBox(20, comboBoxChoix, afficheChart);
    affichage.setAlignment(Pos.CENTER);
    
    return affichage;
}
}





    


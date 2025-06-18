package vue.admin;

import java.sql.SQLException;
import java.util.Map;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modeles.ChaineLibrairie;

public class FenetreStat extends BorderPane {

    /** La fenêtre principale AdminView */
    private AdminView fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;

    public FenetreStat(AdminView fenetrePrin, ChaineLibrairie modele) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
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
        try{
            Map<String, Integer> dataGosciny = this.modele.getStatistiquesBD().getQteLivresGoscinyOrigineClients();
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
    //8)  LineChart Map<String, Double>> getEvolutionCATotalParClient()
    //
    public HBox fenetreStat() {
        ComboBox comboBoxChoix = new ComboBox<>();
        comboBoxChoix.getItems().add("Nombre de livres vendus par magasin");
        comboBoxChoix.getItems().add("CA 2024 par thème");
        comboBoxChoix.getItems().add("Evolution CA des magasins par mois en 2024");
        comboBoxChoix.getItems().add("Evolution chiffre d'affaire, comparaison ventes en ligne et en magasin");
        comboBoxChoix.getItems().add("Valeur du stock par magasin");
        comboBoxChoix.getItems().add("Evolution CA total par client");
        comboBoxChoix.getItems().add("10 éditeurs les plus importants en nombres d'auteurs ");
        comboBoxChoix.getItems().add("Quantité de livres deRené Goscinny achetées en fonction de l'origine des clients");
         

        String valCombo= (String) comboBoxChoix.getValue();

        
    
        
        HBox hbox = new HBox(comboBoxChoix);

        return hbox;

        }

    
}

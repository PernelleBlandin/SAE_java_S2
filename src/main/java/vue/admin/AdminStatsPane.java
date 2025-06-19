package vue.admin;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import controleurs.admin.ControleurAdminStatsComboBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import vue._components.alerts.AlertErreurException;

/** Pane des statistiques administrateurs */
public class AdminStatsPane extends VBox {
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane des statistiques administrateurs.
     * 
     * @param modele Le modèle.
     */
    public AdminStatsPane(ChaineLibrairie modele) {
        this.modele = modele;

        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
            this.getTitle(),
            this.getComboBox(),
            this.graphNbLivresParMagasinParAn()
        );
    }

    /**
     * Définir le graphique de la page.
     * 
     * @param chart Un graphique.
     */
    public void setChart(Chart chart) {
        this.getChildren().set(2, chart);
    }

    /**
     * Obtenir le titre du menu.
     * 
     * @return Le label du titre du menu.
     */
    private Label getTitle() {
        Label title = new Label("Tableau de bord");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        return title;
    }

    /**
     * Obtenir le combo-box avec les graphiques possibles.
     * 
     * @return Le combo-box avec les graphiques possibles.
     */
    private ComboBox<String> getComboBox() {
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
        comboBoxChoix.setValue(comboBoxChoix.getItems().get(0)); // On sélectionne le premier élément par défaut
        comboBoxChoix.setOnAction(new ControleurAdminStatsComboBox(this));

        return comboBoxChoix;
    }

    // GRAPH TABLEAU DE BORD

    // 1)BarChart Map<String, Map<Integer, Integer>> getNbLivresParMagasinParAn()
    public BarChart<String, Number> graphNbLivresParMagasinParAn() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChartNbLivresParMagasin = new BarChart<>(xAxis, yAxis);
        barChartNbLivresParMagasin.setTitle("Nombre de livre vendus par magasin");
        try {
            Map<String, Map<Integer, Integer>> dataBar = modele.getStatistiquesBD().getNbLivresParMagasinParAn();

            Set<Integer> annee = new HashSet<>();
            for (Map<Integer, Integer> dataAnnee : dataBar.values()) {
                annee.addAll(dataAnnee.keySet());
            }

            for (Integer an : annee) {
                XYChart.Series<String, Number> serieParAn = new XYChart.Series<>();
                serieParAn.setName(String.valueOf(an));

                for (String magasin : dataBar.keySet()) {
                    Map<Integer, Integer> venteParAn = dataBar.get(magasin);
                    if (venteParAn.containsKey(an)) {
                        int nbLivre = venteParAn.get(an);
                        serieParAn.getData().add(new XYChart.Data<>(magasin, nbLivre));
                    }
                }

                barChartNbLivresParMagasin.getData().add(serieParAn);
            }

        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());
        }
        return barChartNbLivresParMagasin;
    }

    public PieChart graphCA2024ParTheme() {
        // 2)PieChart
        PieChart pieChartParTheme = new PieChart();
        pieChartParTheme.setTitle("Chiffre d'affaire 2024 par thème");
        try {
            Map<String, Double> dataParTheme = this.modele.getStatistiquesBD().getCA2024ParTheme();
            for (String key : dataParTheme.keySet()) {
                PieChart.Data categorie = new PieChart.Data(key, dataParTheme.get(key));
                pieChartParTheme.getData().add(categorie);
            }
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());
        }
        return pieChartParTheme;
    }

    // 3) AreaChart Map<String, Map<Integer, Double>>
    public AreaChart<Number, Number> graphEvolutionCAParMoisParMagasin2024() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        AreaChart<Number, Number> areaChartCAParMoisParMagasin = new AreaChart<>(xAxis, yAxis);
        areaChartCAParMoisParMagasin.setTitle("Evolution CA des magasins par mois en 2024");
        try {
            Map<String, Map<Integer, Double>> dataAreaChart = this.modele.getStatistiquesBD()
                    .getEvolutionCAParMoisParMagasin2024();

            for (String magasin : dataAreaChart.keySet()) {
                XYChart.Series<Number, Number> serie = new XYChart.Series<>();
                serie.setName(magasin);

                Map<Integer, Double> caParMois = dataAreaChart.get(magasin);
                for (int mois = 1; mois <= 12; mois++) {
                    Double val = caParMois.get(mois);
                    if (val != null) {
                        serie.getData().add(new XYChart.Data<>(mois, val));
                    }
                }
                areaChartCAParMoisParMagasin.getData().add(serie);
            }
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());
        }
        return areaChartCAParMoisParMagasin;
    }

    //4) LineChartMap<String, Map<Integer, Double>> getComparaisonVentesLigneMagasin()


//        //LineChart  Map<String, Map<Integer, Double>>
    // NumberAxis xAxisLine = new NumberAxis();
    // NumberAxis yAxisLine = new NumberAxis();
    // //xAxis.setLabel(null);


    // LineChart lineCompLigneMagasin = new LineChart(xAxisLine, yAxisLine);
    // XYChart.Series series3 = new XYChart.Series<>();

    public BarChart<Number, String> graphTop10EditeursNbAuteurs() {
        // 5) BarChart Map<String, Integer> getTop10EditeursNbAuteurs()
        NumberAxis xAxisBar = new NumberAxis();
        CategoryAxis yAxisBar = new CategoryAxis();

        BarChart<Number, String> barChartTopDix = new BarChart<>(xAxisBar, yAxisBar);
        XYChart.Series<Number, String> series4 = new XYChart.Series<>();

        barChartTopDix.setTitle("Dix éditeurs les plus important en nombre d'auteurs");
        xAxisBar.setTickLabelRotation(90);
        try {
            Map<String, Integer> dataTopDix = this.modele.getStatistiquesBD().getTop10EditeursNbAuteurs();
            for (String key : dataTopDix.keySet()) {
                XYChart.Data<Number, String> categorie = new XYChart.Data<>(dataTopDix.get(key), key);
                series4.getData().add(categorie);
            }
            barChartTopDix.getData().add(series4);
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());
        }
        return barChartTopDix;
    }

    // 6)
    public PieChart graphQteLivresGoscinyOrigineClients() {
        PieChart pieChartGosciny = new PieChart();
        pieChartGosciny.setTitle("Quantité de livres de René Gosciny achetés\n en fonction de l'origine des clients");
        pieChartGosciny.setPrefWidth(500);
        pieChartGosciny.setPrefHeight(400);
        try {
            Map<String, Integer> dataGosciny = this.modele.getStatistiquesBD().getQteLivresGoscinyOrigineClients();
            for (String key : dataGosciny.keySet()) {
                PieChart.Data categorie2 = new PieChart.Data(key, dataGosciny.get(key));
                pieChartGosciny.getData().add(categorie2);
            }
            // pieChartParTheme.setLegendSide(side.RIGHT);
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());
        }
        return pieChartGosciny;
    }

    // 7) PieChart Map<String, Double> getValeurStockParMagasin()
    public PieChart graphValeurStockParMagasin() {
        PieChart pieChartValStockMag = new PieChart();
        pieChartValStockMag.setTitle("Valeur du stock par magasin");
        pieChartValStockMag.setPrefWidth(500);
        pieChartValStockMag.setPrefHeight(400);
        try {
            Map<String, Double> dataStockMag = this.modele.getStatistiquesBD().getValeurStockParMagasin();
            for (String key : dataStockMag.keySet()) {
                double valeurData= dataStockMag.get(key);
                PieChart.Data dataStock=new PieChart.Data(key,valeurData );
                pieChartValStockMag.getData().add(dataStock);

                Tooltip tooltip= new Tooltip(key+ " : "+ String.format("%.2f €", valeurData));
                Tooltip.install(dataStock.getNode(), tooltip);
            }
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer les données.", e.getMessage());

        }
        return pieChartValStockMag;
    }

    // 8) LineChart Map<String, Double>> getEvolutionCATotalParClient()
    //
}
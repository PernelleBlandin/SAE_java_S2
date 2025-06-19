package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import vue.admin.AdminStatsPane;

/** Contrôleur du ComboBox dans la page statistiques (administrateur) */
public class ControleurAdminStatsComboBox implements EventHandler<ActionEvent> {
    /** La pane de la page statistiques */
    private AdminStatsPane adminStatsPane;

    /**
     * Initialiser le contrôleur du ComboBox dans la page statistiques (administrateur).
     * @param adminStatsPane La pane de la page statistiques
     */
    public ControleurAdminStatsComboBox(AdminStatsPane adminStatsPane) {
        this.adminStatsPane = adminStatsPane;
    }

    @Override
    /**
     * Recevoir un événement et récupérer le graph choisi pour l'afficher.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        @SuppressWarnings("unchecked")
        ComboBox<String> comboBox = (ComboBox<String>) event.getSource();

        String graphChoisi = comboBox.getValue();
        switch (graphChoisi) {
            case "Nombre de livres vendus par magasin":
                this.adminStatsPane.setChart(this.adminStatsPane.graphNbLivresParMagasinParAn());
                break;
            case "CA 2024 par thème":
                this.adminStatsPane.setChart(this.adminStatsPane.graphCA2024ParTheme());
                break;
            case "Evolution CA des magasins par mois en 2024":
                this.adminStatsPane.setChart(this.adminStatsPane.graphEvolutionCAParMoisParMagasin2024());
                break;
            case "Evolution chiffre d'affaire, comparaison ventes en ligne et en magasin":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            case "10 éditeurs les plus importants en nombres d'auteurs":
                this.adminStatsPane.setChart(this.adminStatsPane.graphTop10EditeursNbAuteurs());
                break;
            case "Quantité de livres de René Goscinny achetées en fonction de l'origine des clients":
                this.adminStatsPane.setChart(this.adminStatsPane.graphQteLivresGoscinyOrigineClients());
                break;
            case "Valeur du stock par magasin":
                this.adminStatsPane.setChart(this.adminStatsPane.graphValeurStockParMagasin());
                break;
            case "Evolution CA total par client":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            default:
                break;
        }
    }
}

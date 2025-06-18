package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import vue.admin.AdminStatsPane;

public class ControleurAdminStatsComboBox implements EventHandler<ActionEvent> {
    private AdminStatsPane adminStatsPane;

    public ControleurAdminStatsComboBox(AdminStatsPane adminStatsPane) {
        this.adminStatsPane = adminStatsPane;
    }

    @Override
    public void handle(ActionEvent event) {
        @SuppressWarnings("unchecked")
        ComboBox<String> comboBox = (ComboBox<String>) event.getSource();

        String graphChoisi = comboBox.getValue();
        switch (graphChoisi) {
            case "Nombre de livres vendus par magasin":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            case "CA 2024 par thème":
                this.adminStatsPane.setGraph(this.adminStatsPane.graphCA2024ParTheme());
                break;
            case "Evolution CA des magasins par mois en 2024":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            case "Evolution chiffre d'affaire, comparaison ventes en ligne et en magasin":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            case "10 éditeurs les plus importants en nombres d'auteurs":
                this.adminStatsPane.setGraph(this.adminStatsPane.graphTop10EditeursNbAuteurs());
                break;
            case "Quantité de livres de René Goscinny achetées en fonction de l'origine des clients":
                this.adminStatsPane.setGraph(this.adminStatsPane.graphQteLivresGoscinyOrigineClients());
                break;
            case "Valeur du stock par magasin":
                this.adminStatsPane.setGraph(this.adminStatsPane.graphCA2024ParTheme());
                break;
            case "Evolution CA total par client":
                //afficheChart.getChildren().setAll(graphCA2024ParTheme());
                break;
            default:
                break;
        }
    }
}

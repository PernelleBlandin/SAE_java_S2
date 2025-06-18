package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.admin.AdminScene;

/**
 * Le contrôleur pour changer de menu dans la page administrateur
 */
public class ControleurMenuAdmin implements EventHandler<ActionEvent>{
    /** La scène de la page vendeur */
    private AdminScene adminScene;

    /**
     * Initiailiser le contrôleur pour changer de menu dans la page administrateur.
     * @param adminScene La scène de la page administrateur.
     */
    public ControleurMenuAdmin(AdminScene adminScene) {
        this.adminScene = adminScene;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur un des boutons a gauche
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button bouton = (Button) event.getSource();

        String label = bouton.getText();
        switch (label) {
            case "Tableau de bord": {
                this.adminScene.showHome();
                break;
            }
            case "Magasins & vendeurs":{
                this.adminScene.showMagasins();
                break;
            }
            case "Exporter des factures": {
                this.adminScene.showExportFactures();
                break;
            }
            default:
                break;
        }
    }
}


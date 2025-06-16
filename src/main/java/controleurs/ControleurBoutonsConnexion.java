package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.AppIHM;

public class ControleurBoutonsConnexion implements EventHandler<ActionEvent> {
    private AppIHM app;
    public ControleurBoutonsConnexion(AppIHM app) {
        this.app = app;
    }

    @Override
    public void handle(ActionEvent event) {
        Button bouton = (Button) event.getSource();

        // String label = bouton.getText();
        // switch (label) {
        //     case "Client": {
        //         this.app.modeClient();
        //         break;
        //     }
        //     case "Vendeur": {
        //         this.app.modeVendeur();
        //         break;
        //     }
        //     case "Administrateur": {
        //         this.app.modeAdministrateur();
        //         break;
        //     }
        //     default:
        //         break;
        // }
    }
}

package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.AppIHM;

/** Le contrôleur de connexion dans la page de connexion. */
public class ControleurBoutonsConnexion implements EventHandler<ActionEvent> {
    /** L'application principale */
    private AppIHM app;

    /**
     * Initialiser le contrôleur de connexion.
     * @param app L'application principale.
     */
    public ControleurBoutonsConnexion(AppIHM app) {
        this.app = app;
    }

    @Override
    /**
     * Récupère un événèment lors d'un clic sur un bouton.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        Button bouton = (Button) event.getSource();

        String label = bouton.getText();
        switch (label) {
            case "Client": {
                this.app.showCustomer();
                break;
            }
            // case "Vendeur": {
            //     this.app.modeVendeur();
            //     break;
            // }
            // case "Administrateur": {
            //     this.app.modeAdministrateur();
            //     break;
            // }
            default:
                break;
        }
    }
}

package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.AppIHM;

/** Le contrôleur de déconnexion */
public class ControleurDeconnexion implements EventHandler<ActionEvent> {
    /** L'application principale */
    private AppIHM app;

    /**
     * Initialiser le contrôleur de deconnexion.
     * @param app L'application principale.
     */
    public ControleurDeconnexion(AppIHM app) {
        this.app = app;
    }

    @Override
    /**
     * Récupère un événèment lors d'un clic sur un bouton et retourne sur la page de connexion.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        this.app.showConnexion();
    }
}

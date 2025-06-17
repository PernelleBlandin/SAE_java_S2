package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.AppIHM;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurAcceuilClient implements EventHandler<ActionEvent> {
    private AppIHM app;

    /**
     * Initiailiser le contrôleur du bouton de l'image pour retourner à l'acceuil.
     * @param app La vue à mettre à jour en cas de clic sur le bouton. 
     */
    public ControleurAcceuilClient(AppIHM app) {
        this.app = app;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton pour retourner à l'acceuil client.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.app.showCustomer();
    }
}

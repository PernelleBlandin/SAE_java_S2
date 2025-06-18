package controleurs.customers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.customers.CustomerScene;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurAcceuilClient implements EventHandler<ActionEvent> {
    private CustomerScene customerScene;

    /**
     * Initiailiser le contrôleur du bouton de l'image pour retourner à l'acceuil.
     * @param customerScene La vue à mettre à jour en cas de clic sur le bouton. 
     */
    public ControleurAcceuilClient(CustomerScene customerScene) {
        this.customerScene = customerScene;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton pour retourner à l'acceuil client.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.customerScene.showHome();
    }
}

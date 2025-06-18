package controleurs.customers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.customers.CustomerScene;

public class ControleurBoutonPanier implements EventHandler<ActionEvent> {
    private CustomerScene customerScene;

    /**
     * Initiailiser le contrôleur du bouton "Ajouter Panier"
     * @param customerScene La vue de la page "Client".
     */
    public ControleurBoutonPanier(CustomerScene customerScene) {
        this.customerScene = customerScene;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton "Ajouter panier".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
         this.customerScene.showPanier();
    }
}

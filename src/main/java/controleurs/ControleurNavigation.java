package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.customers.CustomerListBookPane;

public class ControleurNavigation implements EventHandler<ActionEvent> {
    /** Vue de l'acceuil de la page client */
    private CustomerListBookPane customerListView;

    /**
     * Initialiser le contrôleur du bouton "Voir plus", affichant une liste de livres.
     * @param customerListView La vue de l'acceuil de la page client.
     * @param listeLivres Une liste de livres à afficher en cas de clic sur le bouton.
     */
    public ControleurNavigation(CustomerListBookPane customerListView) {
        this.customerListView = customerListView;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Voir plus", pour afficher une liste de livres.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();

        int pageActuelle = this.customerListView.getCurPage();
        String labelText = button.getText();
        if (labelText.equals("Précédent")) {
            this.customerListView.setCurPage(--pageActuelle);
        } else {
            this.customerListView.setCurPage(++pageActuelle);
        }

        this.customerListView.miseAJourAffichage();
    }
}

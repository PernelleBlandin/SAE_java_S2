package controleurs.customers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modeles.Livre;
import vue._components.alerts.AlertInfoLivre;

/** Contrôleur du bouton "Info" sur un livre */
public class ControleurInfoLivre implements EventHandler<ActionEvent> {
    private Livre livre;

    public ControleurInfoLivre(Livre livre) {
        this.livre = livre;
    }

    @Override
    /**
     * Récupère les informations d'un clic sur le bouton "Info" pour afficher une pop-up.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Alert alert = new AlertInfoLivre(this.livre);
        alert.show();
    }
}

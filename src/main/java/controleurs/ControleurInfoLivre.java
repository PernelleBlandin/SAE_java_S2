package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Livre;
import vue.customers.CustomerHomeView;

/** Contrôleur du bouton "Info" sur un livre */
public class ControleurInfoLivre implements EventHandler<ActionEvent> {
    /** Un livre */
    private Livre livre;

    /**
     * Initialiser le contrôleur d'info de livres.
     * @param livre Un livre.
     */
    public ControleurInfoLivre(Livre livre) {
        this.livre = livre;
    }

    @Override
    /**
     * Récupère les informations d'un clic sur le bouton "Info" pour afficher une pop-up.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        CustomerHomeView.sendAlertInfoLivre(this.livre);
    }
}

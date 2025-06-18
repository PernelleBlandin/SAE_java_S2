package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue._components.BaseListBooksPane;

/**
 * Le contrôleur des boutons de navigations dans la liste de livres.
 */
public class ControleurNavigation implements EventHandler<ActionEvent> {
    /** Une pane contenant la liste des livres affiché */
    private BaseListBooksPane listBookPane;

    /**
     * Initialiser le contrôleur des boutons de navigations sur le composant des liste de livres.
     * @param listBooksPane Le composant de la liste des livres.
     */
    public ControleurNavigation(BaseListBooksPane listBooksPane) {
        this.listBookPane = listBooksPane;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur les boutons "Suivant" ou "Précédent".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();

        int pageActuelle = this.listBookPane.getCurPage();
        String labelText = button.getText();
        if (labelText.equals("Précédent")) {
            this.listBookPane.setCurPage(--pageActuelle);
        } else {
            this.listBookPane.setCurPage(++pageActuelle);
        }

        this.listBookPane.miseAJourAffichage();
    }
}

package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue._components.BaseListElementsPane;

/**
 * Le contrôleur des boutons de navigations dans la liste des élements.
 */
public class ControleurNavigation<T> implements EventHandler<ActionEvent> {
    /** Une pane contenant la liste des élements affichés */
    private BaseListElementsPane<T> listElementsPane;

    /**
     * Initialiser le contrôleur des boutons de navigations sur le composant des liste des élements.
     * @param listElementsPane Le composant de la liste des livres.
     */
    public ControleurNavigation(BaseListElementsPane<T> listElementsPane) {
        this.listElementsPane = listElementsPane;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur les boutons "Suivant" ou "Précédent".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();

        int pageActuelle = this.listElementsPane.getCurPage();
        String labelText = button.getText();
        if (labelText.equals("Précédent")) {
            this.listElementsPane.setCurPage(--pageActuelle);
        } else {
            this.listElementsPane.setCurPage(++pageActuelle);
        }

        this.listElementsPane.miseAJourAffichage();
    }
}

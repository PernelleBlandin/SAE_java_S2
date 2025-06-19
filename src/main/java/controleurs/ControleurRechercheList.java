package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import modeles.RecherchableInterface;
import vue._components.BaseListElementsWithSearchPane;

/**
 * Le contrôleur de la barre de recherche 
 */
public class ControleurRechercheList<T extends RecherchableInterface> implements EventHandler<KeyEvent> {
    /** La scène de la pane */
    private BaseListElementsWithSearchPane<T> scene;
    
    /**
     * Initialiser le contrôleur de la barre de recherche.
     * @param scene La scène de la page.
     */
    public ControleurRechercheList(BaseListElementsWithSearchPane<T> scene) {
        this.scene = scene;
    }

    @Override
    /**
     * Recevoir un événement lors de la saisie d'un caractère dans la barre de recherche.
     * @param event Un événement.
     */
    public void handle(KeyEvent event) {
        this.scene.setCurPage(0);
        this.scene.miseAJourAffichage();
    }
}

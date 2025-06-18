package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.SceneInterface;

/** Contrôleur pour revenir à l'acceuil de la page */
public class ControleurAcceuil implements EventHandler<ActionEvent> {
    /** Une scène */
    private SceneInterface scene;

    /**
     * Initiailiser le contrôleur du bouton de l'image pour retourner à l'acceuil.
     * @param scene La scène. 
     */
    public ControleurAcceuil(SceneInterface scene) {
        this.scene = scene;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton pour retourner à l'acceuil.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.scene.showHome();
    }
}
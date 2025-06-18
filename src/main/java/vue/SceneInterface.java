package vue;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/** L'interface d'une scène */
public interface SceneInterface {
    /**
     * Obtenir le header de la scène.
     * @return Le hader
     */
    public Pane getHeader();

    /**
     * Afficher la page d'accueil.
     */
    public void showHome();

    /**
     * Obtenir la scène de la classe;
     * @return La scène.
     */
    public Scene getScene();
}

package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import vue._components.numberField.DoubleField;

/** Contrôleur d'un DoubleField, permettant de vérifier si un nombre inscrit est valide */
public class ControleurDoubleFieldVerif implements EventHandler<KeyEvent> {
    /** Le composant du DoubleField */
    private DoubleField component;

    /**
     * Initialiser le contrôleur.
     * @param component Un componsant de nombre.
     */
    public ControleurDoubleFieldVerif(DoubleField component) {
        this.component = component;
    }

    @Override
    /**
     * Recevoir un événement et mettre à jour le DoubleField selon la valeur indiquée.
     * @param event Un événement.
     */
    public void handle(KeyEvent event) {
        if (!this.component.isValid()) {
            this.component.setStyle("-fx-background-color: #FFCCCB; -fx-border-color: red; -fx-border-width: 2px; -fx-border-style: solid;");
        } else {
            this.component.setStyle("");
        }
    }
}

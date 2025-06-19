package controleurs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import vue._components.numberField.IntegerField;

/** Contrôleur d'un IntegerField, permettant de vérifier si un nombre inscrit est valide */
public class ControleurIntegerFieldVerif implements EventHandler<KeyEvent> {
    /** Le composant du IntegerField */
    private IntegerField component;

    /**
     * Initialiser le contrôleur.
     * @param component Un componsant de nombre.
     */
    public ControleurIntegerFieldVerif(IntegerField component) {
        this.component = component;
    }

    @Override
    /**
     * Recevoir un événement et mettre à jour le IntegerField selon la valeur indiquée.
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

package controleurs;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import vue._components.numberField.NumberFieldDisableButton;

/** Contrôleur d'un NumberField, permettant de désactiver un autre bouton s'il est invalide */
public class ControleurNumberFieldVerifWithButton implements EventHandler<KeyEvent> {
    /** Le composant du NumberField */
    private NumberFieldDisableButton component;

    /**
     * Initialiser le contrôleur.
     * @param component Un componsant de nombre.
     */
    public ControleurNumberFieldVerifWithButton(NumberFieldDisableButton component) {
        this.component = component;
    }

    @Override
    /**
     * Recevoir un événement et mettre à jour le bouton et le NumberField
     * @param event Un événement.
     */
    public void handle(KeyEvent event) {
        Button button = this.component.getButton();

        if (!this.component.isValid()) {
            button.setDisable(true);
            this.component.setStyle("-fx-background-color: #FFCCCB; -fx-border-color: red; -fx-border-width: 2px; -fx-border-style: solid;");
        } else {
            button.setDisable(false);
            this.component.setStyle("");
        }
    }
}

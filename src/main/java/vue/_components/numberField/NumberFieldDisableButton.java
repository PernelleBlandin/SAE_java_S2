package vue._components.numberField;

import controleurs.ControleurNumberFieldVerifWithButton;
import javafx.scene.control.Button;

/** Un NumberField, permettant de désactiver un autre bouton si invalide */
public class NumberFieldDisableButton extends NumberField {
    /** Le bouton à désactiver/activer */
    private Button button;
    
    /**
     * Initialiser un number field.
     * @param button Le bouton à activer/désactiver si valeur valide ou non.
     */
    public NumberFieldDisableButton(Button button) {
        super();
        this.button = button;

        this.setOnKeyTyped(new ControleurNumberFieldVerifWithButton(this));
    }

    /**
     * Initialiser un number field avec une entier par défaut.
     * @param button Le bouton à activer/désactiver si valeur valide ou non.
     * @param defaultValue Un nombre entier.
     */
    public NumberFieldDisableButton(Button button, int defaultValue) {
        this(button);

        this.setText(String.valueOf(defaultValue));
    }

    /**
     * Obtenir le bouton à modifier.
     * @return Le bouton.
     */
    public Button getButton() {
        return this.button;
    }
}

package vue._components.numberField;

import controleurs.ControleurIntegerFieldVerifWithButton;
import javafx.scene.control.Button;

/** Un IntegerField, permettant de désactiver un autre bouton si invalide */
public class IntegerFieldDisableButton extends IntegerField {
    /** Le bouton à désactiver/activer */
    private Button button;
    
    /**
     * Initialiser un integer field.
     * @param button Le bouton à activer/désactiver si valeur valide ou non.
     */
    public IntegerFieldDisableButton(Button button) {
        super();
        this.button = button;

        this.setOnKeyTyped(new ControleurIntegerFieldVerifWithButton(this));
    }

    /**
     * Initialiser un integer field avec un entier par défaut.
     * @param button Le bouton à activer/désactiver si valeur valide ou non.
     * @param defaultValue Un nombre entier.
     */
    public IntegerFieldDisableButton(Button button, int defaultValue) {
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

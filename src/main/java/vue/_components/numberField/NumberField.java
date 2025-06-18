package vue._components.numberField;

import controleurs.ControleurNumberFieldVerif;
import javafx.scene.control.TextField;

/** Un NumberField */
public class NumberField extends TextField {
    /**
     * Initialiser un number field.
     */
    public NumberField() {
        this.setPrefWidth(80);

        this.setOnKeyTyped(new ControleurNumberFieldVerif(this));
    }

    /**
     * Initialiser un number field avec une entier par défaut.
     * @param defaultValue Un nombre entier.
     */
    public NumberField(int defaultValue) {
        this();

        this.setText(String.valueOf(defaultValue));
    }

    /**
     * Récupère la valeur numérique du champ
     * @return La valeur numérique ou 0 si invalide.
     */
    public int getValeur() {
        try {
            return Integer.parseInt(this.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Vérifie si la valeur entrée est valide
     * @return true si c'est un nombre valide, false sinon
     */
    public boolean isValid() {
        try {
            Double.parseDouble(this.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

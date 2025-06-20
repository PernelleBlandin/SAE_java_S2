package vue._components.numberField;

import controleurs.ControleurDoubleFieldVerif;
import javafx.scene.control.TextField;

/** Un DoubleField */
public class DoubleField extends TextField {
    /**
     * Initialiser un double field.
     */
    public DoubleField() {
        this.setPrefWidth(80);

        this.setOnKeyTyped(new ControleurDoubleFieldVerif(this));
    }

    /**
     * Initialiser un number field avec un double par défaut.
     * @param defaultValue Un double.
     */
    public DoubleField(double defaultValue) {
        this();

        this.setText(String.valueOf(defaultValue));
    }

    /**
     * Récupère la valeur numérique du champ
     * @return La valeur numérique ou 0.0 si invalide.
     */
    public double getValeur() {
        try {
            return Double.parseDouble(this.getText());
        } catch (NumberFormatException e) {
            return 0.0;
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

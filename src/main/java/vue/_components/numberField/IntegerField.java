package vue._components.numberField;

import controleurs.ControleurIntegerFieldVerif;
import javafx.scene.control.TextField;

/** Un Integerfield */
public class IntegerField extends TextField {
    /**
     * Initialiser un integer field.
     */
    public IntegerField() {
        this.setPrefWidth(80);

        this.setOnKeyTyped(new ControleurIntegerFieldVerif(this));
    }

    /**
     * Initialiser un integer field avec un entier par défaut.
     * @param defaultValue Un nombre entier.
     */
    public IntegerField(int defaultValue) {
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
            Integer.parseInt(this.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

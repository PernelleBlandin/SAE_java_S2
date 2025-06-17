package vue;
import javafx.scene.control.TextField;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Une bibliothèque de composants
 */
public class BibliothequeComposants {

    /**
     * Constructeur privé car bibliothèque
     */
    public BibliothequeComposants() {
    }

    ;

    /**
     * Obtenir une barre de recherche.
     * @param placeholder Le texte affiché sur la barre.
     * @return La barre de recherche.
     */
    public static TextField getSearchBar(String placeholder) {
        TextField root = new TextField();
        root.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        root.setPromptText(placeholder);
        root.setMinHeight(50);
        root.setPrefWidth(18*50);

        return root;
    }
}

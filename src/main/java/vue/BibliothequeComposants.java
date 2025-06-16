package vue;

import javafx.scene.control.TextField;

/** Une bibliothèque de composants */
public class BibliothequeComposants {
    /** Constructeur privé car bibliothèque */
    public BibliothequeComposants() {};

    /**
     * Obtenir une barre de recherche.
     * @param placeholder Le texte affiché sur la barre.
     * @return La barre de recherche.
     */
    public static TextField getSearchBar(String placeholder) {
        TextField root = new TextField();
        root.setPromptText(placeholder);

        return root;
    }
}

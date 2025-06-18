package vue._components;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Une barre de recherche */
public class SearchBar extends TextField {
    /** Le placeholder/le texte affiché en fond */
    private String placeholder;

    /**
     * Initialiser la barre de recherche.
     * @param placeholder Le placeholder de la barre de recherche.
     */
    public SearchBar(String placeholder) {
        this.placeholder = placeholder;

        this.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        this.setPromptText(this.placeholder);
        this.setMinHeight(20);
    }
}

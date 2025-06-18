package vue._components;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SearchBar extends TextField {
    private String placeholder;
    public SearchBar(String placeholder) {
        this.placeholder = placeholder;

        this.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        this.setPromptText(this.placeholder);
        this.setMinHeight(20);
    }
}

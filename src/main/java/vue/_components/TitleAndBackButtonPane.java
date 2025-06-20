package vue._components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Un BorderPane avec un titre et un bouton "Retour".
 */
public class TitleAndBackButtonPane extends BorderPane {
    /** Le titre */
    private String title;
    /** Le contrôleur lors du clic sur le bouton "Retour" */
    private EventHandler<ActionEvent> controleur;

    /**
     * Initialiser le composant avec un bouton "Retour" et un titre.
     * @param title Le titre
     * @param controleur Le contrôleur lors du clic sur le bouton "Retour"
     */
    public TitleAndBackButtonPane(String title, EventHandler<ActionEvent> controleur) {
        this.title = title;
        this.controleur = controleur;

        this.setLeft(this.getBackButton());
        this.setCenter(this.getTitle());
    }

    /**
     * Obtenir le bouton "Retour".
     * @return Le bouton "Retour".
     */
    private Button getBackButton() {
        Button backButton = new Button("Retour");
        backButton.setOnAction(this.controleur);
        return backButton;
    }

    /**
     * Obtenir le titre.
     * @return Le titre.
     */
    private Label getTitle() {
        Label labelTitre = new Label(this.title);
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        return labelTitre;
    }
}

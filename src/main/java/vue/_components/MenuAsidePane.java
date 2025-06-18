package vue._components;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Composant pour le menu de navigation sur le côté */
public class MenuAsidePane extends VBox {
    /** La liste des menus */
    private List<String> listMenus;
    /** Le contrôleur des boutons */
    private EventHandler<ActionEvent> controleur;

    /**
     * Initialiser le menu de navigation sur le côté.
     * @param listMenus La liste des menus possible.
     * @param controleur Le contrôleur initialisé.
     */
    public MenuAsidePane(List<String> listMenus, EventHandler<ActionEvent> controleur) {
        this.listMenus = listMenus;
        this.controleur = controleur;

        this.setSpacing(15);
        this.setPadding(new Insets(15, 20, 0, 15));

        this.addMenus();
    }

    /**
     * Ajouter les menus au panel.
     */
    public void addMenus() {
        for (String menu: this.listMenus) {
            Button button = new Button(menu);

            button.setFont(Font.font("Arial", FontWeight.NORMAL, 18)); 
            button.setAlignment(Pos.CENTER);

            button.setPrefHeight(50);
            button.setMaxWidth(Double.MAX_VALUE);

            this.getChildren().add(button);
            button.setOnAction(this.controleur);
        }
    }
}

package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue._components.BaseListBooksPane;

public class ControleurNavigation implements EventHandler<ActionEvent> {
    private BaseListBooksPane listBookPane;
    public ControleurNavigation(BaseListBooksPane listBooksPane) {
        this.listBookPane = listBooksPane;
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();

        int pageActuelle = this.listBookPane.getCurPage();
        String labelText = button.getText();
        if (labelText.equals("Précédent")) {
            this.listBookPane.setCurPage(--pageActuelle);
        } else {
            this.listBookPane.setCurPage(++pageActuelle);
        }

        this.listBookPane.miseAJourAffichage();
    }
}

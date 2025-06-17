package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.MAJVueInterface;

public class ControleurInfoLivre implements EventHandler<ActionEvent> {
    private MAJVueInterface app;
    private ChaineLibrairie modele;
    private Livre livre;

    public ControleurInfoLivre(MAJVueInterface app, ChaineLibrairie modele, Livre livre) {
        this.app = app;
        this.modele = modele;
        this.livre = livre;
    }

    @Override
    public void handle(ActionEvent event) {
        // TODO
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.showAndWait();
    }
}

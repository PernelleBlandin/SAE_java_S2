package controleurs.customers;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import modeles.ChaineLibrairie;
import modeles.Client;
import vue.customers.CustomerScene;

public class ControleurBoutonPayer implements EventHandler<ActionEvent> {
    private CustomerScene customerScene;
    private ChaineLibrairie modele;
    private ToggleGroup groupLivraison;
    public ControleurBoutonPayer(CustomerScene customerScene, ChaineLibrairie modele, ToggleGroup groupLivraison) {
        this.customerScene = customerScene;
        this.modele = modele;
        this.groupLivraison = groupLivraison;
    }

    @Override
    public void handle(ActionEvent event) {
        RadioButton selectedLivraison = (RadioButton) this.groupLivraison.getSelectedToggle();
        char livraison = selectedLivraison.getText().equals("En magasin") ? 'M' : 'C';

        Client client = this.modele.getClientActuel();
        try {
            client.commander(livraison, 'O');
        } catch (SQLException e) {
            // TODO: handle exception
        }

        Alert alertInfo = new Alert(AlertType.INFORMATION);
        alertInfo.setTitle("Commande passée !");
        alertInfo.setContentText("Votre commande a bien été réalisée !");
        alertInfo.show();

        this.customerScene.showHome();
    }
}

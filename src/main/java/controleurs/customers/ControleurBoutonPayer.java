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
import vue._components.alerts.AlertErreurException;
import vue.customers.CustomerScene;

/**
 * Contrôleur du bouton "Payer" dans le panier client.
 */
public class ControleurBoutonPayer implements EventHandler<ActionEvent> {
    /** La scène de la page client */
    private CustomerScene customerScene;
    /** Le modèle de données */
    private ChaineLibrairie modele;
    /** Le groupe de radio button pour le choix de la livraison */
    private ToggleGroup groupLivraison;

    /**
     * Initialiser le contrôleur du bouton "Payer".
     * @param customerScene La scène de la page client
     * @param modele Le modèle de données
     * @param groupLivraison Le groupe de radio button pour le choix de la livraison
     */
    public ControleurBoutonPayer(CustomerScene customerScene, ChaineLibrairie modele, ToggleGroup groupLivraison) {
        this.customerScene = customerScene;
        this.modele = modele;
        this.groupLivraison = groupLivraison;
    }

    @Override
    /**
     * Recevoir un événement du bouton "Payer" pour effectuer la paiement.
     * @param event Un événément.
     */
    public void handle(ActionEvent event) {
        RadioButton selectedLivraison = (RadioButton) this.groupLivraison.getSelectedToggle();
        char livraison = selectedLivraison.getText().equals("En magasin") ? 'M' : 'C';

        Client client = this.modele.getClientActuel();
        try {
            client.commander(livraison, 'O');
        } catch (SQLException e) {
            new AlertErreurException("Une erreur s'est produite lors de l'enregistrement de la commande !", e.getMessage());
            return;
        }

        Alert alertInfo = new Alert(AlertType.INFORMATION);
        alertInfo.setTitle("Commande passée !");
        alertInfo.setContentText("Votre commande a bien été réalisée !");
        alertInfo.show();

        this.customerScene.showHome();
    }
}

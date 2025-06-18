package controleurs.customers;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Magasin;
import modeles.Panier;
import vue._components.alerts.AlertYesNo;
import vue.customers.CustomerScene;

public class ControleurChangerMagasin implements EventHandler<ActionEvent> {
    private CustomerScene customerScene;
    private ChaineLibrairie modele;

    /**
     * Initiailiser le contrôleur du bouton de l'image pour retourner à l'acceuil.
     * @param customerScene La vue à mettre à jour en cas de clic sur le bouton. 
     */
    public ControleurChangerMagasin(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton pour retourner à l'acceuil client.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        @SuppressWarnings("unchecked")
        ComboBox<Magasin> comboBox = (ComboBox<Magasin>) event.getSource();

        Client client = this.modele.getClientActuel();
        Magasin magasinChoisi = comboBox.getValue();
        if (client.getMagasin().equals(magasinChoisi)) return;

        Panier panier = client.getPanier();
        if (panier.getDetailLivres().size() > 0) {
            AlertYesNo alert = new AlertYesNo(
                "Changement de magasin",
                "Voulez-vous changer de magasin ?",
                "Vous possédez des articles dans votre panier. Changer de magasin le réinitialisera."
            );

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent() || !result.get().getText().equals("Oui")) return;

            panier.setMagasin(magasinChoisi);
            try {
                this.modele.getPanierBD().changerMagasin(panier);
            } catch (SQLException e) {
               // TODO
            }
        }
        
        panier.setMagasin(magasinChoisi);
        client.setMagasin(magasinChoisi);
        try {
            this.modele.getClientBD().changerMagasin(client.getId(), magasinChoisi.getId());
        } catch (SQLException e) {
            // TODO
        }

        this.customerScene.reloadHome();
    }
}

package controleurs.customers;

import java.sql.SQLException;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.customers.CustomerScene;

public class ControleurCustomerRecherche implements EventHandler<KeyEvent> {
    private CustomerScene customerScene;
    private ChaineLibrairie modele;
    public ControleurCustomerRecherche(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;
    }

    @Override
    public void handle(KeyEvent event) {
        TextField textField = (TextField) event.getSource();

        String search = textField.getText();
        if (search.equals("")) {
            this.customerScene.showHome();
        } else {
            try {
                List<Livre> tousLesLivres = this.modele.getLivreBD().obtenirListeLivre();
                List<Livre> listeLivresRecherche = this.modele.rechercherLivres(tousLesLivres, search);

                this.customerScene.showListBooks("Recherche", listeLivresRecherche);
            } catch (SQLException e) {
                // TODO: handle exception
            }
        }
    }
}

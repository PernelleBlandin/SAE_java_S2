package controleurs.customers;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private List<Livre> listLivres;
    
    public ControleurCustomerRecherche(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;

        try {
            this.listLivres = this.modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            this.listLivres = new ArrayList<>();
        }
    }

    @Override
    public void handle(KeyEvent event) {
        TextField textField = (TextField) event.getSource();

        String search = textField.getText();
        if (search.equals("")) {
            this.customerScene.showHome();
        } else {
            List<Livre> listeLivresRecherche = this.modele.rechercherLivres(this.listLivres, search);
            this.customerScene.showListBooks("Recherche", listeLivresRecherche);
        }
    }
}

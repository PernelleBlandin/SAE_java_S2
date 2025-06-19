package controleurs.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.alerts.AlertErreurException;
import vue.admin.AdminScene;

/**
 * Contrôleur du bouton "Stock" pour un magasin.
 */
public class ControleurBoutonMagasinStock implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Un magasin */
    private Magasin magasin;

    /**
     * Initialiser le contrôleur du bouton "Stock" pour un magasin.
     * @param adminScene La scène de la page administrateur
     * @param modele Le modèle de la chaîne de librairie
     * @param magasin Un magasin.
     */
    public ControleurBoutonMagasinStock(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton et afficher la page stock pour le magasin.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer la liste des livres.", e.getMessage());
            return;
        }

        this.adminScene.showStock(livres, this.magasin);
    }
}

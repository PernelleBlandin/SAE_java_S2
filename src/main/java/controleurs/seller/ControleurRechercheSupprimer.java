package controleurs.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.SceneListBooksInterface;

/**
 * Le contrôleur de la barre de recherche 
 */
public class ControleurRechercheSupprimer implements EventHandler<KeyEvent> {
    /** La scène de la page */
    private SceneListBooksInterface scene;
    /** Le modèle de données */
    private ChaineLibrairie modele;
    /** La liste des livres */
    private List<Livre> listBooks;
    
    /**
     * Initialiser le contrôleur de la barre de recherche.
     * @param scene La scène de la page.
     * @param modele Le modèle de données.
     */
    public ControleurRechercheSupprimer(SceneListBooksInterface scene, ChaineLibrairie modele) {
        this.scene = scene;
        this.modele = modele;

        try {
            this.listBooks = this.modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            this.listBooks = new ArrayList<>();
        }
    }

    @Override
    /**
     * Recevoir un événement lors de la saisie d'un caractère dans la barre de recherche.
     * @param event Un événement.
     */
    public void handle(KeyEvent event) {
        TextField textField = (TextField) event.getSource();

        String search = textField.getText();
        if (search.equals("")) {
            this.scene.showHome();
        } else {
            List<Livre> listeLivresRecherche = this.modele.rechercherLivres(this.listBooks, search);
            this.scene.showListBooks("Recherche", listeLivresRecherche);
        }
    }
}

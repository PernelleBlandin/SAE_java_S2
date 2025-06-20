package controleurs.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue.SceneGestionStockInterface;
import vue._components.alerts.AlertErreur;
import vue._components.alerts.AlertErreurException;
import vue._components.alerts.AlertInfo;
import vue._components.alerts.AlertYesNo;
import vue._components.numberField.IntegerField;

/**
 * Contrôleur du bouton "Enregistrer Stock" pour modifier le stock d'un livre
 * dans un magasin.
 */
public class ControleurBoutonEnregistrerStock implements EventHandler<ActionEvent> {
    /** La scène de gestion de stocks */
    private SceneGestionStockInterface scene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin */
    private Magasin magasin;
    /** Le livre à modifier */
    private Livre livre;
    /** Le champ de saisie du stock */
    private IntegerField numberField;

    /**
     * Initialiser le contrôleur du bouton "Enregistrer Stock".
     * 
     * @param scene       La scène de la page de gestions de stocks
     * @param modele      Le modèle de données
     * @param magasin     Le magasin dans lequel le stock est modifié
     * @param livre       Le livre dont le stock est modifié
     * @param numberField Le champ de saisie du stock
     */
    public ControleurBoutonEnregistrerStock(SceneGestionStockInterface scene, ChaineLibrairie modele, Magasin magasin, Livre livre,
            IntegerField numberField) {
        this.scene = scene;
        this.modele = modele;
        this.magasin = magasin;
        this.livre = livre;
        this.numberField = numberField;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Enregistrer Stock" et
     * modifier le stock du livre.
     * 
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        AlertYesNo alert = new AlertYesNo(
                "Modification du stock",
                "Êtes-vous sûr de vouloir modifier le stock du livre " + this.livre.getTitre() + "dans le magasin "
                        + this.magasin.getNom() + " ?",
                "Le nouveau stock sera de " + this.numberField.getValeur() + ".");

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || !result.get().getText().equals("Oui"))
            return;

        try {
            this.modele.getMagasinBD().setStockLivre(this.magasin.getId(), this.livre.getISBN(),
                    this.numberField.getValeur());
        } catch (SQLException e) {
            new AlertErreur("Le magasin contient des vendeurs, stocks, ou commandes, et ne peut pas être supprimé.");
        }

        new AlertInfo("Stock modifié", "Le stock du livre " + this.livre.toString() + " a bien été modifié.");

        // Actualiser de la liste
        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            new AlertErreurException("Impossible de récupérer la liste des livres.", e.getMessage());
            return;
        }

        this.scene.showStock(livres, this.magasin);
    }
}

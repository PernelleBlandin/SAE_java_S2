package controleurs.seller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.seller.SellerDeleteBookListPane;
import vue._components.alerts.AlertErreurException;
import vue._components.alerts.AlertYesNo;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurSupprimerLivre implements EventHandler<ActionEvent> {
    /** La pane de la liste de livres */
    private SellerDeleteBookListPane sellerDeleteBookPane;
    /** Le modèle de données */
    private ChaineLibrairie modele;
    /** Le livre à supprimer */
    private Livre livre;

    /**
     * Initiailiser le contrôleur du bouton "Supprimer" d'une carte de livre.
     * 
     * @param sellerDeleteBookPane La pane de la liste de livres.
     * @param modele               Le modèle.
     * @param livre                Le livre à ajouter.
     */
    public ControleurSupprimerLivre(SellerDeleteBookListPane sellerDeleteBookPane, ChaineLibrairie modele,
            Livre livre) {
        this.sellerDeleteBookPane = sellerDeleteBookPane;
        this.modele = modele;
        this.livre = livre;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton "Supprimer".
     * 
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        AlertYesNo confirmee = new AlertYesNo("Confirmation suppression",
                "Êtes-vous sûr de vouloir supprimer le livre \"" + this.livre.getTitre() + "\" ?",
                "Cette action est irréversible.");
        confirmee.showAndWait();

        if (confirmee.getResult().getText().equals("Oui") && confirmee.getResult() != null) {
            try {
                this.modele.getLivreBD().supprimerLivre(livre.getISBN());
                this.sellerDeleteBookPane.retirerLivre(this.livre);
                this.sellerDeleteBookPane.miseAJourAffichage();
            } catch (SQLException e) {
                new AlertErreurException("Le livre n'a pas pu être supprimé", e.getMessage());
            }
        }
    }
}
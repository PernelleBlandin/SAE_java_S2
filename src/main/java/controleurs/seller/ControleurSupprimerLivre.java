package controleurs.seller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.DetailLivre;
import modeles.Livre;
import modeles.Panier;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookCardComponent;
import vue._components.bookCard.CustomerBookCardComponent;
import vue._components.alerts.AlertYesNo;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurSupprimerLivre implements EventHandler<ActionEvent> {
    /** La carte du livre */
    private SellerBookCardComponent component;
    /** Le modèle de données */
    private ChaineLibrairie modele;
    /** Le livre à supprimer */
    private Livre livre;

    /**
     * Initiailiser le contrôleur du bouton "Supprimer" d'une carte de livre.
     * @param component La carte du livre. 
     * @param modele Le modèle.
     * @param livre Le livre à ajouter.
     */
    public ControleurSupprimerLivre(SellerBookCardComponent component, ChaineLibrairie modele, Livre livre) {
        this.component = component;
        this.modele = modele;
        this.livre = livre;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton "Supprimer".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        AlertYesNo confirmee = new AlertYesNo("Confirmation suppression", "Êtes-vous sûr de vouloir supprimer le livre \"" + this.livre.getTitre() + "\" ?", 
            "Cette action est irréversible."
        );
        confirmee.showAndWait();
        if (confirmee.getResult().getText().equals("Oui")&&confirmee.getResult()!=null) {
            try{
                this.modele.getLivreBD().supprimerLivre(livre.getISBN());
                this.component.supprimerCarte();
                this.component.getVue().miseAJourAffichage();
            }catch (SQLException e) {
                // TODO gérer l'erreur
                e.printStackTrace();
            }
        }
    }
}
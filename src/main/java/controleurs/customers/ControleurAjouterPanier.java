package controleurs.customers;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.DetailLivre;
import modeles.Livre;
import modeles.Panier;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.CustomerBookCardComponent;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurAjouterPanier implements EventHandler<ActionEvent> {
    /** La carte du livre, affiché dans la vue */
    private CustomerBookCardComponent component;
    /** Le modèle de données */
    private ChaineLibrairie modele;
    /** Le livre à ajouter au panier */
    private Livre livre;

    /**
     * Initiailiser le contrôleur du bouton "Ajouter Panier"
     * @param component La carte du livre. 
     * @param modele Le modèle.
     * @param livre Le livre à ajouter.
     */
    public ControleurAjouterPanier(CustomerBookCardComponent component, ChaineLibrairie modele, Livre livre) {
        this.component = component;
        this.modele = modele;
        this.livre = livre;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton "Ajouter panier".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Client client = this.modele.getClientActuel();
        Panier panier = client.getPanier();

        DetailLivre detailLivre = panier.ajouterLivre(this.livre);
        try {
            this.modele.getPanierBD().ajouterLivre(panier, detailLivre);
        } catch (SQLException e) {
            new AlertErreurException("Une erreur est survenue lors de l'ajout du livre au panier.", e);
            return;
        }

        int curQuantiteStock = this.component.getQuantite();
        this.component.setQuantite(curQuantiteStock - 1);
        this.component.miseAJourAffichage();
    }
}

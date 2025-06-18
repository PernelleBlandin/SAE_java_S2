package controleurs.customers;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.DetailLivre;
import modeles.LivreIntrouvableException;
import modeles.Panier;
import vue.customers.CustomerPanierPane;

public class ControleurRetirerLivrePanier implements EventHandler<ActionEvent> {
    /** Vue du panier client */
    private CustomerPanierPane customerPanierView;
    private ChaineLibrairie modele;
    private DetailLivre detailLivre;

    /**
     * Initialiser le contrôleur pour supprimer une quantité d'un livre d'un panier client.
     * @param customerPanierView La vue du panier client
     * @param modele Le modèle
     * @param detailPanier Le detail panier à supprimer.
     */
    public ControleurRetirerLivrePanier(CustomerPanierPane customerPanierView, ChaineLibrairie modele, DetailLivre detailPanier) {
        this.customerPanierView = customerPanierView;
        this.modele = modele;
        this.detailLivre = detailPanier;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Supprimer" dans le panier client.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Client client = this.modele.getClientActuel();
        Panier panier = client.getPanier();

        try {
            panier.retirerQuantiteLivre(detailLivre.getLivre(), 1);
            this.modele.getPanierBD().updatePanier(panier);

            this.customerPanierView.miseAJourAffichage();
        } catch (SQLException e) {
            // TODO
        } catch (LivreIntrouvableException e) {
            // TODO: afficher popup
        }
    }
}

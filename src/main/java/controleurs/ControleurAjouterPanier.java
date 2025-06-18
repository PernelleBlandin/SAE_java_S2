package controleurs;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.DetailLivre;
import modeles.Livre;
import modeles.Panier;
import vue._components.bookCard.CustomerBookCardComponent;

/** Contrôleur sur un livre pour l'ajouter au panier */
public class ControleurAjouterPanier implements EventHandler<ActionEvent> {
    private CustomerBookCardComponent component;
    private ChaineLibrairie modele;
    private Livre livre;

    /**
     * Initiailiser le contrôleur du bouton "Ajouter Panier"
     * @param app La vue à mettre à jour en cas de clic sur le bouton. 
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
            // TODO
            System.err.println("Une erreur est survenue lors de la mise à jour du panier en base de données : " + e.getMessage());
            return;
        }

        int curQuantiteStock = this.component.getQuantite();
        this.component.setQuantite(curQuantiteStock - 1);
        this.component.miseAJourAffichage();
    }
}

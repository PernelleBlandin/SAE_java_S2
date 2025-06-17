package controleurs;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Livre;
import vue.seller.SellerHomeView;

/** Contrôleur sur les boutons "Voir plus" pour afficher une liste des livres */
public class ControleurVoirPlusSeller implements EventHandler<ActionEvent> {
    /** Vue de l'acceuil de la page vendeur */
    SellerHomeView sellerHomeView;
    /** Une liste de livres */
    private List<Livre> listeLivres;

    /**
     * Initialiser le contrôleur du bouton "Voir plus", affichant une liste de livres.
     * @param sellerHomeView La vue de l'acceuil de la page vendeur.
     * @param listeLivres Une liste de livres à afficher en cas de clic sur le bouton.
     */
    public ControleurVoirPlusSeller(SellerHomeView sellerHomeView, List<Livre> listeLivres) {
        this.sellerHomeView = sellerHomeView;
        this.listeLivres = listeLivres;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Voir plus", pour afficher une liste de livres.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.sellerHomeView.showListBooks(this.listeLivres);
    }
}
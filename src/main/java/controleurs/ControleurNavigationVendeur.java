package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.seller.SellerListBook;

public class ControleurNavigationVendeur implements EventHandler<ActionEvent> {
    /** Vue de l'acceuil de la page client */
    private SellerListBook sellerListView;

    /**
     * Initialiser le contrôleur du bouton "Voir plus", affichant une liste de livres.
     * @param sellerListView La vue de l'acceuil de la page client.
     * @param listeLivres Une liste de livres à afficher en cas de clic sur le bouton.
     */
    public ControleurNavigationVendeur(SellerListBook sellerListView) {
        this.sellerListView = sellerListView;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Voir plus", pour afficher une liste de livres.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();

        int pageActuelle = this.sellerListView.getCurPage();
        String labelText = button.getText();
        if (labelText.equals("Précédent")) {
            this.sellerListView.setCurPage(--pageActuelle);
        } else {
            this.sellerListView.setCurPage(++pageActuelle);
        }

        this.sellerListView.miseAJourAffichage();
    }
}

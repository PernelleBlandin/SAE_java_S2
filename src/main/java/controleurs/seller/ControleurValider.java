package controleurs.seller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.seller.SellerAddBookView;

public class ControleurValider implements EventHandler<ActionEvent> {

    private SellerAddBookView sellerAddBookView;

    public ControleurValider(SellerAddBookView vue) {
        this.sellerAddBookView = vue;
    }

    @Override
    /**
     * Récupère un événement lors d'un clic sur "valider" et retourne sur la page.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        this.sellerAddBookView.miseAJourAffichage();
    }
}

package controleurs.seller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.seller.SellerScene;

/**
 * Le contrôleur pour changer de menu dans la page vendeur
 */
public class ControleurMenuVendeur implements EventHandler<ActionEvent>{
    /** La scène de la page vendeur */
    private SellerScene sellerScene;

    /**
     * Initiailiser le contrôleur pour changer de menu dans la page vendeur.
     * @param sellerScene La scène de la page vendeur.
     */
    public ControleurMenuVendeur(SellerScene sellerScene) {
        this.sellerScene = sellerScene;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur un des boutons a gauche
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        Button bouton = (Button) event.getSource();

        String label = bouton.getText();
        switch (label) {
            case "Ajouter un livre": {
                // this.sellerScene.showAddBookSeller();
                break;
            }
            case "Supprimer un livre":{
                // this.app.showDeleteBookSeller();
                break;
            }
            case "Mettre à jour la quantité d'un livre": {
                // this.app.showUpdateBook();
                break;
            }
            case "Transférer un livre": {
                // this.app.showTransfer();
                break;
            }
            case "Agir en tant que client": {
                // this.app.showRpCustomer();
                break;
            }
            default:
                break;
        }
    }
}


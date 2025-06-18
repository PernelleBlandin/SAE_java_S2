package controleurs.seller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import vue.AppIHM;

public class ControleurPage implements EventHandler<ActionEvent>{
    private AppIHM app;

    /**
     * Initiailiser le contrôleur du bouton de "ajouter un livre".
     * @param app La vue à mettre à jour en cas de clic sur le bouton. 
     */
    public ControleurPage(AppIHM app) {
        this.app = app;
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
                this.app.showAddBookSeller();
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


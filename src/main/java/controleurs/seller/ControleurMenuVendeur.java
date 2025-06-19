package controleurs.seller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue.seller.SellerScene;

/**
 * Le contrôleur pour changer de menu dans la page vendeur
 */
public class ControleurMenuVendeur implements EventHandler<ActionEvent>{
    /** La scène de la page vendeur */
    private SellerScene sellerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initiailiser le contrôleur pour changer de menu dans la page vendeur.
     * @param sellerScene La scène de la page vendeur.
     * @param modele Le modèle
     */
    public ControleurMenuVendeur(SellerScene sellerScene, ChaineLibrairie modele) {
        this.sellerScene = sellerScene;
        this.modele = modele;
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
                this.sellerScene.showAddBook();
                break;
            }
            case "Supprimer un livre":{
                Magasin magasin = this.modele.getVendeurActuel().getMagasin();

                List<Livre> listeLivres = new ArrayList<>();
                try {
                    listeLivres = this.modele.getLivreBD().obtenirLivreEnStockMagasin(magasin);
                } catch (SQLException e) {
                    // TODO: handle exception
                }
                
                this.sellerScene.showDeleteBook(listeLivres);
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


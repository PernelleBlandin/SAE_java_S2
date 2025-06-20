package controleurs.seller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Livre;
import modeles.Magasin;
import vue._components.alerts.AlertErreurException;
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
            case "Accueil": {
                this.sellerScene.showHome();
                break;
            }
            case "Ajouter un livre": {
                this.sellerScene.showAddBook();
                break;
            }
            case "Supprimer un livre":{
                Magasin magasin = this.modele.getVendeurActuel().getMagasin();

                List<Livre> listeLivres = new ArrayList<>();
                try {
                    listeLivres = this.modele.getLivreBD().obtenirLivreDejaEnStockMagasin(magasin);
                } catch (SQLException e) {
                    new AlertErreurException("Erreur lors de la récupération des livres en stock", e.getMessage());
                    return;
                }
                
                this.sellerScene.showDeleteBook(listeLivres);
                break;
            }
            case "Mettre à jour la quantité d'un livre": {
                Magasin magasin = this.modele.getVendeurActuel().getMagasin();

                List<Livre> listeLivres = new ArrayList<>();
                try {
                    listeLivres = this.modele.getLivreBD().obtenirListeLivre();
                } catch (SQLException e) {
                    new AlertErreurException("Erreur lors de la récupération des livres en stock", e.getMessage());
                    return;
                }

                this.sellerScene.showStock(listeLivres, magasin);
                break;
            }
            case "Transférer un livre": {
                Magasin magasin = this.modele.getVendeurActuel().getMagasin();

                List<Livre> listeLivres = new ArrayList<>();
                try {
                    listeLivres = this.modele.getLivreBD().obtenirLivreEnStockMagasin(magasin);
                } catch (SQLException e) {
                    new AlertErreurException("Erreur lors de la récupération des livres en stock", e.getMessage());
                    return;
                }

                this.sellerScene.showTransfer(listeLivres, magasin);
                break;
            }
            case "Agir en tant que client": {
                List<Client> listeClients = new ArrayList<>();
                try {
                    listeClients = this.modele.getClientBD().obtenirListeClient();
                } catch (SQLException e) {
                    new AlertErreurException("Erreur lors de la récupération des clients", e.getMessage());
                    return;
                }

                this.sellerScene.showViewAsCustomer(listeClients);
                break;
            }
            default:
                break;
        }
    }
}


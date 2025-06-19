package vue.seller;

import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.BaseListElementsWithSearchPane;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookRemoveCardComponent;

/**
 * La pane pour afficher la liste des livres à supprimer pour le vendeur.
 */
public class SellerDeleteBookListPane extends BaseListElementsWithSearchPane<Livre> {
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane affichant la liste des livres à supprimer à afficher au vendeur.
     * @param listeLivres La liste des livres.
     * @param sellerScene La scène de la page vendeur.
     * @param modele Le modèle
     */
    public SellerDeleteBookListPane(List<Livre> listeLivres, ChaineLibrairie modele) {
        super("Supprimer des livres", listeLivres, 2, 3, "Rechercher un livre à supprimer...");
        
        this.modele = modele;

        this.addComponents();
    }

    /**
     * Retirer un livre de la liste.
     * @param livre Un livre.
     */
    public void retirerLivre(Livre livre) {
        this.listeElements.remove(livre);
    }

    /**
     * Obtenir le titre.
     * @return Le BorderPane contenant ses deux informations.
     */
    public Label getHeaderPane() {
        Label headerLabel = new Label(this.getTitre());
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        headerLabel.setAlignment(Pos.CENTER);
        return headerLabel;
    }

    /**
     * Obtenir le composant d'un élément de la liste.
     * @param livre Le livre à afficher.
     * @return Un composant SellerBookRemoveCardComponent contenant les informations du livre.
     */
    public SellerBookRemoveCardComponent getElementComponent(Livre livre) {
        Vendeur vendeur = this.modele.getVendeurActuel();
        Magasin magasin = vendeur.getMagasin();

        int quantiteStock = 0;
        try {
            quantiteStock = this.modele.getMagasinBD().obtenirStockLivre(vendeur.getMagasin().getId(), livre.getISBN());
        } catch (SQLException e) {
            new AlertErreurException("Le stock dans le magasin n'a pas pu être récupéré.", e.getMessage());
        }

        int nbVentes = 0;
        try {
            nbVentes = this.modele.getLivreBD().getNombreVentesLivreMagasin(livre.getISBN(), magasin.getId());
        } catch (SQLException e) {
            new AlertErreurException("Le nombre de ventes du livre n'a pas pu être récupéré.", e.getMessage());
        }

        SellerBookRemoveCardComponent bookCard = new SellerBookRemoveCardComponent(livre, quantiteStock, nbVentes, this, this.modele);
        return bookCard;
    }
}

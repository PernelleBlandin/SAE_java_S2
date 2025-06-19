package vue.seller;

import java.sql.SQLException;
import java.util.List;

import controleurs.ControleurAcceuil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.BaseListBooksPane;
import vue._components.TitleAndBackButtonPane;
import vue._components.alerts.AlertErreurException;
import vue._components.bookCard.SellerBookRemoveCardComponent;

public class SellerDeleteBookListPane extends BaseListBooksPane {
    private SellerScene sellerScene;
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane affichant la liste des livres à supprimer à afficher au vendeur.
     * @param listeLivres La liste des livres.
     * @param sellerScene La scène de la page vendeur.
     * @param modele Le modèle
     */
    public SellerDeleteBookListPane(List<Livre> listeLivres, SellerScene sellerScene, ChaineLibrairie modele) {
        super("Supprimer des livres", listeLivres, 2, 3);
        
        this.sellerScene = sellerScene;
        this.modele = modele;

        this.setSpacing(30);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(60, 40, 40, 40));

        this.getChildren().add(this.getListeLivresPane());
    }

    /**
     * Retirer un livre de la liste.
     * @param livre Un livre.
     */
    public void retirerLivre(Livre livre) {
        this.listeLivres.remove(livre);
    }

    /**
     * Mettre à jour l'affichage de la page.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(0, this.getListeLivresPane());
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    protected BorderPane getTitleAndBackButtonPane() {
        return new TitleAndBackButtonPane(this.getTitre(), new ControleurAcceuil(this.sellerScene));
    }

    /**
     * Obtenir la VBox contenant la liste des "cartes" des livres.
     * @return La VBox contenant la liste des "cartes" des livres.
     */
    protected VBox getListeLivresPane() {
        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbElementsParPage = nbLignes * nbColonnes;

        List<Livre> listeLivres = this.getListeLivres();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.getCurPage()) + (intLigne * nbColonnes) + intColonne;
                if (index >= listeLivres.size()) break;

                Livre livre = listeLivres.get(index);

                BorderPane bookCard = this.createDeleteComponent(livre);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }
        listeLivresVBox.getChildren().add(getNavigationsBoutonsPane());
        return listeLivresVBox;
    }

    /**
     * Créer ou obtenir la "carte" d'un livre, affichant les informations de ce dernier et permettant de le supprimer.
     * @param livre Un livre.
     * @return La carte du livre.
     */
    private SellerBookRemoveCardComponent createDeleteComponent(Livre livre) {
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

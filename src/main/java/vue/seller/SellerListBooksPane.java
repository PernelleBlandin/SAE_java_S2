package vue.seller;

import java.util.List;

import controleurs.ControleurAcceuil;
import javafx.scene.layout.BorderPane;
import modeles.Livre;
import vue._components.BaseListElementsPane;
import vue._components.TitleAndBackButtonPane;

/**
 * Pane de la liste des livres à afficher au vendeur.
 */
public class SellerListBooksPane extends BaseListElementsPane<Livre> {
    /** La scène de la page vendeur */
    private SellerScene sellerScene;

    /**
     * Initialiser la pane affichant la liste des livres à afficher au vendeur.
     * @param titre Le titre de la liste.
     * @param listeLivres La liste des livres.
     * @param sellerScene La scène de la page vendeur.
     */
    public SellerListBooksPane(String titre, List<Livre> listeLivres, SellerScene sellerScene) {
        super(titre, listeLivres, 2, 3);
        this.sellerScene = sellerScene;

        this.addComponents();
    }

    /**
    * Définir la liste des livres à afficher.
    * @param listeLivres La nouvelle liste de livres.
    */
    public void setListeLivres(List<Livre> listeLivres) {
        this.listeElements = listeLivres;
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public BorderPane getHeaderPane() {
        return new TitleAndBackButtonPane(this.getTitre(), new ControleurAcceuil(this.sellerScene));
    }

    public BorderPane getBookComponent(Livre livre) {
        return this.sellerScene.createOrGetCardComponent(livre);
    }
}
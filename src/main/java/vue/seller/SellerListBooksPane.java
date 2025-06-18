package vue.seller;

import java.util.List;

import controleurs.ControleurAcceuil;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modeles.Livre;
import vue._components.BaseListBooksPane;
import vue._components.TitleAndBackButtonPane;

/**
 * Pane de la liste des livres à afficher au vendeur.
 */
public class SellerListBooksPane extends BaseListBooksPane {
    /** La scène de la page vendeur */
    private SellerScene sellerScene;

    /**
     * Initialiser la pane affichant la liste des livres à afficher au client.
     * @param titre Le titre de la liste.
     * @param listeLivres La liste des livres.
     * @param sellerScene La scène de la page client.
     */
    public SellerListBooksPane(String titre, List<Livre> listeLivres, SellerScene sellerScene) {
        super(titre, listeLivres, 2, 3);
        this.sellerScene = sellerScene;

        this.addComponents();
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

                BorderPane bookCard = this.sellerScene.createOrGetCardComponent(livre);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

        return listeLivresVBox;
    }
}
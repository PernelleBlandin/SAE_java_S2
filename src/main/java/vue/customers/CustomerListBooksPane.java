package vue.customers;

import java.util.List;

import controleurs.ControleurAcceuil;
import javafx.scene.layout.BorderPane;
import modeles.Livre;
import vue._components.BaseListBooksPane;
import vue._components.TitleAndBackButtonPane;

/**
 * Pane de la liste des livres à afficher au client.
 */
public class CustomerListBooksPane extends BaseListBooksPane {
    /** La scène de la page client */
    private CustomerScene customerScene;

    /**
     * Initialiser la pane affichant la liste des livres à afficher au client.
     * @param titre Le titre de la liste.
     * @param listeLivres La liste des livres.
     * @param customerScene La scène de la page client.
     */
    public CustomerListBooksPane(String titre, List<Livre> listeLivres, CustomerScene customerScene) {
        super(titre, listeLivres, 2, 4);
        this.customerScene = customerScene;

        this.addComponents();
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public BorderPane getHeaderPane() {
        return new TitleAndBackButtonPane(this.getTitre(), new ControleurAcceuil(this.customerScene));
    }

    public BorderPane getBookComponent(Livre livre) {
        return this.customerScene.createOrGetCardComponent(livre);
    }
}
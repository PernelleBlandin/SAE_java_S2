package vue.customers;

import java.util.List;

import controleurs.customers.ControleurAcceuilClient;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;
import vue._components.BaseListBooksPane;

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
        super(titre, listeLivres);
        this.customerScene = customerScene;

        this.addComponents();
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    protected BorderPane getTitleAndBackButtonPane() {
        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilClient(this.customerScene));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label(this.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        borderPaneTitre.setCenter(labelTitre);
        return borderPaneTitre;
    }

    /**
     * Obtenir la VBox contenant la liste des "cartes" des livres.
     * @return La VBox contenant la liste des "cartes" des livres.
     */
    protected VBox getListeLivresPane() {
        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        List<Livre> listeLivres = this.getListeLivres();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.getCurPage()) + (intLigne * nbColonnes) + intColonne;
                if (index >= listeLivres.size()) break;

                Livre livre = listeLivres.get(index);

                BorderPane bookCard = this.customerScene.createOrGetCardComponent(livre);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

        return listeLivresVBox;
    }
}
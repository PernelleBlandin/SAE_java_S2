package vue.customers;

import java.util.List;

import controleurs.ControleurAcceuilClient;
import controleurs.ControleurNavigation;
import javafx.geometry.Insets;
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

/** Page d'une liste de livres */
public class CustomerListBookPane extends VBox {
    private CustomerScene customerScene;

    private String titre;
    private List<Livre> listeLivres;

    private int curPage;
    private int nbLignes;
    private int nbColonnes;

    public CustomerListBookPane(CustomerScene customerScene, String titre, List<Livre> listeLivres) {
        this.customerScene = customerScene;

        this.titre = titre;
        this.listeLivres = listeLivres;

        this.curPage = 0;
        this.nbLignes = 2;
        this.nbColonnes = 4;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));

        this.getChildren().addAll(
            this.getTitleAndBackButton(),
            this.getListeLivres(),
            this.getNavigationsBoutons()
        );
    }

    private BorderPane getTitleAndBackButton() {
        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilClient(this.customerScene));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label(this.titre);
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        borderPaneTitre.setCenter(labelTitre);
        return borderPaneTitre;
    }

    private VBox getListeLivres() {
        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.curPage) + (intLigne * nbColonnes) + intColonne;
                if (index >= this.listeLivres.size()) break;

                Livre livre = this.listeLivres.get(index);

                BorderPane bookCard = this.customerScene.createOrGetCardComponent(livre);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

        return listeLivresVBox;
    }

    private HBox getNavigationsBoutons() {
        HBox hboxBoutons = new HBox();

        int maxPages = Math.ceilDiv(this.listeLivres.size(), this.nbColonnes * this.nbLignes);

        Button previousButton = new Button("Précédent");
        if (this.curPage == 0) previousButton.setDisable(true);
        previousButton.setOnAction(new ControleurNavigation(this));
        previousButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Label currentPageLabel = new Label(String.format("Page %d sur %d", this.curPage + 1, maxPages));
        currentPageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button nextButton = new Button("Suivant");
        if ((this.curPage + 1) >= maxPages) nextButton.setDisable(true);
        nextButton.setOnAction(new ControleurNavigation(this));
        nextButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        hboxBoutons.setSpacing(10);
        hboxBoutons.setAlignment(Pos.CENTER);
        hboxBoutons.getChildren().addAll(previousButton, currentPageLabel, nextButton);

        return hboxBoutons;
    }
    
    public void miseAJourAffichage() {
        this.getChildren().set(1, this.getListeLivres());
        this.getChildren().set(2, this.getNavigationsBoutons());
    }

    /**
     * Obtenir la page actuelle.
     * @return La page actuelle.
     */
    public int getCurPage() {
        return this.curPage;
    }

    /**
     * Définir la page actuelle.
     * @param page La nouvelle page.
     */
    public void setCurPage(int page) {
        this.curPage = page;
    }
}
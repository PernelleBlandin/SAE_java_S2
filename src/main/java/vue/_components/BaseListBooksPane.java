package vue._components;

import java.util.List;

import controleurs.ControleurNavigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;

public abstract class BaseListBooksPane extends VBox {
    private String titre;
    private List<Livre> listeLivres;

    private int curPage;
    private int nbLignes;
    private int nbColonnes;

    public BaseListBooksPane(String titre, List<Livre> listeLivres) {
        this.titre = titre;
        this.listeLivres = listeLivres;

        this.curPage = 0;
        this.nbLignes = 2;
        this.nbColonnes = 4;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));
    }

    public void addComponents() {
        this.getChildren().addAll(
            this.getTitleAndBackButtonPane(),
            this.getListeLivresPane(),
            this.getNavigationsBoutonsPane()
        );
    }

    public String getTitre() {
        return this.titre;
    }

    public List<Livre> getListeLivres() {
        return this.listeLivres;
    }

    protected abstract BorderPane getTitleAndBackButtonPane();
    
    protected abstract VBox getListeLivresPane();

    private HBox getNavigationsBoutonsPane() {
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
    
    /**
     * Mettre à jour l'affichage de la liste.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(1, this.getListeLivresPane());
        this.getChildren().set(2, this.getNavigationsBoutonsPane());
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

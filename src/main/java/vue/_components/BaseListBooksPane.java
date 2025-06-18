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

/** Base de la liste de livres à afficher */
public abstract class BaseListBooksPane extends VBox {
    /** Le titre de la liste à afficher à l'utilisateur */
    private String titre;
    /** La liste des livres */
    private List<Livre> listeLivres;

    /** La page actuelle */
    private int curPage;
    /** Le nombre de lignes */
    private int nbLignes;
    /** Le nombre de colonnes */
    private int nbColonnes;

    /**
     * Initialiser la base de la liste des livres à afficher.
     * @param titre Le titre de la liste. 
     * @param listeLivres La liste de livres à afficher.
     */
    public BaseListBooksPane(String titre, List<Livre> listeLivres) {
        this.titre = titre;
        this.listeLivres = listeLivres;

        this.curPage = 0;
        this.nbLignes = 2;
        this.nbColonnes = 4;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));
    }

    /**
     * Ajouter les composants à la pane.
     */
    public void addComponents() {
        this.getChildren().addAll(
            this.getTitleAndBackButtonPane(),
            this.listeLivres.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()
        );
    }

    /**
     * Obtenir le titre de la liste.
     * @return Le titre de la liste.
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Obtenir la liste des livres.
     * @return La liste des livres donnés..
     */
    public List<Livre> getListeLivres() {
        return this.listeLivres;
    }

    /**
     * Obtenir la pane avec le titre de la liste et le bouton "Retour".
     * @return La pane avec le titre de la liste et le bouton "Retour".
     */
    protected abstract BorderPane getTitleAndBackButtonPane();
    
    /**
     * Obtenir la pane avec la liste des cartes de livres à afficher.
     * @return La pane avec la liste des cartes de livres à afficher.
     */
    protected abstract VBox getListeLivresPane();

    /**
     * Obtenir une pane si aucun résultat est présent dans la liste.
     * @return Pane affiché si aucun résultat.
     */
    private VBox aucunResultatBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Aucun résultat.");
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 48));

        vbox.getChildren().add(label);
        return vbox;
    }

    /**
     * Obtenir la pane avec les boutons de navigations.
     * @return La pane avec les boutons de navigations.
     */
    private HBox getNavigationsBoutonsPane() {
        HBox hboxBoutons = new HBox();

        int maxPages = Math.ceilDiv(this.listeLivres.size(), this.nbColonnes * this.nbLignes);

        Button previousButton = new Button("Précédent");
        if (this.curPage == 0) previousButton.setDisable(true);
        previousButton.setOnAction(new ControleurNavigation(this));
        previousButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        int curPageAffichage = this.curPage;
        if (maxPages > 0) curPageAffichage++;

        Label currentPageLabel = new Label(String.format("Page %d sur %d", curPageAffichage, maxPages));
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
        this.getChildren().set(1, this.listeLivres.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox());
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

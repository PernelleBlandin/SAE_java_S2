package vue._components;

import java.util.List;

import controleurs.ControleurNavigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Base de la liste de livres à afficher */
public abstract class BaseListElementsPane<T> extends VBox {
    /** Le titre de la liste à afficher à l'utilisateur */
    private String titre;
    /** La liste des livres */
    protected List<T> listeElements;

    /** La page actuelle */
    private int curPage;
    /** Le nombre de lignes */
    protected int nbLignes;
    /** Le nombre de colonnes */
    protected int nbColonnes;

    /**
     * Initialiser la base de la liste des élements à afficher.
     * @param titre Le titre de la liste. 
     * @param listeElements La liste de élements à afficher.
     * @param nbLignes Le nombre de lignes
     * @param nbColonnes Le nombre de colonnes
     */
    public BaseListElementsPane(String titre, List<T> listeElements, int nbLignes, int nbColonnes) {
        this.titre = titre;
        this.listeElements = listeElements;

        this.curPage = 0;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));
    }

    /**
     * Ajouter les composants à la pane.
     */
    public void addComponents() {
        this.getChildren().addAll(
            this.getHeaderPane(),
            this.listeElements.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
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
     * Obtenir la liste des éléments.
     * @return La liste des éléments donnés.
     */
    public List<T> getListeElements() {
        return this.listeElements;
    }

    /**
     * Obtenir le nombre d'éléments par page.
     * @return Le nombre d'éléments par page.
     */
    public int getElementsParPage() {
        return this.nbColonnes * this.nbLignes;
    }

    /**
     * Obtenir le composant de livre à afficher.
     * @param element L'élement à afficher.
     * @return La pane avec le livre à afficher.
     */
    public abstract Pane getBookComponent(T element);

    /**
     * Obtenir le header de la pane.
     * @return Le header de la pane.
     */
    public abstract BorderPane getHeaderPane();
    
    /**
     * Obtenir la VBox contenant la liste des "cartes" des livres.
     * @return La VBox contenant la liste des "cartes" des livres.
     */
    public VBox getListeLivresPane() {
        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbElementsParPage = this.getElementsParPage();
        List<T> listeElements = this.getListeElements();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.getCurPage()) + (intLigne * nbColonnes) + intColonne;
                if (index >= listeElements.size()) break;

                T livre = listeElements.get(index);

                Pane card = this.getBookComponent(livre);
                HBox.setHgrow(card, Priority.ALWAYS);
                hboxLigne.getChildren().add(card);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

        return listeLivresVBox;
    }

    /**
     * Obtenir une pane si aucun résultat est présent dans la liste.
     * @return Pane affiché si aucun résultat.
     */
    public VBox aucunResultatBox() {
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
    public HBox getNavigationsBoutonsPane() {
        HBox hboxBoutons = new HBox();

        int maxPages = Math.ceilDiv(this.listeElements.size(), this.nbColonnes * this.nbLignes);

        Button previousButton = new Button("Précédent");
        if (this.curPage == 0) previousButton.setDisable(true);
        previousButton.setOnAction(new ControleurNavigation<T>(this));
        previousButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        int curPageAffichage = this.curPage;
        if (maxPages > 0) curPageAffichage++;

        Label currentPageLabel = new Label(String.format("Page %d sur %d", curPageAffichage, maxPages));
        currentPageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button nextButton = new Button("Suivant");
        if ((this.curPage + 1) >= maxPages) nextButton.setDisable(true);
        nextButton.setOnAction(new ControleurNavigation<T>(this));
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
        this.getChildren().set(1, this.listeElements.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox());
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

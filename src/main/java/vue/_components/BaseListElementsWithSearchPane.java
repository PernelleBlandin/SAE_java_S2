package vue._components;

import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurRechercheList;
import modeles.RecherchableInterface;

/**
 * La classe de base pour les listes d'éléments avec une barre de recherche.
 * @param <T> Le type des éléments de la liste, qui doit implémenter l'interface RecherchableInterface.
 */
public abstract class BaseListElementsWithSearchPane<T extends RecherchableInterface> extends BaseListElementsPane<T> {
    /** La barre de recherche */
    public SearchBar searchBar;

    /**
     * Initialiser la liste d'éléments avec une barre de recherche.
     * @param titre Le titre de la liste.
     * @param listeElements La liste des éléments à afficher.
     * @param nbLignes Le nombre de lignes à afficher.
     * @param nbColonnes Le nombre de colonnes à afficher.
     * @param searchBarPlaceholder Le texte affiché sur la barre de recherche.
     */
    public BaseListElementsWithSearchPane(String titre, List<T> listeElements, int nbLignes, int nbColonnes,
            String searchBarPlaceholder) {
        super(titre, listeElements, nbLignes, nbColonnes);

        this.searchBar = this.getSearchBarPane(searchBarPlaceholder);
        this.searchBar.setOnKeyTyped(new ControleurRechercheList<>(this));
    }

    /**
     * Ajouter les composants à la pane.
     */
    @Override
    public void addComponents() {
        this.getChildren().addAll(
            this.getHeaderPane(),
            this.searchBar,
            this.getListeElements().size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()        
        );
    }

    /**
     * Mettre à jour l'affichage de la liste.
     */
    @Override
    public void miseAJourAffichage() {
        this.getChildren().set(2, this.getListeElements().size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox());
        this.getChildren().set(3, this.getNavigationsBoutonsPane());
    }

    /**
     * Retourne la barre de recherche.
     * @param placeholder Le texte d'espace réservé pour la barre de recherche.
     * @return La barre de recherche.
     */
    private SearchBar getSearchBarPane(String placeholder) {
        SearchBar searchBar = new SearchBar(placeholder);
        return searchBar;
    }

    @Override
    /**
     * Retourne la liste des éléments filtrée par la recherche.
     * @return La liste des éléments filtrée par la recherche.
     */
    public List<T> getListeElements() {
        if (this.searchBar.getText().isEmpty()) {
            return this.listeElements;
        }

        List<T> listeElementsTries = new ArrayList<>();
        for (T element : this.listeElements) {
            if (element.estIncluDansRecherche(this.searchBar.getText())) {
                listeElementsTries.add(element);
            }
        }
        return listeElementsTries;
    }
}
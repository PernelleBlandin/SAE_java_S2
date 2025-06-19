package vue._components;

import java.util.ArrayList;
import java.util.List;

import modeles.RecherchableInterface;

public abstract class BaseListElementsWithSearchPane<T extends RecherchableInterface> extends BaseListElementsPane<T> {
    public SearchBar searchBar;

    public BaseListElementsWithSearchPane(String titre, List<T> listeElements, int nbLignes, int nbColonnes,
            String searchBarPlaceholder) {
        super(titre, listeElements, nbLignes, nbColonnes);

        this.searchBar = this.getSearchBarPane(searchBarPlaceholder);
    }

    /**
     * Ajouter les composants à la pane.
     */
    @Override
    public void addComponents() {
        this.getChildren().addAll(
            this.getHeaderPane(),
            this.searchBar,
            this.listeElements.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()        
        );
    }

    private SearchBar getSearchBarPane(String placeholder) {
        SearchBar searchBar = new SearchBar(placeholder);
        return searchBar;
    }

    @Override
    public List<T> getListeElements() {
        List<T> listeElementsTries = new ArrayList<>();
        for (T element : this.listeElements) {
            if (element.estIncluDansRecherche(searchBar.getText())) {
                listeElementsTries.add(element);
            }
        }
        return this.listeElements;
    }
}
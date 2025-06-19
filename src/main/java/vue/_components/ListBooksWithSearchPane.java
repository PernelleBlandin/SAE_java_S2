package vue._components;

import java.util.ArrayList;
import java.util.List;

import modeles.Livre;

public abstract class ListBooksWithSearchPane extends BaseListBooksPane {
    public SearchBar searchBar;

    public ListBooksWithSearchPane(String titre, List<Livre> listeLivres, int nbLignes, int nbColonnes,
            String searchBarPlaceholder) {
        super(titre, listeLivres, nbLignes, nbColonnes);

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
            this.listeLivres.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()        
        );
    }

    private SearchBar getSearchBarPane(String placeholder) {
        SearchBar searchBar = new SearchBar(placeholder);
        return searchBar;
    }

    @Override
    public List<Livre> getListeLivres() {
        List<Livre> listeLivresTries = new ArrayList<>();
        for (Livre livre : this.listeLivres) {
            if (livre.estIncluDansRecherche(searchBar.getText())) {
                listeLivresTries.add(livre);
            }
        }
        return this.listeLivres;
    }
}
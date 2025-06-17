package modeles;
import java.util.Comparator;
import java.util.HashMap;

/** Comparer deux livres par rapport à leur nombre de "recommendations" */
public class ComparatorLivreRecommandation implements Comparator<Livre> {
    private HashMap<Livre, Integer> recommendationsLivres;
    
    /**
     * Initialiser le comparateur.
     * @param recommendationsLivres Un dictionnaire avec comme clé un livre et comme valeur son nombre de recommandations.
     */
    public ComparatorLivreRecommandation(HashMap<Livre, Integer> recommendationsLivres) {
        this.recommendationsLivres = recommendationsLivres;
    }

    /**
     * Comparer deux livres.
     * @param livre1 Le premier livre.
     * @param livre2 Le deuxième livre.
     * @return La comparaison entre les deux livres.
     */
    @Override
    public int compare(Livre livre1, Livre livre2) {
        Integer recommendationLivre1 = this.recommendationsLivres.get(livre1);
        if (recommendationLivre1 == null) recommendationLivre1 = 0;

        Integer recommendationLivre2 = this.recommendationsLivres.get(livre2);
        if (recommendationLivre2 == null) recommendationLivre2 = 0;

        return recommendationLivre2.compareTo(recommendationLivre1);
    }
}

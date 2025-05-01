import java.util.Comparator;
import java.util.HashMap;

/** Comparer deux livres par rapport à leur nombre de "recommendations" */
public class ComparatorLivreRecommandation implements Comparator<Livre> {
    private HashMap<Livre, Integer> recommandationClient;
    public ComparatorLivreRecommandation(HashMap<Livre, Integer> recommendationClient) {
        this.recommandationClient = recommendationClient;
    }

    @Override
    public int compare(Livre livre1, Livre livre2) {
        Integer recommendationLivre1 = this.recommandationClient.get(livre1);
        if (recommendationLivre1 == null) recommendationLivre1 = 0;

        Integer recommendationLivre2 = this.recommandationClient.get(livre2);
        if (recommendationLivre2 == null) recommendationLivre2 = 0;

        return recommendationLivre1.compareTo(recommendationLivre2);
    }
}

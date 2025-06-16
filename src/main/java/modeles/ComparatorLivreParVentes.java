package modeles;
import java.util.Comparator;

/** Comparer deux livre par rapport à leurs nombre de ventes */
public class ComparatorLivreParVentes implements Comparator<Livre> {
    private ChaineLibrairie chaineLibrairie;

    /**
     * Initialiser le comparateur de livres par leurs nombres de ventes.
     * @param chaineLibrairie La chaîne de librairie.
     */
    public ComparatorLivreParVentes(ChaineLibrairie chaineLibrairie) {
        this.chaineLibrairie = chaineLibrairie;
    }

    @Override
    /**
     * Comparer deux livres selon leur nombre de ventes.
     * @param livre1 Le premier livre.
     * @param livre2 Le second livre.
     * @return La comparaison entre les deux livres.
     */
    public int compare(Livre livre1, Livre livre2) {
        return chaineLibrairie.getNombreVentesLivre(livre2) - chaineLibrairie.getNombreVentesLivre(livre1);
    }
}

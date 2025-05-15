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
    public int compare(Livre livre1, Livre livre2) {
        return chaineLibrairie.getNombreVentesLivre(livre2) - chaineLibrairie.getNombreVentesLivre(livre1);
    }
}

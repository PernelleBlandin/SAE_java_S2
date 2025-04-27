import java.util.Comparator;

public class ComparatorLivreParVentes implements Comparator<Livre> {
    private ChaineLibrairie chaineLibrairie;
    public ComparatorLivreParVentes(ChaineLibrairie chaineLibrairie) {
        this.chaineLibrairie = chaineLibrairie;
    }

    @Override
    public int compare(Livre livre1, Livre livre2) {
        return chaineLibrairie.getNombreVentesLivre(livre2) - chaineLibrairie.getNombreVentesLivre(livre1);
    }
}

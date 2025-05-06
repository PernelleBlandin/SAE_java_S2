import java.util.Comparator;

/** Compararer deux commandes par rapport à leur date, de façon décroissante. */
public class ComparatorCommandeDateDesc implements Comparator<Commande> {

    @Override
    public int compare(Commande commande1, Commande commande2) {
        return commande2.getDate().compareTo(commande1.getDate());
    }
}

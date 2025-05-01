import java.util.Comparator;

/** Compararer deux commandes par rapport à leur date, de façon décroissante. */
public class ComparatorCommandeDateDesc implements Comparator<Commande> {

    @Override
    public int compare(Commande commande1, Commande commande2) {
        return commande1.getDate().compareTo(commande2.getDate());
    }
}

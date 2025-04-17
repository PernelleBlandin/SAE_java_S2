import java.time.LocalDate;

public class Commande {
    private int id;
    private LocalDate date;
    private char enLigne;
    private char livraison;

    public Commande(int id, LocalDate date, char enLigne, char livraison){
        this.id = id;
        this.date = date;
        this.enLigne = enLigne;
        this.livraison = livraison;
    }

    /**
     * Obtenir l'identifiant de la commande.
     * @return L'identifiant de la commande.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtenir la date de la commande.
     * @return La date de la commande.
     */
    public LocalDate getDate() {
        return this.date;
    }
}

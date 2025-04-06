import java.util.Date;

public class Commande {
    private int id;
    private Date date;
    private char enLigne;
    private char livraison;

    public Commande(int num, Date date, char enLigne, char livraison){
        this.id = num;
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
    public Date getDate() {
        return this.date;
    }
}

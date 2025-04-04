import java.util.Date;

public class Commande {
    private int num;
    private Date date;
    private char enLigne;
    private char livraison;

    public Commande(int num, Date date, char enLigne, char livraison){
        this.num = num;
        this.date = date;
        this.enLigne = enLigne;
        this.livraison = livraison;
    }
}

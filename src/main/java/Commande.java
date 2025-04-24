import java.sql.Date;
import java.util.List;

public class Commande {
    private int id;
    private Date date;
    private char enLigne;
    private char livraison;
    private String status;
    private Magasin magasin;
    private List<DetailCommande> detailCommandes;

    public Commande(int id, Date date, char enLigne, char livraison, String status, Magasin magasin, List<DetailCommande> detailCommandes) {
        this.id = id;
        this.date = date;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.status = status;
        this.magasin = magasin;
        this.detailCommandes = detailCommandes;
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

    /**
     * Obtenir le magasin lié à la commande.
     * @return Le magasin lié à la commande.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }
}

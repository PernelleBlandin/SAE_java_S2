import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/** Une commande */
public class Commande {
    private int id;
    private Date date;
    private char enLigne;
    private char livraison;
    private Magasin magasin;
    private List<DetailCommande> detailCommandes;

    /**
     * Créer une commande.
     * @param id Son identifiant.
     * @param date Sa date.
     * @param enLigne Si elle est en ligne. (O/N)
     * @param livraison Son type de livraison (C: Chez le client / M: En magasin)
     * @param magasin Le magasin de la commande.
     * @param detailCommandes Les détails de la commande (livres, quantités).
     */
    public Commande(int id, Date date, char enLigne, char livraison, Magasin magasin, List<DetailCommande> detailCommandes) {
        this.id = id;
        this.date = date;
        this.enLigne = enLigne;
        this.livraison = livraison;
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
     * Savoir si une commande a été passée en ligne.
     * @return true si elle a été passée en ligne, sinon false.
     */
    public boolean estEnLigne() {
        return this.enLigne == 'O';
    }

    /**
     * Savoir si une commande est en livraison.
     * @return true si elle est en livraison, sinon false.
     */
    public boolean estEnLivraison() {
        return this.livraison == 'C';
    }

    /**
     * Obtenir le magasin lié à la commande.
     * @return Le magasin lié à la commande.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Obtenir les détails d'une commande.
     * @return Les détails d'une commande (livre, quantité, ...).
     */
    public List<DetailCommande> getDetailCommandes() {
        return this.detailCommandes;
    }

    /**
     * Obtenir le total d'une commande.
     * @return Le total d'une commande, sous la forme d'un double.
     */
    public double getTotalCommande() {
        double totalCommande = 0.0;
        List<DetailCommande> detailCommandes = this.getDetailCommandes();
        for (DetailCommande detailCommande: detailCommandes) {
            totalCommande += detailCommande.getPrixVente() * detailCommande.getQuantite();
        }

        return totalCommande;
    }

    /**
     * Obtenir la commande sous la forme d'une chaîne de caractères.
     * @return La commande sous la forme d'une chaîne de caractères.
     */
    @Override
    public String toString() {
        Date date = this.getDate();

        // Aide : https://stackoverflow.com/questions/24320378/how-do-i-format-a-java-sql-date-into-this-format-mm-dd-yyyy
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateDisplay = dateFormat.format(date);

        List<DetailCommande> detailCommandes = this.getDetailCommandes();
        return String.format("Commande #%s du %s - %.2f€ - %s article(s) - %s", this.id, dateDisplay, this.getTotalCommande(), detailCommandes.size(), this.getMagasin().toString());
    }
}

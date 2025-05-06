/** Les détails d'une commande */
public class DetailCommande {
    private Livre livre;
    private int numLigne;
    private int quantite;
    private double prixVente;
    
    /**
     * Créer le détail de la commande d'un livre.
     * @param livre Le livre
     * @param numLigne Le numéro de ligne (pour la facture/affichage).
     * @param quantite La quantité du livre.
     * @param prixVente Le prix de vente du livre.
     */
    public DetailCommande(Livre livre, int numLigne, int quantite, double prixVente) {
        this.livre = livre;
        this.numLigne = numLigne;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    /**
     * Obtenir le livre du détail de la commande. 
     * @return Le livre.
     */
    public Livre getLivre() {
        return this.livre;
    }

    /**
     * Obtenir le numéro de la ligne du détail de la commande. 
     * @return Le numéro de la ligne.
     */
    public int getNumLigne() {
        return this.numLigne;
    }

    /**
     * Définir le numéro de la ligne du détail de la commande. 
     * @param numLigne Le nouveau numéro de la ligne.
     */
    public void setNumLigne(int numLigne) {
        this.numLigne = numLigne;
    }

    /**
     * Obtenir la quantité du livre du livre commandé. 
     * @return La quantité du livre.
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Obtenir le prix de vente du livre commandé.
     * @return Le prix de vente du livre.
     */
    public double getPrixVente() {
        return this.prixVente;
    }

    /**
     * Ajouter une quantité du livre dans la commande.
     */
    public void ajouterQuantite() {
        this.quantite++;
    }

    /**
     * Définir la quantité d'un livre.
     * @param quantite La nouvelle quantité d'un livre.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

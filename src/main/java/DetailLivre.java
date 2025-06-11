/** Les détails d'un livre, dans le cadre d'une commande ou d'un panier client */
public class DetailLivre {
    private Livre livre;
    private int numLigne;
    private int quantite;
    private double prixVente;
    
    /**
     * Créer le détail d'un livre dans le cadre d'une commande ou d'un panier client.
     * @param livre Le livre
     * @param numLigne Le numéro de ligne (pour la facture/affichage).
     * @param quantite La quantité du livre.
     * @param prixVente Le prix de vente du livre.
     */
    public DetailLivre(Livre livre, int numLigne, int quantite, double prixVente) {
        this.livre = livre;
        this.numLigne = numLigne;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    /**
     * Obtenir le livre concerné. 
     * @return Le livre.
     */
    public Livre getLivre() {
        return this.livre;
    }

    /**
     * Obtenir le numéro de la ligne concernée. 
     * @return Le numéro de la ligne.
     */
    public int getNumLigne() {
        return this.numLigne;
    }

    /**
     * Définir le numéro de la ligne concernée. 
     * @param numLigne Le nouveau numéro de la ligne.
     */
    public void setNumLigne(int numLigne) {
        this.numLigne = numLigne;
    }

    /**
     * Obtenir la quantité du livre concernée. 
     * @return La quantité du livre.
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Obtenir le prix de vente concerné.
     * @return Le prix de vente du livre.
     */
    public double getPrixVente() {
        return this.prixVente;
    }

    /**
     * Ajouter une quantité du livre dans le détail.
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

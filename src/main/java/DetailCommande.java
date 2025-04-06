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
     * @param prixvente Le prix de vente du livre.
     */
    public DetailCommande(Livre livre, int numLigne, int quantite, double prixvente) {
        this.livre = livre;
        this.numLigne = numLigne;
        this.quantite = quantite;
        this.prixVente = prixvente;
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
}

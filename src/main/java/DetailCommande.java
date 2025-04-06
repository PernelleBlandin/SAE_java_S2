public class DetailCommande {
    private Livre livre;
    private int numLigne;
    private int quantite;
    private double prixVente;
    
    public DetailCommande(Livre livre, int numlig, int qte, double prixvente) {
        this.livre = livre;
        this.numLigne = numlig;
        this.quantite = qte;
        this.prixVente = prixvente;
    }

    public Livre getLivre() {
        return this.livre;
    }

    public int getNumLigne() {
        return this.numLigne;
    }

    public int getQuantite() {
        return this.quantite;
    }

    public double getPrixVente() {
        return this.prixVente;
    }

    public void ajouterQuantite() {
        this.quantite++;
    }
}

public class DetailCommande {
    private Commande commande;
    private Livre livre;
    private int numlig;
    private int qte;
    private double prixvente;
    
    public DetailCommande(Commande commande, Livre livre, int numlig, int qte, double prixvente) {
        this.commande = commande;
        this.livre = livre;
        this.numlig = numlig;
        this.qte = qte;
        this.prixvente = prixvente;
    }
}

public class Posseder {
    private Livre livre;
    private int quantite;

    public Posseder(Livre livre, int quantite) {
        this.livre = livre;
        this.quantite = quantite;
    }

    /**
     * Obtenir le livre possédé.
     * @return Le livre possédé.
     */
    public Livre getLivre() {
        return this.livre;
    }

    /**
     * Obtenir la quantité du livre possédé.
     * @return La quantité du livre possédé.
     */
    public int getQuantite() {
        return this.quantite;
    }
}

public class ResultatSelectionLivre {
    private int nbPage;
    private Livre livre;
    public ResultatSelectionLivre() {
        this.nbPage = 0;
    }

    public ResultatSelectionLivre(int nbPage, Livre livre) {
        this.nbPage = nbPage;
        this.livre = livre;
    }

    public Livre getLivre() {
        return this.livre;
    }

    public int getNbPage() {
        return this.nbPage;
    }
}

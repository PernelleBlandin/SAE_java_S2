public class ResultatSelectionMagasin {
    private int nbPage;
    private Magasin magasin;
    public ResultatSelectionMagasin() {
        this.nbPage = 0;
    }

    public ResultatSelectionMagasin(int nbPage, Magasin magasin) {
        this.nbPage = nbPage;
        this.magasin = magasin;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public int getNbPage() {
        return this.nbPage;
    }
}

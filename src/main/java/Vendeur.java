/** Un vendeur */
public class Vendeur extends Personne {
    private Magasin magasin;
    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param adresse L'adresse du vendeur.
     * @param codePostal Le code postal du vendeur.
     * @param ville La ville du vendeur.
     * @param magasin Le magasin rattaché au vendeur
     */
    public Vendeur(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin) {
        super(id, nom, prenom, adresse, codePostal, ville);
        this.magasin = magasin;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

        /**
     * Définir le magasin pour un vendeur.
     * @param magasin Le nouveau magasin du client.
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public void ajouteLivreChaineLib(){}

    public void majStock(){}

    public void accesStock(){}

    public void passerCommande(){}

    public void transfertLivre(){}
}

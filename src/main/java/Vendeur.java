/** Un vendeur */
public class Vendeur extends Personnel {
    private Magasin magasin;

    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     */
    public Vendeur(int id, String nom, String prenom, Magasin magasin) {
        super(id, nom, prenom, "Vendeur");
        this.magasin = magasin;
    }

    /**
     * Obtenir le magasin du vendeur.
     * @return Le magasin du vendeur.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }
}

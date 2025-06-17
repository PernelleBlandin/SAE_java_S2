package modeles;
/** Un vendeur */
public class Vendeur extends Personne {
    private Magasin magasin;
    private ChaineLibrairie chaineLibrairie;

    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param magasin Le magasin rattaché au vendeur
     * @param chaineLibrairie La chaîne de librairie.
     */
    public Vendeur(int id, String nom, String prenom, Magasin magasin, ChaineLibrairie chaineLibrairie) {
        super(id, nom, prenom);
        this.magasin = magasin;
        this.chaineLibrairie = chaineLibrairie;
    }

    /**
     * Obtenir le magasin du vendeur.
     * @return Le magasin du vendeur.
     */
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

    public ChaineLibrairie getChaineLibrairie(){
        return this.chaineLibrairie;
    }
}

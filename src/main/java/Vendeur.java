/** Un vendeur */
public class Vendeur extends Personnel {
    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     */
    public Vendeur(int id, String nom, String prenom) {
        super(id, nom, prenom, "Vendeur");
    }
}
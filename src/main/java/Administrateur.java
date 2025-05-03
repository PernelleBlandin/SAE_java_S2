/** Un administrateur */
public class Administrateur extends Personnel {
    /**
     * Créer un administrateur.
     * @param id L'identifiant de l'administrateur.
     * @param nom Le nom du prénom de l'administrateur.
     * @param prenom Le prénom de l'administrateur.
     */
    public Administrateur(int id, String nom, String prenom) {
        super(id, nom, prenom, "Administrateur");
    }
}

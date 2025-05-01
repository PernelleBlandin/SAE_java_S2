/** Un membre du personnel de la chaîne de librairie */
public abstract class Personnel extends Personne {
    private String role;

    /**
     * Créer un membre du personnel.
     * @param id L'identifiant du personnel.
     * @param nom Le nom du prénom du personnel.
     * @param prenom Le prénom du personnel.
     * @param role Le rôle du personnel (administrateur/vendeur).
     */
    public Personnel(int id, String nom, String prenom, String role) {
        super(id, nom, prenom);
        this.role = role;
    }

    /**
     * Obtenir le rôle (administrateur/vendeur) d'un personnel.
     * @return Son rôle.
     */
    public String getRole() {
        return this.role;
    }
}

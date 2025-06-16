package modeles;
/** Une personne */
public abstract class Personne {
    private int id;
    private String nom;
    private String prenom;

    /**
     * Créer une personne.
     * @param id L'identifiant de la personne.
     * @param nom Le nom du prénom de la personne.
     * @param prenom Le prénom de la personne.
     */
    public Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Obtenir l'identifiant de la personne.
     * @return Son identifiant.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtenir le nom de la personne.
     * @return Son nom.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obtenir le prénom de la personne.
     * @return Son prénom.
     */
    public String getPrenom() {
        return this.prenom;
    }
    
    /**
     * Obtenir le prénom et le nom de la personne.
     * @return Le prénom et le nom de la personne.
     */
    @Override
    public String toString() {
        return this.getPrenom() + " " + this.getNom();
    }
}
/** Créer un identifiable (classe abstraite) */
public abstract class Identifiable {
    private String id;
    private String nom;

    /**
     * Créer un identifiable (id, nom).
     * @param id L'identifiant de l'identifiable.
     * @param nom Le nom de l'identifiable.
     */
    public Identifiable(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * Obtenir l'identifiant de l'identifiable.
     * @return L'identifiant de l'identifiable.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Obtenir le nom de l'identifiable.
     * @return Le nom de l'identifiable.
     */
    public String getNom() {
        return this.nom;
    }
}
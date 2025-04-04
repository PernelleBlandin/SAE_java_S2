public abstract class Identifiable {
    private String id;
    private String nom;

    public Identifiable(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
}
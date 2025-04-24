public abstract class Personnel extends Personne {
    private String role;
    public Personnel(int id, String nom, String prenom, String adresse, String codePostal, String ville, String role) {
        super(id, nom, prenom, adresse, codePostal, ville);
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}

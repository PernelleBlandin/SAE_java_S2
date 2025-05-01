/** Une personne */
public abstract class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;

    /**
     * Créer une personne.
     * @param id L'identifiant de la personne.
     * @param nom Le nom du prénom de la personne.
     * @param prenom Le prénom de la personne.
     * @param adresse L'adresse de la personne.
     * @param codePostal Le code postal de la personne.
     * @param ville La ville de la personne.
     */
    public Personne(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
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
     * Obtenir l'adresse de la personne.
     * @return Son adresse.
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Obtenir le code postal de la personne.
     * @return Son code postal.
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     * Obtenir la ville de la personne.
     * @return Sa ville.
     */
    public String getVille() {
        return this.ville;
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
package modeles;
/** Une personne */
public abstract class Personne implements RecherchableInterface {
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
     * Indique si une personne est inclu dans une recherche donnée.
     * @param recherche Une recherche utilisateur.
     * @return true si le personne est inclu dans la recherche donnée, sinon false.
     */
    public boolean estIncluDansRecherche(String recherche) {
        String nom = this.getNom().toLowerCase();
        String prenom = this.getPrenom().toLowerCase();

        String[] mots = recherche.toLowerCase().split(" ");
        for (String mot: mots) {
            if (!(nom.contains(mot) || prenom.contains(mot))) {
                return false;
            }
        }
        return true;
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
package modeles;
/** Un magasin de la chaîne de librairie */
public class Magasin implements RecherchableInterface {
    private String id;
    private String nom;
    private String ville; 

    /**
     * Créer un magasin.
     * @param id L'identifiant du magasin.
     * @param nom Le nom du magasin.
     * @param ville La ville du magasin.
     */
    public Magasin(String id, String nom, String ville) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
    }

    /**
     * Obtenir l'identifiant du magasin.
     * @return L'identifiant du magasin.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Obtenir le nom du magasin.
     * @return Le nom du magasin.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obtenir la ville du magasin.
     * @return La ville du magasin.
     */
    public String getVille() {
        return this.ville;
    }

    /**
     * Indique si un magasin est inclu dans une recherche donnée.
     * @param recherche Une recherche utilisateur.
     * @return true si le magasin est inclu dans la recherche donnée, sinon false.
     */
    public boolean estIncluDansRecherche(String recherche) {
        String nomMagasin = this.getNom().toLowerCase();
        String villeMagasin = this.getVille().toLowerCase();

        String[] mots = recherche.toLowerCase().split(" ");
        for (String mot: mots) {
            if (!(nomMagasin.contains(mot) || villeMagasin.contains(mot))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtenir le magasin en chaîne de caractères.
     * @return Le magasin en chaîne de caractères.
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", this.getNom(), this.getVille());
    }
}

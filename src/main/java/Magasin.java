import java.util.List;

/** Un magasin de la chaîne de librairie */
public class Magasin extends Identifiable {
    private String ville;
    private List<Posseder> possessions; 

    /**
     * Créer un magasin.
     * @param id L'identifiant du magasin.
     * @param nom Le nom du magasin.
     * @param ville La ville du magasin.
     * @param possessions Les "possessions" des livres du magasin.
     */
    public Magasin(String id, String nom, String ville, List<Posseder> possessions) {
        super(id, nom);
        this.ville = ville;
        this.possessions = possessions;
    }
    

    /**
     * Obtenir la ville du magasin.
     * @return La ville du magasin.
     */
    public String getVille() {
        return this.ville;
    }
    
    /**
     * Obtenir la liste des livres possédés par le magasin.
     * @return La liste des livres possédés par le magasin.
     */
    public List<Posseder> getPossessions() {
        return this.possessions;
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

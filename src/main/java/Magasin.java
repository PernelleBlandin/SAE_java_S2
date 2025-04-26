import java.util.List;

public class Magasin extends Identifiable{
    private String ville;
    private List<Posseder> possessions; 

    /**
     * Créer un magasin.
     * @param id L'identifiant du magasin.
     * @param nom Le nom du magasin.
     * @param ville La ville du magasin.
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
}

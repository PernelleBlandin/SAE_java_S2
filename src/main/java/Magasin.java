public class Magasin extends Identifiable{
    private String ville;

    /**
     * Créer un magasin.
     * @param id L'identifiant du magasin.
     * @param nom Le nom du magasin.
     * @param ville La ville du magasin.
     */
    public Magasin(String id, String nom, String ville){
        super(id, nom);
        this.ville = ville;
    }

    /**
     * Obtenir la ville du magasin.
     * @return La ville du magasin.
     */
    public String getVille() {
        return this.ville;
    }
    
}

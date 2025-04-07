public class Magasin extends Identifiable{
    private String adresse;
    private String codePostal;
    private String ville;

    /**
     * Créer un magasin.
     * @param id L'identifiant du magasin.
     * @param nom Le nom du magasin.
     * @param adresse L'adresse du magasin.
     * @param codePostal Le code postal du magasin.
     * @param ville La ville du magasin.
     */
    public Magasin(String id, String nom, String adresse, String codePostal, String ville) {
        super(id, nom);
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    /**
     * Obtenir l'adresse du magasin.
     * @return L'adresse du magasin.
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Obtenir le code postal du magasin.
     * @return Le code postal du magasin.
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     * Obtenir la ville du magasin.
     * @return La ville du magasin.
     */
    public String getVille() {
        return this.ville;
    }
    
}

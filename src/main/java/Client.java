import java.util.List;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private List<Commande> commandes;
    private Panier panier;

    /**
     * Créer un client.
     * @param id L'identifiant du client.
     * @param nom Le nom du prénom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @param codePostal Le code postal du client.
     * @param ville La ville du client.
     */
    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville, List<Commande> commandes, Panier panier) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal= codePostal;
        this.ville = ville;
        this.commandes = commandes;
        this.panier = panier;
    }

    /**
     * Obtenir l'identifiant du client.
     * @return Son identifiant.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtenir le nom du client.
     * @return Son nom.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obtenir le prénom du client.
     * @return Son prénom.
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Obtenir l'adresse du client.
     * @return Son adresse.
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Obtenir le code postal du client.
     * @return Son code postal.
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     * Obtenir la ville du client.
     * @return Sa villes.
     */
    public String getVille() {
        return this.ville;
    }

    /**
     * Obtenir la liste des commandes du client.
     * @return La liste des commandes qu'il a effectuées.
     */
    public List<Commande> getCommandes() {
        return this.commandes;
    }

    /**
     * Obtenir le panier du client.
     * @return Le panier du client.
     */
    public Panier getPanier() {
        return this.panier;
    }

    // public void commander(Commande commande){
        
    // }

    
    /**
     * Obtenir le prénom et le nom du client.
     * @return Le prénom et le nom du client.
     */
    @Override
    public String toString() {
        return this.getPrenom() + " " + this.getNom();
    }
}
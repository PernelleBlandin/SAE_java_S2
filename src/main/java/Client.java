import java.util.List;

public class Client extends Personne {
    private Magasin magasin;
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
     * @param magasin Le magasin du client.
     * @param commandes La liste des commandes du client.
     * @param panier Le panier du client.
     */
    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin, List<Commande> commandes, Panier panier) {
        super(id, nom, prenom, adresse, codePostal, ville);
        this.magasin = magasin;
        this.commandes = commandes;
        this.panier = panier;
    }

    /**
     * Créer un client sans panier rempli.
     * @param id L'identifiant du client.
     * @param nom Le nom du prénom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @param codePostal Le code postal du client.
     * @param ville La ville du client.
     * @param magasin Le magasin du client.
     * @param commandes La liste des commandes du client.
     */
    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin, List<Commande> commandes) {
        this(id, nom, prenom, adresse, codePostal, ville, magasin, commandes, new Panier(magasin));
    }

    /**
     * Obtenir le magasin choisi par le client.
     * @return Le magasin du client pour sa prochaine commande.
     */
    public Magasin getMagasin() {
        return this.magasin;
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

    /**
     * Définir le panier pour un client.
     * @param panier Le panier du client.
     */
    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    /**
     * Commander un livre pour un client.
     * @param commande
     */
    public void commander(Commande commande){
        this.commandes.add(commande);
    }
}
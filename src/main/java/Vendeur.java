/** Un vendeur */
public class Vendeur extends Personnel {
    private Magasin magasin;
    private List<Commande> commandes;
    private Panier panier;
    private Magasin magasin; //le vendeur est associé à un magasin

    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param adresse L'adresse du vendeur.
     * @param codePostal Le code postal du vendeur.
     * @param ville La ville du vendeur.
     */
    public Vendeur(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        super(id, nom, prenom, adresse, codePostal, ville, "Vendeur");
    }

    public void passerCommande(Client client, List<DetailCommande> detailCommande, char modeLivraison){
        Commande commande= new Commande (1, Date.valueOf(LocalDate.now()), 'N', modeLivraison, this.magasin, List<DetailCommande> detailCommande )

    }

    public void transfererLivre(){

    }

    public void gererStock(){
        
    }
}

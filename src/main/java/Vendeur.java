import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; 

/** Un vendeur */
public class Vendeur extends Personnel {
    private Magasin magasin; //le vendeur est associé à un magasin
    private List<Commande> commandes; //a revoir
    private Panier panier; // a revoir 
    

    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param adresse L'adresse du vendeur.
     * @param codePostal Le code postal du vendeur.
     * @param ville La ville du vendeur.
     */
    public Vendeur(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin, Commande commande, Panier panier) {
        super(id, nom, prenom, adresse, codePostal, ville, "Vendeur");
        this.magasin= magasin;
        this.commandes= commande;// peut etre a enlever
        this.panier= panier; // peut etre a enlever 

    }



    public void passerCommande(Client client, List<DetailCommande> detailCommande, char modeLivraison){
        Commande commande= new Commande (1, Date.valueOf(LocalDate.now()), 'N', modeLivraison, this.magasin, detailCommande);
        
        List<Commande> commandeClient= client.getCommandes(); 
        commandesClient.add(commande);
        
    }

     /*public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public void ajouteLivreChaineLib(Livre livre){}

    public void majStock(){}

    public void accesStock(){}*/

    public void transfererLivre(Livre livre, Magasin magasinVendeur, Magasin magasinDestination, int qte){
        //fct verifDispo ->Emrecan
        //Mettre à jour la quantité disponible d'un livre
        //Vérifier la disponibilité d'un livre dans une librairie. 
        // a verifier prendre magasin a et enlever qte dans magasin b (faire attention qu'il
        // n'y ait pas de solde null et donc qu'il y ait assez en stock)

        


        List<Posseder> possessionMag= magasinVendeur.getPossessions();
        List<Posseder> possessionMagDest = magasinDestination.getPossessions();
        for(Posseder book : possessionMag){
            if(book.equals(livre)){
                int qteMag= book.getQuantite();
                this.magasin
            }
        }
        //TEST 1ERE VERSION
        //if possessionMag
        /*if (!possessionMagDest.contains(livre)){
            possessionMagDest.add(livre);
        }
        else{ }//ajouter qte a la qte actuel*/
        List<Posseder> possessionsMagasin =  magasinVendeur.getPossessions();
        if (possessionsMagasin.contains(livre))
        {    }
            {livreQte= possessionsMagasin.getQuantite();
            if (qte>){
        }}


    }

}

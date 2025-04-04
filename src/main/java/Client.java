import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private List<Commande> commandes;

    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville){
        this.id= id;
        this.nom= nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal= codePostal;
        this.ville = ville;
        this.commandes = new ArrayList<>();
    }

    public void commander(Commande commande){
        
    }

    // public List<Livre> onVousRecommande(){

    // }
}
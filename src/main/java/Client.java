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

    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal= codePostal;
        this.ville = ville;
        this.commandes = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public String getVille() {
        return this.ville;
    }

    public List<Commande> getCommandes() {
        return this.commandes;
    }

    // public void commander(Commande commande){
        
    // }
}
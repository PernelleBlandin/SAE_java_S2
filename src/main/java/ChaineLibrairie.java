import java.util.List;
import java.util.ArrayList;

public class ChaineLibrairie {
    private List<Livre> livres;
    private List<Client> clients;
    private List<Magasin> magasins;

    public ChaineLibrairie() {
        this.livres = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.magasins = new ArrayList<>();
    }

    public void ajouterLivre(Livre livre) {
        this.livres.add(livre);
    }

    public void ajouterClient(Client client) {
        this.clients.add(client);
    }

    public void ajouterMagasin(Magasin magasin) {
        this.magasins.add(magasin);
    }

    // public List<Livre> onVousRecommande(Client client){

    // }
}
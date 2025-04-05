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

    /**
     * Obtenir la liste des livres de la chaîne de librairie.
     * @return La liste des livres de la chaîne de librairie.
     */
    public List<Livre> getLivres() {
        return this.livres;
    }

    /**
     * Obtenir la liste des clients de la chaîne de librairie.
     * @return La liste des clients de la chaîne de librairie.
     */
    public List<Client> getClients() {
        return this.clients;
    }

    /**
     * Obtenir la liste des magasins de la chaîne de librairie.
     * @return La liste des magasins de la chaîne de librairie.
     */
    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    /**
     * Ajouter un livre à la chaîne de librairie.
     * @param livre Le livre à ajouter.
     */
    public void ajouterLivre(Livre livre) {
        this.livres.add(livre);
    }

    /**
     * Ajouter un client à la chaîne de librairie.
     * @param client Le client à ajouter.
     */
    public void ajouterClient(Client client) {
        this.clients.add(client);
    }

    /**
     * Ajouter un magasin à la chaîne de librairie.
     * @param magasin Le magasin à ajouter.
     */
    public void ajouterMagasin(Magasin magasin) {
        this.magasins.add(magasin);
    }

    /**
     * Trouver un client à partir de son nom et prénom.
     * @param nom Le nom du client.
     * @param prenom Le prénom du client.
     * @return Le client trouvé, ou null si aucun n'a été trouvé.s
     */
    public Client trouverClient(String nom, String prenom) {
        for (Client client: this.clients) {
            if (client.getNom().equals(nom) && client.getPrenom().equals(prenom)) {
                return client;
            }
        }
        return null;
    }

    // public List<Livre> onVousRecommande(Client client){

    // }
}
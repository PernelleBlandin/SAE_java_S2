import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChaineLibrairie {
    private List<Livre> livres;
    private List<Client> clients;
    private List<Personnel> personnels;
    private List<Magasin> magasins;

    public ChaineLibrairie() {
        this.livres = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.personnels = new ArrayList<>();
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
     * Obtenir la liste des personnels de la chaîne de librairie.
     * @return La liste des personnels de la chaîne de librairie.
     */
    public List<Personnel> getPersonnels() {
        return this.personnels;
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

    /**
     * Rechercher un livre selon une recherche données.
     * @param livres La liste des livres dans laquelle effectuer la recherche.
     * @param recherche La recherche utilisateur.
     * @return Les livres étant inclus dans la recherche.
     */
    public List<Livre> rechercherLivres(List<Livre> livres, String recherche) {
        List<Livre> livresCorrespondants = new ArrayList<>();
        for (Livre livre: livres) {
            if (livre.estIncluDansRecherche(recherche)) {
                livresCorrespondants.add(livre);
            }
        }
        return livresCorrespondants;
    }

    /**
     * Obtenir la liste de l'ensemble des commandes de la chaîne de librairie.
     * @return La liste des commandes de la chaîne de librairie, tous magasins et clients confondus.
     */
    public List<Commande> getCommandes() {
        List<Commande> commandes = new ArrayList<>();
        
        List<Client> clients = this.getClients();
        for (Client client: clients) {
            commandes.addAll(client.getCommandes());
        }
        return commandes;
    }

    /**
     * Obtenir le nombre de vente d'un livre dans toute la chaîne de librairie.
     * @param livre Le livre concerné.
     * @return Le nombre de vente du livre dans toute la chaîne de librairie.
     */
    public int getNombreVentesLivre(Livre livre) {
        int nbVentes = 0;
        List<Commande> commandes = this.getCommandes();
        for (Commande commande: commandes) {
            List<DetailCommande> detailCommandes = commande.getDetailCommandes();
            for (DetailCommande detailCommande: detailCommandes) {
                Livre livreCommande = detailCommande.getLivre();
                if (livreCommande.equals(livre)) nbVentes++;
            }
        }
        return nbVentes;
    }

    /**
     * Obtenir la liste des livres triés par leur nombre de ventes.
     * @return La liste de livres triés par leur nombre de ventes.
     */
    public List<Livre> getLivresTriesParVentes() {
        ComparatorLivreParVentes comparerVentesLivre = new ComparatorLivreParVentes(this);

        List<Livre> livresTries = new ArrayList<>(this.livres);
        Collections.sort(livresTries, comparerVentesLivre);
        return livresTries;
    }

    /**
     * Obtenir la liste des livres recommandés pour un client, triés dans l'ordre le plus pertinant.
     * TODO: Voir si on déplace cela dans Client (ce qui serait le plus logique).
     * @param client Le client.
     * @return La liste des livres recommandés pour un client, triés dans l'ordre le plus pertinant.
     */
    public List<Livre> onVousRecommande(Client client) {
        List<Commande> commandesClient = client.getCommandes();
        // TODO: Voir si on prend en compte le panier de l'utilisateur
        if (commandesClient.size() == 0) return this.getLivresTriesParVentes();

        // Aide : https://www.w3schools.com/java/java_hashmap.asp
        HashMap<Livre, Integer> livresRecommandes = new HashMap<>();
        for (Client clientQuelconque: this.clients) {
            // On ne regarde pas le client lui-même.
            if (client.equals(clientQuelconque)) continue;
            
            if (this.ontLivresCommuns(client, clientQuelconque)) {
                List<Livre> livresNonPossedes = this.getLivresNonAchetesParClient1(client, clientQuelconque);
                for (Livre livre: livresNonPossedes) {
                    Integer curLivreRecommendations = livresRecommandes.get(livre);
                    if (curLivreRecommendations == 0) curLivreRecommendations = 0;

                    livresRecommandes.put(livre, curLivreRecommendations);
                }
            }
        }

        // TODO: Reprendre à partir de la, non fini

        return this.getLivresTriesParVentes();
    }

    // TODO: Voir si on retourne un nombre, et priorisé selon leur pourcentage de "compatibilité".
    // Voir aussi si on merge pas "ontLivresCommuns" && "getLivresNonAchetesParClient1"
    public boolean ontLivresCommuns(Client client1, Client client2) {
        List<Livre> livresClient1 = client1.getLivresAchetes();
        List<Livre> livresClient2 = client2.getLivresAchetes();

        for (Livre livre: livresClient2) {
            if (livresClient1.contains(livre)) return true;
        }
        return false;
    }

    public List<Livre> getLivresNonAchetesParClient1(Client client1, Client client2) {
        List<Livre> livresClient1 = client1.getLivresAchetes();
        List<Livre> livresClient2 = client2.getLivresAchetes();

        List<Livre> livresNonPossedesClient1 = new ArrayList<>();
        for (Livre livre: livresClient2) {
            if (!livresClient1.contains(livre)) {
                livresNonPossedesClient1.add(livre);
            }
        }
        return livresNonPossedesClient1;
    }
}
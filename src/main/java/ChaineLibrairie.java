import java.util.List;
import java.util.Set;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/** La chaîne de librairie */
public class ChaineLibrairie {
    private List<Livre> livres;
    private List<Client> clients;
    private List<Vendeur> vendeurs;
    private List<Magasin> magasins;

    private ConnexionMariaDB connexionMariaDB;

    private LivreBD livreBD;
    private ClientBD clientBD;
    private MagasinBD magasinBD;

    /**
     * Intiailiser la chaîne de librairie.
     */
    public ChaineLibrairie() {
        this.livres = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.vendeurs = new ArrayList<>();
        this.magasins = new ArrayList<>();

        // Base de données
        try {
            this.connexionMariaDB = new ConnexionMariaDB();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MariaDB non trouvé.");
            System.exit(1);
        }

        try {
            // TODO: A modifier via des variables par exemple ou .env

            String nomServeur = "servinfo-maria";
            String nomBase = "DBgautier";
            String nomLogin = "gautier";
            String motDePasse = "gautier";

            this.connexionMariaDB.connecter(nomServeur, nomBase, nomLogin, motDePasse);
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la BD : " + e.getMessage());
            System.exit(1);
        }

        this.livreBD = new LivreBD(this.connexionMariaDB);
        this.clientBD = new ClientBD(this.connexionMariaDB);
        this.magasinBD = new MagasinBD(this.connexionMariaDB);
    }

    public LivreBD getLivreBD() {
        return this.livreBD;
    }

    public ClientBD getClientBD() {
        return this.clientBD;
    }

    public MagasinBD getMagasinBD() {
        return this.magasinBD;
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
     * Obtenir la liste des vendeurs de la chaîne de librairie.
     * @return La liste des vendeurs de la chaîne de librairie.
     */
    public List<Vendeur> getVendeurs() {
        return this.vendeurs;
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
     * Ajouter un vendeur à la chaîne de librairie.
     * @param vendeur Le vendeur à ajouter.
     */
    public void ajouterVendeur(Vendeur vendeur){
        this.vendeurs.add(vendeur);
    }

    /**
     * Ajouter un magasin à la chaîne de librairie.
     * @param magasin Le magasin à ajouter.
     */
    public void ajouterMagasin(Magasin magasin) {
        this.magasins.add(magasin);
    }

    /*
    * Touver un vendeur à partir de son nom et de son prenom     
    * @param nom Le nom du vendeur.
    * @param prenom Le prénom du vendeur.
    * @return le vendeur trouvé, ou null si aucun n'a été trouvé.s
    */
    public Vendeur trouverVendeur(String nom, String prenom) {
        for (Vendeur vendeur: this.vendeurs) {
            if (vendeur.getNom().equals(nom) && vendeur.getPrenom().equals(prenom)) {
                return vendeur;
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
            List<DetailLivre> detailCommandes = commande.getDetailCommandes();
            for (DetailLivre detailCommande: detailCommandes) {
                Livre livreCommande = detailCommande.getLivre();
                if (livreCommande.equals(livre)) nbVentes++;
            }
        }
        return nbVentes;
    }

    /**
     * Obtenir la liste des livres triés par leur nombre de ventes.
     * @param listeLivres La liste de livres initial.
     * @return La liste de livres triés par leur nombre de ventes.
     */
    public List<Livre> getLivresTriesParVentes(List<Livre> listeLivres) {
        ComparatorLivreParVentes comparerVentesLivre = new ComparatorLivreParVentes(this);

        List<Livre> livresTries = new ArrayList<>(listeLivres);
        Collections.sort(livresTries, comparerVentesLivre);
        return livresTries;
    }

    /**
     * Obtenir la liste des livres recommandés pour un client, triés dans l'ordre le plus pertinant.
     * On vérifie d'abord suivant les autres clients, puis selon les classifications similaires du client par rapport à ces dernieres commandes.
     * Enfin, on tri selon le nombre de ventes nationals, notammant en cas d'ex aequo.
     * @param client Le client.
     * @return La liste des livres recommandés pour un client, triés dans l'ordre le plus pertinant.
     */
    public List<Livre> onVousRecommande(Client client) {
        Panier panierClient = client.getPanier();
        List<DetailLivre> detailPanierClient = panierClient.getDetailLivres();
        
        List<Commande> commandesClient = client.getCommandes();
        if (commandesClient.size() == 0 && detailPanierClient.size() == 0) {
            return this.getLivresTriesParVentes(this.livres);
        }

        // -- Recommendations par rapport aux autres clients

        // On tri par défaut suivant le nombre de ventes en cas d'ex aequo.
        List<Livre> livresNonAchetesParClient = this.getLivresTriesParVentes(client.getLivresNonAchetes(this.livres));
        HashMap<Livre, Integer> recommendationsLivres = new HashMap<>();
        for (Client clientQuelconque: this.clients) {
            // On ne regarde pas le client lui-même.
            if (client.equals(clientQuelconque)) continue;
   
            if (this.ontLivresEnCommun(client, clientQuelconque)) {
                List<Livre> livresAchetesParAutreClient = clientQuelconque.getLivresAchetes();
                for (DetailLivre detailCommande: clientQuelconque.getDetailCommandes()) {
                    Livre livre = detailCommande.getLivre();
                    if (livresNonAchetesParClient.contains(livre)) {
                        Integer curLivreRecommendations = recommendationsLivres.get(livre);
                        if (curLivreRecommendations == null) curLivreRecommendations = 0;
        
                        if (livresAchetesParAutreClient.contains(livre)) {
                            curLivreRecommendations += 3;
                        } else {
                            curLivreRecommendations += 2;
                        }
                        recommendationsLivres.put(livre, curLivreRecommendations);
                    }
                }
            }
        }

        // Recommendations suivant classifications similaires

        Set<String> classificationsClient = client.getClassifications();
        for (Livre livre: livresNonAchetesParClient) {
            for (String classification: livre.getClassifications()) {
                if (classificationsClient.contains(classification)) {
                    Integer curLivreRecommendations = recommendationsLivres.get(livre);
                    if (curLivreRecommendations == null) curLivreRecommendations = 0;
    
                    curLivreRecommendations += 1;
                    recommendationsLivres.put(livre, curLivreRecommendations);
                    break;
                }
            }
        }

        ComparatorLivreRecommandation comparatorRecommendation = new ComparatorLivreRecommandation(recommendationsLivres);
        Collections.sort(livresNonAchetesParClient, comparatorRecommendation);
        return livresNonAchetesParClient;
    }

    /**
     * Indique si deux clients ont des livres en commun, dans leur commande et/ou panier.
     * @param client1 Un client 1.
     * @param client2 Un client 2.
     * @return Vrai s'ils ont des livres en communs, sinon false.
     */
    public boolean ontLivresEnCommun(Client client1, Client client2) {
        for (DetailLivre detailCommande1: client1.getDetailCommandes()) {
            for (DetailLivre detailCommande2: client2.getDetailCommandes()) {
                if (detailCommande1.getLivre().equals(detailCommande2.getLivre())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Génère le corps d'une commande pour l'afficher sous forme de facture notamment. 
     * @param detailLivres Les détails des livres, avec leur quantité et prix d'achat.
     * @param longueurAffichage La longueur d'affichage maximal.
     * @return Une liste avec tout le texte nécessaire.
     */
    public static List<String> genererCorpsCommandeTextuel(List<DetailLivre> detailLivres, int longueurAffichage) {
        if (detailLivres.size() == 0) return new ArrayList<>();

        double totalCommande = 0.00;
        List<String> res = new ArrayList<>();
        res.add("       ISBN                               Titre                              Qte    Prix   Total");
        for (int i = 0; i < detailLivres.size(); i++) {
            DetailLivre detailCommande = detailLivres.get(i);
            Livre livre = detailCommande.getLivre();

            String numLigne = String.format("%2s", detailCommande.getNumLigne());
            String isbn = String.format("%13s", livre.getISBN());
            String titre = String.format("%-59s", livre.getTitre());
            String qte = String.format("%3s", detailCommande.getQuantite());
            String prix = String.format("%6.2f€", detailCommande.getPrixVente());
            String total = String.format("%6.2f€", detailCommande.getPrixVente() * detailCommande.getQuantite());

            totalCommande += detailCommande.getPrixVente() * detailCommande.getQuantite();
            res.add(String.format("%s %s %s %s %s %s", numLigne, isbn, titre, qte, prix, total));
        }

        res.add(String.format("%-" + (longueurAffichage - 11) + "s%s", "", "-------"));
        res.add(String.format("%-" + (longueurAffichage - 11) + "s%6.2f€", "", totalCommande));
        return res;
    }
}
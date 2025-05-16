import java.util.List;
import java.util.Set;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/** La chaîne de librairie */
public class ChaineLibrairie {
    private ConnexionMariaDB connexionMariaDB;

    private LivreBD livreBD;
    private ClientBD clientBD;
    private CommandeBD commandeBD;
    private PanierBD panierBD;
    private MagasinBD magasinBD;
    private VendeurBD vendeurBD;

    /**
     * Intiailiser la chaîne de librairie.
     */
    public ChaineLibrairie() {
        // Base de données
        try {
            this.connexionMariaDB = new ConnexionMariaDB();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MariaDB non trouvé.");
            System.exit(1);
        }

        try {
            // TODO: A modifier via des variables par exemple ou .env

            String nomServeur = "localhost";
            String nomBase = "Librairie";
            String nomLogin = "root";
            String motDePasse = "root_mdp";

            this.connexionMariaDB.connecter(nomServeur, nomBase, nomLogin, motDePasse);
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la BD : " + e.getMessage());
            System.exit(1);
        }

        this.livreBD = new LivreBD(this.connexionMariaDB);
        this.clientBD = new ClientBD(this, this.connexionMariaDB);
        this.commandeBD = new CommandeBD(this, this.connexionMariaDB);
        this.panierBD = new PanierBD(this, this.connexionMariaDB);
        this.magasinBD = new MagasinBD(this.connexionMariaDB);
        this.vendeurBD = new VendeurBD(this.connexionMariaDB);
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des livres.
     * @return La classe de la base de données pour récupérer des livres.
     */
    public LivreBD getLivreBD() {
        return this.livreBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des clients.
     * @return La classe de la base de données pour récupérer des clients.
     */
    public ClientBD getClientBD() {
        return this.clientBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des commandes.
     * @return La classe de la base de données pour récupérer des commandes.
     */
    public CommandeBD getCommandeBD() {
        return this.commandeBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer les paniers clients.
     * @return La classe de la base de données pour récupérer les paniers clients.
     */
    public PanierBD getPanierBD() {
        return this.panierBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des magasins.
     * @return La classe de la base de données pour récupérer des magasins.
     */
    public MagasinBD getMagasinBD() {
        return this.magasinBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des vendeurs.
     * @return La classe de la base de données pour récupérer des vendeurs.
     */
    public VendeurBD getVendeurBD() {
        return this.vendeurBD;
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
     * Obtenir le nombre de vente d'un livre dans toute la chaîne de librairie.
     * @param livre Le livre concerné.
     * @return Le nombre de vente du livre dans toute la chaîne de librairie.
     */
    public int getNombreVentesLivre(Livre livre) {
        try {
            return this.getLivreBD().getNombreVentesLivre(livre.getISBN());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du nombre de ventes d'un livre : " + e.getMessage());
            return 0;
        }
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
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public List<Livre> onVousRecommande(Client client) throws SQLException {
        Panier panierClient = client.getPanier();
        List<DetailLivre> detailPanierClient = panierClient.getDetailLivres();
        
        List<Livre> listeLivresNational = this.livreBD.obtenirListeLivre();

        List<Commande> commandesClient = client.getCommandes();
        if (commandesClient.size() == 0 && detailPanierClient.size() == 0) {
            return this.getLivresTriesParVentes(listeLivresNational);
        }

        // -- Recommendations par rapport aux autres clients

        // On tri par défaut suivant le nombre de ventes en cas d'ex aequo.
        List<Livre> livresNonAchetesParClient = this.getLivresTriesParVentes(client.getLivresNonAchetes(listeLivresNational));
        
        HashMap<Livre, Integer> recommendationsLivres = new HashMap<>();
        List<Client> clientsCommuns = this.clientBD.obtenirClientsAyantLivresCommuns(client.getId());
        for (Client clientQuelconque: clientsCommuns) {
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
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/** La chaîne de librairie */
public class ChaineLibrairie {
    private List<Livre> livres;
    private List<Client> clients;
    private List<Personnel> personnels;
    private List<Magasin> magasins;

    /**
     * Intiailiser la chaîne de librairie.
     */
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
        nom = nom.toLowerCase();
        prenom = prenom.toLowerCase();

        for (Client client: this.clients) {
            String nomClient = client.getNom().toLowerCase();
            String prenomClient = client.getPrenom().toLowerCase();
            if (nomClient.equals(nom) && prenomClient.equals(prenom)) {
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
        // TODO: Voir pour ajouter exception
        if (!this.livres.contains(livre)) return 0;

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
     * On vérifie d'abord suivant les autres clients. Puis à defaut, les livres ayant les mêmes genres que ceux achetés par ce client, avec un tri selon le nombre de ventes nationals;
     * Enfin, s'il y a toujours pas de résultat, on renvoie le classement des livres les plus vendus.
     * @param client Le client.
     * @return La liste des livres recommandés pour un client, triés dans l'ordre le plus pertinant.
     */
    public List<Livre> onVousRecommande(Client client) {
        Panier panierClient = client.getPanier();
        List<DetailCommande> detailPanierClient = panierClient.getDetailCommandes();
        
        List<Commande> commandesClient = client.getCommandes();
        if (commandesClient.size() == 0 && detailPanierClient.size() == 0) {
            return this.getLivresTriesParVentes(this.livres);
        } 

        // -- Recommendations par rapport aux autres clients

        // Aide : https://www.w3schools.com/java/java_hashmap.asp
        HashMap<Livre, Integer> livresRecommandes = new HashMap<>();
        for (Client clientQuelconque: this.clients) {
            // On ne regarde pas le client lui-même.
            if (client.equals(clientQuelconque)) continue;
   
            int pourcentageLivresCommuns = this.getPourcentageLivresCommuns(client, clientQuelconque);
            // Si le pourcentage = 100, cela veut dire qu'ils ont déjà achetés les mêmes livres, dont pas la peine de vérifier davantage
            if (pourcentageLivresCommuns > 0 && pourcentageLivresCommuns < 100) {
                Set<Livre> livresNonPossedes = this.getLivresClient2NonAchetesParClient1(client, clientQuelconque);
                for (Livre livre: livresNonPossedes) {
                    Integer curLivreRecommendations = livresRecommandes.get(livre);
                    if (curLivreRecommendations == null) curLivreRecommendations = 0;

                    curLivreRecommendations += pourcentageLivresCommuns;
                    livresRecommandes.put(livre, curLivreRecommendations);
                }
            }
        }

        // -- Suivant les thèmes des livres précédents achetés + leur nombre de ventes
        if (livresRecommandes.size() == 0) {
            Set<Livre> livresAchetes = client.getLivresAchetes();
            HashMap<String, Integer> classificationsOccurenceClient = client.getClassificationsOccurence();

            List<Livre> listeLivre = this.getLivres();
            for (Livre livre: listeLivre) {
                // On ne mets pas en avant les livres que le client a déjà acheté
                if (!livresAchetes.contains(livre)) {
                    int occurenceClassificationLivreMax = 0;
                    List<String> listeClassifications = livre.getClassifications();
                    for (String classification: listeClassifications) {
                        Integer occurenceClassification = classificationsOccurenceClient.get(classification);
                        if (occurenceClassification != null) {
                            if (occurenceClassification > occurenceClassificationLivreMax) {
                                occurenceClassificationLivreMax = occurenceClassification;
                            }
                        }
                    }
    
                    if (occurenceClassificationLivreMax != 0) {
                        livresRecommandes.put(livre, occurenceClassificationLivreMax);
                    }
                }
            }
        }

        // Si livres en commun avec d'autres clients ou thèmes du livre
        if (livresRecommandes.size() >= 1) {
            List<Livre> listeLivresCopie = new ArrayList<>(this.getLivres());
            ComparatorLivreRecommandation comparatorRecommendation = new ComparatorLivreRecommandation(livresRecommandes);
            Collections.sort(listeLivresCopie, comparatorRecommendation);

            return listeLivresCopie;
        }

        // -- Valeur de secours
        return this.getLivresTriesParVentes(this.livres);
    }

    /**
     * Obtenir le pourcentage de livres communs entre deux clients.
     * @param client1 Le client 1.
     * @param client2 Le client 2.
     * @return Le pourcentage (sous forme d'entier) de livres en communs entre les deux clients.
     */
    public int getPourcentageLivresCommuns(Client client1, Client client2) {
        Set<Livre> livresClient2 = client2.getLivresAchetes();
        if (livresClient2.size() == 0) return 0;
        
        int nbLivreEnCommun = 0;
        Set<Livre> livresClient1 = client1.getLivresAchetes();
        for (Livre livre: livresClient2) {
            if (livresClient1.contains(livre)) nbLivreEnCommun++;
        }

        return (nbLivreEnCommun / livresClient2.size()) * 100;
    }

    /**
     * Obtenir l'ensemble des livres d'un client B non acheté par un client A.
     * @param client1 Le premier client.
     * @param client2 Le deuxième client.
     * @return Les livres du client B non acheté par le client A.
     */
    public Set<Livre> getLivresClient2NonAchetesParClient1(Client client1, Client client2) {
        Set<Livre> livresClient1 = client1.getLivresAchetes();
        Set<Livre> livresClient2 = client2.getLivresAchetes();

        Set<Livre> livresNonPossedesClient1 = new HashSet<>();
        for (Livre livre: livresClient2) {
            if (!livresClient1.contains(livre)) {
                livresNonPossedesClient1.add(livre);
            }
        }
        return livresNonPossedesClient1;
    }

    /**
     * Génère le corps d'une commande pour l'afficher sous forme de facture notamment. 
     * @param detailCommandes Les détails des commandes (livres, quantité, ...).
     * @param longueurAffichage La longueur d'affichage maximal.
     * @return Une liste avec tout le texte nécessaire.
     */
    public List<String> genererCorpsCommandeTextuel(List<DetailCommande> detailCommandes, int longueurAffichage) {
        if (detailCommandes.size() == 0) return new ArrayList<>();

        double totalCommande = 0.00;
        List<String> res = new ArrayList<>();
        res.add("       ISBN                               Titre                              Qte    Prix   Total");
        for (int i = 0; i < detailCommandes.size(); i++) {
            DetailCommande detailCommande = detailCommandes.get(i);
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Un client */
public class Client extends Personne {
    private Magasin magasin;
    private List<Commande> commandes;
    private Panier panier;

    /**
     * Créer un client.
     * @param id L'identifiant du client.
     * @param nom Le nom du prénom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @param codePostal Le code postal du client.
     * @param ville La ville du client.
     * @param magasin Le magasin du client.
     * @param commandes La liste des commandes du client.
     * @param panier Le panier du client.
     */
    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin, List<Commande> commandes, Panier panier) {
        super(id, nom, prenom, adresse, codePostal, ville);
        this.magasin = magasin;
        this.commandes = commandes;
        this.panier = panier;
    }

    /**
     * Créer un client sans panier rempli.
     * @param id L'identifiant du client.
     * @param nom Le nom du prénom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @param codePostal Le code postal du client.
     * @param ville La ville du client.
     * @param magasin Le magasin du client.
     * @param commandes La liste des commandes du client.
     */
    public Client(int id, String nom, String prenom, String adresse, String codePostal, String ville, Magasin magasin, List<Commande> commandes) {
        this(id, nom, prenom, adresse, codePostal, ville, magasin, commandes, new Panier(magasin));
    }

    /**
     * Obtenir le magasin choisi par le client.
     * @return Le magasin du client pour sa prochaine commande.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Obtenir la liste des commandes du client.
     * @return La liste des commandes qu'il a effectuées.
     */
    public List<Commande> getCommandes() {
        return this.commandes;
    }

    /**
     * Obtenir le panier du client.
     * @return Le panier du client.
     */
    public Panier getPanier() {
        return this.panier;
    }

    /**
     * Définir le panier pour un client.
     * @param panier Le panier du client.
     */
    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    /**
     * Définir le magasin pour un client.
     * @param magasin Le nouveau magasin du client.
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    /**
     * Commander un livre pour un client.
     * @param modeLivraison Le mode de livraison : M en magasin / C pour la livraison à domicile.
     * @param detailCommandes Les livres de la commande
     */
    public void commander(char modeLivraison, List<DetailCommande> detailCommandes) {
        // TODO: Voir pour l'ID de la commande, normalement cela devrait être la DB qui devrait la donner
        Commande commande = new Commande(1, Date.valueOf(LocalDate.now()), 'O', modeLivraison, panier.getMagasin(), detailCommandes);
        this.commandes.add(commande);
        
        Panier panier = this.getPanier();
        panier.viderPanier();
    }

    /**
     * Obtenir les détails de l'ensemble des commandes et panier du client.
     * @return La liste avec les détails de l'ensemble des commandes et panier du client.
     */
    public List<DetailCommande> getDetailCommandes() {
        List<DetailCommande> detailCommandes = new ArrayList<>();
        for (Commande commande: this.getCommandes()) {
            detailCommandes.addAll(commande.getDetailCommandes());
        }

        Panier panier = this.getPanier();
        detailCommandes.addAll(panier.getDetailCommandes());

        return detailCommandes;
    }

    /**
     * Obtenir la liste des livres achetés et mis dans le panier par un client.
     * @return La liste des livres achetés et mis dans le panier par ce client.
     */
    public Set<Livre> getLivresAchetes() {
        Set<Livre> livresAchetes = new HashSet<>();
        List<DetailCommande> detailCommandesClient = this.getDetailCommandes();
        for (DetailCommande detailCommande: detailCommandesClient) {
            livresAchetes.add(detailCommande.getLivre());
        }
        return livresAchetes;
    }

    /**
     * Obtenir un dictionnaire avec comme clé une classification et comme valeur le nombre d'occurence de cette classification parmi les commandes et panier du client.
     * @return Un dictionnaire avec comme clé une classification et comme valeur le nombre d'occurence de cette classification parmi les commandes et panier du client.
     */
    public HashMap<String, Integer> getClassificationsOccurence() {
        List<Commande> commandes = this.getCommandes();
        if (commandes.size() == 0) return new HashMap<>();

        HashMap<String, Integer> classificationsOccurance = new HashMap<>();
        List<DetailCommande> detailCommandes = this.getDetailCommandes();
        for (DetailCommande detailCommande: detailCommandes) {
            Livre livreCommande = detailCommande.getLivre();

            List<String> classifications = livreCommande.getClassifications();
            for (String classification: classifications) {
                if (classificationsOccurance.get(classification) == null) {
                    classificationsOccurance.put(classification, 1);
                } else {
                    Integer occuranceActuelle = classificationsOccurance.get(classification);
                    classificationsOccurance.put(classification, occuranceActuelle++);
                }
            }
        }
        return classificationsOccurance;
    }

    /**
     * Obtenir la liste des commandes triées de la plus récente à la plus ancienne.
     * @return La liste des commandes du client triées de la plus récente à la plus ancienne.
     */
    public List<Commande> getCommandesTriesParDateDesc() {
        List<Commande> commandes = this.getCommandes();
        
        List<Commande> commandesTries = new ArrayList<>(commandes);
        ComparatorCommandeDateDesc comparator = new ComparatorCommandeDateDesc();
        Collections.sort(commandesTries, comparator);
        
        return commandesTries;
    }
}
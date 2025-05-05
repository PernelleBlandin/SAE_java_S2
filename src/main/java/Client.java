import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Un client */
public class Client extends Personne {
    private String adresse;
    private String codePostal;
    private String ville;
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
        super(id, nom, prenom);
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.magasin = magasin;
        this.commandes = commandes;
        this.panier = panier;
    }

    /**
     * Créer une copie d'un client.
     * @param client Un client.
     */
    public Client(Client client) {
        this(client.getId(), client.getNom(), client.getPrenom(), client.getAdresse(), client.getCodePostal(), client.getVille(), client.getMagasin(), client.getCommandes(), client.getPanier());
    }

    /**
     * Obtenir l'adresse du client.
     * @return Son adresse.
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Obtenir le code postal du client.
     * @return Son code postal.
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     * Obtenir la ville du client.
     * @return Sa ville.
     */
    public String getVille() {
        return this.ville;
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
     * @param enLigne O si en ligne, N si la commande a été passée en magasin.
     * @return true si la commande a été passée, sinon false.
     */
    public boolean commander(char modeLivraison, char enLigne) {
        Panier panier = this.getPanier();
        List<DetailCommande> detailCommandes = panier.getDetailCommandes();
        if (detailCommandes.size() > 0) {
            // TODO: Voir pour l'ID de la commande, normalement cela devrait être la DB qui devrait la donner
            Commande commande = new Commande(1, Date.valueOf(LocalDate.now()), enLigne, modeLivraison, panier.getMagasin(), detailCommandes);
            this.commandes.add(commande);
            
            panier.viderPanier();
            return true;
        }
        return false;
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
     * Obtenir la liste des livres achetés par un client.
     * @return La liste des livres achetés par ce client.
     */
    public List<Livre> getLivresAchetes() {
        List<Livre> livresAchetes = new ArrayList<>();

        for (Commande commande: this.getCommandes()) {
            List<DetailCommande> detailCommandesClient = commande.getDetailCommandes();
            for (DetailCommande detailCommande: detailCommandesClient) {
                Livre livreDetailCommande = detailCommande.getLivre();
                if (!livresAchetes.contains(livreDetailCommande)) {
                    livresAchetes.add(detailCommande.getLivre());
                }
            }
        }
        return livresAchetes;
    }

    /**
     * Obtenir la liste des livres non achetés et non mis dans le panier par le client.
     * @param livres Les livres selectionnés.
     * @return La liste des livres non achetés et non mis dans le panier par le client.
     */
    public List<Livre> getLivresNonAchetes(List<Livre> livres) {
        List<Livre> livresNonAchetes = new ArrayList<>();

        List<Livre> livresAchetes = this.getLivresAchetes();
        List<Livre> livresDansPanier = this.getPanier().getLivres();
        for (Livre livre: livres) {
            if (!livresAchetes.contains(livre) && !livresDansPanier.contains(livre)) {
                livresNonAchetes.add(livre);
            }
        }
        return livresNonAchetes;
    }

    /**
     * Obtenir l'ensemble des classifications des achats du client.
     * @return L'ensemble des classifications des achats du client.
     */
    public Set<String> getClassifications() {
        Set<String> classificationsClient = new HashSet<>();
        List<Livre> livresAchetes = this.getLivresAchetes();
        for (Livre livre: livresAchetes) {
            classificationsClient.addAll(livre.getClassifications());
        }
        return classificationsClient;
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
import java.util.ArrayList;
import java.util.List;

/** Un panier client */
public class Panier {
    private Magasin magasin;
    private List<DetailCommande> detailCommandes;
    
    /**
     * Créer un panier client vide.
     * @param magasin Le magasin du panier.
     */
    public Panier(Magasin magasin) {
        this.magasin = magasin;
        this.detailCommandes = new ArrayList<>();
    }

    /**
     * Créer un panier client.
     * @param magasin Le magasin du panier.
     * @param detailCommandes L'ensemble des éléments du panier.
     */
    public Panier(Magasin magasin, List<DetailCommande> detailCommandes) {
        this.magasin = magasin;
        this.detailCommandes = detailCommandes;
    }

    /**
     * Récupérer le magasin lié au panier.
     * @return Le magasin lié au panier.
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Récupérer la liste des livres du panier.
     * @return Les livres présents dans le panier.
     */
    public List<DetailCommande> getDetailCommandes() {
        return this.detailCommandes;
    }

    /**
     * Obtenir la liste de livres d'un panier.
     * @return La liste de livres d'un panier.
     */
    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        for (DetailCommande detailCommande: this.detailCommandes) {
            livres.add(detailCommande.getLivre());
        }
        return livres;
    }

    /**
     * Obtenir les détails d'une commande liée à un livre.
     * @param livre Un livre.
     * @return Les détails d'une commande d'un livre donnée, ou null s'il y en a pas.
     */
    public DetailCommande getDetailCommandeLivre(Livre livre) {
        for (DetailCommande detailCommande: this.detailCommandes) {
            if (detailCommande.getLivre().equals(livre)) {
                return detailCommande;
            }
        }
        return null;
    }

    /**
     * Ajouter un livre à un panier client.
     * @param livre Le livre à ajouter.
     * @return La quantité du livre dans le panier.
     */
    public int ajouterLivre(Livre livre) {
        DetailCommande detailCommande = this.getDetailCommandeLivre(livre);
        if (detailCommande == null) {
            detailCommande = new DetailCommande(livre, this.detailCommandes.size() + 1, 1, livre.getPrix());
            this.detailCommandes.add(detailCommande);
        } else {
            detailCommande.ajouterQuantite();
        }
        return detailCommande.getQuantite();
    }

    /**
     * Supprimer une certaine quantité d'un livre du panier client.
     * @param livre Le livre.
     * @param quantite La quantité à retirer.
     */
    public void retirerQuantiteLivre(Livre livre, int quantite) {
        DetailCommande detailCommandeLivre = this.getDetailCommandeLivre(livre);
        if (detailCommandeLivre != null) {
            int quantiteLivrePanier = detailCommandeLivre.getQuantite();
            if (quantite >= quantiteLivrePanier) {
                // TODO: Recalculer les ids des numéros de lignes
                this.detailCommandes.remove(detailCommandeLivre);
            } else {
                detailCommandeLivre.setQuantite(quantiteLivrePanier - quantite);
            }
        }
    }

    /**
     * Vider le panier client.
     */
    public void viderPanier() {
        this.detailCommandes.clear();
    }
}

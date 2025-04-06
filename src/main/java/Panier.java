import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<DetailCommande> detailCommandes;
    
    /**
     * Créer un panier client.
     */
    public Panier() {
        this.detailCommandes = new ArrayList<>();
    }

    /**
     * Récupérer la liste des livres du panier.
     * @return Les livres présents dans le panier.
     */
    public List<DetailCommande> getDetailCommandes() {
        return this.detailCommandes;
    }

    /**
     * Ajouter un livre à un panier client.
     * @param livre Le livre à ajouter.s
     */
    public void ajouterLivre(Livre livre) {
        int i = 0;
        boolean trouve = false;
        while (!trouve && i < this.detailCommandes.size()) {
            DetailCommande detailCommande = this.detailCommandes.get(i);
            if (detailCommande.getLivre().equals(livre)) {
                detailCommande.ajouterQuantite();
                trouve = true;
            }
            i++;
        }

        if (!trouve) {
            DetailCommande detailCommande = new DetailCommande(livre, this.detailCommandes.size() + 1, 1, livre.getPrix());
            this.detailCommandes.add(detailCommande);
        }
    }

    /**
     * Vider le panier client.
     */
    public void viderPanier() {
        this.detailCommandes.clear();
    }
}

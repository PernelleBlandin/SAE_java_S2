import java.util.ArrayList;
import java.util.List;

/** Un panier client */
public class Panier {
    private int idPanier;
    private Magasin magasin;
    private List<DetailLivre> detailLivres;
    
    /**
     * Créer un panier client vide.
     * @param idPanier L'identifiant du panier.
     * @param magasin Le magasin du panier.
     */
    public Panier(int idPanier, Magasin magasin) {
        this.idPanier = idPanier;
        this.magasin = magasin;
        this.detailLivres = new ArrayList<>();
    }

    /**
     * Créer un panier client.
     * @param idPanier L'identifiant du panier.
     * @param magasin Le magasin du panier.
     * @param detailLivres L'ensemble des éléments du panier.
     */
    public Panier(int idPanier, Magasin magasin, List<DetailLivre> detailLivres) {
        this.idPanier = idPanier;
        this.magasin = magasin;
        this.detailLivres = detailLivres;
    }

    /**
     * Obtenir l'identifiant du panier.
     * @return L'identifiant du panier.
     */
    public int getId() {
        return this.idPanier;
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
    public List<DetailLivre> getDetailLivres() {
        return this.detailLivres;
    }

    /**
     * Obtenir la liste de livres d'un panier.
     * @return La liste de livres d'un panier.
     */
    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        for (DetailLivre detailLivre: this.detailLivres) {
            livres.add(detailLivre.getLivre());
        }
        return livres;
    }

    /**
     * Obtenir les détails d'une commande liée à un livre.
     * @param livre Un livre.
     * @return Les détails d'une commande d'un livre donnée, ou null s'il y en a pas.
     * @throws LivreIntrouvableException Exception si le livre est introuvable.
     */
    public DetailLivre getDetailLivre(Livre livre) throws LivreIntrouvableException {
        for (DetailLivre detailLivre: this.detailLivres) {
            if (detailLivre.getLivre().equals(livre)) {
                return detailLivre;
            }
        }
        throw new LivreIntrouvableException();
    }

    /**
     * Obtenir la quantité d'un livre dans le panier du client.
     * @param livre Un livre.
     * @return La quantité du livre dans le panier.
     */
    public int getQuantiteLivre(Livre livre) {
        try {
            DetailLivre detailLivre = this.getDetailLivre(livre);
            return detailLivre.getQuantite();
        } catch (LivreIntrouvableException e) {
            return 0;
        }
    }

    /**
     * Changer de magasin et réinitialiser le panier client.
     * @param magasin Le nouveau magasin.
     */
    public void setMagasin(Magasin magasin) {
        this.viderPanier();
        this.magasin = magasin;
    }

    /**
     * Ajouter un livre à un panier client.
     * @param livre Le livre à ajouter.
     * @return La detail livre créé/modifié.
     */
    public DetailLivre ajouterLivre(Livre livre) {
       DetailLivre detailLivre;
        try {
            detailLivre = this.getDetailLivre(livre);
            detailLivre.ajouterQuantite();
        } catch (LivreIntrouvableException e) {
            detailLivre = new DetailLivre(livre, this.detailLivres.size() + 1, 1, livre.getPrix());
            this.detailLivres.add(detailLivre);
        }
        return detailLivre;
    }

    /**
     * Supprimer une certaine quantité d'un livre du panier client.
     * @param livre Le livre.
     * @param quantite La quantité à retirer.
     * @throws LivreIntrouvableException Exception si le livre est introuvable.
     */
    public void retirerQuantiteLivre(Livre livre, int quantite) throws LivreIntrouvableException {
        DetailLivre detailLivre = this.getDetailLivre(livre);
        int quantiteLivrePanier = detailLivre.getQuantite();
        if (quantite >= quantiteLivrePanier) {
            int index = this.detailLivres.indexOf(detailLivre);

            this.detailLivres.remove(detailLivre);
            if (index != this.detailLivres.size()) {
                this.recalculateNumLignes(index);
            }
        } else {
            detailLivre.setQuantite(quantiteLivrePanier - quantite);
        }
    }

    /**
     * Recalculer les numéros de lignes du détail commandes.
     * @param startIndex L'index de début.
     */
    private void recalculateNumLignes(int startIndex) {
        List<DetailLivre> detailLivres = this.getDetailLivres();
        for (int i = startIndex; i < detailLivres.size(); i++) {
            DetailLivre detailLivre = detailLivres.get(i);
            detailLivre.setNumLigne(i + 1);
        }
    }

    /**
     * Vider le panier client.
     */
    public void viderPanier() {
        this.detailLivres.clear();
    }
}

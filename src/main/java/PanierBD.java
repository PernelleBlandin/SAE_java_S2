import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* Liaison entre les paniers et la base de données. */
public class PanierBD {
    private ChaineLibrairie chaineLibrairie;
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe PanierBD.
     * @param chaineLibrairie Les données de la chaîne de librairie.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public PanierBD(ChaineLibrairie chaineLibrairie, ConnexionMariaDB connexionMariaDB) {
        this.chaineLibrairie = chaineLibrairie;
        this.connexionMariaDB = connexionMariaDB;
    }

    /**
     * Obtenir le panier d'un client.
     * @param idClient L'identifiant du client.
     * @return Le panier du client indiqué.
     * @throws SQLException Exception SQL en cas de problème avec la base de données.
     */
    public Panier obtenirPanierClient(int idClient) throws SQLException {
        PreparedStatement panierStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idmag, nommag, villemag, numlig, qte, prixvente, isbn
            FROM PANIER NATURAL JOIN MAGASIN NATURAL JOIN DETAILPANIER
            WHERE idcli = ?
            ORDER BY numlig;
        """);
        panierStatement.setInt(1, idClient);

        ResultSet result = panierStatement.executeQuery();
        
        boolean hasElements = result.next();
        if (!hasElements) return null;

        // Magasin Panier

        String idmag = result.getString("idmag");
        String nommag = result.getString("nommag");
        String villemag = result.getString("villemag");

        Magasin magasinClient = new Magasin(idmag, nommag, villemag);

        result.previous();

        // Detail Livre

        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        while (result.next()) {
            // Livre
            String isbn = result.getString("isbn");
            Livre livre = this.chaineLibrairie.getLivreBD().obtenirLivre(isbn);

            int numLigne = result.getInt("numlig");
            int quantite = result.getInt("qte");
            double prixVente = result.getDouble("prixvente");

            listeDetailLivres.add(new DetailLivre(livre, numLigne, quantite, prixVente));
        }
        result.close();
        
        return new Panier(magasinClient, listeDetailLivres);
    }

    /**
     * Vider un panier en base de données.
     * @param idPanier L'identifiant du panier.
     * @throws SQLException Exception SQL en cas d'erreur venant de la base de données.
     */
    public void viderPanier(int idPanier) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            DELETE FROM DETAILPANIER
            WHERE idpanier = ?
        """);
        statement.setInt(1, idPanier);

        statement.executeUpdate();
    }
}

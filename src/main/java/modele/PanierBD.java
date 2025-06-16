package modele;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Liaison entre les paniers et la base de données. */
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
     * Obtenir le plus grand identifiant de panier.
     * @return Le plus grand identifiant de panier.
     * @throws SQLException Exception SQL en cas de problème.
     */
    private int getMaxIdPanier() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
		ResultSet result = statement.executeQuery("""
            SELECT IFNULL(MAX(idpanier), 0) maxIdPanier
            FROM PANIER
        """);

		result.next();
		int maxIdPanier = result.getInt("maxIdPanier");
		result.close();

		return maxIdPanier;
    }

    /**
     * Créer un panier client.
     * @param idClient L'identifiant du client.
     * @param magasin Le magasin du client.
     * @return Le nouveau panier client.
     * @throws SQLException Exception SQL en cas de problème avec la base de données.
     */
    public Panier creerPanierClient(int idClient, Magasin magasin) throws SQLException {
        int maxIdPanier = this.getMaxIdPanier();
        int newIdPanier = maxIdPanier + 1;

        PreparedStatement statement = this.connexionMariaDB.prepareStatement("INSERT INTO PANIER (idpanier, idcli, idmag) VALUES (?, ?, ?)");
        statement.setInt(1, newIdPanier);
        statement.setInt(2, idClient);
        statement.setString(3, magasin.getId());

        int insertCount = statement.executeUpdate();
        if (insertCount == 0) throw new SQLException("Création de panier non réalisée.");

        return new Panier(newIdPanier, magasin);
    }

    /**
     * Obtenir le panier d'un client.
     * @param idClient L'identifiant du client.
     * @param magasin Le magasin client.
     * @return Le panier du client indiqué.
     * @throws SQLException Exception SQL en cas de problème avec la base de données.
     */
    public Panier obtenirPanierClient(int idClient, Magasin magasin) throws SQLException {
        PreparedStatement panierStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idmag, nommag, villemag, idpanier, numlig, qte, prixvente, isbn
            FROM PANIER NATURAL JOIN MAGASIN NATURAL LEFT JOIN DETAILPANIER
            WHERE idcli = ?
            ORDER BY numlig;
        """);
        panierStatement.setInt(1, idClient);

        ResultSet result = panierStatement.executeQuery();
        
        // Si jamais il y a pas de panier en BD, on en créer un.
        boolean hasElements = result.next();
        if (!hasElements) return this.creerPanierClient(idClient, magasin);

        int idPanier = result.getInt("idpanier");

        result.previous();

        // Detail Livre

        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            // Vu qu'on utilise un naturel left join, on peut ne pas avoir de livres
            if (isbn != null) {
                Livre livre = this.chaineLibrairie.getLivreBD().obtenirLivre(isbn);

                int numLigne = result.getInt("numlig");
                int quantite = result.getInt("qte");
                double prixVente = result.getDouble("prixvente");

                listeDetailLivres.add(new DetailLivre(livre, numLigne, quantite, prixVente));
            }

        }
        result.close();
        
        return new Panier(idPanier, magasin, listeDetailLivres);
    }

    /**
     * Changer le magasin d'un panier.
     * @param panier Le panier d'un client.
     * @throws SQLException Exception SQL en cas d'erreur venant de la base de données.
     */
    public void changerMagasin(Panier panier) throws SQLException {
        // On vide le panier lors du changement de magasin 
        this.viderPanier(panier.getId());
        
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            UPDATE PANIER
            SET idmag = ?
            WHERE idpanier = ?
        """);
        statement.setString(1, panier.getMagasin().getId());
        statement.setInt(2, panier.getId());

        statement.executeUpdate();
    }

    /**
     * Ajouter un livre au panier client ou le modifier le cas échéant.
     * @param panier Le panier du client.
     * @param detailLivre Le detail du livre à modifier/ajouter.
     * @throws SQLException Exception SQL en cas d'erreur venant de la base de données.
     */
    public void ajouterLivre(Panier panier, DetailLivre detailLivre) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            INSERT INTO DETAILPANIER (idpanier, numlig, qte, prixvente, isbn)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE qte = ?
        """);
        statement.setInt(1, panier.getId());
        statement.setInt(2, detailLivre.getNumLigne());
        statement.setInt(3, detailLivre.getQuantite());
        statement.setDouble(4, detailLivre.getPrixVente());
        statement.setString(5, detailLivre.getLivre().getISBN());
        
        statement.setInt(6, detailLivre.getQuantite());

        statement.executeUpdate();
    }

    /**
     * Mettre à jour le panier d'un client en base de données.
     * @param panier Le panier du client.
     * @throws SQLException Exception SQL en cas d'erreur venant de la base de données.
     */
    public void updatePanier(Panier panier) throws SQLException {
        this.viderPanier(panier.getId());
        
        for (DetailLivre detailLivre: panier.getDetailLivres()) {
            PreparedStatement statement = this.connexionMariaDB.prepareStatement("INSERT INTO DETAILPANIER (idpanier, numlig, qte, prixvente, isbn) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, panier.getId());
            statement.setInt(2, detailLivre.getNumLigne());
            statement.setInt(3, detailLivre.getQuantite());
            statement.setDouble(4, detailLivre.getPrixVente());
            statement.setString(5, detailLivre.getLivre().getISBN());

            statement.executeUpdate();
        }
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

package modele;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Liaison entre les magasins et la base de données. */
public class MagasinBD {
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe MagasinBD.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public MagasinBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    /**
     * Obtenir la liste des magasins de la base de données.
     * @return La liste des magasins de la base de données.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Magasin> obtenirListeMagasin() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet result = statement.executeQuery("""
            SELECT idmag, nommag, villemag
            FROM MAGASIN;
        """);

        List<Magasin> listeMagasins = new ArrayList<>();
        while (result.next()) {
            String idMagasin = result.getString("idmag");
            String nomMagasin = result.getString("nommag");
            String villeMagasin = result.getString("villemag");

            Magasin magasin = new Magasin(idMagasin, nomMagasin, villeMagasin);
            listeMagasins.add(magasin);
        }
        result.close();

        return listeMagasins;
    }

    /**
     *Obtenir la quantite d'un livre disponible  dans un magasin donné.
     * @param idMagasin L'identifiant du magasin.
     * @param isbnLivre ISBN du livre
     * @return La quantité du livre.
     * @throws SQLException exception SQL en cas d'erreur avec la base de données.
     */
    public int obtenirStockLivre(String idMagasin, String isbnLivre) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT IFNULL(SUM(qte), 0) quantite
            FROM POSSEDER
            WHERE idmag = ? AND isbn = ?;
        """);
        statement.setString(1, idMagasin);
        statement.setString(2, isbnLivre);

        ResultSet result = statement.executeQuery();
        result.next();

        int quantite = result.getInt("quantite");

        result.close();
        return quantite;
    }

    /**
     * Définir la quantité d'un livre disponible dans un magasin donné.
     * Aide : https://sql.sh/cours/insert-into/on-duplicate-key
     * @param idMagasin L'identifiant du magasin.
     * @param isbnLivre L'ISBN du livre.
     * @param nouvelleQuantite La nouvelle quantité du livre dans le magasin.
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public void setStockLivre(String idMagasin, String isbnLivre, int nouvelleQuantite) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            INSERT INTO POSSEDER(idmag, isbn, qte)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE qte=?
        """);
        statement.setString(1, idMagasin);
        statement.setString(2, isbnLivre);
        statement.setInt(3, nouvelleQuantite);
        statement.setInt(4, nouvelleQuantite);

		statement.executeUpdate();
    }

    /**
     * Retirer une certaine quantité d'un livre d'un livre dans un magasin donné.
     * @param idMagasin L'identifiant du magasin.
     * @param isbnLivre L'ISBN du livre.
     * @param quantiteRetiree La quantité a retirée du livre dans le magasin.
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public void retirerStockLivre(String idMagasin, String isbnLivre, int quantiteRetiree) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            UPDATE POSSEDER
            SET qte = qte - ?
            WHERE idmag = ? AND isbn = ?;
        """);
        statement.setInt(1, quantiteRetiree);
        statement.setString(2, idMagasin);
        statement.setString(3, isbnLivre);

		statement.executeUpdate();
    }

    /**
     * Obtenir le plus grand identifiant de magasin.
     * @return Le plus grand identifiant de magasin.
     * @throws SQLException Exception SQL en cas de problème.
     */
    public int getMaxMagasinId() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
		ResultSet result = statement.executeQuery("""
            SELECT IFNULL(MAX(idmag), 0) maxIdMag
            FROM MAGASIN
        """);

		result.next();
		int maxIdCli = result.getInt("maxIdMag");
		result.close();

		return maxIdCli;
    }

    /**
     * Créer un magasin et le stocker en base de données.
     * @param donneesMagasin Un dictionnaire avec les données du magasin (nom et ville).
     * @throws SQLException Exception SQL en cas d'erreur avec la BD ou s'il manque une information dans le dictionnaire.
     */
    public void creerMagasin(HashMap<String, String> donneesMagasin) throws SQLException {
        List<String> clesRequises = new ArrayList<>(Arrays.asList("nom", "ville"));
        for (String cle: clesRequises) {
            if (!donneesMagasin.containsKey(cle)) {
                throw new SQLException("Clé manquante dans les données : " + cle);
            }
        }

        int maxMagasinId = this.getMaxMagasinId();

        PreparedStatement statement = this.connexionMariaDB.prepareStatement("INSERT INTO MAGASIN(idmag, nommag, villemag) VALUES (?, ?, ?)");
        statement.setInt(1, maxMagasinId + 1);
        statement.setString(2, donneesMagasin.get("nom"));
        statement.setString(3, donneesMagasin.get("ville"));

        statement.executeUpdate();
    }

    /**
     * Supprimer un magasin de la base de données.
     * @param idMag L'identifiant du magasin
     * @throws SQLException Exception SQL en cas de problème.
     */
    public void supprimerMagasin(String idMag) throws SQLException{
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("DELETE FROM MAGASIN where idmag = ?");
        statement.setString(1, idMag);

        statement.executeUpdate();
    }
}

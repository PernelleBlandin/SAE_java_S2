import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
     * Obtenir la quantité d'un livre disponible dans un magasin donné.
     * @param idMagasin L'identifiant du magasin.
     * @param isbnLivre L'ISBN du livre.
     * @return La quantité du livre
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
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
}

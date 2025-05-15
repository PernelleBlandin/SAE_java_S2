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
}

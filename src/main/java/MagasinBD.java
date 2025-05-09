import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MagasinBD {
    private ConnexionMariaDB connexionMariaDB;
    public MagasinBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

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

            // TODO: Possession
            Magasin magasin = new Magasin(idMagasin, nomMagasin, villeMagasin, new ArrayList<>());
            listeMagasins.add(magasin);
        }
        result.close();

        return listeMagasins;
    }
}

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LivreBD {
    private ConnexionMariaDB connexionMariaDB;
    public LivreBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    // TODO: Voir pour optimiser avec système de page
    public List<Livre> obtenirListeLivre() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet result = statement.executeQuery("""
            SELECT isbn, titre, nbpages, datepubli, prix, idauteur, nomauteur, anneenais, anneedeces, nomclass, nomedit
            FROM LIVRE NATURAL JOIN ECRIRE NATURAL JOIN AUTEUR NATURAL JOIN THEMES NATURAL JOIN CLASSIFICATION NATURAL JOIN EDITER NATURAL JOIN EDITEUR
            ORDER BY isbn, idauteur, nomclass, nomedit;
        """);

        List<Livre> listeLivres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            String titre = result.getString("titre");

            Integer nbpages = result.getInt("nbpages");
            if (result.wasNull()) nbpages = null;

            Integer date = result.getInt("datepubli");
            Double prix = result.getDouble("prix");

            String nomAuteur = result.getString("nomauteur");
            String nomClassifications = result.getString("nomclass");
            String nomEditeur = result.getString("nomedit");

            List<String> listeAuteurs = new ArrayList<>(Arrays.asList(nomAuteur));
            List<String> listeClassifications = new ArrayList<>(Arrays.asList(nomClassifications));
            List<String> listeEditeurs = new ArrayList<>(Arrays.asList(nomEditeur));

            listeLivres.add(new Livre(isbn, titre, nbpages, date, prix, listeAuteurs, listeClassifications, listeEditeurs));
        }
        result.close();

        return listeLivres;
    }
}

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

        String isbn = null;
        String titre = null;
        Integer nbpages = null;
        Integer date = null;
        Double prix = null;

        // TODO: Voir si on mets un set
        List<String> listeAuteurs = new ArrayList<>();
        List<String> listeClassifications = new ArrayList<>();
        List<String> listeEditeurs = new ArrayList<>();

        List<Livre> listeLivres = new ArrayList<>();
        while (result.next()) {
            String isbnResult = result.getString("isbn");
            if (!isbnResult.equals(isbn)) {
                if (isbn != null) {
                    Livre livre = new Livre(isbn, titre, nbpages, date, prix, listeAuteurs, listeClassifications, listeEditeurs);
                    listeLivres.add(livre);
                }

                isbn = isbnResult;
                titre = result.getString("titre");

                nbpages = result.getInt("nbpages");
                if (result.wasNull()) nbpages = null;

                date = result.getInt("datepubli");
                prix = result.getDouble("prix");

                listeAuteurs = new ArrayList<>();
                listeClassifications = new ArrayList<>();
                listeEditeurs = new ArrayList<>();
            }

            // TODO: Optimiser cette partie, vu qu'on tri correctement ou utiliser un set

            String nomAuteur = result.getString("nomauteur");
            if (!listeAuteurs.contains(nomAuteur)) listeAuteurs.add(nomAuteur);

            String nomClass = result.getString("nomclass");
            if (!listeClassifications.contains(nomClass)) listeClassifications.add(nomClass);

            String nomEditeur = result.getString("nomedit");
            if (!listeEditeurs.contains(nomEditeur)) listeEditeurs.add(nomEditeur);
        }
        result.close();

        // On ajoute le dernier livre
        if (isbn != null) {
            Livre livre = new Livre(isbn, titre, nbpages, date, prix, listeAuteurs, listeClassifications, listeEditeurs);
            listeLivres.add(livre);
        }
        return listeLivres;
    }
}

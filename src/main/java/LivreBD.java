import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LivreBD {
    private ConnexionMariaDB connexionMariaDB;
    public LivreBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    // TODO: Voir pour optimiser avec système de page
    public List<Livre> obtenirListeLivre() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet result = statement.executeQuery("SELECT isbn FROM LIVRE");

        List<Livre> livres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            Livre livre = this.obtenirLivre(isbn);
            livres.add(livre);
        }
        result.close();

        return livres;
    }

    public int getNombreVentesLivre(String isbn) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT COUNT(numcom) nbVentes
            FROM COMMANDE NATURAL JOIN DETAILCOMMANDE
            WHERE isbn = ?
        """);
        statement.setString(1, isbn);

        ResultSet result = statement.executeQuery();
        
        result.next();
        int nbVentes = result.getInt("nbVentes");
        result.close();

        return nbVentes;
    }

    public Livre obtenirLivre(String isbn) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT titre, nbpages, datepubli, prix, nomauteur, nomclass, nomedit
            FROM LIVRE NATURAL LEFT JOIN ECRIRE NATURAL LEFT JOIN AUTEUR NATURAL LEFT JOIN THEMES NATURAL LEFT JOIN CLASSIFICATION NATURAL LEFT JOIN EDITER NATURAL LEFT JOIN EDITEUR
            WHERE isbn = ?
        """);
        statement.setString(1, isbn);

        ResultSet result = statement.executeQuery();
        if (!result.next()) throw new SQLException("Livre non trouvé");

        Set<String> setAuteurs = new HashSet<>();
        Set<String> setEditeurs = new HashSet<>();
        Set<String> setClassifications = new HashSet<>();

        String titre = result.getString("titre");
        Integer nbpages = result.getInt("nbpages");
        if (result.wasNull()) nbpages = null;

        Integer date = result.getInt("datepubli");
        Double prix = result.getDouble("prix");    

        result.previous();

        while (result.next()) {
            String nomAuteur = result.getString("nomauteur");
            if (!result.wasNull()) setAuteurs.add(nomAuteur);

            String nomEditeur = result.getString("nomedit");
            if (!result.wasNull()) setEditeurs.add(nomEditeur);

            String nomClassifications = result.getString("nomclass");
            if (!result.wasNull()) setClassifications.add(nomClassifications);
        }

        result.close();

        return new Livre(isbn, titre, nbpages, date, prix, setAuteurs, setEditeurs, setClassifications);
    }
}

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Liaison entre les vendeurs et la base de données. */
public class VendeurBD {
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe VendeurBD.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public VendeurBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }
    
    /**
     * Obtenir un vendeur par son identifiant.
     * @param idVendeur L'identifiant du vendeur.
     * @return L'instance du vendeur. 
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public Vendeur obtenirVendeurParId(int idVendeur) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT nomvendeur, prenomvendeur, nommag, villemag 
            FROM VENDEUR NATURAL JOIN MAGASIN
            WHERE idvendeur = ?;
        """);
        statement.setInt(1, idVendeur);

        // Vendeur & Magasin

        ResultSet result = statement.executeQuery();

		boolean hasElement = result.next();
        if (!hasElement) throw new SQLException("Vendeur non trouvé");

        String nom = result.getString("nomvendeur");
        String prenom = result.getString("prenomvendeur");

        String idmag = result.getString("idmag");
        String nommag = result.getString("nommag");
        String villemag = result.getString("villemag");

        Magasin magasin = new Magasin(idmag, nommag, villemag);

        result.close();

        return new Vendeur(idVendeur, nom, prenom, magasin);
    }

    /**
     * Obtenir le plus grand identifiant de magasin.
     * @return Le plus grand identifiant de magasin.
     * @throws SQLException Exception SQL en cas de problème.
     */
    public int getMaxVendeurId() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
		ResultSet result = statement.executeQuery("""
            SELECT IFNULL(MAX(idvendeur), 0) maxIdMag
            FROM VENDEUR
        """);

		result.next();
		int maxIdCli = result.getInt("maxIdMag");
		result.close();

		return maxIdCli;
    }

    /**
     * Créer un vendeur en base de données.
     * @param nom Le nom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param idMagasin L'identifiant du magasin auquel il est rattaché.
     * @throws SQLException Exception SQL en cas de problème.
     */
    public void creerVendeur(String nom, String prenom, String idMagasin) throws SQLException {
        int maxVendeurId = this.getMaxVendeurId();

        PreparedStatement statement = this.connexionMariaDB.prepareStatement("INSERT INTO VENDEUR(idvendeur, nomvendeur, prenomvendeur, idmag) VALUES (?, ?, ?, ?)");
        statement.setInt(1, maxVendeurId + 1);
        statement.setString(2, nom);
        statement.setString(3, prenom);
        statement.setString(4, idMagasin);

        statement.executeUpdate();
    }
}

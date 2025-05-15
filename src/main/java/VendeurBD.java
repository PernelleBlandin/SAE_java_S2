import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}

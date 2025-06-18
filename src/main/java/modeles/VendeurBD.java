package modeles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Liaison entre les vendeurs et la base de données. */
public class VendeurBD {
    /** La connexion MariaDB */
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe VendeurBD.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public VendeurBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }
    
    /**
     * Obtenir la liste des vendeurs de la base de données.
     * @return La liste des vendeurs de la base de données.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Vendeur> obtenirListeVendeur() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet result = statement.executeQuery("""
            SELECT idvendeur, nomvendeur, prenomvendeur, idmag, nommag, villemag
            FROM VENDEUR natural join MAGASIN;
        """);

        List<Vendeur> listeVendeurs = new ArrayList<>();
        while (result.next()) {
            int idVendeur = result.getInt("idvendeur");
            String nomVendeur = result.getString("nomvendeur");
            String prenomVendeur = result.getString("prenomvendeur");
            
            Vendeur vendeur = new Vendeur(idVendeur, nomVendeur, prenomVendeur, null);
            listeVendeurs.add(vendeur);
        }
        result.close();

        return listeVendeurs;
    }


    /**
     * Obtenir la liste des vendeurs de la base de données par magasin.
     * @return La liste des vendeurs de la base de données par magasin.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Vendeur> obtenirListeVendeurParMagasin(String idmag) throws SQLException {
        PreparedStatement ps = this.connexionMariaDB.prepareStatement("""
            SELECT idvendeur, nomvendeur, prenomvendeur
            FROM VENDEUR natural join MAGASIN
            WHERE idmag = ?;
            """);
        ps.setString(1, idmag);
        ResultSet result = ps.executeQuery();

        List<Vendeur> listeVendeurs = new ArrayList<>();
        while (result.next()) {
            int idVendeur = result.getInt("idvendeur");
            String nomVendeur = result.getString("nomvendeur");
            String prenomVendeur = result.getString("prenomvendeur");
            
            Vendeur vendeur = new Vendeur(idVendeur, nomVendeur, prenomVendeur, null);
            listeVendeurs.add(vendeur);
        }
        result.close();

        return listeVendeurs;
            
        };
       
    

    /**
     * Obtenir un vendeur par son identifiant.
     * @param idVendeur L'identifiant du vendeur.
     * @return L'instance du vendeur. 
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public Vendeur obtenirVendeurParId(int idVendeur) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT nomvendeur, prenomvendeur, idmag, nommag, villemag 
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

    /**
     * Supprimer un vendeur en base de données.
     * @param id L'identifiant du vendeur
     * @throws SQLException Exception SQL en cas de problème.
     */
    public void supprimerVendeur(int id) throws SQLException{
        
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("DELETE FROM VENDEUR where idvendeur = ?");
        statement.setInt(1, id);

        statement.executeUpdate();
    }
}

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientBD {
    private ConnexionMariaDB connexionMariaDB;
    public ClientBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    public Client obtenirClientParId(int id) throws SQLException {
        PreparedStatement clientStatement = this.connexionMariaDB.prepareStatement("""
            SELECT nomcli, prenomcli, adressecli, codepostal, villecli, idmag, nommag, villemag
            FROM CLIENT NATURAL JOIN MAGASIN
            WHERE idcli = ?;
        """);
        clientStatement.setInt(1, id);

        // Client & Magasin

        ResultSet clientResult = clientStatement.executeQuery();

		boolean hasElement = clientResult.next();
        if (!hasElement) throw new SQLException("Client non trouvé");

        String nom = clientResult.getString("nomcli");
        String prenom = clientResult.getString("prenomcli");
        String adresse = clientResult.getString("adressecli");
        String codepostal = clientResult.getString("codepostal");
        String villecli = clientResult.getString("villecli");

        String idmag = clientResult.getString("idmag");
        String nommag = clientResult.getString("nommag");
        String villemag = clientResult.getString("villemag");

        clientResult.close();

        Magasin magasinClient = new Magasin(idmag, nommag, villemag);

        // Commandes

        // WIP
        PreparedStatement commandesStatement = this.connexionMariaDB.prepareStatement("""
            SELECT nomcli, prenomcli, adressecli, codepostal, villecli, idmag, nommag, villemag
            FROM CLIENT NATURAL JOIN MAGASIN
            WHERE idcli = ?;
        """);
        clientStatement.setInt(1, id);

        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        List<Commande> listeCommandes = new ArrayList<>();
        Panier panier = new Panier(magasinClient, listeDetailLivres);

        return new Client(id, nom, prenom, adresse, codepostal, villecli, magasinClient, listeCommandes, panier);
    }
}

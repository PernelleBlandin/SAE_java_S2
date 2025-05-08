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
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT nomcli, prenomcli, adressecli, codepostal, villecli, idmag, nommag, villemag
            FROM CLIENT NATURAL JOIN MAGASIN
            WHERE idcli = ?;
        """);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

		boolean hasElement = result.next();
        if (!hasElement) throw new SQLException("Joueur non trouvé");

        String nom = result.getString("nomcli");
        String prenom = result.getString("prenomcli");
        String adresse = result.getString("adressecli");
        String codepostal = result.getString("codepostal");
        String villecli = result.getString("villecli");

        String idmag = result.getString("idmag");
        String nommag = result.getString("nommag");
        String villemag = result.getString("villemag");

        result.close();

        // TODO: voir ce qu'on fait
        List<Posseder> listePosseders = new ArrayList<>();
        Magasin magasinClient = new Magasin(idmag, nommag, villemag, listePosseders);

        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        List<Commande> listeCommandes = new ArrayList<>();
        Panier panier = new Panier(magasinClient, listeDetailLivres);

        return new Client(id, nom, prenom, adresse, codepostal, villecli, magasinClient, listeCommandes, panier);
    }
}

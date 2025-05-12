import java.sql.Date;
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

        PreparedStatement commandesStatement = this.connexionMariaDB.prepareStatement("""
            SELECT numcom, datecom, enligne, livraison, numlig, qte, prixvente, isbn, titre, nbpages, datepubli, prix, nomedit, nomclass, nomauteur 
            FROM COMMANDE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN LIVRE NATURAL JOIN EDITER NATURAL JOIN EDITEUR NATURAL JOIN THEMES NATURAL JOIN CLASSIFICATION NATURAL JOIN ECRIRE NATURAL JOIN AUTEUR
            WHERE idcli = ?
            ORDER BY numcom, numlig;
        """);
        clientStatement.setInt(1, id);

        ResultSet resultCommandes = commandesStatement.executeQuery();

        String numCom = null;
        Date datecom  = null;
        Character enligne = null;
        Character livraison = null;
        Magasin magasin = null;

        List<Commande> listeCommandes = new ArrayList<>();
        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        while (resultCommandes.next()) {
            String curNumCom = clientResult.getString("numcom");
            if (curNumCom != numCom) {
                listeCommandes.add(new Commande(numCom, datecom, enligne, livraison))

                numCom = clientResult.getString("numcom");
                datecom  = clientResult.getDate("datecom");
                enligne = clientResult.getString("enligne").charAt(0);
                livraison = clientResult.getString("livraison").charAt(0);
            }
        }
        
        Panier panier = new Panier(magasinClient, listeDetailLivres);

        return new Client(id, nom, prenom, adresse, codepostal, villecli, magasinClient, listeCommandes, panier);
    }
}

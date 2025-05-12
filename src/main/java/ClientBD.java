import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientBD {
    private ChaineLibrairie chaineLibrairie;
    private ConnexionMariaDB connexionMariaDB;
    public ClientBD(ChaineLibrairie chaineLibrairie, ConnexionMariaDB connexionMariaDB) {
        this.chaineLibrairie = chaineLibrairie;
        this.connexionMariaDB = connexionMariaDB;
    }

    public List<Client> obtenirListeClient() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet result = statement.executeQuery("SELECT idcli FROM CLIENT");

        List<Client> clients = new ArrayList<>();
        while (result.next()) {
            int idcli = result.getInt("idcli");
            Client client = this.obtenirClientParId(idcli);
            clients.add(client);
        }
        result.close();

        return clients;
    }

    public List<Client> obtenirClientsAyantLivresCommuns(int clientId) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            WITH LivresClientA AS (
                SELECT isbn
                FROM PANIER NATURAL JOIN DETAILPANIER
                WHERE idcli = ?
                UNION
                SELECT isbn
                FROM COMMANDE NATURAL JOIN DETAILCOMMANDE
                WHERE idcli = ?
            ),
            LivresAutresClients AS (
                SELECT idcli, isbn
                FROM PANIER NATURAL JOIN DETAILPANIER
                WHERE idcli != ?
                UNION
                SELECT idcli, isbn
                FROM COMMANDE NATURAL JOIN DETAILCOMMANDE
                WHERE idcli != ?
            )
            SELECT DISTINCT idcli
            FROM LivresAutresClients
            WHERE isbn IN (
                SELECT isbn
                FROM LivresClientA
            );  
        """);
        for (int i = 1; i < 5; i++) {
            statement.setInt(i, clientId);
        }

        List<Client> clients = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int idcli = result.getInt("idcli");
            Client client = this.obtenirClientParId(idcli);
            clients.add(client);
        }
        result.close();

        return clients;
    }

    public Client obtenirClientParId(int idClient) throws SQLException {
        PreparedStatement clientStatement = this.connexionMariaDB.prepareStatement("""
            SELECT nomcli, prenomcli, adressecli, codepostal, villecli, idmag, nommag, villemag
            FROM CLIENT NATURAL JOIN MAGASIN
            WHERE idcli = ?;
        """);
        clientStatement.setInt(1, idClient);

        // Client & Magasin

        ResultSet result = clientStatement.executeQuery();

		boolean hasElement = result.next();
        if (!hasElement) throw new SQLException("Client non trouvé");

        String nom = result.getString("nomcli");
        String prenom = result.getString("prenomcli");
        String adresse = result.getString("adressecli");
        String codepostal = result.getString("codepostal");
        String villecli = result.getString("villecli");

        String idmag = result.getString("idmag");
        String nommag = result.getString("nommag");
        String villemag = result.getString("villemag");

        Magasin magasinClient = new Magasin(idmag, nommag, villemag);

        result.close();

        // Commandes & Panier

        List<Commande> listeCommandes = this.obtenirCommandesClient(idClient);
        
        Panier panier = this.obtenirPanierClient(idClient);
        if (panier == null) panier = new Panier(magasinClient, new ArrayList<>());

        return new Client(idClient, nom, prenom, adresse, codepostal, villecli, magasinClient, listeCommandes, panier);
    }

    public List<Commande> obtenirCommandesClient(int idClient) throws SQLException {
        PreparedStatement commandesStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idmag, nommag, villemag, numcom, datecom, enligne, livraison, numlig, qte, prixvente, isbn
            FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE
            WHERE idcli = ?
            ORDER BY numcom, numlig;
        """);
        commandesStatement.setInt(1, idClient);

        ResultSet result = commandesStatement.executeQuery();

        Integer numCom = null;
        Date datecom  = null;
        Character enligne = null;
        Character livraison = null;
        Magasin magasin = null;

        List<Commande> listeCommandes = new ArrayList<>();
        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        while (result.next()) {
            Integer curNumCom = result.getInt("numcom");
            if (!curNumCom.equals(numCom)) {
                if (numCom != null) {
                    listeCommandes.add(new Commande(numCom, datecom, enligne, livraison, magasin, listeDetailLivres));
                    listeDetailLivres = new ArrayList<>();
                }

                numCom = result.getInt("numcom");
                datecom  = result.getDate("datecom");
                enligne = result.getString("enligne").charAt(0);
                livraison = result.getString("livraison").charAt(0);

                String idmagCom = result.getString("idmag");
                String nomMagCom = result.getString("nommag");
                String villeMagCom = result.getString("villemag");

                magasin = new Magasin(idmagCom, nomMagCom, villeMagCom);
            }

            // Livre

            String isbn = result.getString("isbn");
            Livre livre = this.chaineLibrairie.getLivreBD().obtenirLivre(isbn);

            int numLigne = result.getInt("numlig");
            int quantite = result.getInt("qte");
            double prixVente = result.getDouble("prixvente");

            listeDetailLivres.add(new DetailLivre(livre, numLigne, quantite, prixVente));
        }
        result.close();

        // Dernière commande
        if (numCom != null) listeCommandes.add(new Commande(numCom, datecom, enligne, livraison, magasin, listeDetailLivres));

        return listeCommandes;
    }

    public Panier obtenirPanierClient(int idClient) throws SQLException {
        PreparedStatement panierStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idmag, nommag, villemag, numlig, qte, prixvente, isbn
            FROM PANIER NATURAL JOIN MAGASIN NATURAL JOIN DETAILPANIER
            WHERE idcli = ?
            ORDER BY numlig;
        """);
        panierStatement.setInt(1, idClient);

        ResultSet result = panierStatement.executeQuery();
        
        boolean hasElements = result.next();
        if (!hasElements) return null;

        // Magasin Panier

        String idmag = result.getString("idmag");
        String nommag = result.getString("nommag");
        String villemag = result.getString("villemag");

        Magasin magasinClient = new Magasin(idmag, nommag, villemag);

        result.previous();

        // Detail Livre

        List<DetailLivre> listeDetailLivres = new ArrayList<>();
        while (result.next()) {
            // Livre
            String isbn = result.getString("isbn");
            Livre livre = this.chaineLibrairie.getLivreBD().obtenirLivre(isbn);

            int numLigne = result.getInt("numlig");
            int quantite = result.getInt("qte");
            double prixVente = result.getDouble("prixvente");

            listeDetailLivres.add(new DetailLivre(livre, numLigne, quantite, prixVente));
        }
        result.close();
        
        return new Panier(magasinClient, listeDetailLivres);
    }
}

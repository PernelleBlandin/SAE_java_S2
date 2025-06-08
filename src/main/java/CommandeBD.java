import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Liaison entre les commandes et la base de données. */
public class CommandeBD {
    private ChaineLibrairie chaineLibrairie;
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe CommandeBD.
     * @param chaineLibrairie Les données de la chaîne de librairie.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public CommandeBD(ChaineLibrairie chaineLibrairie, ConnexionMariaDB connexionMariaDB) {
        this.chaineLibrairie = chaineLibrairie;
        this.connexionMariaDB = connexionMariaDB;
    }

    /**
     * Enregister une commande dans la base de données.
     * @param client Le client de la commande.
     * @param commande La commande.
     * @throws SQLException Exception SQL en cas de problème avec la base de données.
     */
    public void enregistrerCommande(Client client, Commande commande) throws SQLException {
        // Commande
        PreparedStatement commandePreparedStatement = this.connexionMariaDB.prepareStatement("INSERT INTO COMMANDE (numcom, datecom, enligne, livraison, idcli, idmag) VALUES (?, ?, ?, ?, ?, ?)");
        commandePreparedStatement.setInt(1, commande.getId());
        commandePreparedStatement.setDate(2, commande.getDate());
        commandePreparedStatement.setString(3, commande.estEnLigne() ? "O" : "N");
        commandePreparedStatement.setString(4, commande.estEnLivraison() ? "C" : "M");
        commandePreparedStatement.setInt(5, client.getId());
        commandePreparedStatement.setString(6, commande.getMagasin().getId());

        commandePreparedStatement.executeUpdate();

        // DetailCommande
        List<DetailLivre> detailCommandes = commande.getDetailCommandes();
        for (DetailLivre detailCommande: detailCommandes) {
            PreparedStatement detailCommandStatement = this.connexionMariaDB.prepareStatement("INSERT INTO DETAILCOMMANDE(numcom, numlig, qte, prixvente, isbn) VALUES (?, ?, ?, ?, ?);");
            detailCommandStatement.setInt(1, commande.getId());
            detailCommandStatement.setInt(2, detailCommande.getNumLigne());
            detailCommandStatement.setInt(3, detailCommande.getQuantite());
            detailCommandStatement.setDouble(4, detailCommande.getPrixVente());
            detailCommandStatement.setString(5, detailCommande.getLivre().getISBN());

            detailCommandStatement.executeUpdate();
        }
    }

    /**
     * Obtenir le plus grand identifiant de commande.
     * @return Le plus grand identifiant de commande.
     * @throws SQLException Exception SQL en cas de problème.
     */
    public int getMaxCommandeId() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
		ResultSet result = statement.executeQuery("""
            SELECT IFNULL(MAX(numcom), 0) maxNumCom
            FROM COMMANDE
        """);

		result.next();
		int maxNumCom = result.getInt("maxNumCom");
		result.close();

		return maxNumCom;
    }

    /**
     * Obtenir la liste des commandes d'un client.
     * @param idClient L'identifiant du client.
     * @return La liste de ses commandes.
     * @throws SQLException Exception SQL en cas de problème avec la BD.
     */
    public List<Commande> obtenirCommandesClient(int idClient) throws SQLException {
        PreparedStatement commandesStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idmag, nommag, villemag, numcom, datecom, enligne, livraison, numlig, qte, prixvente, isbn
            FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE
            WHERE idcli = ?
            ORDER BY numcom DESC, numlig;
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

    /**
     * Obtenir l'iterateur des commandes pour un mois et une année.
     * @param mois Un mois.
     * @param annee Une année.
     * @return L'iterateur des commandes pour le mois et l'année donnée.
     * @throws SQLException Exception SQL en cas de problème avec la BD.
     */
    public ResultSet getCommandesIterator(int mois, int annee) throws SQLException {
        PreparedStatement commandesStatement = this.connexionMariaDB.prepareStatement("""
            SELECT idcli, nomcli, prenomcli, adressecli, codepostal, villecli, numcom, datecom, qte, prixvente, isbn, titre, nommag
            FROM CLIENT NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN LIVRE NATURAL JOIN MAGASIN
            WHERE MONTH(datecom) = ? AND YEAR(datecom) = ?
            ORDER BY idmag, numcom
        """);
        commandesStatement.setInt(1, mois);
        commandesStatement.setInt(2, annee);

        return commandesStatement.executeQuery();
    }
}

package modele;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/** Liaison entre les statistiques et la base de données. */
public class StatistiquesBD {
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe StatistiquesBD.
     * 
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public StatistiquesBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    /**
     * Obtenir le nombre de livres par magasin et par an.
     * 
     * @return Un dictionnaire avec comme clé le nom du magasin et comme valeur un
     *         autre dictionnaire avec l'année en clé et comme valeur le nombre de
     *         livres.
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Map<Integer, Integer>> getNbLivresParMagasinParAn() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT nommag Magasin, YEAR(datecom) Année, SUM(qte) qte
            FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE
            GROUP BY nommag, YEAR(datecom);
        """);

        Map<String, Map<Integer, Integer>> statistiques = new HashMap<>();
        while (resultSet.next()) {
            String magasin = resultSet.getString("Magasin");
            int annee = resultSet.getInt("Année");
            int quantite = resultSet.getInt("qte");

            statistiques.putIfAbsent(magasin, new HashMap<>());
            statistiques.get(magasin).put(annee, quantite);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir le chiffre d'affaire 2024 par thème.
     * @return Un dictionnaire avec comme clé le thème et comme valeur le CA.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Double> getCA2024ParTheme() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            WITH ThemeMontant AS (
                SELECT LEFT(iddewey, 1) ThemeId, SUM(prixvente*qte) Montant
                FROM CLASSIFICATION NATURAL JOIN THEMES NATURAL JOIN LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE
                WHERE YEAR(datecom) = 2024
                GROUP BY ThemeId
            ),
            ThemesPrincipaux AS (
                SELECT LEFT(iddewey, 1) ThemeId, nomclass Theme
                FROM CLASSIFICATION
                WHERE MOD(iddewey, 100) = 0
            )
            SELECT Theme, Montant
            FROM ThemeMontant NATURAL JOIN ThemesPrincipaux
            ORDER BY Theme;
        """);

        Map<String, Double> statistiques = new HashMap<>();
        while (resultSet.next()) {
            String theme = resultSet.getString("Theme");
            double montant = resultSet.getDouble("Montant");

            statistiques.put(theme, montant);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir l'évolution du CA des magasins par mois en 2024.
     * @return Un dictionnaire avec comme clé le nom du magasin et comme valeur un autre dictionnaire ayant comme clé le mois et valeur le CA.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Map<Integer, Double>> getEvolutionCAParMoisParMagasin2024() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT MONTH(dateCom) mois, nomMag Magasin, SUM(prixvente*qte) CA
            FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE
            WHERE YEAR(dateCom) = 2024
            GROUP BY mois, Magasin;
        """);

        Map<String, Map<Integer, Double>> statistiques = new HashMap<>();
        while (resultSet.next()) {
            int mois = resultSet.getInt("mois");
            String magasin = resultSet.getString("Magasin");
            double chiffreAffaires = resultSet.getInt("CA");

            statistiques.putIfAbsent(magasin, new HashMap<>());
            statistiques.get(magasin).put(mois, chiffreAffaires);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir l'évolution du CA en comparant les ventes en ligne et en magasin.
     * @return Un dictionnaire avec comme clé le type de ventes et comme valeur un autre dictionnaire avec comme clé l'année et comme valeur le CA.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Map<Integer, Double>> getComparaisonVentesLigneMagasin() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT YEAR(dateCom) annee, IF(enligne = 'O', 'En ligne', 'En magasin') typevente, ROUND(SUM(prixvente * qte)) montant
            FROM COMMANDE NATURAL JOIN DETAILCOMMANDE
            WHERE YEAR(dateCom) != 2025
            GROUP BY annee, typevente;
        """);

        Map<String, Map<Integer, Double>> statistiques = new HashMap<>();
        while (resultSet.next()) {
            int annee = resultSet.getInt("annee");
            String typeVente = resultSet.getString("typevente");
            double montant = resultSet.getInt("montant");

            statistiques.putIfAbsent(typeVente, new HashMap<>());
            statistiques.get(typeVente).put(annee, montant);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir les dix éditeurs les plus importants en nombre d'auteurs.
     * @return Un dictionnaire avec comme clé le nom de l'éditeur et comme valeur son nombre d'auteurs.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Integer> getTop10EditeursNbAuteurs() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT nomEdit Editeur, COUNT(DISTINCT idAuteur) nbauteurs
            FROM EDITEUR NATURAL JOIN EDITER NATURAL JOIN LIVRE NATURAL JOIN ECRIRE
            GROUP BY nomEdit
            ORDER BY nbauteurs DESC
            LIMIT 10;
        """);

        Map<String, Integer> statistiques = new HashMap<>();
        while (resultSet.next()) {
            String editeur = resultSet.getString("Editeur");
            int nbauteurs = resultSet.getInt("nbauteurs");

            statistiques.put(editeur, nbauteurs);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir la quantité de livres de René Goscinny achetés en fonction de l'origine des clients.
     * @return Un dictionnaire avec comme clé la ville d'achat et comme valeur la quantité achetée.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Integer> getQteLivresGoscinyOrigineClients() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT villecli ville, SUM(qte) qte
            FROM CLIENT NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN LIVRE NATURAL JOIN ECRIRE NATURAL JOIN AUTEUR
            WHERE nomauteur = 'René Goscinny'
            GROUP BY villecli;
        """);

        Map<String, Integer> statistiques = new HashMap<>();
        while (resultSet.next()) {
            String ville = resultSet.getString("ville");
            int qte = resultSet.getInt("qte");

            statistiques.put(ville, qte);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir la valeur du stock par magasin.
     * @return Un dictionnaire avec comme clé le nom du magasin et comme valeur la valeur du stock de ce dernier.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<String, Double> getValeurStockParMagasin() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            SELECT nomMag Magasin, SUM(qte*prix) total
            FROM MAGASIN NATURAL JOIN POSSEDER NATURAL JOIN LIVRE
            GROUP BY nomMag;
        """);

        Map<String, Double> statistiques = new HashMap<>();
        while (resultSet.next()) {
            String magasin = resultSet.getString("Magasin");
            double total = resultSet.getDouble("total");

            statistiques.put(magasin, total);
        }
        resultSet.close();

        return statistiques;
    }

    /**
     * Obtenir l'évolution du CA Total par client.
     * @return Un dictionnaire avec comme clé l'année et comme valeur un dictionnaire ayant comme clé le type (maximum/minimum/moyenne) et comme valeur le résultat correspondant.
     * 
     * @throws SQLException Exception SQL en cas d'erreur avec la base de données.
     */
    public Map<Integer, Map<String, Double>> getEvolutionCATotalParClient() throws SQLException {
        Statement statement = this.connexionMariaDB.createStatement();
        ResultSet resultSet = statement.executeQuery("""
            WITH CAClientAnnee AS (
                SELECT YEAR(dateCom) annee, idcli, SUM(qte*prixvente) CA
                FROM COMMANDE NATURAL JOIN DETAILCOMMANDE
                GROUP BY annee, idcli
            )
            SELECT annee, MAX(CA) maximum, MIN(CA) minimum, ROUND(AVG(CA), 2) moyenne
            FROM CAClientAnnee
            GROUP BY annee; 
        """);

        Map<Integer, Map<String, Double>> statistiques = new HashMap<>();
        while (resultSet.next()) {
            int annee = resultSet.getInt("annee");

            Map<String, Double> stats = new HashMap<>();
            stats.put("maximum", resultSet.getDouble("maximum"));
            stats.put("minimum", resultSet.getDouble("minimum"));
            stats.put("moyenne", resultSet.getDouble("moyenne"));
            
            statistiques.put(annee, stats);
        }
        resultSet.close();

        return statistiques;
    }
}

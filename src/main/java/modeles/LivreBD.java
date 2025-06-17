package modeles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Liaison entre les livres et la base de données. */
public class LivreBD {
    private ConnexionMariaDB connexionMariaDB;

    /**
     * Instancier la classe LivreBD.
     * @param connexionMariaDB La connexion avec la base de données.
     */
    public LivreBD(ConnexionMariaDB connexionMariaDB) {
        this.connexionMariaDB = connexionMariaDB;
    }

    /**
     * Obtenir la liste des livres de la chaîne de librairie.
     * @return La liste des livres de la chaîne de librairie.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
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

    /**
     * Obtenir la liste des livres en stock dans un magasin spécifique.
     * @param magasin Un magasin de la chaîne de librairie.
     * @return La liste des livres du magasin.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Livre> obtenirLivreEnStockMagasin(Magasin magasin) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT isbn
            FROM POSSEDER
            WHERE idmag = ? AND qte > 0;
        """);
        statement.setString(1, magasin.getId());

        ResultSet result = statement.executeQuery();
        
        List<Livre> livres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            Livre livre = this.obtenirLivre(isbn);
            livres.add(livre);
        }
        result.close();

        return livres;
    }

    /**
     * Obtenir la liste des livres en stock dans un magasin spécifique même si la quantite disponible est de 0.
     * @param magasin Un magasin de la chaîne de librairie.
     * @return La liste des livres du magasin.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Livre> obtenirLivreDejaEnStockMagasin(Magasin magasin) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT isbn
            FROM POSSEDER
            WHERE idmag = ?;
        """);
        statement.setString(1, magasin.getId());

        ResultSet result = statement.executeQuery();

        List<Livre> livres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            Livre livre = this.obtenirLivre(isbn);
            livres.add(livre);
        }
        result.close();
        return livres;
    }

    /**
     * Obtenir les meilleures ventes nationales.
     * @param nbLivres Le nombre de livres retournés.
     * @return Les meilleures livres vendus.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public List<Livre> obtenirLivresMeilleuresVentes(int nbLivres) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT isbn, COUNT(numcom) nbVentes
            FROM LIVRE NATURAL JOIN DETAILCOMMANDE
            GROUP BY isbn
            ORDER BY nbVentes DESC
            LIMIT ?;
        """);
        statement.setInt(1, nbLivres);

        ResultSet result = statement.executeQuery();

        List<Livre> livres = new ArrayList<>();
        while (result.next()) {
            String isbn = result.getString("isbn");
            Livre livre = this.obtenirLivre(isbn);
            livres.add(livre);
        }
        result.close();
        return livres;
    }

    /**
     * Obtenir le nombre de ventes d'un livre.
     * @param isbn Son ISBN.
     * @return Son nombre de ventes.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
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

    /**
     * Obtenir l'ID d'un auteur à partir de son nom.
     * @param nomAuteur Le nom de l'auteur.
     * @return L'ID de l'auteur ou null s'il n'existe pas.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public String getIdAuteur(String nomAuteur) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT idauteur
            FROM AUTEUR
            WHERE nomauteur = ?
        """);
        statement.setString(1, nomAuteur);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getString("idauteur");
        }
        return null;
    }

    /**
     * Obtenir l'ID de classification Dewey à partir du nom de la classification.
     * @param nomClass Le nom de la classification.
     * @return L'ID de classification Dewey ou null s'il n'existe pas.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public String getIdDewey(String nomClass) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT iddewey
            FROM CLASSIFICATION
            WHERE nomclass = ?
        """);
        statement.setString(1, nomClass);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getString("iddewey");
        }
        return null;
    }

    /**
     * Obtenir l'ID de l'éditeur à partir du nom de l'éditeur.
     * Si l'éditeur n'existe pas, il est ajouté à la base de données.
     * @param nomEditeur Le nom de l'éditeur.
     * @return L'ID de l'éditeur.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public String getIdEditeur(String nomEditeur) throws SQLException {
        PreparedStatement statement = this.connexionMariaDB.prepareStatement("""
            SELECT idedit
            FROM EDITEUR
            WHERE nomedit = ?
        """);
        statement.setString(1, nomEditeur);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return res.getString("idedit");
        } else {
            Statement statement2 = this.connexionMariaDB.createStatement();
            ResultSet res2 = statement2.executeQuery("SELECT MAX(idedit) FROM EDITEUR");
            res2.next();
            String dernierId = res2.getString(1);
            String nouvelId = String.valueOf(Integer.parseInt(dernierId) + 1);

            PreparedStatement statement3 = this.connexionMariaDB.prepareStatement("""
                INSERT INTO EDITEUR(idedit, nomedit)
                VALUES (?, ?)
            """);
            statement3.setString(1, nouvelId);
            statement3.setString(2, nomEditeur);
            statement3.executeUpdate();

            return nouvelId;
        }
    }

    /**
     * Ajouter un livre à la base de données avec un auteur et une classification donnés par l'utilisateur.
     * l'auteur et la classification sont ajoutés à la base de donnés.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     * @param nbpages Le nombre de pages du livre.
     * @param datepubli La date de publication du livre.
     * @param prix Le prix du livre.
     * @param nomAuteur Le nom de l'auteur du livre.
     * @param nomClass Le nom de la classification du livre.
     * @param nomEditeur Le nom de l'éditeur du livre.
     * @param idAuteur L'ID de l'auteur.
     * @param idDewey L'ID de la classification Dewey.
     * @param anneeNais L'année de naissance de l'auteur.
     * @param anneeDeces L'année de décès de l'auteur.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void ajouteLivreAuteurNonExistantClassificationNonExistante(String isbn, String titre, int nbpages, int datepubli, double prix, String nomAuteur, String nomClass, String nomEditeur, String idAuteur, String idDewey, int anneeNais, int anneeDeces) throws SQLException {
        String idEdit = this.getIdEditeur(nomEditeur);
        PreparedStatement statementAuteur = this.connexionMariaDB.prepareStatement("""
            INSERT INTO AUTEUR(idauteur, nomauteur, anneenais, anneedeces)
            VALUES (?, ?, ?, ?)
        """);
        statementAuteur.setString(1, idAuteur);
        statementAuteur.setString(2, nomAuteur);
        statementAuteur.setInt(3, anneeNais);
        if (anneeDeces == -1) {
            statementAuteur.setNull(4, java.sql.Types.INTEGER);
        } else {
            statementAuteur.setInt(4, anneeDeces);
        }
        statementAuteur.executeUpdate();

        PreparedStatement statementClass = this.connexionMariaDB.prepareStatement("""
            INSERT INTO CLASSIFICATION(iddewey, nomclass)
            VALUES (?, ?)
        """);
        statementClass.setString(1, idDewey);
        statementClass.setString(2, nomClass);
        statementClass.executeUpdate();

        PreparedStatement statementLivre = this.connexionMariaDB.prepareStatement("""
            INSERT INTO LIVRE(isbn, titre, nbpages, datepubli, prix)
            VALUES (?, ?, ?, ?, ?)
        """);
        statementLivre.setString(1, isbn);
        statementLivre.setString(2, titre);
        statementLivre.setInt(3, nbpages);
        statementLivre.setInt(4, datepubli);
        statementLivre.setDouble(5, prix);
        statementLivre.executeUpdate();

        PreparedStatement statementEcrire = this.connexionMariaDB.prepareStatement("""
            INSERT INTO ECRIRE(isbn, idauteur)
            VALUES (?, ?)
        """);
        statementEcrire.setString(1, isbn);
        statementEcrire.setString(2, idAuteur);
        statementEcrire.executeUpdate();

        PreparedStatement statementTheme = this.connexionMariaDB.prepareStatement("""
            INSERT INTO THEMES(isbn, iddewey)
            VALUES (?, ?)
        """);
        statementTheme.setString(1, isbn);
        statementTheme.setString(2, idDewey);
        statementTheme.executeUpdate();

        PreparedStatement statementEditer = this.connexionMariaDB.prepareStatement("""
            INSERT INTO EDITER(isbn, idedit)
            VALUES (?, ?)
        """);
        statementEditer.setString(1, isbn);
        statementEditer.setString(2, idEdit);
        statementEditer.executeUpdate();
    }

    /**
     * Ajouter un livre à la base de données avec un auteur existant et une classification donnée par l'utilisateur.
     * l'auteur est déjà dans la base de donnée et la classification y est ajoutée.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     * @param nbpages Le nombre de pages du livre.
     * @param datepubli La date de publication du livre.
     * @param prix Le prix du livre.
     * @param nomAuteur Le nom de l'auteur du livre.
     * @param nomClass Le nom de la classification du livre.
     * @param nomEditeur Le nom de l'éditeur du livre.
     * @param idDewey L'ID de la classification Dewey.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void ajouteLivreAuteurExistantClassificationNonExistante(String isbn, String titre, int nbpages, int datepubli, double prix, String nomAuteur, String nomClass, String nomEditeur, String idDewey) throws SQLException {
        String idEdit = this.getIdEditeur(nomEditeur);
        PreparedStatement statementClass = this.connexionMariaDB.prepareStatement("""
            INSERT INTO CLASSIFICATION(iddewey, nomclass)
            VALUES (?, ?)
        """);
        statementClass.setString(1, idDewey);
        statementClass.setString(2, nomClass);
        statementClass.executeUpdate();

        PreparedStatement statementLivre = this.connexionMariaDB.prepareStatement("""
            INSERT INTO LIVRE(isbn, titre, nbpages, datepubli, prix)
            VALUES (?, ?, ?, ?, ?)
        """);
        statementLivre.setString(1, isbn);
        statementLivre.setString(2, titre);
        statementLivre.setInt(3, nbpages);
        statementLivre.setInt(4, datepubli);
        statementLivre.setDouble(5, prix);
        statementLivre.executeUpdate();

        String idAuteur = this.getIdAuteur(nomAuteur);

        PreparedStatement statementEcrire = this.connexionMariaDB.prepareStatement("""
            INSERT INTO ECRIRE(isbn, idauteur)
            VALUES (?, ?)
        """);
        statementEcrire.setString(1, isbn);
        statementEcrire.setString(2, idAuteur);
        statementEcrire.executeUpdate();

        PreparedStatement statementTheme = this.connexionMariaDB.prepareStatement("""
            INSERT INTO THEMES(isbn, iddewey)
            VALUES (?, ?)
        """);
        statementTheme.setString(1, isbn);
        statementTheme.setString(2, idDewey);
        statementTheme.executeUpdate();

        PreparedStatement statementEditer = this.connexionMariaDB.prepareStatement("""
            INSERT INTO EDITER(isbn, idedit)
            VALUES (?, ?)
        """);
        statementEditer.setString(1, isbn);
        statementEditer.setString(2, idEdit);
        statementEditer.executeUpdate();
    }

    /**
     * Ajouter un livre à la base de données avec un auteur donné par l'utilisateur et une classification existant.
     * l'auteur est ajouté dans la base de donnée et la classification y est déjà.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     * @param nbpages Le nombre de pages du livre.
     * @param datepubli La date de publication du livre.
     * @param prix Le prix du livre.
     * @param nomAuteur Le nom de l'auteur du livre.
     * @param nomClass Le nom de la classification du livre.
     * @param nomEditeur Le nom de l'éditeur du livre.
     * @param idAuteur L'ID de l'auteur.
     * @param anneeNais L'année de naissance de l'auteur.
     * @param anneeDeces L'année de décès de l'auteur.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void ajouteLivreAuteurNonExistantClassificationExistante(String isbn, String titre, int nbpages, int datepubli, double prix, String nomAuteur, String nomClass, String nomEditeur, String idAuteur, int anneeNais, int anneeDeces) throws SQLException {
        String idEdit = this.getIdEditeur(nomEditeur);
        PreparedStatement statementAuteur = this.connexionMariaDB.prepareStatement("""
            INSERT INTO AUTEUR(idauteur, nomauteur, anneenais, anneedeces)
            VALUES (?, ?, ?, ?)
        """);
        statementAuteur.setString(1, idAuteur);
        statementAuteur.setString(2, nomAuteur);
        statementAuteur.setInt(3, anneeNais);
        if (anneeDeces == -1) {
            statementAuteur.setNull(4, java.sql.Types.INTEGER); // Si l'année de décès donné par l'utilisateur est -1 on met NULL grace a java.sql.Types.INTEGER
        } else {
            statementAuteur.setInt(4, anneeDeces);
        }
        statementAuteur.executeUpdate();

        String idDewey = this.getIdDewey(nomClass);

        PreparedStatement statementLivre = this.connexionMariaDB.prepareStatement("""
            INSERT INTO LIVRE(isbn, titre, nbpages, datepubli, prix)
            VALUES (?, ?, ?, ?, ?)
        """);
        statementLivre.setString(1, isbn);
        statementLivre.setString(2, titre);
        statementLivre.setInt(3, nbpages);
        statementLivre.setInt(4, datepubli);
        statementLivre.setDouble(5, prix);
        statementLivre.executeUpdate();

        PreparedStatement statementEcrire = this.connexionMariaDB.prepareStatement("""
            INSERT INTO ECRIRE(isbn, idauteur)
            VALUES (?, ?)
        """);
        statementEcrire.setString(1, isbn);
        statementEcrire.setString(2, idAuteur);
        statementEcrire.executeUpdate();

        PreparedStatement statementTheme = this.connexionMariaDB.prepareStatement("""
            INSERT INTO THEMES(isbn, iddewey)
            VALUES (?, ?)
        """);
        statementTheme.setString(1, isbn);
        statementTheme.setString(2, idDewey);
        statementTheme.executeUpdate();

        PreparedStatement statementEditer = this.connexionMariaDB.prepareStatement("""
            INSERT INTO EDITER(isbn, idedit)
            VALUES (?, ?)
        """);
        statementEditer.setString(1, isbn);
        statementEditer.setString(2, idEdit);
        statementEditer.executeUpdate();
    }

    /**
     * Ajouter un livre à la base de données avec un auteur et une classification déjà existants.
     * l'auteur et la classification sont déjà dans la base de donnéee.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     * @param nbpages Le nombre de pages du livre.
     * @param datepubli La date de publication du livre.
     * @param prix Le prix du livre.
     * @param nomAuteur Le nom de l'auteur du livre.
     * @param nomClass Le nom de la classification du livre.
     * @param nomEditeur Le nom de l'éditeur du livre.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void ajouteLivreAuteurExistantClassificationExistante(String isbn, String titre, int nbpages, int datepubli, double prix, String nomAuteur, String nomClass, String nomEditeur) throws SQLException {
        String idEdit = this.getIdEditeur(nomEditeur);
        PreparedStatement statementLivre = this.connexionMariaDB.prepareStatement("""
            INSERT INTO LIVRE(isbn, titre, nbpages, datepubli, prix)
            VALUES (?, ?, ?, ?, ?)
        """);
        statementLivre.setString(1, isbn);
        statementLivre.setString(2, titre);
        statementLivre.setInt(3, nbpages);
        statementLivre.setInt(4, datepubli);
        statementLivre.setDouble(5, prix);
        statementLivre.executeUpdate();

        String idAuteur = this.getIdAuteur(nomAuteur);
        String idDewey = this.getIdDewey(nomClass);

        PreparedStatement statementEcrire = this.connexionMariaDB.prepareStatement("""
            INSERT INTO ECRIRE(isbn, idauteur)
            VALUES (?, ?)
        """);
        statementEcrire.setString(1, isbn);
        statementEcrire.setString(2, idAuteur);
        statementEcrire.executeUpdate();

        PreparedStatement statementTheme = this.connexionMariaDB.prepareStatement("""
            INSERT INTO THEMES(isbn, iddewey)
            VALUES (?, ?)
        """);
        statementTheme.setString(1, isbn);
        statementTheme.setString(2, idDewey);
        statementTheme.executeUpdate();

        PreparedStatement statementEditer = this.connexionMariaDB.prepareStatement("""
            INSERT INTO EDITER(isbn, idedit)
            VALUES (?, ?)
        """);
        statementEditer.setString(1, isbn);
        statementEditer.setString(2, idEdit);
        statementEditer.executeUpdate();
    }

    /**
     * Obtenir l'instance d'un livre.
     * @param isbn Son ISBN.
     * @return L'instance du livre demandé.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
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
    /**
     * Transfert une certaine quantité d'un livre du magasin initial vers un autre magasin 
     * @param livre Le livre qui sera transféré
     * @param magSource Le magasin source.
     * @param magDestination Le magasin destination.
     * @param qte La quantité à transférer.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void transfertLivre(Livre livre, Magasin magSource, Magasin magDestination, int qte) throws SQLException {
    
    PreparedStatement stockSource = this.connexionMariaDB.prepareStatement("SELECT qte FROM POSSEDER where idmag = ? AND isbn = ?");
    stockSource.setString(1,magSource.getId());
    stockSource.setString(2,livre.getISBN());
    ResultSet rs=stockSource.executeQuery();

    if (!rs.next()||rs.getInt("qte") < qte) {
        rs.close();
        stockSource.close();
        throw new SQLException("Stock insuffisant dans le magasin source");
    }
    rs.close();
    stockSource.close();

    PreparedStatement diminuerStockSource = this.connexionMariaDB.prepareStatement("UPDATE POSSEDER SET qte = qte - ? where idmag = ? AND isbn = ?");
    diminuerStockSource.setInt(1,qte);
    diminuerStockSource.setString(2,magSource.getId());
    diminuerStockSource.setString(3, livre.getISBN());
    diminuerStockSource.executeUpdate();
    diminuerStockSource.close();

    PreparedStatement stockDest = this.connexionMariaDB.prepareStatement("SELECT qte FROM POSSEDER WHERE idmag = ? AND isbn = ?");
    stockDest.setString(1,magDestination.getId());
    stockDest.setString(2,livre.getISBN());
    ResultSet rs2 = stockDest.executeQuery();

    if (rs2.next()) {
        PreparedStatement augmenterStockDest = this.connexionMariaDB.prepareStatement("UPDATE POSSEDER SET qte = qte + ? WHERE idmag = ? AND isbn = ?");
        augmenterStockDest.setInt(1,qte);
        augmenterStockDest.setString(2,magDestination.getId());
        augmenterStockDest.setString(3, livre.getISBN());
        augmenterStockDest.executeUpdate();
        augmenterStockDest.close();
    } else {
        PreparedStatement ajoutLivreDest = this.connexionMariaDB.prepareStatement("INSERT INTO POSSEDER (idmag, isbn, qte)values (?, ?, ?)");
        ajoutLivreDest.setString(1,magDestination.getId());
        ajoutLivreDest.setString(2,livre.getISBN());
        ajoutLivreDest.setInt(3, qte);
        ajoutLivreDest.executeUpdate();
        ajoutLivreDest.close();
    }
    rs2.close();
    stockDest.close();
}
        
    /**
     * Supprime un livre de la base de données.
     * Supprime aussi les entrées associées dans les tables ECRIRE, EDITER, POSSEDER, DETAILCOMMANDE, DETAILPANIER et THEMES.
     * @param isbn L'ISBN du livre à supprimer.
     * @throws SQLException exception SQL en cas d'erreur.
    */
    public void supprimerLivre(String isbn) throws SQLException {
        PreparedStatement ps = connexionMariaDB.prepareStatement("DELETE FROM ECRIRE WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM EDITER WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM POSSEDER WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM DETAILCOMMANDE WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM DETAILPANIER WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM THEMES WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();

        ps = connexionMariaDB.prepareStatement("DELETE FROM LIVRE WHERE isbn = ?");
        ps.setString(1, isbn);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Modifie le stock d'un livre dans un magasin.
     * @param isbn L'ISBN du livre.
     * @param idMagasin l'ID du magasin dans lequel on fait les modifications.
     * @param nvellQte la nouvelle quantité du livre dans le magasin.
     * @throws SQLException Exception SQL en cas d'erreur.
     */
    public void modifierStockMagasin(String isbn, String idMagasin, int nvellQte) throws SQLException{
        boolean existeDeja=false;
        try(PreparedStatement verifSiExiste= this.connexionMariaDB.prepareStatement(" SELECT qte FROM POSSEDER where idmag=? and isbn= ?")){
            verifSiExiste.setString(1, idMagasin);
            verifSiExiste.setString(2, isbn);
            ResultSet rs= verifSiExiste.executeQuery();
            existeDeja= rs.next();
            rs.close();

        }
        try(PreparedStatement stInsert= this.connexionMariaDB.prepareStatement("INSERT INTO POSSEDER (idmag, isbn, qte) values(?,?,?)")){
            if(!existeDeja){
                stInsert.setString(1, idMagasin);
                stInsert.setString(2, isbn);
                stInsert.setInt(3, nvellQte);
                stInsert.executeUpdate();
            }
            else{
                PreparedStatement stUpdate= this.connexionMariaDB.prepareStatement("UPDATE POSSEDER SET qte=? where idmag = ? and isbn= ?");
                stUpdate.setInt(1, nvellQte);
                stUpdate.setString(2, idMagasin);
                stUpdate.setString(3, isbn);
                stUpdate.executeUpdate();
                stUpdate.close();
            }
        }
    }
}

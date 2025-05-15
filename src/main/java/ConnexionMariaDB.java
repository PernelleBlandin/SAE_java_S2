import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/** Connexion avec la base de données MariaDB */
public class ConnexionMariaDB {
	private Connection mariadb = null;
	private boolean connecte = false;

	/**
	 * Charger le driver MariaDB.
	 * @throws ClassNotFoundException Si le driver n'est pas trouvé.
	 */
	public ConnexionMariaDB() throws ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
	}

	/**
	 * Se connecter à la base de données MariaDB.
	 * @param nomServeur L'adresse/nom du serveur MariaDB.
	 * @param nomBase Le nom de la base de données.
	 * @param nomLogin Le nom d'utilisateur.
	 * @param motDePasse Le mot de passe utilisateur.
	 * @throws SQLException Exception SQL en cas d'erreur.
	 */
	public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
		this.mariadb = DriverManager.getConnection(
			String.format("jdbc:mariadb://%s:3306/%s", nomServeur, nomBase),
			nomLogin, motDePasse
		);

		this.connecte = this.mariadb != null;
	}

	/**
	 * Fermer la connexion avec la base de données.
	 * @throws SQLException Exception SQL en cas d'erreur.
	 */
	public void close() throws SQLException {
		this.mariadb.close();
		this.connecte = false;
	}

	/**
	 * Indique si la classe est connectée avec la base de données.
	 * @return true si elle est connectée avec la base de données, sinon false.
	 */
	public boolean estConnecte() {
		return this.connecte;
	}

	/**
	 * Créer un statement pour une requête.
	 * @return Un statement.
	 * @throws SQLException Exception SQL en cas d'erreur.
	 */
	public Statement createStatement() throws SQLException {
		return this.mariadb.createStatement();
	}

	/**
	 * Créer un statement pour une requête préparée.
	 * @param requete La requête SQL préparée.
	 * @return Un statement d'une requête préparée.
	 * @throws SQLException Exception SQL en cas d'erreur.
	 */
	public PreparedStatement prepareStatement(String requete) throws SQLException {
		return this.mariadb.prepareStatement(requete);
	}
}
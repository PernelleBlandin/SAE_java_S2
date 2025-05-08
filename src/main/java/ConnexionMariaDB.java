import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionMariaDB {
	private Connection mariadb = null;
	private boolean connecte = false;

	public ConnexionMariaDB() throws ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
	}

	public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
		this.mariadb = DriverManager.getConnection(
			String.format("jdbc:mariadb://%s:3306/%s", nomServeur, nomBase),
			nomLogin, motDePasse
		);

		this.connecte = this.mariadb != null;
	}

	public void close() throws SQLException {
		this.mariadb.close();
		this.connecte = false;
	}

	public boolean isConnecte() {
		return this.connecte;
	}

	public Statement createStatement() throws SQLException {
		return this.mariadb.createStatement();
	}

	public PreparedStatement prepareStatement(String requete) throws SQLException {
		return this.mariadb.prepareStatement(requete);
	}
}
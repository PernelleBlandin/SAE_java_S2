package vue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import modeles.ChaineLibrairie;
import modeles.Client;
import vue.connection.ConnexionView;
import vue.customers.CustomerHomeView;

/** L'IHM de notre application */
public class AppIHM extends Application {
    /** Le modèle */
    private ChaineLibrairie chaineLibrairie;
    /** Le stage principal */
    private Stage primaryStage;

    @Override
    /**
     * Initialiser l'application IHM.
     */
    public void init() {
        // Obtention des arguments pour la connexion avec la BD
        Map<String, String> arguments = new HashMap<>();
        List<String> args = this.getParameters().getRaw();
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).startsWith("--") && i + 1 < args.size()) {
                String key = args.get(i).substring(2);
                String value = args.get(i + 1);
                arguments.put(key, value);
                i++;
            }
        }
        
        String bdHost = arguments.getOrDefault("bd-host", "servinfo-maria:3306");
        String bdBase = arguments.getOrDefault("bd-base", "DBgautier");
        String bdLogin = arguments.getOrDefault("bd-login", "gautier");
        String bdPassword = arguments.getOrDefault("bd-password", "gautier");

        // Initialisation de la chaîne de librairie
        this.chaineLibrairie = new ChaineLibrairie(bdHost, bdBase, bdLogin, bdPassword);
    }

    /**
     * Changer de scène pour le mode connexion.
     */
    public void showConnexion(){
        ConnexionView vue = new ConnexionView(this);
        this.primaryStage.setScene(vue.getScene());
    }

    /**
     * Changer de scène pour le mode client.
     */
    public void showCustomer(){
        // TODO: Voir pour client
        try {
            Client client = this.chaineLibrairie.getClientBD().obtenirClientParId(1);
            this.chaineLibrairie.setClientActuel(client);
        } catch (SQLException e) {
            System.err.println("Une erreur est survenue lors de la récupréation du client : " + e.getMessage());
            return;
        }

        CustomerHomeView vue = new CustomerHomeView(this, this.chaineLibrairie);
        this.primaryStage.setScene(vue.getScene());
    }

    public void modeVendeur(){
        try {
            Client client = this.chaineLibrairie.getClientBD().obtenirClientParId(1);
            this.chaineLibrairie.setClientActuel(client);
        } catch (SQLException e) {
            System.err.println("Une erreur est survenue lors de la récupréation du client : " + e.getMessage());
            return;
        }
    }

    /**
     * Créer le graphe de scène et lance l'application.
     * @param stage La fenêtre principale.
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Livre Express");

        this.primaryStage.setWidth(1280);
        this.primaryStage.setHeight(720);

        this.showConnexion();
        primaryStage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    } 
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.ChaineLibrairie;
import vue.AppTerminal;

/** L'exécutable de l'application. */
public class ExecutableTerminal {
    /** L'exécutable de l'application. */
    public ExecutableTerminal() {}

    /**
     * Lancer l'application.
     * @param args Les arguments.
     */
    public static void main(String[] args) {
        // Obtention des arguments pour la connexion avec la BD
        Map<String, String> arguments = new HashMap<>();
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        for (int i = 0; i < argsList.size(); i++) {
            if (argsList.get(i).startsWith("--") && i + 1 < args.length) {
                String key = argsList.get(i).substring(2);
                String value = argsList.get(i + 1);
                arguments.put(key, value);
                i++;
            }
        }
        
        String bdHost = arguments.getOrDefault("bd-host", "servinfo-maria:3306");
        String bdBase = arguments.getOrDefault("bd-base", "DBgautier");
        String bdLogin = arguments.getOrDefault("bd-login", "gautier");
        String bdPassword = arguments.getOrDefault("bd-password", "gautier");

        // Initialisation de la chaîne de librairie
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie(bdHost, bdBase, bdLogin, bdPassword);

        AppTerminal app = new AppTerminal(chaineLibrairie);
        app.run();
    }
}

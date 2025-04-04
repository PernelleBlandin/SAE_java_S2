public class App {
    private boolean quitter;
    public App() {
        this.quitter = false;
    }

    public void run() {
        bienvenue();
        menu();
    }

    public void bienvenue() {
        System.out.println("╭──────────────────────────────────────────────────────────────────────────────────────────────────╮");
		System.out.println("│ ██      ██ ██    ██ ██████  ███████     ███████ ██   ██ ██████  ██████  ███████ ███████ ███████  │");
        System.out.println("│ ██      ██ ██    ██ ██   ██ ██          ██       ██ ██  ██   ██ ██   ██ ██      ██      ██       │");
        System.out.println("│ ██      ██ ██    ██ ██████  █████       █████     ███   ██████  ██████  █████   ███████ ███████  │");
        System.out.println("│ ██      ██  ██  ██  ██   ██ ██          ██       ██ ██  ██      ██   ██ ██           ██      ██  │");
        System.out.println("│ ███████ ██   ████   ██   ██ ███████     ███████ ██   ██ ██      ██   ██ ███████ ███████ ███████  │");
		System.out.println("│──────────────────────────────────────────────────────────────────────────────────────────────────│");
        System.out.println("│                             SAE 2.01 : Développement d'une application                           │");
        System.out.println("╰──────────────────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public void menu() {
        System.out.println("╭──────────────────────────────╮");
        System.out.println("│       Choix du compte        │");
        System.out.println("│──────────────────────────────│");
        System.out.println("│ C: Client                    │");
        System.out.println("│ V: Vendeur                   │");
        System.out.println("│ A: Administrateur            │");
        System.out.println("│ Q: Quitter                   │");
        System.out.println("╰──────────────────────────────╯");

        String commandeBrute = System.console().readLine();
        String commande = commandeBrute.strip().toLowerCase();
        switch (commande) {
            case "c": {
                this.connexionClient();
                break;
            }
            case "q":
                break;
            default: {
                System.out.println("ERREUR: Choix invalide, veuillez réessayer...");
                this.menu();
                break;
            }
        }
    }

    public void connexionClient() {
        // TODO: Voir comment on fait ça
        Client client = new Client(1, "DUPONT", "Richard", "1 rue de la place", "45000", "Orléans");
        this.client(client);
    }

    public void client(Client client) {
        System.out.println("╭──────────────────────────────╮");
        System.out.println("│            Client            │");
        System.out.println("│──────────────────────────────│");
        System.out.println("│ C: Commander                 │");
        System.out.println("│ R: Recommandations           │");
        System.out.println("│ L: Consulter le catalogue    │");
        System.out.println("│ Q: Quitter                   │");
        System.out.println("╰──────────────────────────────╯");

        String commandeBrute = System.console().readLine();
        String commande = commandeBrute.strip().toLowerCase();
        switch (commande) {
            case "c": 
                this.client();
            case "q":
                break;
            default: {
                System.out.println("ERREUR: Choix invalide, veuillez réessayer...");
                this.menu();
            }
        }
    }
}

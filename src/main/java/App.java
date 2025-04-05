public class App {
    private ChaineLibrairie chaineLibrairie;

    /**
     * Créer l'application en ligne de commandes.
     * @param chaineLibrairie La chaîne de librairie.
     */
    public App(ChaineLibrairie chaineLibrairie) {
        this.chaineLibrairie = chaineLibrairie;
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
        boolean finCommande = false;
        while (!finCommande) {
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
                case "q": {
                    finCommande = true;
                    break;
                }
                default: {
                    System.out.println("ERREUR: Choix invalide, veuillez réessayer...");
                    this.menu();
                    break;
                }
            }
        }
    }

    public void connexionClient() {
        // TODO: Voir comment on fait ça
        Client client = this.chaineLibrairie.trouverClient("DUPONT", "Richard");
        this.client(client);
    }

    public void client(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            System.out.println("╭──────────────────────────────╮");
            System.out.println("│            Client            │");
            System.out.println("│──────────────────────────────│");
            System.out.println("│ C: Commander                 │");
            System.out.println("│ R: Recommandations           │");
            System.out.println("│ L: Catalogue de livres       │");
            System.out.println("│ Q: Quitter                   │");
            System.out.println("╰──────────────────────────────╯");
    
            String commandeBrute = System.console().readLine();
            String commande = commandeBrute.strip().toLowerCase();
            switch (commande) {
                case "c": 
                    this.client(client);
                    break;
                case "l": {
                    this.catalogueLivres(client);
                    break;
                }
                case "q": {
                    finCommande = true;
                    break;
                }
                default: {
                    System.out.println("ERREUR: Choix invalide, veuillez réessayer...");
                    this.menu();
                }
            }
        }
    }

    public void catalogueLivres(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            System.out.println("╭──────────────────────────────╮");
            System.out.println("│         Catalogue            │");
            System.out.println("│──────────────────────────────│");
            System.out.println("│ L: Liste                     │");
            System.out.println("│ R: Rechercher                │");
            System.out.println("│ Q: Quitter                   │");
            System.out.println("╰──────────────────────────────╯");
    
            String commandeBrute = System.console().readLine();
            String commande = commandeBrute.strip().toLowerCase();
            switch (commande) {
                case "q": {
                    finCommande = true;
                    break;
                }
                default: {
                    System.out.println("ERREUR: Choix invalide, veuillez réessayer...");
                    this.menu();
                }
            }
        }
    }
}

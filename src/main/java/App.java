import java.util.ArrayList;
import java.util.List;

public class App {
    private int longueurAffichage;
    private ChaineLibrairie chaineLibrairie;

    /**
     * Créer l'application en ligne de commandes.
     * @param chaineLibrairie La chaîne de librairie.
     */
    public App(ChaineLibrairie chaineLibrairie) {
        this.longueurAffichage = 100;
        this.chaineLibrairie = chaineLibrairie;
    }

    public void run() {
        this.bienvenue();
        this.menu();
    }

    public void afficherTitre(String titre) {
        int margeDebut = (this.longueurAffichage - 4 - titre.length()) / 2;
        int margeFin = margeDebut;
        if (titre.length() % 2 != 0) margeFin++;

        System.out.println("╭" + "─".repeat(this.longueurAffichage - 2) + "╮");
        System.out.println("│ " + String.format("%" + margeDebut + "s%s%" + margeFin + "s", "", titre, "") + " │");
        this.afficherSeperateurMilieu();
    }

    public void afficherTexte(String texte) {
        System.out.println(String.format("| %-" + (this.longueurAffichage - 4) + "s |", texte));
    }

    public void afficherSeperateurMilieu() {
        System.out.println("│" + "─".repeat(this.longueurAffichage - 2) + "│");
    }

    public void afficherTitreFin() {
        System.out.println("╰" + "─".repeat(this.longueurAffichage - 2) + "╯");
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
            this.afficherTitre("Choix du compte");
            this.afficherTexte("C: Client");
            this.afficherTexte("V: Vendeur");
            this.afficherTexte("A: Administrateur");
            this.afficherTexte("Q: Quitter");
            this.afficherTitreFin();
    
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
                    System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
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
            this.afficherTitre("Client");
            this.afficherTexte("P: Panier client");
            this.afficherTexte("R: Recommandations");
            this.afficherTexte("L: Consulter le catalogue de livres");
            this.afficherTexte("S: Rechercher livres");
            this.afficherTexte("Q: Quitter");
            this.afficherTitreFin();
    
            String commandeBrute = System.console().readLine();
            String commande = commandeBrute.strip().toLowerCase();
            switch (commande) {
                case "l": {
                    this.selectionnerLivre(client, this.chaineLibrairie.getLivres());
                    break;
                }
                case "q": {
                    finCommande = true;
                    break;
                }
                default: {
                    System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                    break;
                }
            }
        }
    }

    public void selectionnerLivre(Client client, List<Livre> livres) {
        this.selectionnerLivre(client, livres, 0);
    }

    public void selectionnerLivre(Client client, List<Livre> livres, int nbPage) {
        boolean finCommande = false;
        while (!finCommande) {
            int maxLivresParPage = 10;
            int totalPages = livres.size() / maxLivresParPage;
            this.afficherTitre(String.format("Catalogue des livres - page n°%d sur %d", nbPage + 1, totalPages + 1));
            for (int i = nbPage * maxLivresParPage; i < livres.size() && i < maxLivresParPage * (nbPage + 1); i++) {
                Livre livre = livres.get(i);
                this.afficherTexte(String.format("%d - %s", i + 1, livre.toString()));
            }
    
            this.afficherSeperateurMilieu();
            this.afficherTexte("Nombre: Sélection d'un livre dans la liste");
            if (nbPage > 0) this.afficherTexte("P: Page précédente");
            if (nbPage < totalPages) this.afficherTexte("S: Page suivante");
            this.afficherTexte("Q: Quitter");
            this.afficherTitreFin();

            String commandeBrute = System.console().readLine();
            String commande = commandeBrute.strip().toLowerCase();
            switch (commande) {
                case "p": {
                    if (nbPage > 0) nbPage--;
                    break;
                }
                case "s": {
                    if (nbPage + 1 <= totalPages) nbPage++;
                    break;
                }
                case "q": {
                    finCommande = true;
                    break;
                }
                default: {
                    try {
                        int choixNumero = Integer.parseInt(commande);
                        if (choixNumero > nbPage * maxLivresParPage && choixNumero < livres.size()) {
                            Livre livre = livres.get(choixNumero);
                            this.afficherLivre(client, livre);
                        } else {
                            System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                    }
                }
            }
        }
    }

    public void afficherLivre(Client client, Livre livre) {
        List<String> auteursNom = new ArrayList<>();
        for (Auteur auteur: livre.getAuteurs()) {
            auteursNom.add(auteur.getNom());
        }

        this.afficherTitre(livre.getTitre());
        this.afficherTexte(String.format("Auteurs : %s", String.join(", ", auteursNom)));
        this.afficherTexte(String.format("Prix : %.2f€", livre.getPrix()));
        this.afficherTexte("Classifications : ");
        this.afficherTexte("Editeurs : ");
        // TODO: On pourrait ajouter un descriptif du livre

        this.afficherSeperateurMilieu();
        // TODO: Ajouter au panier, quitter
    }
}

import java.sql.Date;
import java.time.LocalDate;
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
        System.out.println("╭" + "─".repeat(this.longueurAffichage - 2) + "╮");
        this.afficherTexteCentrer(titre);
        this.afficherSeperateurMilieu();
    }

    public void afficherTexteCentrer(String texte) {
        int margeDebut = (this.longueurAffichage - 4 - texte.length()) / 2;
        int margeFin = margeDebut;
        if (texte.length() % 2 != 0) margeFin++;

        System.out.println("│ " + String.format("%" + margeDebut + "s%s%" + margeFin + "s", "", texte, "") + " │");
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

    public String getUserCommandInput() {
        try {
            String commandeBrute = System.console().readLine();
            return commandeBrute.strip().toLowerCase();
        } catch (Exception e) {
            // On utilise Exception ici et non l'exception précise pour gérer l'arrêt avec CTRL+C
            // Pour viser la bonne exception, il faudrait installer le paquet "jline", mais pour plus de simpliciter, on ne le fait pas.

            System.out.println("Programme arrêté manuellement.");
            System.exit(0);
            return null;
        }
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
    
            String commande = this.getUserCommandInput();
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
        // Voir aussi si on choisi le magasin à ce moment-là
        Client client = this.chaineLibrairie.trouverClient("DUPONT", "Richard");

        // TODO: Choisir le magasin au début (ou bien changer magasin après coup dans le menu)
        // Faire un avertissement si panier avec des élements et changement de magasin

        this.client(client);
    }

    public void client(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            Panier panier = client.getPanier();

            // TODO: Déplacer ça dans le menu de changement de magasin
            if (!panier.getMagasin().equals(client.getMagasin())) {
                Panier nouveauPanier = new Panier(client.getMagasin());
                client.setPanier(nouveauPanier);
            }

            this.afficherTitre(String.format("Menu Client - %s", client.toString()));
            this.afficherTexte("L: Catalogue de livres");
            this.afficherTexte("P: Panier client");
            this.afficherTexte("R: Recommandations");
            this.afficherTexte("S: Rechercher livres");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();
    
            String commande = this.getUserCommandInput();
            switch (commande) {
                case "l": {
                    ResultatSelectionLivre resultatSelectionLivre = new ResultatSelectionLivre();
                    while (resultatSelectionLivre != null) {
                        resultatSelectionLivre = this.selectionnerLivre("Catalogue de livres", this.chaineLibrairie.getLivres(), resultatSelectionLivre.getNbPage());
                        if (resultatSelectionLivre != null) {
                            Livre livre = resultatSelectionLivre.getLivre();
                            this.afficherLivre(client, livre);
                        }
                    }
                    break;
                }
                case "p": {
                    this.voirPanier(client);
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

    public ResultatSelectionLivre selectionnerLivre(String titre, List<Livre> livres, int nbPage) {
        int maxLivresParPage = 10;
        int totalPages = livres.size() / (maxLivresParPage + 1);

        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre(String.format("%s - page n°%d sur %d", titre, nbPage + 1, totalPages + 1));
            
            int debutIndex = nbPage * maxLivresParPage;
            int finIndex = Math.min(debutIndex + maxLivresParPage, livres.size());
            for (int i = debutIndex; i < finIndex; i++) {
                Livre livre = livres.get(i);
                this.afficherTexte(String.format("%d - %s | par %s | %.2f€", i + 1, livre.getTitre(), livre.joinNomAuteurs(), livre.getPrix()));
            }
    
            this.afficherSeperateurMilieu();
            this.afficherTexte("Nombre: Sélection d'un livre dans la liste");
            if (nbPage > 0) this.afficherTexte("P: Page précédente");
            if (nbPage < totalPages) this.afficherTexte("S: Page suivante");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.getUserCommandInput();
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
                        int indLivre = Integer.parseInt(commande) - 1;                        
                        if (indLivre >= debutIndex && indLivre < finIndex) {
                            Livre livre = livres.get(indLivre);
                            return new ResultatSelectionLivre(nbPage, livre);
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
        return null;
    }

    public void afficherLivre(Client client, Livre livre) {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre(livre.getTitre());
            this.afficherTexte(String.format("Auteurs : %s", livre.joinNomAuteurs()));
            this.afficherTexte(String.format("Prix : %.2f€", livre.getPrix()));
            this.afficherTexte(String.format("Classifications : %s", livre.joinClassifications()));
            this.afficherTexte(String.format("Éditeurs : %s", livre.joinNomEditeurs()));
            // TODO: On pourrait ajouter un descriptif du livre
    
            this.afficherSeperateurMilieu();
            this.afficherTexte("A: Ajouter au panier");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.getUserCommandInput();
            switch (commande) {
                case "a": {
                    int quantiteLivre = client.getPanier().ajouterLivre(livre);
                    System.out.println(String.format("Livre \"%s\" ajouté au panier ! (quantité actuelle : %d)", livre.getTitre(), quantiteLivre));
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

    public void voirPanier(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            Panier panier = client.getPanier();
            List<DetailCommande> detailCommandes = panier.getDetailCommandes();
            this.afficherTitre(String.format("Panier - %s", client.toString()));
            if (detailCommandes.size() > 0) {
                double totalCommande = 0.00;
                this.afficherTexte("       ISBN                               Titre                              Qte    Prix   Total");
                for (int i = 0; i < detailCommandes.size(); i++) {
                    DetailCommande detailCommande = detailCommandes.get(i);
                    Livre livre = detailCommande.getLivre();
                    
                    String numLigne = String.format("%2s", detailCommande.getNumLigne());
                    String isbn = String.format("%13s", livre.getISBN());
                    String titre = String.format("%-59s", livre.getTitre());
                    String qte = String.format("%3s", detailCommande.getQuantite());
                    String prix = String.format("%6.2f€", detailCommande.getPrixVente());
                    String total = String.format("%6.2f€", detailCommande.getPrixVente() * detailCommande.getQuantite());

                    totalCommande += detailCommande.getPrixVente() * detailCommande.getQuantite();
                    this.afficherTexte(String.format("%s %s %s %s %s %s", numLigne, isbn, titre, qte, prix, total));
                }
                this.afficherTexte(String.format("%-" + (this.longueurAffichage - 11) + "s%s", "", "-------"));
                this.afficherTexte(String.format("%-" + (this.longueurAffichage - 11) + "s%6.2f€", "", totalCommande));
            } else {
                this.afficherTexte("Vous n'avez aucun livre dans votre panier !");
            }

            this.afficherSeperateurMilieu();
            if (detailCommandes.size() > 0) {
                this.afficherTexte("P: Passer la commande");
                this.afficherTexte("S: Supprimer un livre");
            }
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.getUserCommandInput();
            switch (commande) {
                case "p": {
                    finCommande = this.commander(client, panier);
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

    public boolean commander(Client client, Panier panier) {
        List<DetailCommande> detailCommandes = panier.getDetailCommandes();
        if (detailCommandes.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }
        
        this.afficherTitre("Passer une commande");
        Character modeLivraison = this.demanderModeLivraison();
        if (modeLivraison != null) {
            // TODO: Voir pour l'ID de la commande, normalement cela devrait être la DB qui devrait la donner
            Commande commande = new Commande(1, Date.valueOf(LocalDate.now()), 'O', modeLivraison.charValue(), "En Attente", panier.getMagasin(), detailCommandes);

            client.commander(commande);
            panier.viderPanier();
            System.out.println("Merci pour votre commande !");
            return true;
        }
        
        return false;
    }

    public Character demanderModeLivraison() {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTexteCentrer("Voulez-vous récupérer la commande en ligne ou en magasin ?");
            this.afficherSeperateurMilieu();
            this.afficherTexte("C: Chez soi");
            this.afficherTexte("M: En magasin");
            this.afficherTexte("Q: Annuler");
            this.afficherTitreFin();

            String commande = this.getUserCommandInput();
            switch (commande) {
                case "c":
                    return 'C';
                case "m":
                    return 'M';
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
        return null;
    }
}

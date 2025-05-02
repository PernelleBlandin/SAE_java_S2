import java.util.List;

/**
 * L'application sous le format ligne de commandes.
 */
public class App {
    /** La longueur d'affichage des menus */
    private int longueurAffichage;
    /** La chaîne de librairie */
    private ChaineLibrairie chaineLibrairie;

    /**
     * Créer l'application en ligne de commandes.
     * @param chaineLibrairie La chaîne de librairie.
     */
    public App(ChaineLibrairie chaineLibrairie) {
        this.longueurAffichage = 100;
        this.chaineLibrairie = chaineLibrairie;
    }

    /**
     * Lancer les fonctions nécéssaires au lancement de l'application.
     */
    public void run() {
        this.bienvenue();
        this.menu();
    }

    /**
     * Limiter la taille d'un texte.
     * Si jamais le texte dépasse la longueur d'affichage, on ajoute des "..." à la fin pour le limiter à la taille maximal.
     * @param texte Un texte
     * @param longueurAffichage La longueur d'affichage.
     * @return Le texte qui ne dépasse par la longueur d'affichage indiquée.
     */
    public static String truncate(String texte, int longueurAffichage) {
        int maxCaracteres = longueurAffichage;
        if (texte.length() <= maxCaracteres) return texte;

        return texte.substring(0, maxCaracteres - 3) + "...";
    }

    /**
     * Afficher un titre dans le terminal de manière centrée, en y ajoutant une bordure au départ et un séparateur.
     * @param titre Le titre a afficher.
     */
    public void afficherTitre(String titre) {
        System.out.println("╭" + "─".repeat(this.longueurAffichage - 2) + "╮");
        this.afficherTexteCentrer(App.truncate(titre, this.longueurAffichage - 4));
        this.afficherSeperateurMilieu();
    }

    /**
     * Afficher un texte de manière centrée, selon la longueur d'affichage dans les attributs de la classe.
     * @param texte Un texte.
     */
    public void afficherTexteCentrer(String texte) {
        int margeDebut = (this.longueurAffichage - 4 - texte.length()) / 2;
        int margeFin = margeDebut;
        if (texte.length() % 2 != 0) margeFin++;

        System.out.println("│ " + String.format("%" + margeDebut + "s%s%" + margeFin + "s", "", App.truncate(texte, this.longueurAffichage - 4), "") + " │");
    }

    /**
     * Afficher un texte en respectant la "charte graphique" de l'application.
     * @param texte Un texte.
     */
    public void afficherTexte(String texte) {
        System.out.println(String.format("| %-" + (this.longueurAffichage - 4) + "s |", App.truncate(texte, this.longueurAffichage - 4)));
    }

    /**
     * Afficher un séparateur horizontal.
     */
    public void afficherSeperateurMilieu() {
        System.out.println("│" + "─".repeat(this.longueurAffichage - 2) + "│");
    }

    /**
     * Afficher un "séparateur" lors de la fin d'un menu.
     */
    public void afficherTitreFin() {
        System.out.println("╰" + "─".repeat(this.longueurAffichage - 2) + "╯");
    }

    /**
     * Obtenir l'entrée de l'utilisateur dans le terminal.
     * En cas d'exception (généralement CTRL+C/CTRL+D), on arrête le programme normalement.
     * @return La réponse de l'utilisateur.
     */
    public String obtenirEntreeUtilisateur() {
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

    /**
     * Afficher dans le terminal une introduction en ASCII de l'application.
     */
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

    /**
     * Afficher le menu principal en choisisant son compte (client/vendeur/administrateur).
     */
    public void menu() {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre("Choix du compte");
            this.afficherTexte("C: Client");
            this.afficherTexte("V: Vendeur");
            this.afficherTexte("A: Administrateur");
            this.afficherTexte("Q: Quitter");
            this.afficherTitreFin();

            String commande = this.obtenirEntreeUtilisateur();
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

    /**
     * Sélectionner un élément dans une liste de page.
     * Aide pour les types génériques : https://www.baeldung.com/java-generics#generic-methods
     * @param <T> Le type de l'élément
     * @param elements La liste des éléments possible à sélectionner.
     * @param nbPage La page actuelle.
     * @param titre Le titre du menu.
     * @return L'élément choisi
     */
    public <T> ResultatSelection<T> selectionnerElement(List<T> elements, int nbPage, String titre) {
        int maxElementsParPage = 5;
        int totalPages = elements.size() / (maxElementsParPage + 1);

        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre(String.format("%s - page n°%d sur %d", titre, nbPage + 1, totalPages + 1));

            int debutIndex = nbPage * maxElementsParPage;
            int finIndex = Math.min(debutIndex + maxElementsParPage, elements.size());

            boolean hasResults = debutIndex < finIndex;
            if (hasResults) {
                for (int i = debutIndex; i < finIndex; i++) {
                    T element = elements.get(i);
                    this.afficherTexte(String.format("%d - %s", i + 1, element.toString()));
                }
            } else {
                this.afficherTexte("Il n'y a aucun résultat.");
            }

            this.afficherSeperateurMilieu();
            if (hasResults) this.afficherTexte("Nombre: Sélection dans la liste");
            if (nbPage > 0) this.afficherTexte("P: Page précédente");
            if (nbPage < totalPages) this.afficherTexte("S: Page suivante");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String entreeUtilisateur = this.obtenirEntreeUtilisateur();
            switch (entreeUtilisateur) {
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
                        int indElement = Integer.parseInt(entreeUtilisateur) - 1;
                        if (indElement >= debutIndex && indElement < finIndex) {
                            T element = elements.get(indElement);
                            return new ResultatSelection<>(nbPage, element);
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

    /**
     * Demander une confirmation à un utilisateur (Oui/Non).
     * @param titre Le titre de la confirmation.
     * @return true si c'est oui, false si c'est non.
     */
    public boolean demanderConfirmation(String titre) {
        return this.demanderConfirmation(titre, null);
    }

    /**
     * Demander une confirmation à un utilisateur (Oui/Non).
     * @param titre Le titre de la confirmation.
     * @param description La description de la confirmation.
     * @return true si c'est oui, false si c'est non.
     */
    public boolean demanderConfirmation(String titre, String description) {
        this.afficherTitre(titre);
        if (description != null) {
            this.afficherTexteCentrer(description);
            this.afficherSeperateurMilieu();
        }
        this.afficherTexte("O: Oui");
        this.afficherTexte("N: Non");
        this.afficherTitreFin();

        String confirm = this.obtenirEntreeUtilisateur();
        return confirm.equals("o");
    }

    /**
     * Demander à l'utilisateur la recherche qu'il souhaite réaliser dans le terminal.
     * @return La recherche de l'utilisateur, ou null s'il a annulé l'opération.
     */
    public String demanderRecherche() {
        this.afficherTitre(String.format("Quel est votre recherche ?"));
        this.afficherTexte("Q: Retour");
        this.afficherTitreFin();

        String recherche = this.obtenirEntreeUtilisateur();
        if (recherche.equals("q")) return null;

        return recherche;
    }

    // Client

    /**
     * Accéder à un menu de connexion pour choisir son compte client.
     */
    public void connexionClient() {
        // TODO: Voir comment on fait ça
        // Voir aussi si on choisi le magasin à ce moment-là
        Client client = this.chaineLibrairie.trouverClient("Petit", "Louis");

        // TODO: Choisir le magasin au début (ou bien changer magasin après coup dans le menu)
        // Faire un avertissement si panier avec des élements et changement de magasin

        this.client(client);
    }

    /**
     * Accéder au menu pour un client donné.
     * @param client Un client.
     */
    public void client(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            Magasin magasin = client.getMagasin();

            this.afficherTitre(String.format("Menu Client - %s | Magasin : %s", client.toString(), magasin.toString()));
            this.afficherTexte("L: Catalogue de livres");
            this.afficherTexte("P: Panier client");
            this.afficherTexte("R: Recommandations");
            this.afficherTexte("S: Rechercher livres");
            this.afficherTexte("M: Changer de magasin");
            this.afficherTexte("C: Dernières commandes");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.obtenirEntreeUtilisateur();
            switch (commande) {
                case "l": {
                    this.consulterCatalogueClient(client, this.chaineLibrairie.getLivres(), "Catalogue de livres");
                    break;
                }
                case "r": {
                    List<Livre> livresRecommandes = this.chaineLibrairie.onVousRecommande(client);
                    this.consulterCatalogueClient(client, livresRecommandes, "Livres recommandés");
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
                case "s": {
                    String recherche = this.demanderRecherche();
                    if (recherche != null) {
                        List<Livre> livresCorrespondants = this.chaineLibrairie.rechercherLivres(this.chaineLibrairie.getLivres(), recherche);
                        this.consulterCatalogueClient(client, livresCorrespondants, String.format("Recherche de livres - %s", recherche));
                    }
                    break;
                }
                case "m": {
                    this.changerMagasin(client);
                    break;
                }
                case "c": {
                    this.consulterCommandesClient(client);
                    break;
                }
                default: {
                    System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                    break;
                }
            }
        }
    }

    /**
     * Afficher la catalogue de livre sous forme de page et afficher les détails d'un livre choisi.
     * @param client Un client.
     * @param livres La liste des livres à afficher dans les pages.
     * @param titre Le titre du menu.
     */
    public void consulterCatalogueClient(Client client, List<Livre> livres, String titre) {
        ResultatSelection<Livre> resultatSelectionLivre = new ResultatSelection<>();
        while (resultatSelectionLivre != null) {
            resultatSelectionLivre = this.selectionnerElement(livres, resultatSelectionLivre.getNbPage(), titre);
            if (resultatSelectionLivre != null) {
                Livre livre = resultatSelectionLivre.getElement();
                this.afficherLivre(client, livre);
            }
        }
    }

    /**
     * Afficher les détails d'un livre et laisser la possibilité de l'ajouter dans son panier client.
     * @param client Un client.
     * @param livre Le livre choisi.
     */
    public void afficherLivre(Client client, Livre livre) {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre(livre.getTitre());
            this.afficherTexte(String.format("Auteur : %s", livre.joinNomAuteurs()));
            this.afficherTexte(String.format("Prix : %.2f€", livre.getPrix()));
            this.afficherTexte(String.format("Classification : %s", livre.joinClassifications()));
            this.afficherTexte(String.format("Éditeur : %s", livre.joinNomEditeurs()));
            // TODO: On pourrait ajouter un descriptif du livre

            this.afficherSeperateurMilieu();
            this.afficherTexte("A: Ajouter au panier");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.obtenirEntreeUtilisateur();
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

    // Panier Client

    /**
     * Voir son panier client
     * @param client Un client.
     */
    public void voirPanier(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            Panier panier = client.getPanier();
            Magasin magasin = client.getMagasin();
            List<DetailCommande> detailCommandes = panier.getDetailCommandes();
            this.afficherTitre(String.format("Panier - %s | Magasin : %s", client.toString(), magasin.toString()));

            if (detailCommandes.size() > 0) {
                List<String> detailCommandeTextuel = this.chaineLibrairie.genererCorpsCommandeTextuel(detailCommandes, this.longueurAffichage);
                for (String ligne: detailCommandeTextuel) {
                    this.afficherTexte(ligne);
                }
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

            String commande = this.obtenirEntreeUtilisateur();
            switch (commande) {
                case "p": {
                    finCommande = this.commander(client, panier);
                    break;
                }
                case "s": {
                    this.supprimerLivrePanier(client, panier);
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

    /**
     * Commander les éléments dans son panier.
     * @param client Un client.
     * @param panier Son panier.
     * @return true si la commande a été réalisée, sinon false.
     */
    public boolean commander(Client client, Panier panier) {
        List<DetailCommande> detailCommandes = panier.getDetailCommandes();
        if (detailCommandes.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }

        this.afficherTitre("Passer une commande");
        Character modeLivraison = this.demanderModeLivraison();
        if (modeLivraison != null) {
            boolean commandeReussie = client.commander(modeLivraison, 'O');
            if (commandeReussie) System.out.println("Merci pour votre commande !");
            return commandeReussie;
        }

        return false;
    }

    /**
     * Demander à un client son mode de livraison.
     * @return Le mode de livraison du client (C: Chez lui, M: En magasin), ou null si demande annulée
     */
    public Character demanderModeLivraison() {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTexteCentrer("Voulez-vous récupérer la commande en ligne ou en magasin ?");
            this.afficherSeperateurMilieu();
            this.afficherTexte("C: Chez soi");
            this.afficherTexte("M: En magasin");
            this.afficherTexte("Q: Annuler");
            this.afficherTitreFin();

            String commande = this.obtenirEntreeUtilisateur();
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

    /**
     * Supprimer un livre de son panier.
     * @param client Un client. 
     * @param panier Son panier.
     * @return true si un livre a été retiré de son panier, sinon false.
     */
    public boolean supprimerLivrePanier(Client client, Panier panier) {
        List<DetailCommande> detailCommandes = panier.getDetailCommandes();
        if (detailCommandes.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }

        List<Livre> livresPanier = panier.getLivres();
        ResultatSelection<Livre> resultatSelectionLivre = new ResultatSelection<>();
        while (resultatSelectionLivre != null) {
            resultatSelectionLivre = this.selectionnerElement(livresPanier, resultatSelectionLivre.getNbPage(), "Supprimer un livre du panier");
            if (resultatSelectionLivre != null) {
                Livre livre = resultatSelectionLivre.getElement();
                DetailCommande detailCommande = panier.getDetailCommandeLivre(livre);

                Integer quantite = this.demanderQuantiterSupprimer(detailCommande);
                if (quantite != null) {
                    System.out.println(String.format("Retrait de %dx %s du panier effectuée !", quantite, livre.getTitre()));
                    panier.retirerQuantiteLivre(livre, quantite);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Demander la quantité à supprimer du détail d'unecommande.
     * @param detailCommande Le détail d'une commande.
     * @return La quantité à supprimer, ou null si opération annulée.
     */
    public Integer demanderQuantiterSupprimer(DetailCommande detailCommande) {
        Integer quantite = null;
        int quantitePanier = detailCommande.getQuantite();
        if (quantitePanier == 1) quantite = 1;

        Livre livre = detailCommande.getLivre();
        while (quantite == null) {
            this.afficherTitre(String.format("Quelle est la quantité du livre %s que vous voulez retirer ?", livre.getTitre()));
            this.afficherTexte(String.format("Il est présent à %d exemplaire(s).", detailCommande.getQuantite()));
            this.afficherSeperateurMilieu();
            this.afficherTexte("Nombre: Quantité à retirer");
            this.afficherTexte("Q: Annuler");
            this.afficherTitreFin();

            String entree = this.obtenirEntreeUtilisateur();
            if (entree.equals("q")) return null;

            try {
                quantite = Integer.parseInt(entree);
                if (quantite > 0) {
                    if (quantite > quantitePanier) {
                        System.out.println(String.format("INFO: Vous avez demandé à retirer %d exemplaire(s), mais le panier n'en contient que %d. La quantité a été automatiquement ajustée.", quantite, quantitePanier));
                        quantite = quantitePanier;
                    }
                } else {
                    System.err.println("ERREUR: Nombre invalide, merci d'indiquer une valeure supérieure ou égale à 1. Veuillez réessayer...");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
            }
        }

        boolean confirmation = this.demanderConfirmation(String.format("Confirmez-vous la suppression de %dx %s ?", quantite, livre.getTitre()));
        if (confirmation) return quantite;

        return null;
    }

    /**
     * Changer de magasin client.
     * @param client Le client.
     */
    public void changerMagasin(Client client) {
        List<Magasin> magasins = this.chaineLibrairie.getMagasins();
        ResultatSelection<Magasin> resultatSelectionMagasin = this.selectionnerElement(magasins, 0, "Changer de magasin");
        if (resultatSelectionMagasin == null) return;

        Panier panier = client.getPanier();
        Magasin magasin = resultatSelectionMagasin.getElement();
        boolean effectuerChangement = true;

        if (panier.getDetailCommandes().size() > 0 && !panier.getMagasin().equals(magasin)) {
            effectuerChangement = this.demanderConfirmation(
                String.format("Voulez-vous vraiment définir votre magasin actuel pour %s ?", magasin.toString()), 
                "Vous avez des articles dans votre panier. Changer de magasin réinitialisera votre panier."
            );
            if (effectuerChangement) client.setPanier(new Panier(magasin));
        }

        if (effectuerChangement) {
            client.setMagasin(magasin);
            System.out.println(String.format("Le magasin a été changé pour %s.", magasin.toString()));
        } else {
            System.out.println("Magasin non changé.");
        }
    }

    /**
     * Permettre à un client de consulter la liste de ses commandes et de voir le détail d'une d'entre-elle.
     * @param client Un client.
     */
    public void consulterCommandesClient(Client client) {
        List<Commande> commandes = client.getCommandesTriesParDateDesc();
        ResultatSelection<Commande> resultatSelectionCommande = new ResultatSelection<>();
        while (resultatSelectionCommande != null) {
            resultatSelectionCommande = this.selectionnerElement(commandes, resultatSelectionCommande.getNbPage(), "Sélectionner une commande à afficher");
            if (resultatSelectionCommande != null) {
                Commande commande = resultatSelectionCommande.getElement();
                this.afficherCommande(commande);
            }
        }
    }

    /**
     * Afficher les détails d'une commande.
     * @param commande Une commande.
     */
    public void afficherCommande(Commande commande) {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre(commande.toString());

            List<String> detailCommandeTextuel = this.chaineLibrairie.genererCorpsCommandeTextuel(commande.getDetailCommandes(), this.longueurAffichage);
            for (String ligne: detailCommandeTextuel) {
                this.afficherTexte(ligne);
            }

            this.afficherSeperateurMilieu();
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String entreeUtilisateur = this.obtenirEntreeUtilisateur();
            switch (entreeUtilisateur) {
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
}

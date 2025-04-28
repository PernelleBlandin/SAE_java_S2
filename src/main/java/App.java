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

    // Aide pour les types génériques : https://www.baeldung.com/java-generics#generic-methods
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

    public boolean demanderConfirmation(String titre) {
        return this.demanderConfirmation(titre, null);
    }

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

    public String demanderRecherche() {
        this.afficherTitre(String.format("Quel est votre recherche ?"));
        this.afficherTexte("Q: Retour");
        this.afficherTitreFin();

        String recherche = this.obtenirEntreeUtilisateur();
        if (recherche.equals("q")) return null;

        return recherche;
    }

    // Client

    public void connexionClient() {
        // TODO: Voir comment on fait ça
        // Voir aussi si on choisi le magasin à ce moment-là
        Client client = this.chaineLibrairie.trouverClient("Petit", "Louis");

        // TODO: Choisir le magasin au début (ou bien changer magasin après coup dans le menu)
        // Faire un avertissement si panier avec des élements et changement de magasin

        this.client(client);
    }

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
                default: {
                    System.err.println("ERREUR: Choix invalide, veuillez réessayer...");
                    break;
                }
            }
        }
    }

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

    public void voirPanier(Client client) {
        boolean finCommande = false;
        while (!finCommande) {
            Panier panier = client.getPanier();
            Magasin magasin = client.getMagasin();
            List<DetailCommande> detailCommandes = panier.getDetailCommandes();
            this.afficherTitre(String.format("Panier - %s | Magasin : %s", client.toString(), magasin.toString()));

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

    public boolean commander(Client client, Panier panier) {
        List<DetailCommande> detailCommandes = panier.getDetailCommandes();
        if (detailCommandes.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }

        this.afficherTitre("Passer une commande");
        Character modeLivraison = this.demanderModeLivraison();
        if (modeLivraison != null) {
            client.commander(modeLivraison, detailCommandes);
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
}

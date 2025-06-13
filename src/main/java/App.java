import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * L'application sous le format ligne de commandes.
 */

public class App {
    private ChaineLibrairie chaineLibrairie;
    private int longueurAffichage;

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
     * @param longueurTexteMax La longueur d'affichage maximal du texte.
     * @return Le texte qui ne dépasse par la longueur d'affichage indiquée.
     */
    public static String truncate(String texte, int longueurTexteMax) {
        int maxCaracteres = longueurTexteMax;
        if (texte.length() <= maxCaracteres) return texte;

        return texte.substring(0, maxCaracteres - 3) + "...";
    }

    /**
     * Afficher le séparateur du début.
     */
    public void afficherTitreDebut() {
        System.out.println("╭" + "─".repeat(this.longueurAffichage - 2) + "╮");
    }

    /**
     * Afficher un titre dans le terminal de manière centrée, en y ajoutant une bordure au départ et un séparateur.
     * @param titre Le titre a afficher.
     */
    public void afficherTitre(String titre) {
        this.afficherTitreDebut();
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
     * Afficher un titre avecc un séparateur de début et de fin uniquement.
     * @param titre Le titre
     */
    public void afficherTitreUniquement(String titre) {
        this.afficherTitreDebut();
        this.afficherTexteCentrer(titre);
        this.afficherTitreFin();
    }

    /**
     * Obtenir la commande de l'utilisateur dans le terminal.
     * En cas d'exception (généralement CTRL+C/CTRL+D), on arrête le programme normalement.
     * @return La réponse de l'utilisateur.
     */
    public String obtenirCommandeUtilisateur() {
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
     * Obtenir l'entrée de l'utilisateur dans le terminal.
     * En cas d'exception (généralement CTRL+C/CTRL+D), on arrête le programme normalement.
     * @return La réponse de l'utilisateur.
     */
    public String obtenirEntreeUtilisateur() {
        try {
            String commandeBrute = System.console().readLine();
            return commandeBrute.strip();
        } catch (Exception e) {
            // On utilise Exception ici et non l'exception précise pour gérer l'arrêt avec CTRL+C
            // Pour viser la bonne exception, il faudrait installer le paquet "jline", mais pour plus de simpliciter, on ne le fait pas.

            System.out.println("Programme arrêté manuellement.");
            System.exit(0);
            return null;
        }
    }

    /**
     * Obtenir l'entrée d'un nombre de l'utilisateur dans le terminal.
     * En cas d'exception (généralement CTRL+C/CTRL+D), on arrête le programme normalement.
     * @return La réponse de l'utilisateur.
     */
    public Integer obtenirEntreeNombreUtilisateur() {
        try {
            String entreeBrut = System.console().readLine();
            return Integer.parseInt(entreeBrut.strip());
        } catch (java.lang.NumberFormatException e) {
            System.out.println("Erreur : L'entrée que vous avez indiquée n'est pas un nombre.");
            return null;
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

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "c": {
                    this.connexionClient();
                    break;
                }
                case "v": {
                    this.connexionVendeur();
                    break;
                }
                case "a": {
                    this.menuAdministrateur();
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
        int maxElementsParPage = 10;
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

            String entreeUtilisateur = this.obtenirCommandeUtilisateur();
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

        String confirm = this.obtenirCommandeUtilisateur();
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

        String recherche = this.obtenirCommandeUtilisateur();
        if (recherche.equals("q")) return null;

        return recherche;
    }

    // Client

    /**
     * Accéder à un menu de connexion pour choisir son compte client.
     */
    public void connexionClient() {
        try {
            Client client = this.chaineLibrairie.getClientBD().obtenirClientParId(1);
            this.client(client);
        } catch (SQLException e) {
            System.err.println("Une erreur est survenue lors de la récupréation du client : " + e.getMessage());
            return;
        }
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

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "l": {
                    List<Livre> listeLivres;
                    try {
                        listeLivres = this.chaineLibrairie.getLivreBD().obtenirListeLivre();
                    } catch (SQLException e) {
                        System.err.println("Une erreur est survenue lors de la récupération des livres : " + e.getMessage());
                        break;
                    }

                    this.consulterCatalogueClient(client, listeLivres, "Catalogue de livres");
                    break;
                }
                case "r": {
                    try {
                        List<Livre> livresRecommandes = this.chaineLibrairie.onVousRecommande(client);
                        this.consulterCatalogueClient(client, livresRecommandes, "Livres recommandés");
                    } catch (SQLException e) {
                        System.err.println("Une erreur est survenue lors de la récupération des données en base de données. " + e.getMessage());
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
                case "s": {
                    String recherche = this.demanderRecherche();
                    if (recherche != null) {
                        List<Livre> listeLivres;
                        try {
                            listeLivres = this.chaineLibrairie.getLivreBD().obtenirListeLivre();
                            List<Livre> livresCorrespondants = this.chaineLibrairie.rechercherLivres(listeLivres, recherche);
                            this.consulterCatalogueClient(client, livresCorrespondants, String.format("Recherche de livres - %s", recherche));
                        } catch (SQLException e) {
                            System.err.println("Une erreur est survenue lors de la récupération des livres : " + e.getMessage());
                        }
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
            String nbPages = "Inconnu";
            Integer nbpagesInteger = livre.getNbPages();
            if (nbpagesInteger != null) nbPages = String.valueOf(nbpagesInteger);

            this.afficherTitre(livre.getTitre());
            this.afficherTexte(String.format("Auteur : %s", livre.joinNomAuteurs()));
            this.afficherTexte(String.format("Prix : %.2f€", livre.getPrix()));
            this.afficherTexte(String.format("Classification : %s", livre.joinClassifications()));
            this.afficherTexte(String.format("Éditeur : %s", livre.joinNomEditeurs()));
            this.afficherTexte(String.format("Nombre de pages : %s", nbPages));
            this.afficherSeperateurMilieu();

            Panier panierClient = client.getPanier();
            Magasin magasin = client.getMagasin();
            int quantiteLivrePanier = panierClient.getQuantiteLivre(livre);

            int quantiteLivreStock;
            try {
                quantiteLivreStock = this.chaineLibrairie.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
            } catch (SQLException e) {
                System.err.println("Une erreur est survenu lors de la récupération du stock du livre.");
                finCommande = true;
                break;
            }

            boolean livreEnStock = quantiteLivreStock > 0 && quantiteLivreStock > quantiteLivrePanier;
            if (livreEnStock) {
                int quantiteEnStock = quantiteLivreStock - quantiteLivrePanier;
                this.afficherTexte(String.format("A: Ajouter au panier (quantité en stock : %d)", quantiteEnStock));
            } else {
                this.afficherTexte(String.format("⚠ Ce livre n'est plus en stock dans votre magasin %s.", magasin.toString()));
                this.afficherSeperateurMilieu();
            }

            if (quantiteLivrePanier > 0) {
                this.afficherTexte(String.format("R: Retirer une quantité de votre panier (quantité : %d)", quantiteLivrePanier));
            }
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "a": {
                    if (livreEnStock) {
                        DetailLivre detailLivre = panierClient.ajouterLivre(livre);
                        System.out.println(String.format("Livre \"%s\" ajouté au panier ! (quantité actuelle : %d)", livre.getTitre(), detailLivre.getQuantite()));

                        try {
                            this.chaineLibrairie.getPanierBD().ajouterLivre(panierClient, detailLivre);
                        } catch (SQLException e) {
                            System.err.println("Une erreur est survenue lors de la mise à jour du panier en base de données : " + e.getMessage());
                        }
                    } else {
                        System.err.println(String.format("Ce livre n'est plus en stock dans votre magasin %s.", magasin.toString()));
                    }
                    break;
                }
                case "r": {
                    if (quantiteLivrePanier > 0) {
                        try {
                            panierClient.retirerQuantiteLivre(livre, 1);
                            this.chaineLibrairie.getPanierBD().updatePanier(panierClient);
                        } catch (LivreIntrouvableException e) {
                            System.err.println("Le livre n'a pas été trouvé dans votre panier...");
                        } catch (SQLException e) {
                            System.err.println("Une erreur est survenue lors de la mise à jour du panier en base de données : " + e.getMessage());
                        }
                    }
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

    // Connexion Vendeur

    /**
     * Se connecter en tant que vendeur dans l'application.
     */
    public void connexionVendeur(){
        try {
            Vendeur vendeur = this.chaineLibrairie.getVendeurBD().obtenirVendeurParId(1);
            this.menuVendeur(vendeur);
        } catch (SQLException e) {
            System.err.println("Une erreur est survenue lors de la récupération du vendeur : " + e.getMessage());
            return;
        }
    }

    /**
     * Afficher le menu vendeur. 
     * @param vendeur Un vendeur.
     */
    public void menuVendeur(Vendeur vendeur) {
        boolean finCommande = false;
        while (!finCommande) {
            Magasin magasin = vendeur.getMagasin();

            this.afficherTitre(String.format("Menu Vendeur - %s | Magasin : %s", vendeur.toString(), magasin.toString()));
            this.afficherTexte("A : Ajouter livre");
            this.afficherTexte("S : Accès stock magasins");
            this.afficherTexte("M : Modifications stock magasin");
            this.afficherTexte("C : Passer commande client");
            this.afficherTexte("T : Transférer Livre stock");
            this.afficherTexte("Q : Retour");
            this.afficherTitreFin();

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "a": {
                    Integer nbPages = null;
                    Double prix = null;
                    Integer anneeDePublication = null;

                    this.afficherTitreUniquement("Entrez l'identifiant du livre");
                    String inputIsbn = this.obtenirEntreeUtilisateur();
                    String isbn = inputIsbn.trim();
                    if(isbn.isEmpty()){
                        System.err.println("Erreur : Veuillez entrer un identifient");
                        break;
                    }

                    this.afficherTitreUniquement("Entrez son titre");
                    String inputTitre = this.obtenirEntreeUtilisateur();
                    String titre = inputTitre.trim();
                    if(titre.isEmpty()){
                        System.err.println("Erreur : Veuillez entrer un titre");
                        break;
                    }

                    try{
                        this.afficherTitreUniquement("Entrez son nombre de pages");
                        String inputNbPages = this.obtenirEntreeUtilisateur();
                        String nbPagesString = inputNbPages.trim();
                        nbPages = Integer.parseInt(nbPagesString);
                    }
                    catch(NumberFormatException e){
                        System.err.println("Erreur : Le nombre de pages doit etre un nombre entier.");
                        break;
                    }
                    if(nbPages <=0) {
                        System.err.println("Erreur : Le nombre de pages ne peut pas 0 ou négatif.");
                        break;
                    }

                    try{
                        this.afficherTitreUniquement("Entrez son prix");
                        String inputPrix = this.obtenirEntreeUtilisateur();
                        String prixString = inputPrix.trim();
                        prix = Double.parseDouble(prixString);
                    } 
                    catch (NumberFormatException e) {
                        System.err.println("Le prix doit etre en décimale (mettre un . au lieu de ,)");
                        break;
                    }
                    if(prix <=0){
                        System.err.println("Erreur : Le prix ne peut pas être négatif ou egal a 0.");
                        break;
                    }

                    try {
                        this.afficherTitreUniquement("Entrez son année de publication");
                        String inputAnneeDePublication = this.obtenirEntreeUtilisateur();
                        String anneeDePublicationString = inputAnneeDePublication.trim();
                        anneeDePublication = Integer.parseInt(anneeDePublicationString);
                    } catch (NumberFormatException e){
                        System.err.println("L'année de publication doit etre un nombre entier");
                        break;
                    }
                    if(anneeDePublication <= 0){
                        System.err.println("Erreur : L'année de publication ne peut pas être négative ou egale a 0.");
                        break;
                    }

                    String[] infoAuteurs;
                    String auteurNom;
                    int naissance;
                    int deces;
                    try {
                        this.afficherTitreUniquement("Entrez nom, année naissance, décès (-1 si vivant) de l'auteur, séparés par virgules :");
                        String inputAuteurs = this.obtenirEntreeUtilisateur();
                        
                        infoAuteurs = inputAuteurs.split(",");
                        for (int i = 0; i < infoAuteurs.length; i++){
                            infoAuteurs[i] = infoAuteurs[i].trim();
                        }
                        
                        if (infoAuteurs.length != 3) {
                            System.err.println("Erreur : Veuillez entrez nom, année naissance, décès (-1 si vivant) de l'auteur, séparés par virgules.");
                            break;
                        }
                        auteurNom = infoAuteurs[0];
                        naissance = Integer.parseInt(infoAuteurs[1]);
                        deces = Integer.parseInt(infoAuteurs[2]);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur : l'année de naissance et décès (-1 si vivant) doivent etre des nombres entiers.");
                        break;
                    }
                    if(deces<naissance && deces!=-1){
                        System.err.println("Erreur : L'année de décès ne peut pas être inférieure à l'année de naissance.");
                        break;
                    }
                    Set<String> auteur = new HashSet<>();
                    auteur.add(auteurNom);

                    String auteurExistant;
                    String idAuteur = "";
                    try {
                        auteurExistant = this.chaineLibrairie.getLivreBD().getIdAuteur(auteurNom);
                        if (auteurExistant == null){
                            this.afficherTitreUniquement("Entrez l'identifiant de l'auteur");
                            String input = this.obtenirEntreeUtilisateur();
                            idAuteur = input.trim();
                        }
                    } catch(SQLException e){
                        System.err.println("Une erreur est survenue lors de la récupération des données en base de données. " + e.getMessage());
                        break;
                    }

                    this.afficherTitreUniquement("Entrez l'éditeur");
                    String inputEditeur = this.obtenirEntreeUtilisateur();
                    String editeurNom = inputEditeur.trim();
                    Set<String> editeur = new HashSet<>();
                    editeur.add(editeurNom);

                    this.afficherTitreUniquement("Entrez le nom de sa classification");
                    String inputClassification = this.obtenirEntreeUtilisateur();
                    String classificationNom = inputClassification.trim();
                    
                    Set<String> classification = new HashSet<>();
                    classification.add(classificationNom);
                    
                    String classificationExistante;
                    String idClassifications = "";
                    try {
                        classificationExistante = this.chaineLibrairie.getLivreBD().getIdDewey(classificationNom);
                        if(classificationExistante == null){
                            this.afficherTitreUniquement("Entrez l'identifiant de la classification");
                            String input = this.obtenirEntreeUtilisateur();
                            idClassifications = input.trim();
                        }
                    } catch(SQLException e){
                        System.err.println("Une erreur est survenue lors de la récupération des données en base de données. " + e.getMessage());
                        break;
                    }
                    try {
                        if (this.chaineLibrairie.getLivreBD().getIdAuteur(auteurNom) == null  && this.chaineLibrairie.getLivreBD().getIdDewey(classificationNom) == null) {
                            this.chaineLibrairie.getLivreBD().ajouteLivreAuteurNonExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, idClassifications, naissance, deces);
                        } else if (this.chaineLibrairie.getLivreBD().getIdAuteur(auteurNom) == null && this.chaineLibrairie.getLivreBD().getIdDewey(classificationNom) != null) {
                            this.chaineLibrairie.getLivreBD().ajouteLivreAuteurNonExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, naissance, deces);
                        } else if (this.chaineLibrairie.getLivreBD().getIdAuteur(auteurNom) != null && this.chaineLibrairie.getLivreBD().getIdDewey(classificationNom) == null) {
                            this.chaineLibrairie.getLivreBD().ajouteLivreAuteurExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idClassifications);
                        } else {
                            this.chaineLibrairie.getLivreBD().ajouteLivreAuteurExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom);
                        }
                    } catch (SQLException e) {
                        System.err.println("Une erreur est survenue lors de l'ajout du livre en base de données : " + e.getMessage());
                        break;
                    }

                    System.out.println("Livre ajouté avec succès !");
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
     * Afficher le menu administrateur.
     */
    public void menuAdministrateur() {
        boolean finCommande = false;
        while (!finCommande) {
            this.afficherTitre("Menu Administrateur");
            this.afficherTexte("C: Création compte vendeur");
            this.afficherTexte("M: Ajout magasin");
            this.afficherTexte("S: Modification stock global");
            this.afficherTexte("V: Statistiques de vente");
            this.afficherTexte("F: Exporter les factures en PDF");
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "c": {
                    this.creationCompteVendeur();
                    break;
                }

                /*case "m" : {
                *ajouter une nouvelle boutique appartenant à la chaîne de librairie
                } 
                */

                /*case "s": {
                 * 
                } */
               /*case "v": {
                * accéder aux statistique de vente
               } */
                 case "f": {
                    this.exporterFactures();
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
     * Afficher le menu pour exporter des factures.
     */
    public void exporterFactures() {
        this.afficherTitreUniquement("De quel mois voulez-vous exporter les factures (1-12) ?");
        Integer mois = this.obtenirEntreeNombreUtilisateur();
        if (mois == null) return;

        if (mois < 0 || mois > 12) {
            System.err.println("Erreur : Le mois doit être compris entre 1 et 12.");
            return;
        }

        this.afficherTitreUniquement("De quelle année voulez-vous exporter les factures ?");
        Integer annee = this.obtenirEntreeNombreUtilisateur();
        if (annee == null) return;

        try {
            this.chaineLibrairie.exporterFactures(mois, annee);
            System.out.println(String.format("Factures exportés dans le dossier ./factures/%d-%d", annee, mois));
        } catch (SQLException e) {
            System.err.println("Erreur : Un problème est survenue avec la BD" + e.getMessage());
        } catch (PasDeCommandeException e) {
            System.err.println("Erreur : Il n'y a aucune facture à exporter.");
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
            List<DetailLivre> detailLivresPanier = panier.getDetailLivres();
            this.afficherTitre(String.format("Panier - %s | Magasin : %s", client.toString(), magasin.toString()));

            boolean ruptureProduit = false;
            if (detailLivresPanier.size() > 0) {
                double totalCommande = 0.00;
                this.afficherTexte("       ISBN                               Titre                              Qte    Prix   Total");
                for (DetailLivre detailLivre: detailLivresPanier) {
                    Livre livre = detailLivre.getLivre();
                    int livreQuantite = detailLivre.getQuantite();
                    double totalLivre = detailLivre.getPrixVente() * livreQuantite;

                    String numLigne = String.format("%2s", detailLivre.getNumLigne());
                    String isbn = String.format("%13s", livre.getISBN());
                    String titre = String.format("%-59s", livre.getTitre());
                    String qte = String.format("%3s", livreQuantite);
                    String prix = String.format("%6.2f€", detailLivre.getPrixVente());
                    String total = String.format("%6.2f€", totalLivre);

                    totalCommande += totalLivre;
                    this.afficherTexte(String.format("%s %s %s %s %s %s", numLigne, isbn, titre, qte, prix, total));

                    int quantiteEnStock;
                    try {
                        quantiteEnStock = this.chaineLibrairie.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
                    } catch (SQLException e) {
                        System.err.println("Une erreur est survenu lors de la récupération du stock du livre.");
                        return;
                    }

                    if (livreQuantite > quantiteEnStock) {
                        ruptureProduit = true;
                        this.afficherTexte(String.format("  → ⚠ Quantité dans le panier supérieure au stock du magasin (%d disponible)", quantiteEnStock));
                    }
                }

                this.afficherTexte(String.format("%-" + (longueurAffichage - 11) + "s%s", "", "-------"));
                this.afficherTexte(String.format("%-" + (longueurAffichage - 11) + "s%6.2f€", "", totalCommande));
            } else {
                this.afficherTexte("Vous n'avez aucun livre dans votre panier !");
            }

            this.afficherSeperateurMilieu();
            if (detailLivresPanier.size() > 0) {
                this.afficherTexte("P: Passer la commande");
                this.afficherTexte("S: Supprimer un livre");
            }
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String commande = this.obtenirCommandeUtilisateur();
            switch (commande) {
                case "p": {
                    if (!ruptureProduit) {
                        finCommande = this.commander(client, panier);
                    } else {
                        System.err.println("Un ou des articles dans votre panier sont en rupture. Merci de retirer les livres en rupture pour commander.");
                    }
                    break;
                }
                case "s": {
                    this.supprimerLivrePanier(panier);
                    try {
                        this.chaineLibrairie.getPanierBD().updatePanier(panier);
                    } catch (SQLException e) {
                        System.err.println("Une erreur est survenue lors de la mise à jour du panier en base de données : " + e.getMessage());
                    }
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
        List<DetailLivre> detailLivres = panier.getDetailLivres();
        if (detailLivres.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }

        this.afficherTitre("Passer une commande");
        Character modeLivraison = this.demanderModeLivraison();
        if (modeLivraison != null) {
            try {
                boolean commandeReussie = client.commander(modeLivraison, 'O');
                if (commandeReussie) {
                    this.chaineLibrairie.getPanierBD().viderPanier(panier.getId());
                    panier.viderPanier();

                    System.out.println("Merci pour votre commande !");
                }
                return commandeReussie;
            } catch (SQLException e) {
                System.err.println("Une erreur est survenue lors de l'enregistrement de la commande : " + e.getMessage());
                return false;
            }
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

            String commande = this.obtenirCommandeUtilisateur();
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
     * @param panier Son panier.
     * @return true si un livre a été retiré de son panier, sinon false.
     */
    public boolean supprimerLivrePanier(Panier panier) {
        List<DetailLivre> detailLivres = panier.getDetailLivres();
        if (detailLivres.size() == 0) {
            System.err.println("ERREUR: Vous n'avez aucun livre actuellement dans votre panier.");
            return false;
        }

        List<Livre> livresPanier = panier.getLivres();
        ResultatSelection<Livre> resultatSelectionLivre = new ResultatSelection<>();
        while (resultatSelectionLivre != null) {
            resultatSelectionLivre = this.selectionnerElement(livresPanier, resultatSelectionLivre.getNbPage(), "Supprimer un livre du panier");
            if (resultatSelectionLivre != null) {
                Livre livre = resultatSelectionLivre.getElement();

                try {
                    DetailLivre detailLivre = panier.getDetailLivre(livre);
                    Integer quantite = this.demanderQuantiterSupprimer(detailLivre);
                    if (quantite != null) {
                        System.out.println(String.format("Retrait de %dx %s du panier effectuée !", quantite, livre.getTitre()));
                        panier.retirerQuantiteLivre(livre, quantite);
                        return true;
                    }
                } catch (LivreIntrouvableException e) {
                    System.out.println("Livre introuvable, il a été supprimé du panier...");
                }
            }
        }
        return false;
    }

    /**
     * Demander la quantité à supprimer d'un livre dans un panier.
     * @param detailLivre Le détail d'une commande.
     * @return La quantité à supprimer, ou null si opération annulée.
     */
    public Integer demanderQuantiterSupprimer(DetailLivre detailLivre) {
        Integer quantite = null;
        int quantitePanier = detailLivre.getQuantite();
        if (quantitePanier == 1) quantite = 1;

        Livre livre = detailLivre.getLivre();
        while (quantite == null) {
            this.afficherTitre(String.format("Quelle est la quantité du livre %s que vous voulez retirer ?", livre.getTitre()));
            this.afficherTexte(String.format("Il est présent à %d exemplaire(s).", detailLivre.getQuantite()));
            this.afficherSeperateurMilieu();
            this.afficherTexte("Nombre: Quantité à retirer");
            this.afficherTexte("Q: Annuler");
            this.afficherTitreFin();

            String entree = this.obtenirCommandeUtilisateur();
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
        List<Magasin> magasins;
        try {
            magasins = this.chaineLibrairie.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de la récupération des magasins : " + e.getMessage());
            return;
        }

        ResultatSelection<Magasin> resultatSelectionMagasin = this.selectionnerElement(magasins, 0, "Changer de magasin");
        if (resultatSelectionMagasin == null) return;

        Panier panier = client.getPanier();
        Magasin magasin = resultatSelectionMagasin.getElement();
        
        boolean effectuerChangement = false;
        if (panier.getDetailLivres().size() > 0 && !panier.getMagasin().equals(magasin)) {
            effectuerChangement = this.demanderConfirmation(
                String.format("Voulez-vous vraiment définir votre magasin actuel pour %s ?", magasin.toString()), 
                "Vous avez des articles dans votre panier. Changer de magasin réinitialisera votre panier."
            );
            if (!effectuerChangement) return;
        }

        if (effectuerChangement) {
            panier.setMagasin(magasin);
            try {
                this.chaineLibrairie.getPanierBD().changerMagasin(panier);
                System.out.println(String.format("Le magasin a été changé pour %s.", magasin.toString()));
            } catch (SQLException e) {
                System.err.println("Une erreur est survenue lors de la mise à jour du panier en base de données : " + e.getMessage());
            }
        } else {
            System.out.println("Magasin non changé.");
        }
    }

    /**
     * Permettre à un client de consulter la liste de ses commandes et de voir le détail d'une d'entre-elle.
     * @param client Un client.
     */
    public void consulterCommandesClient(Client client) {
        List<Commande> commandes = client.getCommandes();
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

            List<String> detailCommandeTextuel = ChaineLibrairie.genererCorpsCommandeTextuel(commande.getDetailCommandes(), this.longueurAffichage);
            for (String ligne: detailCommandeTextuel) {
                this.afficherTexte(ligne);
            }

            this.afficherSeperateurMilieu();
            this.afficherTexte("Q: Retour");
            this.afficherTitreFin();

            String entreeUtilisateur = this.obtenirCommandeUtilisateur();
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


    //Fonctionnalités administrateur Hashmap

    public void creationCompteVendeur(){
        List<String> donnees = new ArrayList<>();
        donnees.add("Nom");
        donnees.add("Prenom");
        donnees.add("Magasin");
        HashMap<String, String> dicoDonnees = new HashMap<>();


        System.out.println("Pour interrompre, tapez \"exit\"");
        for(String donnee : donnees ){
            System.out.println();
            System.out.println(donnee);
            String entree = this.obtenirEntreeUtilisateur();
            while(!(entree instanceof String)){
                System.out.println("Entrer une chaine de caracteres");
            }
            dicoDonnees.put(donnee, entree);
        }
        System.out.println(dicoDonnees);
    }
}
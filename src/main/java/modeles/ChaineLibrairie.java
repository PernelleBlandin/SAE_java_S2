package modeles;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

/**
 * La chaîne de librairie
 */
public class ChaineLibrairie {

    private ConnexionMariaDB connexionMariaDB;

    private LivreBD livreBD;
    private ClientBD clientBD;
    private CommandeBD commandeBD;
    private PanierBD panierBD;
    private MagasinBD magasinBD;
    private VendeurBD vendeurBD;
    private StatistiquesBD statistiquesBD;

    private Client clientActuel;
    private Vendeur vendeurActuel;

    /**
     * Intiailiser la chaîne de librairie.
     *
     * @param bdHost L'adresse et le port de la base de données.
     * @param bdBase Le nom de la base de données.
     * @param bdLogin Le nom d'utilisateur.
     * @param bdPassword Le mot de passe de l'utilisateur.
     */
    public ChaineLibrairie(String bdHost, String bdBase, String bdLogin, String bdPassword) {
        // Base de données
        try {
            this.connexionMariaDB = new ConnexionMariaDB();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MariaDB non trouvé.");
            System.exit(1);
        }

        try {
            this.connexionMariaDB.connecter(bdHost, bdBase, bdLogin, bdPassword);
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la BD : " + e.getMessage());
            System.exit(1);
        }

        this.livreBD = new LivreBD(this.connexionMariaDB);
        this.clientBD = new ClientBD(this, this.connexionMariaDB);
        this.commandeBD = new CommandeBD(this, this.connexionMariaDB);
        this.panierBD = new PanierBD(this, this.connexionMariaDB);
        this.magasinBD = new MagasinBD(this.connexionMariaDB);
        this.vendeurBD = new VendeurBD(this.connexionMariaDB, this);
        this.statistiquesBD = new StatistiquesBD(this.connexionMariaDB);
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des livres.
     *
     * @return La classe de la base de données pour récupérer des livres.
     */
    public LivreBD getLivreBD() {
        return this.livreBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des clients.
     *
     * @return La classe de la base de données pour récupérer des clients.
     */
    public ClientBD getClientBD() {
        return this.clientBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des commandes.
     *
     * @return La classe de la base de données pour récupérer des commandes.
     */
    public CommandeBD getCommandeBD() {
        return this.commandeBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer les paniers
     * clients.
     *
     * @return La classe de la base de données pour récupérer les paniers
     * clients.
     */
    public PanierBD getPanierBD() {
        return this.panierBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des magasins.
     *
     * @return La classe de la base de données pour récupérer des magasins.
     */
    public MagasinBD getMagasinBD() {
        return this.magasinBD;
    }

    /**
     * Obtenir la classe de la base de données pour récupérer des vendeurs.
     *
     * @return La classe de la base de données pour récupérer des vendeurs.
     */
    public VendeurBD getVendeurBD() {
        return this.vendeurBD;
    }

    /**
     * Obtenir la classe de la base de données pour les statistiques.
     *
     * @return La classe de la base de données pour les statistiques.
     */
    public StatistiquesBD getStatistiquesBD() {
        return this.statistiquesBD;
    }

    /**
     * Définir le client actuel.
     *
     * @param client Un client.
     */
    public void setClientActuel(Client client) {
        this.clientActuel = client;
    }

     /**
     * Définir le vendeur actuel.
     *
     * @param vendeur Un vendeur.
     */
    public void setVendeurActuel(Vendeur vendeur){
        this.vendeurActuel = vendeur;
    }

    /**
     * Obtenir le client actuel.
     *
     * @return Le client actuel.
     */
    public Client getClientActuel() {
        return this.clientActuel;
    }

    /**
     * Obtenir le vendeur actuel.
     *
     * @return Le vendeur actuel.
     */
    public Vendeur getVendeurActuel() {
        return this.vendeurActuel;
    }

    /**
     * Rechercher un livre selon une recherche données.
     *
     * @param livres La liste des livres dans laquelle effectuer la recherche.
     * @param recherche La recherche utilisateur.
     * @return Les livres étant inclus dans la recherche.
     */
    public List<Livre> rechercherLivres(List<Livre> livres, String recherche) {
        List<Livre> livresCorrespondants = new ArrayList<>();
        for (Livre livre : livres) {
            if (livre.estIncluDansRecherche(recherche)) {
                livresCorrespondants.add(livre);
            }
        }
        return livresCorrespondants;
    }

    /**
     * Obtenir le nombre de vente d'un livre dans toute la chaîne de librairie.
     *
     * @param livre Le livre concerné.
     * @return Le nombre de vente du livre dans toute la chaîne de librairie.
     */
    public int getNombreVentesLivre(Livre livre) {
        try {
            return this.getLivreBD().getNombreVentesLivre(livre.getISBN());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du nombre de ventes d'un livre : " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtenir la liste des livres triés par leur nombre de ventes.
     *
     * @param listeLivres La liste de livres initial.
     * @return La liste de livres triés par leur nombre de ventes.
     */
    public List<Livre> getLivresTriesParVentes(List<Livre> listeLivres) {
        ComparatorLivreParVentes comparerVentesLivre = new ComparatorLivreParVentes(this);

        List<Livre> livresTries = new ArrayList<>(listeLivres);
        Collections.sort(livresTries, comparerVentesLivre);
        return livresTries;
    }

    /**
     * Obtenir la liste des livres recommandés pour un client, triés dans
     * l'ordre le plus pertinant. On vérifie d'abord suivant les autres clients,
     * puis selon les classifications similaires du client par rapport à ces
     * dernieres commandes. Enfin, on tri selon le nombre de ventes nationals,
     * notammant en cas d'ex aequo.
     *
     * @param client Le client.
     * @return La liste des livres recommandés pour un client, triés dans
     * l'ordre le plus pertinant.
     * @throws SQLException Exception SQL en cas d'erreur avec la base de
     * données.
     */
    public List<Livre> onVousRecommande(Client client) throws SQLException {
        Magasin magasinClient = client.vendeur();
        List<Livre> listeLivresMagasin = this.livreBD.obtenirLivreEnStockMagasin(magasinClient);

        List<Commande> commandesClient = client.getCommandes();

        Panier panierClient = client.getPanier();
        List<DetailLivre> detailPanierClient = panierClient.getDetailLivres();
        if (commandesClient.size() == 0 && detailPanierClient.size() == 0) {
            return this.getLivresTriesParVentes(listeLivresMagasin);
        }

        // -- Recommendations par rapport aux autres clients
        // On tri par défaut suivant le nombre de ventes en cas d'ex aequo.
        List<Livre> livresNonAchetes = client.getLivresNonAchetes(listeLivresMagasin);
        List<Livre> livresRecommendes = this.getLivresTriesParVentes(livresNonAchetes);

        HashMap<Livre, Integer> recommendationsLivres = new HashMap<>();
        List<Client> clientsCommuns = this.clientBD.obtenirClientsAyantLivresCommuns(client.getId());
        for (Client clientQuelconque : clientsCommuns) {
            List<Livre> livresAchetesParAutreClient = clientQuelconque.getLivresAchetes();
            for (DetailLivre detailCommande : clientQuelconque.getDetailCommandes()) {
                Livre livre = detailCommande.getLivre();
                if (livresRecommendes.contains(livre)) {
                    Integer curLivreRecommendations = recommendationsLivres.get(livre);
                    if (curLivreRecommendations == null) {
                        curLivreRecommendations = 0;
                    }

                    if (livresAchetesParAutreClient.contains(livre)) {
                        curLivreRecommendations += 3;
                    } else {
                        curLivreRecommendations += 2;
                    }
                    recommendationsLivres.put(livre, curLivreRecommendations);
                }
            }
        }

        // Recommendations suivant classifications similaires
        Set<String> classificationsClient = client.getClassifications();
        for (Livre livre : livresRecommendes) {
            for (String classification : livre.getClassifications()) {
                if (classificationsClient.contains(classification)) {
                    Integer curLivreRecommendations = recommendationsLivres.get(livre);
                    if (curLivreRecommendations == null) {
                        curLivreRecommendations = 0;
                    }

                    curLivreRecommendations += 1;
                    recommendationsLivres.put(livre, curLivreRecommendations);
                    break;
                }
            }
        }

        ComparatorLivreRecommandation comparatorRecommendation = new ComparatorLivreRecommandation(
                recommendationsLivres);
        Collections.sort(livresRecommendes, comparatorRecommendation);
        return livresRecommendes;
    }

    /**
     * Exporter les factures d'un mois et d'une année dans le dossier
     * "./factures/annee-mois".
     *
     * @param mois Le mois demandé.
     * @param annee L'année demandé.
     * @throws SQLException En cas d'exception SQL.
     * @throws PasDeCommandeException S'il n'y a pas de factures à exporter.
     */
    public void exporterFactures(int mois, int annee) throws SQLException, PasDeCommandeException {
        ResultSet commandesIterator = this.commandeBD.getCommandesIterator(mois, annee);
        if (!commandesIterator.next()) {
            throw new PasDeCommandeException();
        }

        String dirPath = String.format("./factures/%d-%d", annee, mois);
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // On a utilisé next avant pour l'exception, on refait marche arrière pour la boucle
        commandesIterator.previous();

        Integer curNumCom = null;
        List<String> curCustomersInfos = new ArrayList<>();
        String curTitle = null;
        String curSubTitle = null;
        String detailCommande = null;
        List<DetailLivre> curDetailLivres = new ArrayList<>();

        while (commandesIterator.next()) {
            Integer numCom = commandesIterator.getInt("numcom");
            if (curNumCom == null || !curNumCom.equals(numCom)) {
                if (curNumCom != null) {
                    String filePath = String.format("%s/facture-%d.pdf", dirPath, curNumCom);
                    this.genererFacturePDF(filePath, curCustomersInfos, curTitle, curSubTitle, detailCommande, curDetailLivres);
                }

                String nomcli = commandesIterator.getString("nomcli");
                String prenomcli = commandesIterator.getString("prenomcli");
                String adressecli = commandesIterator.getString("adressecli");
                String codepostal = commandesIterator.getString("codepostal");
                String villecli = commandesIterator.getString("villecli");

                Date datecom = commandesIterator.getDate("datecom");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateDisplay = dateFormat.format(datecom);

                String enLigne = commandesIterator.getString("enligne").equals("O") ? "Commande en ligne" : "Commande sur place";
                String livraison = commandesIterator.getString("livraison").equals("C") ? "Livraison à domicile" : "Récupérer sur place";
                detailCommande = String.format("%s | %s", enLigne, livraison);

                String nomMag = commandesIterator.getString("nommag");

                curCustomersInfos = new ArrayList<>(
                        Arrays.asList(
                                String.format("%s %s", nomcli, prenomcli),
                                adressecli,
                                String.format("%s %s", codepostal, villecli)
                        )
                );
                curTitle = String.format("Commande n°%d du %s", numCom, dateDisplay);
                curSubTitle = String.format("Magasin : %s", nomMag);
                curDetailLivres = new ArrayList<>();
                curNumCom = numCom;
            }

            String isbnLivre = commandesIterator.getString("isbn");
            String titreLivre = commandesIterator.getString("titre");
            Livre livre = new Livre(isbnLivre, titreLivre);

            int quantite = commandesIterator.getInt("qte");
            double prixvente = commandesIterator.getDouble("prixvente");

            DetailLivre detailLivre = new DetailLivre(livre, curDetailLivres.size() + 1, quantite, prixvente);
            curDetailLivres.add(detailLivre);
        }

        // Enregistrer la dernière facture
        String filePath = String.format("%s/facture-%d.pdf", dirPath, curNumCom);
        this.genererFacturePDF(filePath, curCustomersInfos, curTitle, curSubTitle, detailCommande, curDetailLivres);
    }

    private void genererFacturePDF(String path, List<String> customerInfos, String title, String subtitle, String detailsCommande, List<DetailLivre> detailLivres) {
        try {
            Document document = new Document(new PdfDocument(new PdfWriter(path)));

            // Header avec les infos du client
            document.add(new Paragraph(String.join("\n", customerInfos)));

            // Titre principal
            Paragraph titlePDF = new Paragraph(title);
            titlePDF.simulateBold();
            titlePDF.setFontSize(18);
            titlePDF.setTextAlignment(TextAlignment.CENTER);
            titlePDF.setMarginTop(15);
            document.add(titlePDF);

            // Titre secondaire
            Paragraph subtitlePDF = new Paragraph(subtitle);
            subtitlePDF.setFontSize(12);
            subtitlePDF.setTextAlignment(TextAlignment.CENTER);
            subtitlePDF.setMarginTop(10);
            document.add(subtitlePDF);

            // Détails livraison + en ligne
            Paragraph details = new Paragraph(detailsCommande);
            subtitlePDF.setFontSize(10);
            subtitlePDF.setMarginTop(10);
            document.add(details);

            // Table
            Table table = new Table(UnitValue.createPercentArray(new float[]{15, 45, 10, 15, 15}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("ISBN")));
            table.addHeaderCell(new Cell().add(new Paragraph("Titre")));
            table.addHeaderCell(new Cell().add(new Paragraph("Qte")));
            table.addHeaderCell(new Cell().add(new Paragraph("Prix")));
            table.addHeaderCell(new Cell().add(new Paragraph("Total")));

            double totalCommande = 0.00;
            for (DetailLivre detail : detailLivres) {
                totalCommande += detail.getPrixVente() * detail.getQuantite();

                table.addCell(new Cell().add(new Paragraph(detail.getLivre().getISBN())));
                table.addCell(new Cell().add(new Paragraph(detail.getLivre().getTitre())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detail.getQuantite()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f€", detail.getPrixVente()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f€", detail.getPrixVente() * detail.getQuantite()))));
            }
            document.add(table);

            // Montant total
            Paragraph total = new Paragraph(String.format("Total : %6.2f€", totalCommande));
            total.setFontSize(12);
            total.setTextAlignment(TextAlignment.RIGHT);
            total.setMarginTop(5);
            document.add(total);

            document.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'engistrement de la facture " + path);
        }
    }

    /**
     * Génère le corps d'une commande pour l'afficher sous forme de facture
     * notamment.
     *
     * @param detailLivres Les détails des livres, avec leur quantité et prix
     * d'achat.
     * @param longueurAffichage La longueur d'affichage maximal.
     * @return Une liste avec tout le texte nécessaire.
     */
    public static List<String> genererCorpsCommandeTextuel(List<DetailLivre> detailLivres, int longueurAffichage) {
        if (detailLivres.size() == 0) {
            return new ArrayList<>();
        }

        double totalCommande = 0.00;
        List<String> res = new ArrayList<>();
        res.add("       ISBN                               Titre                              Qte    Prix   Total");
        for (int i = 0; i < detailLivres.size(); i++) {
            DetailLivre detailCommande = detailLivres.get(i);
            Livre livre = detailCommande.getLivre();

            String numLigne = String.format("%2s", detailCommande.getNumLigne());
            String isbn = String.format("%13s", livre.getISBN());
            String titre = String.format("%-59s", livre.getTitre());
            String qte = String.format("%3s", detailCommande.getQuantite());
            String prix = String.format("%6.2f€", detailCommande.getPrixVente());
            String total = String.format("%6.2f€", detailCommande.getPrixVente() * detailCommande.getQuantite());

            totalCommande += detailCommande.getPrixVente() * detailCommande.getQuantite();
            res.add(String.format("%s %s %s %s %s %s", numLigne, isbn, titre, qte, prix, total));
        }

        res.add(String.format("%-" + (longueurAffichage - 11) + "s%s", "", "-------"));
        res.add(String.format("%-" + (longueurAffichage - 11) + "s%6.2f€", "", totalCommande));
        return res;
    }
}

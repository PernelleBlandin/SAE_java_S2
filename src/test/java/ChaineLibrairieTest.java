import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ChaineLibrairieTest {
    private ChaineLibrairie chaineLibrairieVide = new ChaineLibrairie();

    private Livre livre1 = new Livre(
        "9782205054750",
        "Simplement 2: 25 ans de commentaires",
        329,
        2025,
        24.99,
        new ArrayList<>(Arrays.asList("Philippe Chéreau", "Christophe Agius")),
        new ArrayList<>(Arrays.asList("Simplement 2")),
        new ArrayList<>(Arrays.asList("Sports"))
    );

    private Livre livre2 = new Livre(
        "9780446570992",
        "Une sacrée envie de foutre le bordel",
        null,
        2024,
        9.99,
        new ArrayList<>(Arrays.asList("Xavier Niel")),
        new ArrayList<>(Arrays.asList("Flammarion")),
        new ArrayList<>(Arrays.asList("Télécom"))
    );

    private Livre livre3 = new Livre(
        "9780340932056",
        "Réseaux & Télécom",
        405,
        2013,
        46.99,
        new ArrayList<>(Arrays.asList("Claude Servi")),
        new ArrayList<>(Arrays.asList("Dunod")),
        new ArrayList<>(Arrays.asList("Télécom"))
    );

    private Livre livre4 = new Livre(
        "9780768939866",
        "Angélique",
        213,
        2023,
        11.99,
        new ArrayList<>(Arrays.asList("Guillaume Musso")),
        new ArrayList<>(Arrays.asList("Lgf")),
        new ArrayList<>(Arrays.asList("Roman"))
    );

    private Livre livre5 = new Livre(
        "9780768939812",
        "Skidamarink",
        313,
        2024,
        13.99,
        new ArrayList<>(Arrays.asList("Guillaume Musso")),
        new ArrayList<>(Arrays.asList("Lgf")),
        new ArrayList<>(Arrays.asList("Roman"))
    );

    private Livre livreInconnu = new Livre(
        "0000000000",
        "Inconnu",
        0,
        2025,
        99.99,
        new ArrayList<>(Arrays.asList()),
        new ArrayList<>(Arrays.asList()),
        new ArrayList<>(Arrays.asList())
    );

    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    private Magasin magasinRennes = new Magasin("3", "Ty Li-Breizh-rie", "Rennes");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");

    // Client 1
    private Panier panierClient1 = new Panier(this.magasinOrleans, new ArrayList<>());
    private Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", this.magasinOrleans, new ArrayList<>(), this.panierClient1);

    // Client 2
    
    private DetailLivre detailCommande1 = new DetailLivre(this.livre4, 1, 2, 11.99);
    private Commande commande1 = new Commande(1, Date.valueOf("2025-09-01"), 'N', 'M', this.magasinMarseille, new ArrayList<>(Arrays.asList(this.detailCommande1)));

    private Panier panierClient2 = new Panier(this.magasinMarseille, new ArrayList<>());
    private Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", this.magasinMarseille, new ArrayList<>(Arrays.asList(this.commande1)), this.panierClient2);

    // Client 3

    private DetailLivre detailCommande2 = new DetailLivre(this.livre4, 1, 3, 11.99);
    private Commande commande2 = new Commande(2, Date.valueOf("2025-09-01"), 'N', 'M', this.magasinMarseille, new ArrayList<>(Arrays.asList(this.detailCommande2)));

    private DetailLivre detailCommande3 = new DetailLivre(this.livre3, 1, 2, 46.99);
    private Commande commande3 = new Commande(3, Date.valueOf("2025-09-01"), 'N', 'M', this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande3)));
    
    private DetailLivre detailCommande4 = new DetailLivre(this.livre2, 1, 1, 9.99);
    private Panier panierClient3 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande4)));
    private Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", this.magasinOrleans, new ArrayList<>(Arrays.asList(this.commande2, this.commande3)), this.panierClient3);

    // Client 4

    private DetailLivre detailCommande5 = new DetailLivre(this.livre2, 1, 1, 11.9);
    private Panier panierClient4 = new Panier(this.magasinParis, new ArrayList<>(Arrays.asList(this.detailCommande5)));
    private Client client4 = new Client(4, "Dubois", "Lucas", "45 place de la Paix", "34000", "Montpellier", this.magasinParis, new ArrayList<>(), this.panierClient4);

    // Client 5

    private DetailLivre detailCommande6 = new DetailLivre(this.livre1, 1, 1, 24.99);
    private Commande commande4 = new Commande(4, Date.valueOf("2025-09-01"), 'N', 'M', this.magasinParis, new ArrayList<>(Arrays.asList(this.detailCommande6)));

    private Panier panierClient5 = new Panier(this.magasinParis, new ArrayList<>());
    private Client client5 = new Client(4, "Guichon", "Julien", "2 rue de la Tour Eiffel", "75000", "Paris", this.magasinParis, new ArrayList<>(Arrays.asList(this.commande4)), this.panierClient5);

    // Client Inconnu

    private Panier panierClientInconnu = new Panier(this.magasinParis, new ArrayList<>());
    private Client clientInconnu = new Client(666, "Inconnu", "Inconnu", "Inconnu", "66666", "Inconnu", this.magasinParis, new ArrayList<>(), this.panierClientInconnu);

    private ChaineLibrairie generatChaineLibrairie() {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        chaineLibrairie.ajouterLivre(this.livre1);
        chaineLibrairie.ajouterLivre(this.livre2);
        chaineLibrairie.ajouterLivre(this.livre3);
        chaineLibrairie.ajouterLivre(this.livre4);
        chaineLibrairie.ajouterLivre(this.livre5);
        
        chaineLibrairie.ajouterMagasin(this.magasinParis);
        chaineLibrairie.ajouterMagasin(this.magasinMarseille);
        chaineLibrairie.ajouterMagasin(this.magasinRennes);
        chaineLibrairie.ajouterMagasin(this.magasinOrleans);

        chaineLibrairie.ajouterClient(this.client1);
        chaineLibrairie.ajouterClient(this.client2);
        chaineLibrairie.ajouterClient(this.client3);
        chaineLibrairie.ajouterClient(this.client4);
        chaineLibrairie.ajouterClient(this.client5);

        return chaineLibrairie;
    }

    @Test
    public void testsGetLivres() {
        assertEquals(new ArrayList<>(), this.chaineLibrairieVide.getLivres());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertEquals(new ArrayList<>(Arrays.asList(this.livre1, this.livre2, this.livre3, this.livre4, this.livre5)), chaineLibrairie.getLivres());
    }

    @Test
    public void testsGetClients() {
        assertEquals(new ArrayList<>(), this.chaineLibrairieVide.getClients());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertEquals(new ArrayList<>(Arrays.asList(this.client1, this.client2, this.client3, this.client4, this.client5)), chaineLibrairie.getClients());
    }

    @Test
    public void testsAjouterLivre() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Livre> livres = chaineLibrairie.getLivres();
        assertEquals(5, livres.size());
        assertFalse(livres.contains(this.livreInconnu));

        chaineLibrairie.ajouterLivre(this.livreInconnu);
        assertEquals(6, livres.size());
        assertTrue(livres.contains(this.livreInconnu));
    }

    @Test
    public void testsAjouterClient() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Client> clients = chaineLibrairie.getClients();
        assertEquals(5, clients.size());
        assertFalse(clients.contains(this.clientInconnu));
        
        chaineLibrairie.ajouterClient(this.clientInconnu);
        assertEquals(6, clients.size());
        assertTrue(clients.contains(this.clientInconnu));
    }

    // @Test
    // public void testsAjouterMagasin() {
    //     ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

    //     List<Magasin> magasins = chaineLibrairie.getMagasins();
    //     assertEquals(4, magasins.size());
    //     assertFalse(magasins.contains(this.magasinLyon));
        
    //     chaineLibrairie.ajouterMagasin(this.magasinLyon);
    //     assertEquals(5, magasins.size());
    //     assertTrue(magasins.contains(this.magasinLyon));
    // }

    @Test
    public void testsRechercherLivres() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        List<Livre> livres = chaineLibrairie.getLivres();

        assertEquals(livres, chaineLibrairie.rechercherLivres(livres, "e"));
        assertEquals(livres, chaineLibrairie.rechercherLivres(livres, ""));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre3)), chaineLibrairie.rechercherLivres(livres, "réseau"));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre2, this.livre3)), chaineLibrairie.rechercherLivres(livres, "télécom"));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre3)), chaineLibrairie.rechercherLivres(livres, "télécom servi"));
        assertEquals(new ArrayList<>(), chaineLibrairie.rechercherLivres(livres, "télécom musso"));
        assertEquals(new ArrayList<>(), chaineLibrairie.rechercherLivres(livres, "zefzef"));
    }

    @Test
    public void testsGetCommandes() {
        ChaineLibrairie chaineLibrairieVide = this.chaineLibrairieVide;
        assertEquals(0, chaineLibrairieVide.getCommandes().size());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        List<Commande> listeCommandes = chaineLibrairie.getCommandes();
        assertEquals(4, listeCommandes.size());
        assertTrue(listeCommandes.contains(this.commande1));
        assertTrue(listeCommandes.contains(this.commande2));
        assertTrue(listeCommandes.contains(this.commande3));
        assertTrue(listeCommandes.contains(this.commande4));
    }

    @Test
    public void testsGetNombreVentesLivre() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        assertEquals(1, chaineLibrairie.getNombreVentesLivre(this.livre1));
        assertEquals(0, chaineLibrairie.getNombreVentesLivre(this.livre2));
        assertEquals(1, chaineLibrairie.getNombreVentesLivre(this.livre3));
        assertEquals(2, chaineLibrairie.getNombreVentesLivre(this.livre4));
        assertEquals(0, chaineLibrairie.getNombreVentesLivre(this.livre5));
        assertEquals(0, chaineLibrairie.getNombreVentesLivre(this.livreInconnu));
    }

    @Test
    public void testsGetLivresTriesParVentes() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Livre> listeTriees = new ArrayList<>(Arrays.asList(this.livre4, this.livre1, this.livre3, this.livre2, this.livre5));
        assertEquals(listeTriees, chaineLibrairie.getLivresTriesParVentes(chaineLibrairie.getLivres()));
    }

    @Test
    public void testsOnVousRecommande() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Livre> recommendationsClient1 = new ArrayList<>(Arrays.asList(this.livre4, this.livre1, this.livre3, this.livre2, this.livre5));
        assertEquals(recommendationsClient1, chaineLibrairie.onVousRecommande(this.client1));

        List<Livre> recommendationsClient2 = new ArrayList<>(Arrays.asList(this.livre3, this.livre2, this.livre5, this.livre1));
        assertEquals(recommendationsClient2, chaineLibrairie.onVousRecommande(this.client2));
        
        List<Livre> recommendationsClient3 = new ArrayList<>(Arrays.asList(this.livre5, this.livre1));
        assertEquals(recommendationsClient3, chaineLibrairie.onVousRecommande(this.client3));

        List<Livre> recommendationsClient4 = new ArrayList<>(Arrays.asList(this.livre4, this.livre3, this.livre1, this.livre5));
        assertEquals(recommendationsClient4, chaineLibrairie.onVousRecommande(this.client4));

        List<Livre> recommendationsClient5 = new ArrayList<>(Arrays.asList(this.livre4, this.livre3, this.livre2, this.livre5));
        assertEquals(recommendationsClient5, chaineLibrairie.onVousRecommande(this.client5));
    }

    @Test
    public void testsGenererCorpsCommandeTextuel() {
        List<DetailLivre> listeCommandesVides = new ArrayList<>();
        assertEquals(new ArrayList<>(), ChaineLibrairie.genererCorpsCommandeTextuel(listeCommandesVides, 100));

        List<DetailLivre> detailCommandesC1 = commande1.getDetailCommandes();
        List<String> listeAttendus = new ArrayList<>(Arrays.asList(
            "       ISBN                               Titre                              Qte    Prix   Total",
            " 1 9780768939866 Angélique                                                     2  11.99€  23.98€",
            "                                                                                         -------",
            "                                                                                          23.98€"

        ));
        assertEquals(listeAttendus, ChaineLibrairie.genererCorpsCommandeTextuel(detailCommandesC1, 100));
    }
}

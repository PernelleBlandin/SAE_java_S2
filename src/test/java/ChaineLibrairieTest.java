import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ChaineLibrairieTest {
    private ChaineLibrairie chaineLibrairieVide = new ChaineLibrairie();

    private Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new ArrayList<>(Arrays.asList(this.leo)),
        new ArrayList<>(Arrays.asList("Dargaud")),
        new ArrayList<>(Arrays.asList("Arts décorartifs"))
    );

    private Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
    private Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new ArrayList<>(Arrays.asList(this.sethGrahameSmith)),
        new ArrayList<>(Arrays.asList("Hachette Book Group Usa")),
        new ArrayList<>(Arrays.asList("Littérature américaine"))
    );

    private Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", null, null);
    private Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new ArrayList<>(Arrays.asList(this.mickInkpen)),
        new ArrayList<>(Arrays.asList("Hodder Children'S")),
        new ArrayList<>(Arrays.asList("Littérature anglaise"))
    );

    private Auteur peterson = new Auteur("OL6835078A", "Peterson's", null, null);
    private Livre livre4 = new Livre(
        "9780768939866",
        "Master the new SAT 2016",
        972,
        2016,
        66.99,
        new ArrayList<>(Arrays.asList(this.peterson)),
        new ArrayList<>(Arrays.asList("Peterson'S")),
        new ArrayList<>(Arrays.asList("éducation"))
    );

    private List<Posseder> listePosessions = new ArrayList<>();
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", this.listePosessions);
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", this.listePosessions);
    private Magasin magasinRennes = new Magasin("3", "Ty Li-Breizh-rie", "Rennes", this.listePosessions);
    private Magasin magasinLyon = new Magasin("4", "LibLyon", "Lyon", this.listePosessions);
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", this.listePosessions);

    private DetailCommande detailCommande1 = new DetailCommande(this.livre1, 1, 2, 7.98);
    private Commande commande1 = new Commande(1, Date.valueOf("2020-08-11"), 'N', 'M', this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande1)));
    private DetailCommande detailCommande2 = new DetailCommande(this.livre2, 1, 1, 16.4);
    private List<DetailCommande> detailCommandesPanierClient1 = new ArrayList<>(Arrays.asList(this.detailCommande2)); 
    private Panier panierClient1 = new Panier(this.magasinOrleans, this.detailCommandesPanierClient1);
    private Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", this.magasinOrleans, new ArrayList<>(Arrays.asList(this.commande1)), this.panierClient1);

    private Panier panierClient2 = new Panier(this.magasinMarseille, new ArrayList<>());
    private Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", this.magasinMarseille, new ArrayList<>(), this.panierClient2);

    private DetailCommande detailCommande3 = new DetailCommande(this.livre3, 2, 4, 11.9);
    private Commande commande2 = new Commande(2, Date.valueOf("2021-09-01"), 'N', 'M', this.magasinParis, new ArrayList<>(Arrays.asList(this.detailCommande2, this.detailCommande2)));
    private Panier panierClient3 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande3)));
    private Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", this.magasinOrleans, new ArrayList<>(Arrays.asList(this.commande2)), this.panierClient3);

    private Panier panierClient4 = new Panier(this.magasinParis, new ArrayList<>());
    private Client client4 = new Client(4, "Dubois", "Lucas", "45 place de la Paix", "34000", "Montpellier", this.magasinParis, new ArrayList<>(), this.panierClient4);

    private ChaineLibrairie generatChaineLibrairie() {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        chaineLibrairie.ajouterLivre(this.livre1);
        chaineLibrairie.ajouterLivre(this.livre2);
        chaineLibrairie.ajouterLivre(this.livre3);
        
        chaineLibrairie.ajouterMagasin(this.magasinParis);
        chaineLibrairie.ajouterMagasin(this.magasinMarseille);
        chaineLibrairie.ajouterMagasin(this.magasinRennes);
        chaineLibrairie.ajouterMagasin(this.magasinOrleans);

        chaineLibrairie.ajouterClient(this.client1);
        chaineLibrairie.ajouterClient(this.client2);
        chaineLibrairie.ajouterClient(this.client3);

        return chaineLibrairie;
    }

    @Test
    public void testsGetLivres() {
        assertEquals(new ArrayList<>(), this.chaineLibrairieVide.getLivres());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertEquals(new ArrayList<>(Arrays.asList(this.livre1, this.livre2, this.livre3)), chaineLibrairie.getLivres());
    }

    @Test
    public void testsGetClients() {
        assertEquals(new ArrayList<>(), this.chaineLibrairieVide.getClients());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertEquals(new ArrayList<>(Arrays.asList(this.client1, this.client2, this.client3)), chaineLibrairie.getClients());
    }

    @Test
    public void testsGetMagasins() {
        assertEquals(new ArrayList<>(), this.chaineLibrairieVide.getMagasins());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertEquals(new ArrayList<>(Arrays.asList(this.magasinParis, this.magasinMarseille, this.magasinRennes, this.magasinOrleans)), chaineLibrairie.getMagasins());
    }

    @Test
    public void testsAjouterLivre() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Livre> livres = chaineLibrairie.getLivres();
        assertEquals(3, livres.size());
        assertFalse(livres.contains(this.livre4));

        chaineLibrairie.ajouterLivre(this.livre4);
        assertEquals(4, livres.size());
        assertTrue(livres.contains(this.livre4));
    }

    @Test
    public void testsAjouterClient() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Client> clients = chaineLibrairie.getClients();
        assertEquals(3, clients.size());
        assertFalse(clients.contains(this.client4));
        
        chaineLibrairie.ajouterClient(this.client4);
        assertEquals(4, clients.size());
        assertTrue(clients.contains(this.client4));
    }

    @Test
    public void testsAjouterMagasin() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Magasin> magasins = chaineLibrairie.getMagasins();
        assertEquals(4, magasins.size());
        assertFalse(magasins.contains(this.magasinLyon));
        
        chaineLibrairie.ajouterMagasin(this.magasinLyon);
        assertEquals(5, magasins.size());
        assertTrue(magasins.contains(this.magasinLyon));
    }

    @Test
    public void testsTrouverClient() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        assertNull(chaineLibrairie.trouverClient("Dubois", "Toto"));
        assertNull(chaineLibrairie.trouverClient("Rodriguez", "Fatimo"));

        assertEquals(this.client1, chaineLibrairie.trouverClient("rodriguez", "fatima"));
        assertEquals(this.client1, chaineLibrairie.trouverClient("RODRIGUEZ", "FATIMA"));

        assertEquals(this.client1, chaineLibrairie.trouverClient("Rodriguez", "Fatima"));
        assertEquals(this.client2, chaineLibrairie.trouverClient("Garcia", "Hugo"));
        assertEquals(this.client3, chaineLibrairie.trouverClient("Martin", "Julie"));
    }

    @Test
    public void testsRechercherLivres() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        List<Livre> livres = chaineLibrairie.getLivres();

        assertEquals(livres, chaineLibrairie.rechercherLivres(livres, "e"));
        assertEquals(livres, chaineLibrairie.rechercherLivres(livres, ""));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre3)), chaineLibrairie.rechercherLivres(livres, "Kipper"));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre3)), chaineLibrairie.rechercherLivres(livres, "kipper"));
        assertEquals(new ArrayList<>(Arrays.asList(this.livre3)), chaineLibrairie.rechercherLivres(livres, "kipper mick inkpen"));
        assertEquals(new ArrayList<>(), chaineLibrairie.rechercherLivres(livres, "kipper micks inkpen"));
        assertEquals(new ArrayList<>(), chaineLibrairie.rechercherLivres(livres, "zefzef"));
    }

    @Test
    public void testsGetCommandes() {
        ChaineLibrairie chaineLibrairieVide = this.chaineLibrairieVide;
        assertEquals(0, chaineLibrairieVide.getCommandes().size());

        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();
        List<Commande> listeCommandes = chaineLibrairie.getCommandes();
        assertEquals(2, listeCommandes.size());
        assertTrue(listeCommandes.contains(this.commande1));
        assertTrue(listeCommandes.contains(this.commande2));
    }

    @Test
    public void testsGetNombreVentesLivre() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        assertEquals(1, chaineLibrairie.getNombreVentesLivre(this.livre1));
        assertEquals(2, chaineLibrairie.getNombreVentesLivre(this.livre2));
        assertEquals(0, chaineLibrairie.getNombreVentesLivre(this.livre3));
        assertEquals(0, chaineLibrairie.getNombreVentesLivre(this.livre4));
    }

    @Test
    public void testsGetLivresTriesParVentes() {
        ChaineLibrairie chaineLibrairie = this.generatChaineLibrairie();

        List<Livre> listeTries = new ArrayList<>(Arrays.asList(this.livre2, this.livre1, this.livre3));
        assertEquals(listeTries, chaineLibrairie.getLivresTriesParVentes(chaineLibrairie.getLivres()));
    }
}

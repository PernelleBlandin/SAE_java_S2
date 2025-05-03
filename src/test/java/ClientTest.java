import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ClientTest {
    private static List<Posseder> listePosessions = new ArrayList<>();
    private static Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", listePosessions);
    private static Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", listePosessions);
    private static Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", listePosessions);

    private static Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private static Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new ArrayList<>(Arrays.asList(leo)),
        new ArrayList<>(Arrays.asList("Dargaud")),
        new ArrayList<>(Arrays.asList("Arts décorartifs"))
    );

    private static Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
    private static Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new ArrayList<>(Arrays.asList(sethGrahameSmith)),
        new ArrayList<>(Arrays.asList("Hachette Book Group Usa")),
        new ArrayList<>(Arrays.asList("Littérature américaine"))
    );

    private static Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", null, null);
    private static Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new ArrayList<>(Arrays.asList(mickInkpen)),
        new ArrayList<>(Arrays.asList("Hodder Children'S")),
        new ArrayList<>(Arrays.asList("Littérature anglaise"))
    );

    private static Livre livre4 = new Livre(
        "9780148104904",
        "Cooper",
        54,
        2009,
        8.99,
        new ArrayList<>(Arrays.asList(mickInkpen)),
        new ArrayList<>(Arrays.asList("Hodder Children'S")),
        new ArrayList<>(Arrays.asList("Littérature anglaise"))
    );

    // Client 1
    private static DetailCommande detailCommande1 = new DetailCommande(livre1, 1, 2, 7.98);
    private static Commande commande1 = new Commande(1, Date.valueOf("2020-08-11"), 'N', 'M', magasinOrleans, new ArrayList<>(Arrays.asList(detailCommande1)));
    private static List<Commande> commandesClient1 = new ArrayList<>(Arrays.asList(commande1));
    private static DetailCommande detailCommande2 = new DetailCommande(livre2, 1, 1, 16.4);

    private static List<DetailCommande> detailCommandesPanierClient1 = new ArrayList<>(Arrays.asList(detailCommande2)); 
    private static Panier panierClient1 = new Panier(magasinOrleans, detailCommandesPanierClient1);
    private static Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, commandesClient1, panierClient1);

    // Client 2
    private static List<Commande> commandesClient2 = new ArrayList<>();
    private static Panier panierClient2 = new Panier(magasinMarseille, new ArrayList<>());
    private static Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, commandesClient2, panierClient2);

    // Client 3
    private static DetailCommande detailCommande3 = new DetailCommande(livre3, 2, 4, 11.9);
    private static Commande commande2 = new Commande(2, Date.valueOf("2021-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande2, detailCommande3)));
    private static DetailCommande detailCommande4 = new DetailCommande(livre4, 2, 4, 8.99);
    private static Commande commande3 = new Commande(3, Date.valueOf("2024-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande4)));
    private static List<Commande> commandesClient3 = new ArrayList<>(Arrays.asList(commande2, commande3));
    private static Panier panierClient3 = new Panier(magasinOrleans, new ArrayList<>());
    private static Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, commandesClient3, panierClient3);

    @Test
    public void testsGetId() {
        assertEquals(1, client1.getId());
        assertEquals(2, client2.getId());
        assertEquals(3, client3.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Rodriguez", client1.getNom());
        assertEquals("Garcia", client2.getNom());
        assertEquals("Martin", client3.getNom());
    }

    @Test
    public void testsGetPrenom() {
        assertEquals("Fatima", client1.getPrenom());
        assertEquals("Hugo", client2.getPrenom());
        assertEquals("Julie", client3.getPrenom());
    }

    @Test
    public void testsGetAdresse() {
        assertEquals("188 chemin de la Forêt", client1.getAdresse());
        assertEquals("167 avenue de la Forêt", client2.getAdresse());
        assertEquals("133 boulevard de l'Université", client3.getAdresse());
    }

    @Test
    public void testsGetCodePostal() {
        assertEquals("45000", client1.getCodePostal());
        assertEquals("06000", client2.getCodePostal());
        assertEquals("45000", client3.getCodePostal());
    }

    @Test
    public void testsGetVille() {
        assertEquals("Orléans", client1.getVille());
        assertEquals("Nice", client2.getVille());
        assertEquals("Orléans", client3.getVille());
    }

    @Test
    public void testsGetMagasin() {
        assertEquals(magasinOrleans, client1.getMagasin());
        assertEquals(magasinMarseille, client2.getMagasin());
        assertEquals(magasinOrleans, client3.getMagasin());
    }

    @Test
    public void testsGetCommandes() {
        assertEquals(commandesClient1, client1.getCommandes());
        assertEquals(commandesClient2, client2.getCommandes());
        assertEquals(commandesClient3, client3.getCommandes());
    }

    @Test
    public void testsGetPanier() {
        assertEquals(panierClient1, client1.getPanier());
        assertEquals(panierClient2, client2.getPanier());
        assertEquals(panierClient3, client3.getPanier());
    }

    @Test
    public void testsSetPanier() {
        Client client1Copie = new Client(client1);
        assertEquals(panierClient1, client1Copie.getPanier());
        client1Copie.setPanier(panierClient2);
        assertEquals(panierClient2, client1Copie.getPanier());

        Client client2Copie = new Client(client2);
        assertEquals(panierClient2, client2Copie.getPanier());
        client2Copie.setPanier(panierClient3);
        assertEquals(panierClient3, client2Copie.getPanier());

        Client client3Copie = new Client(client3);
        assertEquals(panierClient3, client3Copie.getPanier());
        client3Copie.setPanier(panierClient1);
        assertEquals(panierClient1, client3Copie.getPanier());
    }

    @Test
    public void testsSetMagasin() {
        Client client1Copie = new Client(client1);
        assertEquals(magasinOrleans, client1Copie.getMagasin());
        client1Copie.setMagasin(magasinParis);
        assertEquals(magasinParis, client1Copie.getMagasin());

        Client client2Copie = new Client(client2);
        assertEquals(magasinMarseille, client2Copie.getMagasin());
        client2Copie.setMagasin(magasinOrleans);
        assertEquals(magasinOrleans, client2Copie.getMagasin());

        Client client3Copie = new Client(client3);
        assertEquals(magasinOrleans, client3Copie.getMagasin());
        client3Copie.setMagasin(magasinMarseille);
        assertEquals(magasinMarseille, client3Copie.getMagasin());
    }

    @Test
    public void testsCommander() {
        Client client1Copie = new Client(client1);
        List<Commande> commandesClient1Copie = new ArrayList<>(client1Copie.getCommandes());
        assertEquals(commandesClient1Copie.size(), client1Copie.getCommandes().size());
        assertTrue(client1Copie.commander('M', 'O'));
        assertEquals(commandesClient1Copie.size() + 1, client1Copie.getCommandes().size());

        Client client2Copie = new Client(client2);
        List<Commande> commandesClient2Copie = new ArrayList<>(client2Copie.getCommandes());
        assertFalse(client2Copie.commander('M', 'O'));
        assertEquals(commandesClient2Copie, client2Copie.getCommandes());

        Client client3Copie = new Client(client3);
        List<Commande> commandesClient3Copie = new ArrayList<>(client3Copie.getCommandes());
        assertFalse(client3Copie.commander('C', 'N'));
        assertEquals(commandesClient3Copie, client3Copie.getCommandes());
    }

    @Test
    public void testsGetLivresAchetes() {
        Set<Livre> livresClient1 = new HashSet<>(Arrays.asList(livre1, livre2));
        assertEquals(livresClient1, client1.getLivresAchetes());

        Set<Livre> livresClient2 = new HashSet<>();
        assertEquals(livresClient2, client2.getLivresAchetes());

        Set<Livre> livresClient3 = new HashSet<>(Arrays.asList(livre2, livre3, livre4));
        assertEquals(livresClient3, client3.getLivresAchetes());
    }

    @Test
    public void testsGetClassificationsOccurence() {
        HashMap<String, Integer> classificationsClient1 = new HashMap<>();
        classificationsClient1.put("Arts décorartifs", 1);
        classificationsClient1.put("Littérature américaine", 1);
        assertEquals(classificationsClient1, client1.getClassificationsOccurence());

        HashMap<String, Integer> classificationsClient2 = new HashMap<>();
        assertEquals(classificationsClient2, client2.getClassificationsOccurence());

        HashMap<String, Integer> classificationsClient3 = new HashMap<>();
        classificationsClient3.put("Littérature américaine", 1);
        classificationsClient3.put("Littérature anglaise", 2);
        assertEquals(classificationsClient3, client3.getClassificationsOccurence());
    }

    @Test
    public void testsGetCommandesTriesParDateDesc() {
        List<Commande> commandesTrieesClient1 = new ArrayList<>(Arrays.asList(commande1));
        assertEquals(commandesTrieesClient1, client1.getCommandesTriesParDateDesc());

        List<Commande> commandesTrieesClient2 = new ArrayList<>();
        assertEquals(commandesTrieesClient2, client2.getCommandesTriesParDateDesc());

        List<Commande> commandesTrieesClient3 = new ArrayList<>(Arrays.asList(commande3, commande2));
        assertEquals(commandesTrieesClient3, client3.getCommandesTriesParDateDesc());
    }

    @Test
    public void testsToString() {
        assertEquals("Fatima Rodriguez", client1.toString());
        assertEquals("Hugo Garcia", client2.toString());
        assertEquals("Julie Martin", client3.toString());
    }
}

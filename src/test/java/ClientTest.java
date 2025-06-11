import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ClientTest {
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");

    private Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new HashSet<>(Arrays.asList("Léo")),
        new HashSet<>(Arrays.asList("Dargaud")),
        new HashSet<>(Arrays.asList("Arts décorartifs"))
    );

    private Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new HashSet<>(Arrays.asList("Seth Grahame-Smith")),
        new HashSet<>(Arrays.asList("Hachette Book Group Usa")),
        new HashSet<>(Arrays.asList("Littérature américaine"))
    );

    private Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new HashSet<>(Arrays.asList("Mick Inkpen")),
        new HashSet<>(Arrays.asList("Hodder Children'S")),
        new HashSet<>(Arrays.asList("Littérature anglaise"))
    );

    private Livre livre4 = new Livre(
        "9780148104904",
        "Cooper",
        54,
        2009,
        8.99,
        new HashSet<>(Arrays.asList("Mick Inkpen")),
        new HashSet<>(Arrays.asList("Hodder Children'S")),
        new HashSet<>(Arrays.asList("Littérature anglaise"))
    );

    // Client 1
    private DetailLivre detailCommande1 = new DetailLivre(livre1, 1, 2, 7.98);
    private Commande commande1 = new Commande(1, Date.valueOf("2020-08-11"), 'N', 'M', magasinOrleans, new ArrayList<>(Arrays.asList(detailCommande1)));
    private List<Commande> commandesClient1 = new ArrayList<>(Arrays.asList(commande1));
    
    private DetailLivre detailCommande2 = new DetailLivre(livre2, 1, 1, 16.4);
    private List<DetailLivre> detailCommandesPanierClient1 = new ArrayList<>(Arrays.asList(detailCommande2)); 
    private Panier panierClient1 = new Panier(1, magasinOrleans, detailCommandesPanierClient1);
    private Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, commandesClient1, panierClient1, null);

    // Client 2
    private List<Commande> commandesClient2 = new ArrayList<>();
    private Panier panierClient2 = new Panier(2, magasinMarseille, new ArrayList<>());
    private Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, commandesClient2, panierClient2, null);

    // Client 3
    private DetailLivre detailCommande3 = new DetailLivre(livre3, 2, 4, 11.9);
    private Commande commande2 = new Commande(2, Date.valueOf("2021-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande2, detailCommande3)));
    private DetailLivre detailCommande4 = new DetailLivre(livre4, 2, 4, 8.99);
    private Commande commande3 = new Commande(3, Date.valueOf("2024-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande4)));
    private List<Commande> commandesClient3 = new ArrayList<>(Arrays.asList(commande2, commande3));
    private Panier panierClient3 = new Panier(3, magasinOrleans, new ArrayList<>());
    private Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, commandesClient3, panierClient3, null);

    @Test
    public void testsGetId() {
        assertEquals(1, this.client1.getId());
        assertEquals(2, this.client2.getId());
        assertEquals(3, this.client3.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Rodriguez", this.client1.getNom());
        assertEquals("Garcia", this.client2.getNom());
        assertEquals("Martin", this.client3.getNom());
    }

    @Test
    public void testsGetPrenom() {
        assertEquals("Fatima", this.client1.getPrenom());
        assertEquals("Hugo", this.client2.getPrenom());
        assertEquals("Julie", this.client3.getPrenom());
    }

    @Test
    public void testsGetAdresse() {
        assertEquals("188 chemin de la Forêt", this.client1.getAdresse());
        assertEquals("167 avenue de la Forêt", this.client2.getAdresse());
        assertEquals("133 boulevard de l'Université", this.client3.getAdresse());
    }

    @Test
    public void testsGetCodePostal() {
        assertEquals("45000", this.client1.getCodePostal());
        assertEquals("06000", this.client2.getCodePostal());
        assertEquals("45000", this.client3.getCodePostal());
    }

    @Test
    public void testsGetVille() {
        assertEquals("Orléans", this.client1.getVille());
        assertEquals("Nice", this.client2.getVille());
        assertEquals("Orléans", this.client3.getVille());
    }

    @Test
    public void testsGetMagasin() {
        assertEquals(magasinOrleans, this.client1.getMagasin());
        assertEquals(magasinMarseille, this.client2.getMagasin());
        assertEquals(magasinOrleans, this.client3.getMagasin());
    }

    @Test
    public void testsGetCommandes() {
        assertEquals(commandesClient1, this.client1.getCommandes());
        assertEquals(commandesClient2, this.client2.getCommandes());
        assertEquals(commandesClient3, this.client3.getCommandes());
    }

    @Test
    public void testsGetPanier() {
        assertEquals(panierClient1, this.client1.getPanier());
        assertEquals(panierClient2, this.client2.getPanier());
        assertEquals(panierClient3, this.client3.getPanier());
    }

    @Test
    public void testsSetPanier() {
        Client client1Copie = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, commandesClient1, panierClient1, null);
        assertEquals(this.panierClient1, client1Copie.getPanier());
        client1Copie.setPanier(this.panierClient2);
        assertEquals(this.panierClient2, client1Copie.getPanier());

        Client client2Copie = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, commandesClient2, panierClient2, null);
        assertEquals(this.panierClient2, client2Copie.getPanier());
        client2Copie.setPanier(this.panierClient3);
        assertEquals(this.panierClient3, client2Copie.getPanier());

        Client client3Copie = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, commandesClient3, panierClient3, null);
        assertEquals(this.panierClient3, client3Copie.getPanier());
        client3Copie.setPanier(this.panierClient1);
        assertEquals(this.panierClient1, client3Copie.getPanier());
    }

    @Test
    public void testsSetMagasin() {
        Client client1Copie = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, commandesClient1, panierClient1, null);
        assertEquals(this.magasinOrleans, client1Copie.getMagasin());
        client1Copie.setMagasin(this.magasinParis);
        assertEquals(this.magasinParis, client1Copie.getMagasin());

        Client client2Copie = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, commandesClient2, panierClient2, null);
        assertEquals(this.magasinMarseille, client2Copie.getMagasin());
        client2Copie.setMagasin(this.magasinOrleans);
        assertEquals(this.magasinOrleans, client2Copie.getMagasin());

        Client client3Copie = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, commandesClient3, panierClient3, null);
        assertEquals(this.magasinOrleans, client3Copie.getMagasin());
        client3Copie.setMagasin(this.magasinMarseille);
        assertEquals(this.magasinMarseille, client3Copie.getMagasin());
    }

    @Test
    public void testsGetDetailCommandes() {
        List<DetailLivre> detailLivresClient1 = new ArrayList<>(Arrays.asList(this.detailCommande1, this.detailCommande2));
        assertEquals(detailLivresClient1, this.client1.getDetailCommandes());

        List<DetailLivre> detailLivresClient2 = new ArrayList<>();
        assertEquals(detailLivresClient2, this.client2.getDetailCommandes());

        List<DetailLivre> detailLivresClient3 = new ArrayList<>(Arrays.asList(this.detailCommande2, this.detailCommande3, this.detailCommande4));
        assertEquals(detailLivresClient3, this.client3.getDetailCommandes());
    }

    @Test
    public void testsGetLivresAchetes() {
        List<Livre> livresClient1 = new ArrayList<>(Arrays.asList(this.livre1));
        assertEquals(livresClient1, this.client1.getLivresAchetes());

        List<Livre> livresClient2 = new ArrayList<>();
        assertEquals(livresClient2, this.client2.getLivresAchetes());

        List<Livre> livresClient3 = new ArrayList<>(Arrays.asList(this.livre2, this.livre3, this.livre4));
        assertEquals(livresClient3, this.client3.getLivresAchetes());
    }

    @Test
    public void testsGetLivresNonAchetes() {
        List<Livre> tousLesLivres = new ArrayList<>(Arrays.asList(this.livre1, this.livre2, this.livre3, this.livre4));

        List<Livre> livresNonAchetesClient1 = new ArrayList<>(Arrays.asList(this.livre3, this.livre4));
        assertEquals(livresNonAchetesClient1, this.client1.getLivresNonAchetes(tousLesLivres));

        List<Livre> livresNonAchetesClient2 = new ArrayList<>(Arrays.asList(this.livre1, this.livre2, this.livre3, this.livre4));
        assertEquals(livresNonAchetesClient2, this.client2.getLivresNonAchetes(tousLesLivres));

        List<Livre> livresNonAchetesClient3 = new ArrayList<>(Arrays.asList(this.livre1));
        assertEquals(livresNonAchetesClient3, this.client3.getLivresNonAchetes(tousLesLivres));
    }

    @Test
    public void testsGetClassifications() {
        Set<String> classificationsClient1 = new HashSet<>(Arrays.asList("Arts décorartifs"));
        assertEquals(classificationsClient1, this.client1.getClassifications());
    
        Set<String> classificationsClient2 = new HashSet<>(Arrays.asList());
        assertEquals(classificationsClient2, this.client2.getClassifications());

        Set<String> classificationsClient3 = new HashSet<>(Arrays.asList("Littérature américaine", "Littérature anglaise"));
        assertEquals(classificationsClient3, this.client3.getClassifications());
    }

    @Test
    public void testsToString() {
        assertEquals("Fatima Rodriguez", this.client1.toString());
        assertEquals("Hugo Garcia", this.client2.toString());
        assertEquals("Julie Martin", this.client3.toString());
    }
}

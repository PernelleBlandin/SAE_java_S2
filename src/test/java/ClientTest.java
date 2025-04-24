import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ClientTest {
    private static List<Posseder> listePosessions = new ArrayList<>();
    private static Magasin magasin1 = new Magasin("1", "Paris Centre", "2 Grande Rue", "75000", "Paris", listePosessions);
    private static Magasin magasin2 = new Magasin("2", "Orléans Sud", "3 rue de l'olive", "45160", "Olivet", listePosessions);

    // TODO: Ajouter des livres aux commandes
    private static List<Commande> commandes1 = new ArrayList<>();
    private static List<Commande> commandes2 = new ArrayList<>();
    private static List<Commande> commandes3 = new ArrayList<>();

    // TODO: Ajouter données
    private static Panier panier1 = new Panier(magasin1);
    private static Panier panier2 = new Panier(magasin2);

    private static Client client1 = new Client(1, "DUPONT", "Robin", "5 rue de la terre", "75000", "Paris", magasin1, commandes1, panier1);
    private static Client client2 = new Client(2, "DUBOIS", "Sophie", "57 rue du lac", "13000", "Marseille", magasin2, commandes2, panier2);
    private static Client client3 = new Client(3, "ROBERT", "Pierre", "9 route de la paix", "45000", "Orléans", magasin2, commandes3, panier2);

    @Test
    public void testsGetId() {
        assertEquals(1, client1.getId());
        assertEquals(2, client2.getId());
        assertEquals(3, client3.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("DUPONT", client1.getNom());
        assertEquals("DUBOIS", client2.getNom());
        assertEquals("ROBERT", client3.getNom());
    }

    @Test
    public void testsGetPrenom() {
        assertEquals("Robin", client1.getPrenom());
        assertEquals("Sophie", client2.getPrenom());
        assertEquals("Pierre", client3.getPrenom());
    }

    @Test
    public void testsGetAdresse() {
        assertEquals("5 rue de la terre", client1.getAdresse());
        assertEquals("57 rue du lac", client2.getAdresse());
        assertEquals("9 route de la paix", client3.getAdresse());
    }

    @Test
    public void testsGetCodePostal() {
        assertEquals("75000", client1.getCodePostal());
        assertEquals("13000", client2.getCodePostal());
        assertEquals("45000", client3.getCodePostal());
    }

    @Test
    public void testsGetVille() {
        assertEquals("Paris", client1.getVille());
        assertEquals("Marseille", client2.getVille());
        assertEquals("Orléans", client3.getVille());
    }

    // TODO: Implémenter
    // @Test
    // public void testsGetCommandes() {
    //     assertEquals(new ArrayList<>());
    // }

    // TODO: Implémenter
    // @Test
    // public void testsGetPanier() {
    //     assertEquals(new ArrayList<>());
    // }

    // TODO: Implémenter
    // @Test
    // public void toString() {
    //     assertEquals(new ArrayList<>());
    // }
}

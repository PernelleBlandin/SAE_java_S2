import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ClientTest {
    private static Client client1 = new Client(1, "DUPONT", "Robin", "5 rue de la terre", "75000", "Paris");
    private static Client client2 = new Client(2, "DUBOIS", "Sophie", "57 rue du lac", "13000", "Marseille");
    private static Client client3 = new Client(3, "ROBERT", "Pierre", "9 route de la paix", "45000", "Orléans");

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
}

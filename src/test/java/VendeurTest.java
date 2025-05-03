import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VendeurTest {
    private static List<Posseder> listePosessions = new ArrayList<>();
    private static Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", listePosessions);
    private static Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", listePosessions);
    private static Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", listePosessions);
    
    private static Vendeur vendeur1 = new Vendeur(1, "Dubois", "Robert", magasinParis);
    private static Vendeur vendeur2 = new Vendeur(2, "Dupont", "Léa", magasinOrleans);
    private static Vendeur vendeur3 = new Vendeur(3, "Robert", "Paul", magasinMarseille);

    @Test
    public void testsGetId() {
        assertEquals(1, vendeur1.getId());
        assertEquals(2, vendeur2.getId());
        assertEquals(3, vendeur3.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Dubois", vendeur1.getNom());
        assertEquals("Dupont", vendeur2.getNom());
        assertEquals("Robert", vendeur3.getNom());
    }

    @Test
    public void testsGetPrenom() {
        assertEquals("Robert", vendeur1.getPrenom());
        assertEquals("Léa", vendeur2.getPrenom());
        assertEquals("Paul", vendeur3.getPrenom());
    }

    @Test
    public void testsGetMagasin() {
        assertEquals(magasinParis, vendeur1.getMagasin());
        assertEquals(magasinOrleans, vendeur2.getMagasin());
        assertEquals(magasinMarseille, vendeur3.getMagasin());
    }

    @Test
    public void testsGetRole() {
        assertEquals("Vendeur", vendeur1.getRole());
        assertEquals("Vendeur", vendeur2.getRole());
        assertEquals("Vendeur", vendeur3.getRole());
    }
}

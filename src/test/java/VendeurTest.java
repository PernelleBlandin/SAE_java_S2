import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VendeurTest {
    private List<Posseder> listePosessions = new ArrayList<>();
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", listePosessions);
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", listePosessions);
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", listePosessions);
    
    private Vendeur vendeur1 = new Vendeur(1, "Dubois", "Robert", magasinParis);
    private Vendeur vendeur2 = new Vendeur(2, "Dupont", "Léa", magasinOrleans);
    private Vendeur vendeur3 = new Vendeur(3, "Robert", "Paul", magasinMarseille);

    @Test
    public void testsGetId() {
        assertEquals(1, this.vendeur1.getId());
        assertEquals(2, this.vendeur2.getId());
        assertEquals(3, this.vendeur3.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Dubois", this.vendeur1.getNom());
        assertEquals("Dupont", this.vendeur2.getNom());
        assertEquals("Robert", this.vendeur3.getNom());
    }

    @Test
    public void testsGetPrenom() {
        assertEquals("Robert", this.vendeur1.getPrenom());
        assertEquals("Léa", this.vendeur2.getPrenom());
        assertEquals("Paul", this.vendeur3.getPrenom());
    }

    @Test
    public void testsGetMagasin() {
        assertEquals(magasinParis, this.vendeur1.getMagasin());
        assertEquals(magasinOrleans, this.vendeur2.getMagasin());
        assertEquals(magasinMarseille, this.vendeur3.getMagasin());
    }

    @Test
    public void testsGetRole() {
        assertEquals("Vendeur", this.vendeur1.getRole());
        assertEquals("Vendeur", this.vendeur2.getRole());
        assertEquals("Vendeur", this.vendeur3.getRole());
    }
}

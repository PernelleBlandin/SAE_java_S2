import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeles.Magasin;
import modeles.Vendeur;


public class VendeurTest {
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");
    
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
}

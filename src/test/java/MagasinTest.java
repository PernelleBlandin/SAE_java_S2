import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modeles.Magasin;

public class MagasinTest {
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");

    @Test
    public void testsGetId() {
        assertEquals("1", this.magasinParis.getId());
        assertEquals("2", this.magasinMarseille.getId());
        assertEquals("7", this.magasinOrleans.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("La librairie parisienne", this.magasinParis.getNom());
        assertEquals("Cap au Sud", this.magasinMarseille.getNom());
        assertEquals("Loire et livres", this.magasinOrleans.getNom());
    }

    @Test
    public void testsGetVille() {
        assertEquals("Paris", this.magasinParis.getVille());
        assertEquals("Marseille", this.magasinMarseille.getVille());
        assertEquals("Orléans", this.magasinOrleans.getVille());
    }

    @Test
    public void testsToString() {
        assertEquals("La librairie parisienne (Paris)", this.magasinParis.toString());
        assertEquals("Cap au Sud (Marseille)", this.magasinMarseille.toString());
        assertEquals("Loire et livres (Orléans)", this.magasinOrleans.toString());
    }
}

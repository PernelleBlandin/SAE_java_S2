import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MagasinTest {
    private static List<Posseder> listePosessions = new ArrayList<>();
    private static Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", listePosessions);
    private static Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", listePosessions);
    private static Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", listePosessions);

    @Test
    public void testsGetId() {
        assertEquals("1", magasinParis.getId());
        assertEquals("2", magasinMarseille.getId());
        assertEquals("7", magasinOrleans.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("La librairie parisienne", magasinParis.getNom());
        assertEquals("Cap au Sud", magasinMarseille.getNom());
        assertEquals("Loire et livres", magasinOrleans.getNom());
    }

    @Test
    public void testsGetVille() {
        assertEquals("Paris", magasinParis.getVille());
        assertEquals("Marseille", magasinMarseille.getVille());
        assertEquals("Orléans", magasinOrleans.getVille());
    }

    @Test
    public void testsToString() {
        assertEquals("La librairie parisienne (Paris)", magasinParis.toString());
        assertEquals("Cap au Sud (Marseille)", magasinMarseille.toString());
        assertEquals("Loire et livres (Orléans)", magasinOrleans.toString());
    }
}

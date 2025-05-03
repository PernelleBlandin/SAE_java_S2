import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AuteurTest {
    private static Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private static Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
    private static Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", 1936, 2024);

    @Test
    public void testsGetId() {
        assertEquals("OL7572575A", leo.getId());
        assertEquals("OL7572575A", sethGrahameSmith.getId());
        assertEquals("OL18710A", mickInkpen.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Léo", leo.getNom());
        assertEquals("Seth Grahame-Smith", sethGrahameSmith.getNom());
        assertEquals("Mick Inkpen", mickInkpen.getNom());
    }

    @Test
    public void testsGetAnneeNais() {
        assertEquals(Integer.valueOf(1944), leo.getAnneeNais());
        assertNull(sethGrahameSmith.getAnneeNais());
        assertEquals(Integer.valueOf(1936), mickInkpen.getAnneeNais());
    }

    @Test
    public void testsGetAnneeDeces() {
        assertNull(leo.getAneeeDeces());
        assertNull(sethGrahameSmith.getAneeeDeces());
        assertEquals(Integer.valueOf(2024), mickInkpen.getAneeeDeces());
    }
}

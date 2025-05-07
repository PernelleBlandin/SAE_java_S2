import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AuteurTest {
    private Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
    private Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", 1936, 2024);

    @Test
    public void testsGetId() {
        assertEquals("OL7572575A", this.leo.getId());
        assertEquals("OL7572575A", this.sethGrahameSmith.getId());
        assertEquals("OL18710A", this.mickInkpen.getId());
    }

    @Test
    public void testsGetNom() {
        assertEquals("Léo", this.leo.getNom());
        assertEquals("Seth Grahame-Smith", this.sethGrahameSmith.getNom());
        assertEquals("Mick Inkpen", this.mickInkpen.getNom());
    }

    @Test
    public void testsGetAnneeNais() {
        assertEquals(Integer.valueOf(1944), this.leo.getAnneeNais());
        assertNull(this.sethGrahameSmith.getAnneeNais());
        assertEquals(Integer.valueOf(1936), this.mickInkpen.getAnneeNais());
    }

    @Test
    public void testsGetAnneeDeces() {
        assertNull(this.leo.getAneeeDeces());
        assertNull(this.sethGrahameSmith.getAneeeDeces());
        assertEquals(Integer.valueOf(2024), this.mickInkpen.getAneeeDeces());
    }
}

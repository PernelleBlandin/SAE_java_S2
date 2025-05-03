import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ResultatSelectionTest {
    private static ResultatSelection<Livre> resultatSelectionVide = new ResultatSelection<>();

    private static Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private static ResultatSelection<Auteur> resultatSelectionAuteur = new ResultatSelection<>(5, leo);

    @Test
    public void testsGetNbPage() {
        assertEquals(0, resultatSelectionVide.getNbPage());
        assertEquals(5, resultatSelectionAuteur.getNbPage());
    }

    @Test
    public void testsGetElement() {
        assertNull(null, resultatSelectionVide.getElement());
        assertEquals(leo, resultatSelectionAuteur.getElement());
    }
}

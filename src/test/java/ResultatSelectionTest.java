import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ResultatSelectionTest {
    private ResultatSelection<Livre> resultatSelectionVide = new ResultatSelection<>();

    private Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private ResultatSelection<Auteur> resultatSelectionAuteur = new ResultatSelection<>(5, leo);

    @Test
    public void testsGetNbPage() {
        assertEquals(0, this.resultatSelectionVide.getNbPage());
        assertEquals(5, this.resultatSelectionAuteur.getNbPage());
    }

    @Test
    public void testsGetElement() {
        assertNull(null, this.resultatSelectionVide.getElement());
        assertEquals(this.leo, this.resultatSelectionAuteur.getElement());
    }
}

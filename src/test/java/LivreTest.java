import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LivreTest {
    private Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new ArrayList<>(Arrays.asList("Léo")),
        new ArrayList<>(Arrays.asList("Dargaud")),
        new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
    );

    private Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new ArrayList<>(Arrays.asList("Seth Grahame-Smith")),
        new ArrayList<>(Arrays.asList("Hachette Book Group Usa", "Gallimard")),
        new ArrayList<>(Arrays.asList("Littérature américaine"))
    );

    private Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new ArrayList<>(Arrays.asList("Mick Inkpen", "Paul Dubois")),
        new ArrayList<>(Arrays.asList("Hodder Children'S")),
        new ArrayList<>(Arrays.asList("Littérature anglaise"))
    );

    private Livre livre4 = new Livre(
        "00000000000",
        "Inconnu",
        null,
        2008,
        11.9,
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
    );

    @Test
    public void testsGetISBN() {
        assertEquals("9782205054750", this.livre1.getISBN());
        assertEquals("9780446570992", this.livre2.getISBN());
        assertEquals("9780340932056", this.livre3.getISBN());
    }

    @Test
    public void testsGetTitre() {
        assertEquals("Les cavernes", this.livre1.getTitre());
        assertEquals("Abraham Lincoln", this.livre2.getTitre());
        assertEquals("Kipper", this.livre3.getTitre());
    }

    @Test
    public void testsGetNbPages() {
        assertEquals(Integer.valueOf(48), this.livre1.getNbPages());
        assertNull(this.livre2.getNbPages());
        assertEquals(Integer.valueOf(32), this.livre3.getNbPages());
    }

    @Test
    public void testsGetDatePubli() {
        assertEquals(2003, this.livre1.getDatePubli());
        assertEquals(2010, this.livre2.getDatePubli());
        assertEquals(2008, this.livre3.getDatePubli());
    }

    @Test
    public void testsGetPrix() {
        assertEquals(8.81, this.livre1.getPrix(), 0.00);
        assertEquals(16.40, this.livre2.getPrix(), 0.00);
        assertEquals(11.90, this.livre3.getPrix(), 0.00);
    }

    @Test
    public void testsGetAuteurs() {
        List<String> auteurs1 = new ArrayList<>(Arrays.asList("Léo"));
        assertEquals(auteurs1, this.livre1.getAuteurs());

        List<String> auteurs2 = new ArrayList<>(Arrays.asList("Seth Grahame-Smith"));
        assertEquals(auteurs2, this.livre2.getAuteurs());

        List<String> auteurs3 = new ArrayList<>(Arrays.asList("Mick Inkpen", "Paul Dubois"));
        assertEquals(auteurs3, this.livre3.getAuteurs());
    }

    @Test
    public void testsGetEditeurs() {
        List<String> editeurs1 = new ArrayList<>(Arrays.asList("Dargaud"));
        assertEquals(editeurs1, this.livre1.getEditeurs());

        List<String> editeurs2 = new ArrayList<>(Arrays.asList("Hachette Book Group Usa", "Gallimard"));
        assertEquals(editeurs2, this.livre2.getEditeurs());

        List<String> editeurs3 = new ArrayList<>(Arrays.asList("Hodder Children'S"));
        assertEquals(editeurs3, this.livre3.getEditeurs());
    }

    @Test
    public void testsGetClassifications() {
        List<String> classifications1 = new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"));
        assertEquals(classifications1, this.livre1.getClassifications());

        List<String> classifications2 = new ArrayList<>(Arrays.asList("Littérature américaine"));
        assertEquals(classifications2, this.livre2.getClassifications());

        List<String> classifications3 = new ArrayList<>(Arrays.asList("Littérature anglaise"));
        assertEquals(classifications3, this.livre3.getClassifications());
    }

    @Test
    public void testsJoinNomAuteurs() {
        assertEquals("Léo", this.livre1.joinNomAuteurs());
        assertEquals("Seth Grahame-Smith", this.livre2.joinNomAuteurs());
        assertEquals("Mick Inkpen, Paul Dubois", this.livre3.joinNomAuteurs());
        assertEquals("Inconnu", this.livre4.joinNomAuteurs());
    }

    @Test
    public void testsJoinNomEditeurs() {
        assertEquals("Dargaud", this.livre1.joinNomEditeurs());
        assertEquals("Hachette Book Group Usa, Gallimard", this.livre2.joinNomEditeurs());
        assertEquals("Hodder Children'S", this.livre3.joinNomEditeurs());
        assertEquals("Inconnu", this.livre4.joinNomEditeurs());
    }

    @Test
    public void testsJoinClassifications() {
        assertEquals("Arts décorartifs, Jeunesse", this.livre1.joinClassifications());
        assertEquals("Littérature américaine", this.livre2.joinClassifications());
        assertEquals("Littérature anglaise", this.livre3.joinClassifications());
        assertEquals("Inconnu", this.livre4.joinClassifications());
    }

    @Test
    public void testsEstIncluDansRecherche() {
        assertTrue(this.livre1.estIncluDansRecherche("caverne"));
        assertTrue(this.livre1.estIncluDansRecherche("LES CAVERNES"));
        assertTrue(this.livre1.estIncluDansRecherche("Léo"));
        assertTrue(this.livre1.estIncluDansRecherche("Dargaud"));
        assertTrue(this.livre1.estIncluDansRecherche("Jeunesse"));
        assertTrue(this.livre1.estIncluDansRecherche("Arts décorartifs"));

        assertFalse(this.livre1.estIncluDansRecherche("ABRAHAM"));
        assertFalse(this.livre1.estIncluDansRecherche("Hachette"));
        assertFalse(this.livre1.estIncluDansRecherche("américaine"));
        assertFalse(this.livre1.estIncluDansRecherche("Seth Grahame-Smith"));
    }

    @Test
    public void testsToString() {
        assertEquals("Les cavernes | par Léo", this.livre1.toString());
        assertEquals("Abraham Lincoln | par Seth Grahame-Smith", this.livre2.toString());
        assertEquals("Kipper | par Mick Inkpen, Paul Dubois", this.livre3.toString());
        assertEquals("Inconnu | par Inconnu", this.livre4.toString());
    }

    @Test
    public void testsEquals() {
        assertTrue(this.livre1.equals(this.livre1));
        assertTrue(this.livre2.equals(this.livre2));

        Livre livre1Equals = new Livre(
            "9782205054750",
            "Les cavernes",
            48,
            2003,
            8.81,
            new ArrayList<>(Arrays.asList("Léo")),
            new ArrayList<>(Arrays.asList("Dargaud")),
            new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
        );
        assertTrue(this.livre1.equals(livre1Equals));

        assertFalse(this.livre1.equals(this.livre4));
        assertFalse(this.livre2.equals(livre1Equals));
        assertFalse(this.livre1.equals(null));
    }
}

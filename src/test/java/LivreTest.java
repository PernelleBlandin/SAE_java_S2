import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class LivreTest {
    private Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new HashSet<>(Arrays.asList("Léo")),
        new HashSet<>(Arrays.asList("Dargaud")),
        new HashSet<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
    );

    private Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new HashSet<>(Arrays.asList("Seth Grahame-Smith")),
        new HashSet<>(Arrays.asList("Hachette Book Group Usa", "Gallimard")),
        new HashSet<>(Arrays.asList("Littérature américaine"))
    );

    private Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new HashSet<>(Arrays.asList("Mick Inkpen", "Paul Dubois")),
        new HashSet<>(Arrays.asList("Hodder Children'S")),
        new HashSet<>(Arrays.asList("Littérature anglaise"))
    );

    private Livre livre4 = new Livre(
        "00000000000",
        "Inconnu",
        null,
        2008,
        11.9,
        new HashSet<>(),
        new HashSet<>(),
        new HashSet<>()
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
        assertEquals(Integer.valueOf(2003), this.livre1.getDatePubli());
        assertEquals(Integer.valueOf(2010), this.livre2.getDatePubli());
        assertEquals(Integer.valueOf(2008), this.livre3.getDatePubli());
    }

    @Test
    public void testsGetPrix() {
        assertEquals(8.81, this.livre1.getPrix(), 0.00);
        assertEquals(16.40, this.livre2.getPrix(), 0.00);
        assertEquals(11.90, this.livre3.getPrix(), 0.00);
    }

    @Test
    public void testsGetAuteurs() {
        Set<String> auteurs1 = new HashSet<>(Arrays.asList("Léo"));
        assertEquals(auteurs1, this.livre1.getAuteurs());

        Set<String> auteurs2 = new HashSet<>(Arrays.asList("Seth Grahame-Smith"));
        assertEquals(auteurs2, this.livre2.getAuteurs());

        Set<String> auteurs3 = new HashSet<>(Arrays.asList("Mick Inkpen", "Paul Dubois"));
        assertEquals(auteurs3, this.livre3.getAuteurs());
    }

    @Test
    public void testsGetEditeurs() {
        Set<String> editeurs1 = new HashSet<>(Arrays.asList("Dargaud"));
        assertEquals(editeurs1, this.livre1.getEditeurs());

        Set<String> editeurs2 = new HashSet<>(Arrays.asList("Hachette Book Group Usa", "Gallimard"));
        assertEquals(editeurs2, this.livre2.getEditeurs());

        Set<String> editeurs3 = new HashSet<>(Arrays.asList("Hodder Children'S"));
        assertEquals(editeurs3, this.livre3.getEditeurs());
    }

    @Test
    public void testsGetClassifications() {
        Set<String> classifications1 = new HashSet<>(Arrays.asList("Arts décorartifs", "Jeunesse"));
        assertEquals(classifications1, this.livre1.getClassifications());

        Set<String> classifications2 = new HashSet<>(Arrays.asList("Littérature américaine"));
        assertEquals(classifications2, this.livre2.getClassifications());

        Set<String> classifications3 = new HashSet<>(Arrays.asList("Littérature anglaise"));
        assertEquals(classifications3, this.livre3.getClassifications());
    }

    @Test
    public void testsJoinNomAuteurs() {
        assertEquals("Léo", this.livre1.joinNomAuteurs());
        assertEquals("Seth Grahame-Smith", this.livre2.joinNomAuteurs());
        assertEquals("Paul Dubois, Mick Inkpen", this.livre3.joinNomAuteurs());
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
        assertEquals("Jeunesse, Arts décorartifs", this.livre1.joinClassifications());
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
        assertEquals("Kipper | par Paul Dubois, Mick Inkpen", this.livre3.toString());
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
            new HashSet<>(Arrays.asList("Léo")),
            new HashSet<>(Arrays.asList("Dargaud")),
            new HashSet<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
        );
        assertTrue(this.livre1.equals(livre1Equals));

        assertFalse(this.livre1.equals(this.livre4));
        assertFalse(this.livre2.equals(livre1Equals));
        assertFalse(this.livre1.equals(null));
    }

    @Test
    public void testsHashCode() {
        assertEquals(-1025344318, this.livre1.hashCode());
        assertEquals(-538572828, this.livre2.hashCode());
        assertEquals(349700178, this.livre3.hashCode());
    }
}

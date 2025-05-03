import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LivreTest {
    private static Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
    private static Livre livre1 = new Livre(
        "9782205054750",
        "Les cavernes",
        48,
        2003,
        8.81,
        new ArrayList<>(Arrays.asList(leo)),
        new ArrayList<>(Arrays.asList("Dargaud")),
        new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
    );

    private static Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
    private static Livre livre2 = new Livre(
        "9780446570992",
        "Abraham Lincoln",
        null,
        2010,
        16.4,
        new ArrayList<>(Arrays.asList(sethGrahameSmith)),
        new ArrayList<>(Arrays.asList("Hachette Book Group Usa", "Gallimard")),
        new ArrayList<>(Arrays.asList("Littérature américaine"))
    );

    private static Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", null, null);
    private static Auteur paulDubois = new Auteur("OL19010A", "Paul Dubois", null, null);
    private static Livre livre3 = new Livre(
        "9780340932056",
        "Kipper",
        32,
        2008,
        11.9,
        new ArrayList<>(Arrays.asList(mickInkpen, paulDubois)),
        new ArrayList<>(Arrays.asList("Hodder Children'S")),
        new ArrayList<>(Arrays.asList("Littérature anglaise"))
    );

    private static Livre livre4 = new Livre(
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
        assertEquals("9782205054750", livre1.getISBN());
        assertEquals("9780446570992", livre2.getISBN());
        assertEquals("9780340932056", livre3.getISBN());
    }

    @Test
    public void testsGetTitre() {
        assertEquals("Les cavernes", livre1.getTitre());
        assertEquals("Abraham Lincoln", livre2.getTitre());
        assertEquals("Kipper", livre3.getTitre());
    }

    @Test
    public void testsGetNbPages() {
        assertEquals(Integer.valueOf(48), livre1.getNbPages());
        assertNull(livre2.getNbPages());
        assertEquals(Integer.valueOf(32), livre3.getNbPages());
    }

    @Test
    public void testsGetDatePubli() {
        assertEquals(2003, livre1.getDatePubli());
        assertEquals(2010, livre2.getDatePubli());
        assertEquals(2008, livre3.getDatePubli());
    }

    @Test
    public void testsGetPrix() {
        assertEquals(8.81, livre1.getPrix(), 0.00);
        assertEquals(16.40, livre2.getPrix(), 0.00);
        assertEquals(11.90, livre3.getPrix(), 0.00);
    }

    @Test
    public void testsGetAuteurs() {
        List<Auteur> auteurs1 = new ArrayList<>(Arrays.asList(leo));
        assertEquals(auteurs1, livre1.getAuteurs());

        List<Auteur> auteurs2 = new ArrayList<>(Arrays.asList(sethGrahameSmith));
        assertEquals(auteurs2, livre2.getAuteurs());

        List<Auteur> auteurs3 = new ArrayList<>(Arrays.asList(mickInkpen, paulDubois));
        assertEquals(auteurs3, livre3.getAuteurs());
    }

    @Test
    public void testsGetEditeurs() {
        List<String> editeurs1 = new ArrayList<>(Arrays.asList("Dargaud"));
        assertEquals(editeurs1, livre1.getEditeurs());

        List<String> editeurs2 = new ArrayList<>(Arrays.asList("Hachette Book Group Usa", "Gallimard"));
        assertEquals(editeurs2, livre2.getEditeurs());

        List<String> editeurs3 = new ArrayList<>(Arrays.asList("Hodder Children'S"));
        assertEquals(editeurs3, livre3.getEditeurs());
    }

    @Test
    public void testsGetClassifications() {
        List<String> classifications1 = new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"));
        assertEquals(classifications1, livre1.getClassifications());

        List<String> classifications2 = new ArrayList<>(Arrays.asList("Littérature américaine"));
        assertEquals(classifications2, livre2.getClassifications());

        List<String> classifications3 = new ArrayList<>(Arrays.asList("Littérature anglaise"));
        assertEquals(classifications3, livre3.getClassifications());
    }

    @Test
    public void testsJoinNomAuteurs() {
        assertEquals("Léo", livre1.joinNomAuteurs());
        assertEquals("Seth Grahame-Smith", livre2.joinNomAuteurs());
        assertEquals("Mick Inkpen, Paul Dubois", livre3.joinNomAuteurs());
        assertEquals("Inconnu", livre4.joinNomAuteurs());
    }

    @Test
    public void testsJoinNomEditeurs() {
        assertEquals("Dargaud", livre1.joinNomEditeurs());
        assertEquals("Hachette Book Group Usa, Gallimard", livre2.joinNomEditeurs());
        assertEquals("Hodder Children'S", livre3.joinNomEditeurs());
        assertEquals("Inconnu", livre4.joinNomEditeurs());
    }

    @Test
    public void testsJoinClassifications() {
        assertEquals("Arts décorartifs, Jeunesse", livre1.joinClassifications());
        assertEquals("Littérature américaine", livre2.joinClassifications());
        assertEquals("Littérature anglaise", livre3.joinClassifications());
        assertEquals("Inconnu", livre4.joinClassifications());
    }

    @Test
    public void testsEstIncluDansRecherche() {
        assertTrue(livre1.estIncluDansRecherche("caverne"));
        assertTrue(livre1.estIncluDansRecherche("LES CAVERNES"));
        assertTrue(livre1.estIncluDansRecherche("Léo"));
        assertTrue(livre1.estIncluDansRecherche("Dargaud"));
        assertTrue(livre1.estIncluDansRecherche("Jeunesse"));
        assertTrue(livre1.estIncluDansRecherche("Arts décorartifs"));

        assertFalse(livre1.estIncluDansRecherche("ABRAHAM"));
        assertFalse(livre1.estIncluDansRecherche("Hachette"));
        assertFalse(livre1.estIncluDansRecherche("américaine"));
        assertFalse(livre1.estIncluDansRecherche("Seth Grahame-Smith"));
    }

    @Test
    public void testsToString() {
        assertEquals("Les cavernes | par Léo", livre1.toString());
        assertEquals("Abraham Lincoln | par Seth Grahame-Smith", livre2.toString());
        assertEquals("Kipper | par Mick Inkpen, Paul Dubois", livre3.toString());
        assertEquals("Inconnu | par Inconnu", livre4.toString());
    }

    @Test
    public void testsEquals() {
        assertTrue(livre1.equals(livre1));
        assertTrue(livre2.equals(livre2));

        Livre livre1Equals = new Livre(
            "9782205054750",
            "Les cavernes",
            48,
            2003,
            8.81,
            new ArrayList<>(Arrays.asList(leo)),
            new ArrayList<>(Arrays.asList("Dargaud")),
            new ArrayList<>(Arrays.asList("Arts décorartifs", "Jeunesse"))
        );
        assertTrue(livre1.equals(livre1Equals));

        assertFalse(livre1.equals(livre4));
        assertFalse(livre2.equals(livre1Equals));
        assertFalse(livre2.equals(leo));
        assertFalse(livre1.equals(null));
    }
}

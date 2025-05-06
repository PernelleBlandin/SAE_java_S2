import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class PossederTest {
    private Auteur philippeChereau = new Auteur("OL7572575A", "Philippe Chéreau", null, null);
    private Auteur christopheAgius = new Auteur("OL7572575B", "Christophe Agius", null, null);
    private Livre livre1 = new Livre(
        "9782205054750",
        "Simplement 2: 25 ans de commentaires",
        329,
        2025,
        24.99,
        new ArrayList<>(Arrays.asList(this.philippeChereau, this.christopheAgius)),
        new ArrayList<>(Arrays.asList("Simplement 2")),
        new ArrayList<>(Arrays.asList("Sports"))
    );

    private Auteur xavierNiel = new Auteur("OL7572575A", "Xavier Niel", null, null);
    private Livre livre2 = new Livre(
        "9780446570992",
        "Une sacrée envie de foutre le bordel",
        null,
        2024,
        9.99,
        new ArrayList<>(Arrays.asList(this.xavierNiel)),
        new ArrayList<>(Arrays.asList("Flammarion")),
        new ArrayList<>(Arrays.asList("Télécom"))
    );

    private Auteur claudeServi = new Auteur("OL18710A", "Claude Servi", null, null);
    private Livre livre3 = new Livre(
        "9780340932056",
        "Réseaux & Télécom",
        405,
        2013,
        46.99,
        new ArrayList<>(Arrays.asList(this.claudeServi)),
        new ArrayList<>(Arrays.asList("Dunod")),
        new ArrayList<>(Arrays.asList("Télécom"))
    );

    private Posseder posseder1 = new Posseder(this.livre1, 50);
    private Posseder posseder2 = new Posseder(this.livre2, 1);
    private Posseder posseder3 = new Posseder(this.livre3, 0);

    @Test
    public void testsGetLivre() {
        assertEquals(this.livre1, this.posseder1.getLivre());
        assertEquals(this.livre2, this.posseder2.getLivre());
        assertEquals(this.livre3, this.posseder3.getLivre());
    }

    @Test
    public void testsGetQuantite() {
        assertEquals(50, this.posseder1.getQuantite());
        assertEquals(1, this.posseder2.getQuantite());
        assertEquals(0, this.posseder3.getQuantite());
    }
}

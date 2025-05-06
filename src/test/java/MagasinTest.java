import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MagasinTest {
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

    private Posseder posseder1 = new Posseder(this.livre1, 5);
    private Posseder posseder2 = new Posseder(this.livre2, 1);
    private List<Posseder> possessionsMagasinParis = new ArrayList<>(Arrays.asList(this.posseder1, this.posseder2));
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", this.possessionsMagasinParis);
    
    private Posseder posseder3 = new Posseder(this.livre3, 7);
    private List<Posseder> possessionsMagasinMarseille = new ArrayList<>(Arrays.asList(this.posseder3));
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", this.possessionsMagasinMarseille);
    
    private List<Posseder> possessionsMagasinOrleans = new ArrayList<>();
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", this.possessionsMagasinOrleans);

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
    public void testsGetPossessions() {
        assertEquals(this.possessionsMagasinParis, this.magasinParis.getPossessions());
        assertEquals(this.possessionsMagasinMarseille, this.magasinMarseille.getPossessions());
        assertEquals(this.possessionsMagasinOrleans, this.magasinOrleans.getPossessions());
    }

    @Test
    public void testsToString() {
        assertEquals("La librairie parisienne (Paris)", this.magasinParis.toString());
        assertEquals("Cap au Sud (Marseille)", this.magasinMarseille.toString());
        assertEquals("Loire et livres (Orléans)", this.magasinOrleans.toString());
    }
}

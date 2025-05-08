import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class DetailLivreTest {
    private Auteur xavierNiel = new Auteur("OL7572575A", "Xavier Niel", null, null);
    private Livre livre1 = new Livre(
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
    private Livre livre2 = new Livre(
        "9780340932056",
        "Réseaux & Télécom",
        405,
        2013,
        46.99,
        new ArrayList<>(Arrays.asList(this.claudeServi)),
        new ArrayList<>(Arrays.asList("Dunod")),
        new ArrayList<>(Arrays.asList("Télécom"))
    );

    private Auteur guillaumeMusso = new Auteur("OL6835078A", "Guillaume Musso", null, null);
    private Livre livre3 = new Livre(
        "9780768939866",
        "Angélique",
        213,
        2023,
        11.99,
        new ArrayList<>(Arrays.asList(this.guillaumeMusso)),
        new ArrayList<>(Arrays.asList("Lgf")),
        new ArrayList<>(Arrays.asList("Roman"))
    );

    private DetailLivre detailLivre1 = new DetailLivre(this.livre3, 1, 2, 11.99);
    private DetailLivre detailLivre2 = new DetailLivre(this.livre3, 2, 3, 11.99);
    private DetailLivre detailLivre3 = new DetailLivre(this.livre2, 1, 2, 46.99);
    private DetailLivre detailLivre4 = new DetailLivre(this.livre1, 5, 1, 9.99);

    @Test
    public void testsGetLivre() {
        assertEquals(this.livre3, this.detailLivre1.getLivre());
        assertEquals(this.livre3, this.detailLivre2.getLivre());
        assertEquals(this.livre2, this.detailLivre3.getLivre());
        assertEquals(this.livre1, this.detailLivre4.getLivre());
    }

    @Test
    public void testsGetNumLigne() {
        assertEquals(1, this.detailLivre1.getNumLigne());
        assertEquals(2, this.detailLivre2.getNumLigne());
        assertEquals(1, this.detailLivre3.getNumLigne());
        assertEquals(5, this.detailLivre4.getNumLigne());
    }

    @Test
    public void testsGetQuantite() {
        assertEquals(2, this.detailLivre1.getQuantite());
        assertEquals(3, this.detailLivre2.getQuantite());
        assertEquals(2, this.detailLivre3.getQuantite());
        assertEquals(1, this.detailLivre4.getQuantite());
    }

    @Test
    public void testsGetPrixVente() {
        assertEquals(11.99, this.detailLivre1.getPrixVente(), 0.00);
        assertEquals(11.99, this.detailLivre2.getPrixVente(), 0.00);
        assertEquals(46.99, this.detailLivre3.getPrixVente(), 0.00);
        assertEquals(9.99, this.detailLivre4.getPrixVente(), 0.00);
    }

    @Test
    public void testsAjouterQuantite() {
        DetailLivre detailLivre = new DetailLivre(this.livre3, 1, 2, 11.99);

        assertEquals(2, detailLivre.getQuantite());
        detailLivre.ajouterQuantite();
        detailLivre.ajouterQuantite();
        assertEquals(4, detailLivre.getQuantite());
    }

    @Test
    public void testsSetQuantite() {
        DetailLivre detailLivre = new DetailLivre(this.livre3, 1, 2, 11.99);

        assertEquals(2, detailLivre.getQuantite());
        detailLivre.setQuantite(0);
        assertEquals(0, detailLivre.getQuantite());
        detailLivre.setQuantite(56);
        assertEquals(56, detailLivre.getQuantite());
    }
}

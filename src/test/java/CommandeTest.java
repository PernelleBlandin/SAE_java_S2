import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CommandeTest {
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

    private List<Posseder> listePosessions = new ArrayList<>();
    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", this.listePosessions);
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", this.listePosessions);
    
    private DetailLivre detailCommande1 = new DetailLivre(this.livre3, 1, 2, 11.99);
    private Commande commande1 = new Commande(1, Date.valueOf("2025-09-01"), 'O', 'M', this.magasinMarseille, new ArrayList<>(Arrays.asList(this.detailCommande1)));

    private DetailLivre detailCommande2 = new DetailLivre(this.livre3, 1, 3, 11.99);
    private Commande commande2 = new Commande(2, Date.valueOf("2025-12-12"), 'O', 'C', this.magasinMarseille, new ArrayList<>(Arrays.asList(this.detailCommande2)));

    private DetailLivre detailCommande3 = new DetailLivre(this.livre2, 1, 2, 46.99);
    private DetailLivre detailCommande4 = new DetailLivre(this.livre1, 2, 1, 24.99);
    private Commande commande3 = new Commande(3, Date.valueOf("2025-09-05"), 'N', 'M', this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande3, this.detailCommande4)));

    @Test
    public void testsGetId() {
        assertEquals(1, this.commande1.getId());
        assertEquals(2, this.commande2.getId());
        assertEquals(3, this.commande3.getId());
    }

    @Test
    public void testsGetDate() {
        assertEquals(Date.valueOf("2025-09-01"), this.commande1.getDate());
        assertEquals(Date.valueOf("2025-12-12"), this.commande2.getDate());
        assertEquals(Date.valueOf("2025-09-05"), this.commande3.getDate());
    }

    @Test
    public void testsEstEnLigne() {
        assertTrue(this.commande1.estEnLigne());
        assertTrue(this.commande2.estEnLigne());
        assertFalse(this.commande3.estEnLigne());
    }

    @Test
    public void testsEstEnLivraison() {
        assertFalse(this.commande1.estEnLivraison());
        assertTrue(this.commande2.estEnLivraison());
        assertFalse(this.commande3.estEnLivraison());
    }

    @Test
    public void testsGetMagasin() {
        assertEquals(this.magasinMarseille, this.commande1.getMagasin());
        assertEquals(this.magasinMarseille, this.commande2.getMagasin());
        assertEquals(this.magasinOrleans, this.commande3.getMagasin());
    }

    @Test
    public void testsGetDetailCommandes() {
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande1)), this.commande1.getDetailCommandes());
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande2)), this.commande2.getDetailCommandes());
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande3, this.detailCommande4)), this.commande3.getDetailCommandes());
    }

    @Test
    public void testsGetTotalCommande() {
        assertEquals(23.98, this.commande1.getTotalCommande(), 0.00);
        assertEquals(35.97, this.commande2.getTotalCommande(), 0.00);
        assertEquals(118.97, this.commande3.getTotalCommande(), 0.00);
    }

    @Test
    public void testsToString() {
        assertEquals("Commande #1 du 01/09/2025 - 23,98€ - 1 article(s) - Cap au Sud (Marseille)", this.commande1.toString());
        assertEquals("Commande #2 du 12/12/2025 - 35,97€ - 1 article(s) - Cap au Sud (Marseille)", this.commande2.toString());
        assertEquals("Commande #3 du 05/09/2025 - 118,97€ - 2 article(s) - Loire et livres (Orléans)", this.commande3.toString());
    }
}

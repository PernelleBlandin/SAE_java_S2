import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import modele.Commande;
import modele.DetailLivre;
import modele.Livre;
import modele.Magasin;

public class CommandeTest {
    private Livre livre1 = new Livre(
        "9782205054750",
        "Simplement 2: 25 ans de commentaires",
        329,
        2025,
        24.99,
        new HashSet<>(Arrays.asList("Philippe Chéreau", "Christophe Agius")),
        new HashSet<>(Arrays.asList("Simplement 2")),
        new HashSet<>(Arrays.asList("Sports"))
    );

    private Livre livre2 = new Livre(
        "9780340932056",
        "Réseaux & Télécom",
        405,
        2013,
        46.99,
        new HashSet<>(Arrays.asList("Claude Servi")),
        new HashSet<>(Arrays.asList("Dunod")),
        new HashSet<>(Arrays.asList("Télécom"))
    );

    private Livre livre3 = new Livre(
        "9780768939866",
        "Angélique",
        213,
        2023,
        11.99,
        new HashSet<>(Arrays.asList("Guillaume Musso")),
        new HashSet<>(Arrays.asList("Lgf")),
        new HashSet<>(Arrays.asList("Roman"))
    );

    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");
    
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
}

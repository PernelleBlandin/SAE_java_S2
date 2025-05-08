import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PanierTest {
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

    private List<Posseder> listePosessions = new ArrayList<>();
    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", this.listePosessions);
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", this.listePosessions);

    private DetailLivre detailLivre1 = new DetailLivre(this.livre1, 1, 1, 9.99);
    private Panier panierClient1 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));

    private DetailLivre detailLivre2 = new DetailLivre(this.livre2, 1, 1, 11.9);
    private DetailLivre detailLivre3 = new DetailLivre(this.livre3, 2, 3, 46.99);
    private Panier panierClient2 = new Panier(this.magasinParis, new ArrayList<>(Arrays.asList(this.detailLivre2, this.detailLivre3)));

    private Panier panierClient3 = new Panier(this.magasinParis);

    @Test
    public void testsGetMagasin() {
        assertEquals(this.magasinOrleans, this.panierClient1.getMagasin());
        assertEquals(this.magasinParis, this.panierClient2.getMagasin());
        assertEquals(this.magasinParis, this.panierClient3.getMagasin());
    }

    @Test
    public void testsGetDetailLivres() {
        assertEquals(new ArrayList<>(Arrays.asList(this.detailLivre1)), this.panierClient1.getDetailLivres());
        assertEquals(new ArrayList<>(Arrays.asList(this.detailLivre2, this.detailLivre3)), this.panierClient2.getDetailLivres());
        assertEquals(new ArrayList<>(), this.panierClient3.getDetailLivres());
    }

    @Test
    public void testsGetDetailLivre() {
        assertEquals(this.detailLivre1, this.panierClient1.getDetailLivre(this.livre1));
        assertEquals(this.detailLivre2, this.panierClient2.getDetailLivre(this.livre2));
        assertEquals(this.detailLivre3, this.panierClient2.getDetailLivre(this.livre3));

        assertNull(this.panierClient1.getDetailLivre(this.livre2));
        assertNull(this.panierClient1.getDetailLivre(this.livre3));
        assertNull(this.panierClient2.getDetailLivre(this.livre1));
        assertNull(this.panierClient3.getDetailLivre(this.livre1));
    }

    @Test
    public void testRetirerQuantiteLivre() {
        // -- Panier 1
        DetailLivre detailLivre1 = new DetailLivre(this.livre1, 1, 1, 9.99);
        Panier panier1 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre1)));

        List<DetailLivre> listeDetailLivres1 = panier1.getDetailLivres();
        assertEquals(1, listeDetailLivres1.size());
        assertEquals(1, detailLivre1.getQuantite());
        
        // Livre non présent dans panier
        // TODO: Exception
        panier1.retirerQuantiteLivre(this.livre2, 3);
        assertEquals(1, listeDetailLivres1.size());
        assertEquals(1, detailLivre1.getQuantite());

        panier1.retirerQuantiteLivre(this.livre1, 1);
        assertEquals(0, listeDetailLivres1.size());

        // -- Panier 2
        DetailLivre detailLivre2 = new DetailLivre(this.livre1, 1, 1, 9.99);
        Panier panier2 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre2)));

        List<DetailLivre> listeDetailLivres2 = panier2.getDetailLivres();
        assertEquals(1, listeDetailLivres2.size());
        assertEquals(1, detailLivre2.getQuantite());

        // Quantité trop importante 
        // TODO: Exception ?
        panier1.retirerQuantiteLivre(this.livre1, 3);
        assertEquals(0, listeDetailLivres1.size());

        // -- Panier 3
        DetailLivre detailLivre3 = new DetailLivre(this.livre1, 1, 1, 9.99);
        DetailLivre detailLivre4 = new DetailLivre(this.livre2, 2, 1, 9.99);
        DetailLivre detailLivre5 = new DetailLivre(this.livre3, 3, 1, 9.99);
        Panier panier3 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre3, detailLivre4, detailLivre5)));
        
        List<DetailLivre> listeDetailLivress3 = panier3.getDetailLivres();
        assertEquals(3, listeDetailLivress3.size());

        assertEquals(1, detailLivre3.getNumLigne());
        assertEquals(2, detailLivre4.getNumLigne());
        assertEquals(3, detailLivre5.getNumLigne());

        panier3.retirerQuantiteLivre(this.livre2, 1);

        assertEquals(2, listeDetailLivress3.size());

        assertEquals(1, detailLivre3.getNumLigne());
        assertEquals(2, detailLivre5.getNumLigne());
        assertFalse(listeDetailLivress3.contains(detailLivre4));

        // -- Panier 4
        detailLivre3 = new DetailLivre(this.livre1, 1, 1, 9.99);
        detailLivre4 = new DetailLivre(this.livre2, 2, 1, 9.99);
        detailLivre5 = new DetailLivre(this.livre3, 3, 1, 9.99);
        Panier panier4 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre3, detailLivre4, detailLivre5)));
        
        List<DetailLivre> listeDetailLivres4 = panier4.getDetailLivres();
        assertEquals(3, listeDetailLivres4.size());

        assertEquals(1, detailLivre3.getNumLigne());
        assertEquals(2, detailLivre4.getNumLigne());
        assertEquals(3, detailLivre5.getNumLigne());

        panier4.retirerQuantiteLivre(this.livre3, 1);

        assertEquals(2, listeDetailLivres4.size());

        assertEquals(1, detailLivre3.getNumLigne());
        assertEquals(2, detailLivre4.getNumLigne());
        assertFalse(listeDetailLivres4.contains(detailLivre5));
    }

    @Test
    public void testAjouterLivre() {
        Panier panier1 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));
        DetailLivre detailLivre1 = panier1.getDetailLivre(this.livre1);
        assertEquals(1, panier1.getDetailLivres().size());
        assertEquals(1, detailLivre1.getQuantite());
        assertEquals(this.livre1, detailLivre1.getLivre());

        panier1.ajouterLivre(this.livre1);
        assertEquals(1, panier1.getDetailLivres().size());
        assertEquals(2, detailLivre1.getQuantite());
        assertEquals(this.livre1, detailLivre1.getLivre());

        panier1.ajouterLivre(this.livre2);
        assertEquals(2, panier1.getDetailLivres().size());
        DetailLivre detailLivre2 = panier1.getDetailLivre(this.livre2);
        assertEquals(1, detailLivre2.getQuantite());
        assertEquals(this.livre2, detailLivre2.getLivre());
    }

    @Test
    public void testsViderPanier() {
        List<DetailLivre> detailLivresVide = new ArrayList<>();

        Panier panier1Copie = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));
        assertEquals(new ArrayList<>(Arrays.asList(this.detailLivre1)), panier1Copie.getDetailLivres());
        panier1Copie.viderPanier();
        assertEquals(detailLivresVide, panier1Copie.getDetailLivres());

        Panier panier3Copie = new Panier(this.magasinParis);
        assertEquals(detailLivresVide, panier3Copie.getDetailLivres());
        panier3Copie.viderPanier();
        assertEquals(detailLivresVide, panier3Copie.getDetailLivres());
    }
}



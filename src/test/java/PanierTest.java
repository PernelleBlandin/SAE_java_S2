import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import modeles.DetailLivre;
import modeles.Livre;
import modeles.LivreIntrouvableException;
import modeles.Magasin;
import modeles.Panier;

public class PanierTest {
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
        "9780446570992",
        "Une sacrée envie de foutre le bordel",
        null,
        2024,
        9.99,
        new HashSet<>(Arrays.asList("Xavier Niel")),
        new HashSet<>(Arrays.asList("Flammarion")),
        new HashSet<>(Arrays.asList("Télécom"))
    );

    private Livre livre3 = new Livre(
        "9780340932056",
        "Réseaux & Télécom",
        405,
        2013,
        46.99,
        new HashSet<>(Arrays.asList("Claude Servi")),
        new HashSet<>(Arrays.asList("Dunod")),
        new HashSet<>(Arrays.asList("Télécom"))
    );

    private Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
    private Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");

    private DetailLivre detailLivre1 = new DetailLivre(this.livre1, 1, 1, 9.99);
    private Panier panierClient1 = new Panier(1, this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));

    private DetailLivre detailLivre2 = new DetailLivre(this.livre2, 1, 1, 11.9);
    private DetailLivre detailLivre3 = new DetailLivre(this.livre3, 2, 3, 46.99);
    private Panier panierClient2 = new Panier(2, this.magasinParis, new ArrayList<>(Arrays.asList(this.detailLivre2, this.detailLivre3)));

    private Panier panierClient3 = new Panier(3, this.magasinParis);

    @Test
    public void testsGetId() {
        assertEquals(1, this.panierClient1.getId());
        assertEquals(2, this.panierClient2.getId());
        assertEquals(3, this.panierClient3.getId());
    }

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
    public void testsGetDetailLivre() throws LivreIntrouvableException {
        assertEquals(this.detailLivre1, this.panierClient1.getDetailLivre(this.livre1));
        assertEquals(this.detailLivre2, this.panierClient2.getDetailLivre(this.livre2));
        assertEquals(this.detailLivre3, this.panierClient2.getDetailLivre(this.livre3));

        assertThrows(LivreIntrouvableException.class, () -> { this.panierClient1.getDetailLivre(this.livre2); });
        assertThrows(LivreIntrouvableException.class, () -> { this.panierClient1.getDetailLivre(this.livre3); });
        assertThrows(LivreIntrouvableException.class, () -> { this.panierClient2.getDetailLivre(this.livre1); });
        assertThrows(LivreIntrouvableException.class, () -> { this.panierClient3.getDetailLivre(this.livre1); });
    }

    @Test
    public void testsGetQuantiteLivre() {
        assertEquals(1, this.panierClient1.getQuantiteLivre(this.livre1));
        assertEquals(3, this.panierClient2.getQuantiteLivre(this.livre3));
        assertEquals(0, this.panierClient1.getQuantiteLivre(this.livre3));
    }

    @Test
    public void testsSetMagasin() {
        Panier panierClient = new Panier(1, this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));
        assertEquals(1, panierClient.getDetailLivres().size());
        assertEquals(this.magasinOrleans, panierClient.getMagasin());

        panierClient.setMagasin(this.magasinParis);
        assertEquals(0, panierClient.getDetailLivres().size());
        assertEquals(this.magasinParis, panierClient.getMagasin());
    }

    @Test
    public void testRetirerQuantiteLivre() throws LivreIntrouvableException {
        // -- Panier 1
        DetailLivre detailLivre1 = new DetailLivre(this.livre1, 1, 1, 9.99);
        Panier panier1 = new Panier(1, this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre1)));

        List<DetailLivre> listeDetailLivres1 = panier1.getDetailLivres();
        assertEquals(1, listeDetailLivres1.size());
        assertEquals(1, detailLivre1.getQuantite());

        // Livre non présent dans panier
        assertThrows(LivreIntrouvableException.class, () -> { panier1.retirerQuantiteLivre(this.livre2, 3); });
        assertEquals(1, listeDetailLivres1.size());
        assertEquals(1, detailLivre1.getQuantite());

        panier1.retirerQuantiteLivre(this.livre1, 1);
        assertEquals(0, listeDetailLivres1.size());

        // -- Panier 2
        DetailLivre detailLivre2 = new DetailLivre(this.livre1, 1, 1, 9.99);
        Panier panier2 = new Panier(2, this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre2)));

        List<DetailLivre> listeDetailLivres2 = panier2.getDetailLivres();
        assertEquals(1, listeDetailLivres2.size());
        assertEquals(1, detailLivre2.getQuantite());

        // Quantité trop importante
        panier2.retirerQuantiteLivre(this.livre1, 3);
        assertEquals(0, listeDetailLivres1.size());

        // -- Panier 3
        DetailLivre detailLivre3 = new DetailLivre(this.livre1, 1, 1, 9.99);
        DetailLivre detailLivre4 = new DetailLivre(this.livre2, 2, 1, 9.99);
        DetailLivre detailLivre5 = new DetailLivre(this.livre3, 3, 1, 9.99);
        Panier panier3 = new Panier(3, this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre3, detailLivre4, detailLivre5)));
        
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
        Panier panier4 = new Panier(4, this.magasinOrleans, new ArrayList<>(Arrays.asList(detailLivre3, detailLivre4, detailLivre5)));
        
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
    public void testAjouterLivre() throws LivreIntrouvableException {
        Panier panier1 = new Panier(1, this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));
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

        Panier panier1Copie = new Panier(1, this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailLivre1)));
        assertEquals(new ArrayList<>(Arrays.asList(this.detailLivre1)), panier1Copie.getDetailLivres());
        panier1Copie.viderPanier();
        assertEquals(detailLivresVide, panier1Copie.getDetailLivres());

        Panier panier3Copie = new Panier(3, this.magasinParis);
        assertEquals(detailLivresVide, panier3Copie.getDetailLivres());
        panier3Copie.viderPanier();
        assertEquals(detailLivresVide, panier3Copie.getDetailLivres());
    }
}



import static org.junit.Assert.assertEquals;
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

    private DetailCommande detailCommande1 = new DetailCommande(this.livre1, 1, 1, 9.99);
    private Panier panierClient1 = new Panier(this.magasinOrleans, new ArrayList<>(Arrays.asList(this.detailCommande1)));

    private DetailCommande detailCommande2 = new DetailCommande(this.livre2, 1, 1, 11.9);
    private DetailCommande detailCommande3 = new DetailCommande(this.livre3, 2, 3, 46.99);
    private Panier panierClient2 = new Panier(this.magasinParis, new ArrayList<>(Arrays.asList(this.detailCommande2, this.detailCommande3)));

    private Panier panierClient3 = new Panier(this.magasinParis);

    @Test
    public void testsGetMagasin() {
        assertEquals(this.magasinOrleans, this.panierClient1.getMagasin());
        assertEquals(this.magasinParis, this.panierClient2.getMagasin());
        assertEquals(this.magasinParis, this.panierClient3.getMagasin());
    }

    @Test
    public void testsGetDetailCommandes() {
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande1)), this.panierClient1.getDetailCommandes());
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande2, this.detailCommande3)), this.panierClient2.getDetailCommandes());
        assertEquals(new ArrayList<>(), this.panierClient3.getDetailCommandes());
    }

    @Test
    public void testsGetDetailCommandeLivre() {
        assertEquals(this.detailCommande1, this.panierClient1.getDetailCommandeLivre(this.livre1));
        assertEquals(this.detailCommande2, this.panierClient2.getDetailCommandeLivre(this.livre2));
        assertEquals(this.detailCommande3, this.panierClient2.getDetailCommandeLivre(this.livre3));

        assertNull(this.panierClient1.getDetailCommandeLivre(this.livre2));
        assertNull(this.panierClient1.getDetailCommandeLivre(this.livre3));
        assertNull(this.panierClient2.getDetailCommandeLivre(this.livre1));
        assertNull(this.panierClient3.getDetailCommandeLivre(this.livre1));
    }

    @Test
    public void testsViderPanier() {
        List<DetailCommande> detailCommandesVide = new ArrayList<>();

        Panier panier1Copie = new Panier(this.panierClient1);
        assertEquals(new ArrayList<>(Arrays.asList(this.detailCommande1)), panier1Copie.getDetailCommandes());
        panier1Copie.viderPanier();
        assertEquals(detailCommandesVide, panier1Copie.getDetailCommandes());

        Panier panier3Copie = new Panier(this.panierClient3);
        assertEquals(detailCommandesVide, panier3Copie.getDetailCommandes());
        panier3Copie.viderPanier();
        assertEquals(detailCommandesVide, panier3Copie.getDetailCommandes());
    }
}



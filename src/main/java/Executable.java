import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Executable {
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        // Livres

        // Livre 1

        Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
        Editeur dargaud = new Editeur("1", "Dargaud");
        Classification artsDecoratifs = new Classification("740", "Arts décorartifs");

        Livre livre1 = new Livre(
            "9782205054750",
            "Les cavernes",
            48,
            2003,
            8.81,
            new ArrayList<>(Arrays.asList(leo)),
            new ArrayList<>(Arrays.asList(dargaud)),
            new ArrayList<>(Arrays.asList(artsDecoratifs))
        );
        chaineLibrairie.ajouterLivre(livre1);

        // Livre 2

        Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
        Editeur hachetteBookUSA = new Editeur("184", "Hachette Book Group Usa");
        Classification litteratureAmericaine = new Classification("810", "Littérature américaine");

        Livre livre2 = new Livre(
            "9780446570992",
            "Abraham Lincoln",
            null,
            2010,
            16.4,
            new ArrayList<>(Arrays.asList(sethGrahameSmith)),
            new ArrayList<>(Arrays.asList(hachetteBookUSA)),
            new ArrayList<>(Arrays.asList(litteratureAmericaine))
        );
        chaineLibrairie.ajouterLivre(livre2);

        // Livre 3

        Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", null, null);
        Editeur hodder = new Editeur("46", "Hodder Children'S");
        Classification litteratureAnglaise = new Classification("820", "Littérature anglaise");

        Livre livre3 = new Livre(
            "9780340932056",
            "Kipper",
            32,
            2008,
            11.9,
            new ArrayList<>(Arrays.asList(mickInkpen)),
            new ArrayList<>(Arrays.asList(hodder)),
            new ArrayList<>(Arrays.asList(litteratureAnglaise))
        );
        chaineLibrairie.ajouterLivre(livre3);

        // Livre 4

        Auteur peterson = new Auteur("OL6835078A", "Peterson's (Firm : 2006- )", null, null);
        Editeur petersonEditeur = new Editeur("222", "Peterson'S");
        Classification education = new Classification("370", "éducation");

        Livre livre4 = new Livre(
            "9780768939866",
            "Master the new SAT 2016",
            972,
            2016,
            66.99,
            new ArrayList<>(Arrays.asList(peterson)),
            new ArrayList<>(Arrays.asList(petersonEditeur)),
            new ArrayList<>(Arrays.asList(education))
        );
        chaineLibrairie.ajouterLivre(livre4);

        // Livre 5

        Auteur davidMcKee = new Auteur("OL32699A", "David McKee", 1935, 2022);
        Editeur andersen = new Editeur("118", "Andersen");

        Livre livre5 = new Livre(
            "9780862641696",
            "The sad story of Veronica who played the violin",
            26,
            1987,
            12.01,
            new ArrayList<>(Arrays.asList(davidMcKee)),
            new ArrayList<>(Arrays.asList(andersen)),
            new ArrayList<>(Arrays.asList(litteratureAnglaise))
        );
        chaineLibrairie.ajouterLivre(livre5);

        // Livre 6

        Auteur anthonyKiedis = new Auteur("OL1401762A", "Anthony Kiedis", 1962, null);
        Editeur hyperion = new Editeur("27", "Hyperion");
        Classification musique = new Classification("780", "Musique");

        Livre livre6 = new Livre(
            "9781401301019",
            "Scar tissue",
            465,
            2004,
            45.97,
            new ArrayList<>(Arrays.asList(anthonyKiedis)),
            new ArrayList<>(Arrays.asList(hyperion)),
            new ArrayList<>(Arrays.asList(musique))
        );
        chaineLibrairie.ajouterLivre(livre6);

        // Livre 7

        Auteur rolandBarthelemy = new Auteur("OL3148580A", "Roland Barthelemy", null, null);
        Auteur arnaudSperatCzar = new Auteur("OL3148570A", "Arnaud Sperat-Czar", null, null);
        Auteur rolandBarthelemy2 = new Auteur("OL6586170A", "Roland Barthélemy", null, null);

        Editeur hachetteIllustrated = new Editeur("50", "Hachette Illustrated");
        Classification maisonVieDomestique = new Classification("640", "Maison et vie domestique");

        Livre livre7 = new Livre(
            "9781844301515",
            "Guide to cheeses of the world",
            224,
            2005,
            29.95,
            new ArrayList<>(Arrays.asList(rolandBarthelemy, arnaudSperatCzar, rolandBarthelemy2)),
            new ArrayList<>(Arrays.asList(hachetteIllustrated)),
            new ArrayList<>(Arrays.asList(maisonVieDomestique))
        );
        chaineLibrairie.ajouterLivre(livre7);

        // Magasins
        List<Posseder> listePosessions = new ArrayList<>();
        Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris", listePosessions);
        Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille", listePosessions);
        Magasin magasinRennes = new Magasin("3", "Ty Li-Breizh-rie", "Rennes", listePosessions);
        Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans", listePosessions);
        
        chaineLibrairie.ajouterMagasin(magasinParis);
        chaineLibrairie.ajouterMagasin(magasinMarseille);
        chaineLibrairie.ajouterMagasin(magasinRennes);
        chaineLibrairie.ajouterMagasin(magasinOrleans);

        // Clients 
        Client client1 = new Client(1, "DUPONT", "Richard", "1 rue de la place", "45000", "Orléans", magasinMarseille, new ArrayList<>());
        Client client2 = new Client(1, "MICHEL", "Edouard", "3 grande rue", "75000", "Paris", magasinParis, new ArrayList<>());
        
        chaineLibrairie.ajouterClient(client1);
        chaineLibrairie.ajouterClient(client2);

        App app = new App(chaineLibrairie);
        app.run();
    }
}

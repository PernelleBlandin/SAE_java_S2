import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** L'exécutable de l'application. */
public class Executable {
    /** L'exécutable de l'application. */
    public Executable() {}

    /**
     * Lancer l'application.
     * @param args Les arguments.
     */
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        // Livres

        // Livre 1

        Auteur leo = new Auteur("OL7572575A", "Léo", 1944, null);
        Livre livre1 = new Livre(
            "9782205054750",
            "Les cavernes",
            48,
            2003,
            8.81,
            new ArrayList<>(Arrays.asList(leo)),
            new ArrayList<>(Arrays.asList("Dargaud")),
            new ArrayList<>(Arrays.asList("Arts décorartifs"))
        );
        chaineLibrairie.ajouterLivre(livre1);

        // Livre 2

        Auteur sethGrahameSmith = new Auteur("OL7572575A", "Seth Grahame-Smith", null, null);
        Livre livre2 = new Livre(
            "9780446570992",
            "Abraham Lincoln",
            null,
            2010,
            16.4,
            new ArrayList<>(Arrays.asList(sethGrahameSmith)),
            new ArrayList<>(Arrays.asList("Hachette Book Group Usa")),
            new ArrayList<>(Arrays.asList("Littérature américaine"))
        );
        chaineLibrairie.ajouterLivre(livre2);

        // Livre 3

        Auteur mickInkpen = new Auteur("OL18710A", "Mick Inkpen", null, null);
        Livre livre3 = new Livre(
            "9780340932056",
            "Kipper",
            32,
            2008,
            11.9,
            new ArrayList<>(Arrays.asList(mickInkpen)),
            new ArrayList<>(Arrays.asList("Hodder Children'S")),
            new ArrayList<>(Arrays.asList("Littérature anglaise"))
        );
        chaineLibrairie.ajouterLivre(livre3);

        // Livre 4

        Auteur peterson = new Auteur("OL6835078A", "Peterson's (Firm : 2006- )", null, null);
        Livre livre4 = new Livre(
            "9780768939866",
            "Master the new SAT 2016",
            972,
            2016,
            66.99,
            new ArrayList<>(Arrays.asList(peterson)),
            new ArrayList<>(Arrays.asList("Peterson'S")),
            new ArrayList<>(Arrays.asList("éducation"))
        );
        chaineLibrairie.ajouterLivre(livre4);

        // Livre 5

        Auteur davidMcKee = new Auteur("OL32699A", "David McKee", 1935, 2022);
        Livre livre5 = new Livre(
            "9780862641696",
            "The sad story of Veronica who played the violin",
            26,
            1987,
            12.01,
            new ArrayList<>(Arrays.asList(davidMcKee)),
            new ArrayList<>(Arrays.asList("Andersen")),
            new ArrayList<>(Arrays.asList("Littérature anglaise"))
        );
        chaineLibrairie.ajouterLivre(livre5);

        // Livre 6

        Auteur anthonyKiedis = new Auteur("OL1401762A", "Anthony Kiedis", 1962, null);
        Livre livre6 = new Livre(
            "9781401301019",
            "Scar tissue",
            465,
            2004,
            45.97,
            new ArrayList<>(Arrays.asList(anthonyKiedis)),
            new ArrayList<>(Arrays.asList("Hyperion")),
            new ArrayList<>(Arrays.asList("Musique"))
        );
        chaineLibrairie.ajouterLivre(livre6);

        // Livre 7

        Auteur rolandBarthelemy = new Auteur("OL3148580A", "Roland Barthelemy", null, null);
        Auteur arnaudSperatCzar = new Auteur("OL3148570A", "Arnaud Sperat-Czar", null, null);
        Auteur rolandBarthelemy2 = new Auteur("OL6586170A", "Roland Barthélemy", null, null);
        Livre livre7 = new Livre(
            "9781844301515",
            "Guide to cheeses of the world",
            224,
            2005,
            29.95,
            new ArrayList<>(Arrays.asList(rolandBarthelemy, arnaudSperatCzar, rolandBarthelemy2)),
            new ArrayList<>(Arrays.asList("Hachette Illustrated")),
            new ArrayList<>(Arrays.asList("Maison et vie domestique"))
        );
        chaineLibrairie.ajouterLivre(livre7);

        //Livre 

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

        DetailCommande detailCommande1 = new DetailCommande(livre1, 1, 2, 7.98);
        Commande commande1 = new Commande(1, Date.valueOf("2020-08-11"), 'N', 'M', magasinOrleans, new ArrayList<>(Arrays.asList(detailCommande1)));
        Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, new ArrayList<>(Arrays.asList(commande1)));
        chaineLibrairie.ajouterClient(client1);
        
        Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, new ArrayList<>());
        chaineLibrairie.ajouterClient(client2);

        DetailCommande detailCommande2 = new DetailCommande(livre2, 1, 1, 16.4);
        DetailCommande detailCommande3 = new DetailCommande(livre3, 2, 4, 11.9);
        Commande commande2 = new Commande(2, Date.valueOf("2021-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande2, detailCommande3)));
        Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, new ArrayList<>(Arrays.asList(commande2)));
        chaineLibrairie.ajouterClient(client3);

        DetailCommande detailCommande4 = new DetailCommande(livre2, 1, 2, 16.4);
        DetailCommande detailCommande5 = new DetailCommande(livre4, 2, 1, 66.99);
        DetailCommande detailCommande6 = new DetailCommande(livre5, 2, 1, 12.01);
        Commande commande3 = new Commande(3, Date.valueOf("2022-12-01"), 'N', 'M', magasinMarseille, new ArrayList<>(Arrays.asList(detailCommande4)));
        Commande commande4 = new Commande(4, Date.valueOf("2025-04-26"), 'O', 'C', magasinParis, new ArrayList<>(Arrays.asList(detailCommande5, detailCommande6)));
        Client client4 = new Client(4, "Dubois", "Lucas", "45 place de la Paix", "34000", "Montpellier", magasinMarseille, new ArrayList<>(Arrays.asList(commande3, commande4)));
        chaineLibrairie.ajouterClient(client4);
        
        DetailCommande detailCommande7 = new DetailCommande(livre1, 1, 1, 8.4);
        Commande commande5 = new Commande(5, Date.valueOf("2025-04-27"), 'N', 'M', magasinRennes, new ArrayList<>(Arrays.asList(detailCommande7)));
        Client client5 = new Client(5, "Ferrari", "Omar", "32 impasse de l'Université", "35000", "Rennes", magasinRennes, new ArrayList<>(Arrays.asList(commande5)));
        chaineLibrairie.ajouterClient(client5);
        
        DetailCommande detailCommande8 = new DetailCommande(livre6, 1, 1, 45.97);
        Commande commande6 = new Commande(6, Date.valueOf("2025-04-27"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande8)));
        DetailCommande detailCommande9 = new DetailCommande(livre1, 1, 3, 7.98);
        Panier panier6 = new Panier(magasinParis, new ArrayList<>(Arrays.asList(detailCommande9)));
        Client client6 = new Client(6, "Petit", "Louis", "98 route du Musée", "67000", "Strasbourg", magasinParis, new ArrayList<>(Arrays.asList(commande6)), panier6);
        chaineLibrairie.ajouterClient(client6);

        App app = new App(chaineLibrairie);
        app.run();
    }

    //Vendeur
    Vendeur vendeur = new Vendeur(1, "Grande", "Marie", "82 rue de Tours", "37130", "Cinq-Mars-La-Pile", magasinParis);
}

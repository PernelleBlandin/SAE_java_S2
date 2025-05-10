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

        Livre livre1 = new Livre(
            "9782205054750",
            "Les cavernes",
            48,
            2003,
            8.81,
            new ArrayList<>(Arrays.asList("Léo")),
            new ArrayList<>(Arrays.asList("Dargaud")),
            new ArrayList<>(Arrays.asList("Arts décorartifs"))
        );
        chaineLibrairie.ajouterLivre(livre1);

        // Livre 2

        Livre livre2 = new Livre(
            "9780446570992",
            "Abraham Lincoln",
            null,
            2010,
            16.4,
            new ArrayList<>(Arrays.asList("Seth Grahame-Smith")),
            new ArrayList<>(Arrays.asList("Hachette Book Group Usa")),
            new ArrayList<>(Arrays.asList("Littérature américaine"))
        );
        chaineLibrairie.ajouterLivre(livre2);

        // Livre 3

        Livre livre3 = new Livre(
            "9780340932056",
            "Kipper",
            32,
            2008,
            11.9,
            new ArrayList<>(Arrays.asList("Mick Inkpen")),
            new ArrayList<>(Arrays.asList("Hodder Children'S")),
            new ArrayList<>(Arrays.asList("Littérature anglaise"))
        );
        chaineLibrairie.ajouterLivre(livre3);

        // Livre 4

        Livre livre4 = new Livre(
            "9780768939866",
            "Master the new SAT 2016",
            972,
            2016,
            66.99,
            new ArrayList<>(Arrays.asList("Peterson's (Firm : 2006- )")),
            new ArrayList<>(Arrays.asList("Peterson'S")),
            new ArrayList<>(Arrays.asList("éducation"))
        );
        chaineLibrairie.ajouterLivre(livre4);

        // Livre 5

        Livre livre5 = new Livre(
            "9780862641696",
            "The sad story of Veronica who played the violin",
            26,
            1987,
            12.01,
            new ArrayList<>(Arrays.asList("David McKee")),
            new ArrayList<>(Arrays.asList("Andersen")),
            new ArrayList<>(Arrays.asList("Littérature anglaise"))
        );
        chaineLibrairie.ajouterLivre(livre5);

        // Livre 6

        Livre livre6 = new Livre(
            "9781401301019",
            "Scar tissue",
            465,
            2004,
            45.97,
            new ArrayList<>(Arrays.asList("Anthony Kiedis")),
            new ArrayList<>(Arrays.asList("Hyperion")),
            new ArrayList<>(Arrays.asList("Musique"))
        );
        chaineLibrairie.ajouterLivre(livre6);

        // Livre 7

        Livre livre7 = new Livre(
            "9781844301515",
            "Guide to cheeses of the world",
            224,
            2005,
            29.95,
            new ArrayList<>(Arrays.asList("Roland Barthelemy", "Arnaud Sperat-Czar", "Roland Barthélemy")),
            new ArrayList<>(Arrays.asList("Hachette Illustrated")),
            new ArrayList<>(Arrays.asList("Maison et vie domestique"))
        );
        chaineLibrairie.ajouterLivre(livre7);

        //Livre 

        // Magasins
        Magasin magasinParis = new Magasin("1", "La librairie parisienne", "Paris");
        Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
        Magasin magasinRennes = new Magasin("3", "Ty Li-Breizh-rie", "Rennes");
        Magasin magasinOrleans = new Magasin("7", "Loire et livres", "Orléans");
        
        chaineLibrairie.ajouterMagasin(magasinParis);
        chaineLibrairie.ajouterMagasin(magasinMarseille);
        chaineLibrairie.ajouterMagasin(magasinRennes);
        chaineLibrairie.ajouterMagasin(magasinOrleans);

        // Clients

        DetailLivre detailCommande1 = new DetailLivre(livre1, 1, 2, 7.98);
        Commande commande1 = new Commande(1, Date.valueOf("2020-08-11"), 'N', 'M', magasinOrleans, new ArrayList<>(Arrays.asList(detailCommande1)));
        DetailLivre detailCommande2 = new DetailLivre(livre2, 1, 1, 16.4);
        List<DetailLivre> detailCommandesPanierClient1 = new ArrayList<>(Arrays.asList(detailCommande2)); 
        Panier panierClient1 = new Panier(magasinOrleans, detailCommandesPanierClient1);
        Client client1 = new Client(1, "Rodriguez", "Fatima", "188 chemin de la Forêt", "45000", "Orléans", magasinOrleans, new ArrayList<>(Arrays.asList(commande1)), panierClient1);
        chaineLibrairie.ajouterClient(client1);
        
        Panier panierClient2 = new Panier(magasinMarseille, new ArrayList<>());
        Client client2 = new Client(2, "Garcia", "Hugo", "167 avenue de la Forêt", "06000", "Nice", magasinMarseille, new ArrayList<>(), panierClient2);
        chaineLibrairie.ajouterClient(client2);

        DetailLivre detailCommande3 = new DetailLivre(livre3, 2, 4, 11.9);
        Commande commande2 = new Commande(2, Date.valueOf("2021-09-01"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande2, detailCommande3)));
        Panier panierClient3 = new Panier(magasinOrleans, new ArrayList<>());
        Client client3 = new Client(3, "Martin", "Julie", "133 boulevard de l'Université", "45000", "Orléans", magasinOrleans, new ArrayList<>(Arrays.asList(commande2)), panierClient3);
        chaineLibrairie.ajouterClient(client3);

        DetailLivre detailCommande4 = new DetailLivre(livre2, 1, 2, 16.4);
        DetailLivre detailCommande5 = new DetailLivre(livre4, 2, 1, 66.99);
        DetailLivre detailCommande6 = new DetailLivre(livre5, 2, 1, 12.01);
        Commande commande3 = new Commande(3, Date.valueOf("2022-12-01"), 'N', 'M', magasinMarseille, new ArrayList<>(Arrays.asList(detailCommande4)));
        Commande commande4 = new Commande(4, Date.valueOf("2025-04-26"), 'O', 'C', magasinParis, new ArrayList<>(Arrays.asList(detailCommande5, detailCommande6)));
        Panier panierClient4 = new Panier(magasinMarseille, new ArrayList<>());
        Client client4 = new Client(4, "Dubois", "Lucas", "45 place de la Paix", "34000", "Montpellier", magasinMarseille, new ArrayList<>(Arrays.asList(commande3, commande4)), panierClient4);
        chaineLibrairie.ajouterClient(client4);
        
        DetailLivre detailCommande7 = new DetailLivre(livre1, 1, 1, 8.4);
        Commande commande5 = new Commande(5, Date.valueOf("2025-04-27"), 'N', 'M', magasinRennes, new ArrayList<>(Arrays.asList(detailCommande7)));
        Panier panierClient5 = new Panier(magasinRennes, new ArrayList<>());
        Client client5 = new Client(5, "Ferrari", "Omar", "32 impasse de l'Université", "35000", "Rennes", magasinRennes, new ArrayList<>(Arrays.asList(commande5)), panierClient5);
        chaineLibrairie.ajouterClient(client5);
        
        DetailLivre detailCommande8 = new DetailLivre(livre6, 1, 1, 45.97);
        Commande commande6 = new Commande(6, Date.valueOf("2025-04-27"), 'N', 'M', magasinParis, new ArrayList<>(Arrays.asList(detailCommande8)));
        DetailLivre detailCommande9 = new DetailLivre(livre1, 1, 3, 7.98);
        Panier panier6 = new Panier(magasinParis, new ArrayList<>(Arrays.asList(detailCommande9)));
        Client client6 = new Client(6, "Petit", "Louis", "98 route du Musée", "67000", "Strasbourg", magasinParis, new ArrayList<>(Arrays.asList(commande6)), panier6);
        chaineLibrairie.ajouterClient(client6);

        // Vendeur
        Vendeur vendeur1 = new Vendeur(1, "Grande", "Marie", magasinOrleans);
        chaineLibrairie.ajouterVendeur(vendeur1);

        App app = new App(chaineLibrairie);
        app.run();
    }
}

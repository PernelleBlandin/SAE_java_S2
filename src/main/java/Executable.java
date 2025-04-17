import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Executable {
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        Magasin magasin1 = new Magasin("1", "Paris Centre", "2 Grande Rue", "75000", "Paris");
        Magasin magasin2 = new Magasin("2", "Orléans Sud", "3 rue de l'olive", "45160", "Olivet");

        Client client1 = new Client(1, "DUPONT", "Richard", "1 rue de la place", "45000", "Orléans", magasin2, new ArrayList<>(), new Panier());
        chaineLibrairie.ajouterClient(client1);

        Client client2 = new Client(1, "MICHEL", "Edouard", "3 grande rue", "75000", "Paris", magasin1, new ArrayList<>(), new Panier());
        chaineLibrairie.ajouterClient(client2);

        List<Auteur> auteurs = new ArrayList<>(Arrays.asList(new Auteur("10", "Nom PrenomAuteur", 1999, null)));
        List<Editeur> editeurs = new ArrayList<>();
        List<Classification> classifications = new ArrayList<>();

        Livre livre1 = new Livre("4444444444444", "Livre 1", 10, 2024, 9.99, auteurs, editeurs, classifications);
        chaineLibrairie.ajouterLivre(livre1);

        Livre livre2 = new Livre("2222222222222", "Livre 2", 22, 2022, 2.99, auteurs, editeurs, classifications);
        chaineLibrairie.ajouterLivre(livre2);

        App app = new App(chaineLibrairie);
        app.run();
    }
}

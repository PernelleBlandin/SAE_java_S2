public class Executable {
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();
        Client client = new Client(1, "DUPONT", "Richard", "1 rue de la place", "45000", "Orléans");
        chaineLibrairie.ajouterClient(client);

        Livre livre1 = new Livre("44444", "Livre 1", 10, 2024, 9.99);
        chaineLibrairie.ajouterLivre(livre1);
        Livre livre2 = new Livre("22222", "Livre 2", 22, 2022, 2.99);
        chaineLibrairie.ajouterLivre(livre2);

        App app = new App(chaineLibrairie);
        app.run();
    }
}

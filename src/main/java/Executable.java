public class Executable {
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();
        Client client = new Client(1, "DUPONT", "Richard", "1 rue de la place", "45000", "Orléans");
        chaineLibrairie.ajouterClient(client);

        App app = new App(chaineLibrairie);
        app.run();
    }
}

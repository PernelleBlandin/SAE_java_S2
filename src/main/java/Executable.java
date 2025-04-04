public class Executable {
    public static void main(String[] args) {
        ChaineLibrairie chaineLibrairie = new ChaineLibrairie();

        App app = new App(chaineLibrairie);
        app.run();
    }
}

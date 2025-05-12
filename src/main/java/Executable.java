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

        App app = new App(chaineLibrairie);
        app.run();
    }
}

// Aide type génériques classe : https://www.geeksforgeeks.org/generic-class-in-java/
public class ResultatSelection<T> {
    private int nbPage;
    private T element;
    public ResultatSelection() {
        this.nbPage = 0;
    }

    public ResultatSelection(int nbPage, T element) {
        this.nbPage = nbPage;
        this.element = element;
    }

    public int getNbPage() {
        return this.nbPage;
    }

    public T getElement() {
        return this.element;
    }
}

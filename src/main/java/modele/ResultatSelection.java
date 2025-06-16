package modele;
/**
 * Le résultat d'une sélection dans une liste de pages.
 * Aide type génériques classe : https://www.geeksforgeeks.org/generic-class-in-java/
 * @param <T> Le type d'élémént géré.
 */
public class ResultatSelection<T> {
    private int nbPage;
    private T element;
    /**
     * Initialiser le résultat d'une sélection avec comme page 0.
     */
    public ResultatSelection() {
        this.nbPage = 0;
        this.element = null;
    }

    /**
     * Créer le résultat d'une sélection avec la page actuelle et l'élément choisi.
     * @param nbPage La page actuelle.
     * @param element L'élément choisi.
     */
    public ResultatSelection(int nbPage, T element) {
        this.nbPage = nbPage;
        this.element = element;
    }

    /**
     * Obtenir la page actuelle lors de la sélection.
     * @return La page actuelle lors de la sélection de l'élément.
     */
    public int getNbPage() {
        return this.nbPage;
    }

    /**
     * Obtenir l'élément choisi par l'utilisateur.
     * @return L'élement choisi par l'utilisateur.
     */
    public T getElement() {
        return this.element;
    }
}

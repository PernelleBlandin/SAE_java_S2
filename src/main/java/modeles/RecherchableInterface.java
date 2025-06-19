package modeles;

public interface RecherchableInterface {
    /**
     * Vérifier si l'élément est inclus dans la recherche.
     * @param recherche La chaîne de caractères de recherche.
     * @return true si l'élément est inclus dans la recherche, false sinon.
     */
    public boolean estIncluDansRecherche(String recherche);
}

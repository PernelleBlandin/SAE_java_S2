package vue;

import java.util.List;

import modeles.Livre;

/** L'interface des scènes affichant des listes de livres */
public interface SceneListBooksInterface extends SceneInterface {
    /**
     * Afficher la page avec la liste de livres.
     * @param titre Le titre du livre.
     * @param listeLivres La liste des livres.
     */
    public void showListBooks(String titre, List<Livre> listeLivres);
}

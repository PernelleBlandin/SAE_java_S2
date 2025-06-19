package vue;

import java.util.List;

import modeles.Livre;
import modeles.Magasin;

/** L'interface des scènes affichant des listes de livres */
public interface SceneGestionStockInterface extends SceneInterface {
    /**
     * Afficher la page pour gérer le stock d'un magasin.
     * 
     * @param listeLivres La liste des livres à afficher
     * @param magasin Un magasin
     */
    public void showStock(List<Livre> listeLivres, Magasin magasin);
}

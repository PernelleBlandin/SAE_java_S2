package controleurs;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Livre;
import vue.SceneListBooksInterface;

/** Contrôleur sur les boutons "Voir plus" pour afficher une liste des livres */
public class ControleurVoirPlus implements EventHandler<ActionEvent> {
    /** Vue de l'acceuil de la page client */
    private SceneListBooksInterface scene;
    /** Le titre de la liste */
    private String titre;
    /** Une liste de livres */
    private List<Livre> listeLivres;

    /**
     * Initialiser le contrôleur du bouton "Voir plus", affichant une liste de livres.
     * @param scene La scène de la page.
     * @param titre Le titre de la liste de livres.
     * @param listeLivres Une liste de livres à afficher en cas de clic sur le bouton.
     */
    public ControleurVoirPlus(SceneListBooksInterface scene, String titre, List<Livre> listeLivres) {
        this.scene = scene;
        this.titre = titre;
        this.listeLivres = listeLivres;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Voir plus", pour afficher une liste de livres.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.scene.showListBooks(this.titre, this.listeLivres);
    }
}
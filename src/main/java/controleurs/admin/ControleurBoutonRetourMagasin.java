package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.admin.AdminScene;

/**
 * Contrôleur du bouton "Retour" après une page magasin.
 */
public class ControleurBoutonRetourMagasin implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;

    /**
     * Initialiser le contrôleur bouton "Retour" après une page magasin.
     * @param adminScene La scène de la page administrateur
     * @param magasin Un magasin.
     */
    public ControleurBoutonRetourMagasin(AdminScene adminScene) {
        this.adminScene = adminScene;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Retour" et afficher la page magasin.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.adminScene.showMagasins();
    }
}

package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.admin.AdminScene;

public class ControleurBoutonAjouteMagasin implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;

    public ControleurBoutonAjouteMagasin(AdminScene adminScene) {
        this.adminScene = adminScene;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Ajouter magasin" et
     * 
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        // this.adminScene.showDemandeInfoVendeur(this.magasin);
    }
}
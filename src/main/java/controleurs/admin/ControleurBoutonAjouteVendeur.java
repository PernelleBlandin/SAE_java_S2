package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Magasin;
import vue.admin.AdminScene;

/** Contrôleur du bouton "Ajouter vendeur" */
public class ControleurBoutonAjouteVendeur implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le magasin du vendeur à ajouter */
    private Magasin magasin;


    /**
     * Initialiser le contrôleur du bouton "Ajouter vendeur".
     * @param adminScene La scène de la page administrateur
     */
    public ControleurBoutonAjouteVendeur(Magasin magasin, AdminScene adminScene) {
        this.magasin = magasin;
        this.adminScene = adminScene;
    }

    //@Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Ajouter vendeur" et
     * 
     * //@param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.adminScene.showDemandeInfoVendeur(this.magasin);
    }
}

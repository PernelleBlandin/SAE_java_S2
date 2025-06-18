package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Magasin;
import vue.admin.AdminScene;

/**
 * Contrôleur du bouton "Vendeurs" pour un magasin.
 */
public class ControleurBoutonMagasinVendeur implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Un magasin */
    private Magasin magasin;

    /**
     * Initialiser le contrôleur du bouton "Vendeurs" pour un magasin.
     * @param adminScene La scène de la page administrateur
     * @param magasin Un magasin.
     */
    public ControleurBoutonMagasinVendeur(AdminScene adminScene, Magasin magasin) {
        this.adminScene = adminScene;
        this.magasin = magasin;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton et afficher les vendeurs du magasin.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.adminScene.showVendeurs(this.magasin);
    }
}

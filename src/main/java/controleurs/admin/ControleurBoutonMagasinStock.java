package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.Magasin;
import vue.admin.AdminScene;

public class ControleurBoutonMagasinStock implements EventHandler<ActionEvent> {
    private AdminScene adminScene;
    private Magasin magasin;

    public ControleurBoutonMagasinStock(AdminScene adminScene, Magasin magasin) {
        this.adminScene = adminScene;
        this.magasin = magasin;
    }

    @Override
    public void handle(ActionEvent event) {
        this.adminScene.showStock(this.magasin);
    }
}

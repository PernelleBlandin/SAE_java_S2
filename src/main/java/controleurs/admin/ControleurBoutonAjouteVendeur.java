package controleurs.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;
import vue.admin.AdminScene;

public class ControleurBoutonAjouteVendeur implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin du vendeur à ajouter */
    private Magasin magasin;
    /** Le vendeur à supprimer */
    private Vendeur vendeur;

    // /**
    //  * @param magasin Le magasin dans lequel le vendeur est ajoute
    //  */
    // public ControleurBoutonAjouteVendeur(Magasin magasin) {
    //     this.magasin = magasin;
    // }

    //@Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Ajouter vendeur" et
     * 
     * //@param event Un événement.
     */
     public void handle(ActionEvent event) {
    //     this.adminScene.showDemandeInfoVendeur(this.magasin);
     }
}

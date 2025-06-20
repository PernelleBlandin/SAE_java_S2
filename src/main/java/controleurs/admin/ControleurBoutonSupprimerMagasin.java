package controleurs.admin;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue._components.alerts.AlertErreur;
import vue._components.alerts.AlertInfo;
import vue._components.alerts.AlertYesNo;
import vue.admin.AdminScene;

/**
 * Contrôleur du bouton supprimer magasin.
 */
public class ControleurBoutonSupprimerMagasin implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin à supprimer */
    private Magasin magasin;

    /**
     * Initialiser le contrôleur du bouton "Supprimer Magasin".
     * @param adminScene La scène de la page administrateur
     * @param modele Le modèle.
     * @param magasin Le magasin à supprimer.
     */
    public ControleurBoutonSupprimerMagasin(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Supprimer Magasin" et supprimer le magasin.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        AlertYesNo alert = new AlertYesNo(
            "Supprimer le magasin",
            "Êtes-vous sûr de vouloir supprimer le magasin " + this.magasin.toString() + " ?",
            "Cette action est irréversible."
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || !result.get().getText().equals("Oui")) return;

        try {
            this.modele.getMagasinBD().supprimerMagasin(this.magasin.getId());
        } catch (SQLException e) {
            new AlertErreur("Le magasin contient des vendeurs, stocks, ou commandes, et ne peut pas être supprimé.");
            return;
        }

        new AlertInfo("Magasin supprimé", "Le magasin a été supprimé avec succès.");

        // Actualiser de la liste 
        this.adminScene.showMagasins();
    }
}


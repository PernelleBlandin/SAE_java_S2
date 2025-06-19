package controleurs.admin;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.alerts.AlertErreurException;
import vue._components.alerts.AlertInfo;
import vue._components.alerts.AlertYesNo;
import vue.admin.AdminScene;

/** Contrôleur du bouton supprimer vendeur */
public class ControleurBoutonSupprimerMagasinVendeur implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin du vendeur à supprimer */
    private Magasin magasin;
    /** Le vendeur à supprimer */
    private Vendeur vendeur;

    /**
     * Initialiser le contrôleur du bouton "Supprimer Magasin Vendeur".
     * @param adminScene La scène de la page administrateur
     * @param modele Le modèle.
     * @param magasin Le magasin du vendeur à supprimer.
     * @param vendeur Le vendeur à supprimer.
     */
    public ControleurBoutonSupprimerMagasinVendeur(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin, Vendeur vendeur) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;
        this.vendeur = vendeur;
    }

    @Override
    /**
     * Recevoir un événement lors du clic sur le bouton "Supprimer Magasin" et supprimer le vendeur.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        AlertYesNo alert = new AlertYesNo(
            "Supprimer le magasin",
            "Êtes-vous sûr de vouloir supprimer le vendeur " + this.vendeur.toString() + " ?",
            "Cette action est irréversible."
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || !result.get().getText().equals("Oui")) return;

        try {
            this.modele.getVendeurBD().supprimerVendeur(this.vendeur.getId());
        } catch (SQLException e) {
            new AlertErreurException("Le vendeur n'a pas pu être supprimé.", e.getMessage());
            return;
        }

        new AlertInfo("Vendeur supprimé", "Le vendeur a été supprimé avec succès.");

        // Actualiser de la liste 
        this.adminScene.showVendeurs(this.magasin);
    }
}


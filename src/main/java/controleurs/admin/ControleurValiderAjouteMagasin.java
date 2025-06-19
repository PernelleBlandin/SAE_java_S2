package controleurs.admin;

import java.sql.SQLException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import vue._components.alerts.AlertErreurException;
import vue._components.alerts.AlertInfo;
import vue.admin.AdminDemandeInfoMagasinPane;
import vue.admin.AdminScene;

/** Contrôleur du bouton "Valider" pour ajouter un magasin */
public class ControleurValiderAjouteMagasin implements EventHandler<ActionEvent> {
    /** La scène de la page administrateur */
    private AdminScene adminScene;
    /** Le modèle de la librairie */
    private ChaineLibrairie modele;
    /** La pane pour les demandes d'informations sur le magasin */
    private AdminDemandeInfoMagasinPane demandeInfoMagasinPane;

    public ControleurValiderAjouteMagasin(AdminScene adminScene, ChaineLibrairie modele, AdminDemandeInfoMagasinPane demandeInfoMagasinPane) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.demandeInfoMagasinPane = demandeInfoMagasinPane;
    }

    @Override
    /**
     * Recevoir l'événement de clic sur le bouton "Valider" pour ajouter un magasin.
     * @param event L'événement de clic.
     */
    public void handle(ActionEvent event) {
        String nom = demandeInfoMagasinPane.getNom().getText();
        String ville = demandeInfoMagasinPane.getVille().getText();

        HashMap<String, String> donnees = new HashMap<>();
        donnees.put("nom", nom);
        donnees.put("ville", ville);

        try {
            this.modele.getMagasinBD().creerMagasin(donnees);
        } catch (SQLException e) {
            new AlertErreurException("Impossible d'ajouter le magasin", e.getMessage());
            return;
        }
        
        new AlertInfo("Magasin ajouté", "Le magasin a été ajouté avec succès.");

        this.adminScene.showMagasins();
    }
}
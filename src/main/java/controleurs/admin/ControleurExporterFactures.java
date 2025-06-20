package controleurs.admin;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.PasDeCommandeException;
import vue._components.alerts.AlertErreur;
import vue._components.alerts.AlertInfo;
import vue._components.numberField.IntegerField;
import vue.admin.AdminFacturesPane;

/** Contrôleur du bouton supprimer vendeur */
public class ControleurExporterFactures implements EventHandler<ActionEvent> {
    /** La pane de factures de la page administrateur */
    private AdminFacturesPane adminFacturesPane;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser le contrôleur pour exporter les factures dans la page administrateur.
     * @param adminFacturesPane La pane de factures de la page administrateur.
     * @param modele Le modèle
     */
    public ControleurExporterFactures(AdminFacturesPane adminFacturesPane, ChaineLibrairie modele) {
        this.adminFacturesPane = adminFacturesPane;
        this.modele = modele;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton d'exportation des factures.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        IntegerField moisTF = this.adminFacturesPane.getMoisTF();
        IntegerField anneeTF = this.adminFacturesPane.getAnneeTF();

        if (!moisTF.isValid() || !anneeTF.isValid()) {
            new AlertErreur("Veuillez entrer un mois et/ou une année valide.");
            return;
        }

        try {
            this.modele.exporterFactures(moisTF.getValeur(), anneeTF.getValeur());
            this.adminFacturesPane.resetTextFields();

            new AlertInfo(
                "Exportation des factures", 
                "Les factures du mois " + moisTF.getValeur() + " et de l'année " + anneeTF.getValeur() + " ont été exportées avec succès dans le dossier ./factures/" + anneeTF.getValeur() + "-" + moisTF.getValeur() + "."
            );
        } catch (PasDeCommandeException e) {
            new AlertErreur("Aucune facture trouvée pour le mois " + moisTF.getValeur() + " et l'année " + anneeTF.getValeur() + ".");
        }  catch (SQLException e) {
            new AlertErreur("Une erreur est survenue lors de l'exportation des factures : " + e.getMessage());
        }

    }
}


package controleurs.admin;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue.admin.AdminDemandeInfoVendeurPane;
import vue.admin.AdminScene;

public class ControleurValiderAjouteVendeur implements EventHandler<ActionEvent> {
    private AdminScene adminScene;
    private ChaineLibrairie modele;
    private Magasin magasin;
    private AdminDemandeInfoVendeurPane demandeInfoVendeurPane;

    public ControleurValiderAjouteVendeur(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin, AdminDemandeInfoVendeurPane demandeInfoVendeurPane) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;
        this.demandeInfoVendeurPane = demandeInfoVendeurPane;
    }

    @Override
    public void handle(ActionEvent event) {
        String nom = demandeInfoVendeurPane.getNom().getText();
        String prenom = demandeInfoVendeurPane.getPrenom().getText();
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            if (this.modele.getVendeurBD().obtenirIdVendeurExistant(nom, prenom) != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Vendeur déjà existant");
                alert.setContentText("Le vendeur existe déjà dans ce magasin.");
                alert.showAndWait();
                return;
            }

            this.modele.getVendeurBD().getIdVendeur(nom, prenom, this.magasin.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vendeur ajouté avec succès !");
            alert.showAndWait();
            adminScene.showVendeurs(this.magasin);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Erreur lors de l'ajout du vendeur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
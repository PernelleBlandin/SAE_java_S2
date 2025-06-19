package controleurs.admin;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import vue.admin.AdminScene;

public class ControleurValiderAjouteVendeur implements EventHandler<ActionEvent> {
    private AdminScene adminScene;
    private ChaineLibrairie modele;
    private Magasin magasin;
    private String nom;
    private String prenom;
    private int idVendeur;

    public ControleurValiderAjouteVendeur(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin, String nom, String prenom, int idVendeur) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;
        this.nom = nom;
        this.prenom = prenom;
        this.idVendeur = idVendeur;
    }

    @Override
    public void handle(ActionEvent event) {
        if (nom == null || this.nom.isEmpty() ||
            prenom == null || this.prenom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            if(this.modele.getVendeurBD().obtenirVendeurParIdEtMagasin(this.idVendeur, this.magasin.getId()) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("ID déjà utilisé");
                alert.setContentText("L'ID du vendeur existe déjà. Choisissez en un autre.");
                alert.showAndWait();
                return;
            }

            this.modele.getMagasinBD().ajouterVendeur(this.idVendeur, this.nom, this.prenom, this.magasin.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vendeur ajouté !");
            alert.showAndWait();
            adminScene.showVendeurs(this.magasin);
        } catch (SQLException e) {
            // TODO handle exception
        }
    }
}

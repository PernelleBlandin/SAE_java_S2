package vue.admin;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;
import controleurs.admin.ControleurValiderAjouteVendeur;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;


public class AdminDemandeInfoVendeurPane extends VBox {
    /** La scène administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin */
    private Magasin magasin;

    /**
     * Initialiser la pane pour les demandes d'informations sur les vendeurs.
     * @param adminScene La scène administrateur.
     */
    public AdminDemandeInfoVendeurPane(AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;

        this.setSpacing(20);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
            this.titre(),
            this.messageDemande()
        );
    }

    /**
     * Obtenir le titre de la pane.
     * @return Le titre de la pane.
     */
    private BorderPane titre() {
        BorderPane titre = new BorderPane();
        titre.setPadding(new Insets(10, 10, 10, 10));
        Label label = new Label("Demandes d'informations sur les vendeurs");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titre.setCenter(label);
        return titre;
    }

    /**
     * Obtenir les messages de demande d'informations.
     * @return Les messages de demande d'informations.
     */
    private VBox messageDemande(){
        VBox message = new VBox();
        message.setSpacing(10);
        message.setPadding(new Insets(10, 10, 10, 10));
        Label nomVendeur = new Label("Nom du vendeur :");
        TextField nom = new TextField();
        
        Label prenomVendeur = new Label("Prénom du vendeur :");
        TextField prenom = new TextField();

        String dernierId = "";
        try {
            dernierId = this.modele.getVendeurBD().getIdVendeur(nomVendeur.getText(), prenomVendeur.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO handle exception
        }
        Label idVendeur = new Label("ID du vendeur : ");
        TextField id = new TextField(dernierId);
        id.setEditable(true);
        int idInt = Integer.parseInt(id.getText());
        Button valider = new Button("Valider");
        valider.setOnAction(new ControleurValiderAjouteVendeur(this.adminScene, this.modele, this.magasin, nom.getText(), prenom.getText(), idInt));
        
        message.getChildren().addAll(
            nomVendeur, nom,
            prenomVendeur, prenom,
            idVendeur, id
        );
        return message;
    }
}   
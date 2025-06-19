package vue.admin;

import controleurs.admin.ControleurValiderAjouteMagasin;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;

/**
 * La pane pour les demandes d'informations pour l'ajout d'un magasin.
 */
public class AdminDemandeInfoMagasinPane extends VBox {
    /** La scène administrateur */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le nom du vendeur */
    private TextField nom;
    /** Le prénom du vendeur */
    private TextField ville;

    /**
     * Initialiser la pane pour les demandes d'informations sur les vendeurs.
     * @param adminScene La scène administrateur.
     * @param modele Le modèle de la librairie.
     */
    public AdminDemandeInfoMagasinPane(AdminScene adminScene, ChaineLibrairie modele) {
        this.adminScene = adminScene;
        this.modele = modele;

        this.nom = new TextField();
        this.ville = new TextField();

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
        Label label = new Label("Demandes d'informations sur le magasin");
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
        Label nomVendeur = new Label("Nom du magasin :");
        
        Label prenomVendeur = new Label("Ville du magasin :");

        Button valider = new Button("Valider");
        valider.setOnAction(new ControleurValiderAjouteMagasin(this.adminScene, this.modele, this));
        
        message.getChildren().addAll(
            nomVendeur, this.nom,
            prenomVendeur, this.ville,
            valider
        );
        return message;
    }

    /**
     * Obtenir le nom du vendeur.
     * @return Le champ de texte pour le nom du vendeur
     */
    public TextField getNom() {
        return this.nom;
    }

    /**
     * Obtenir le prénom du vendeur.
     * @return Le champ de texte pour le prenom du vendeur
     */
    public TextField getVille() {
        return this.ville;
    }
}   
package vue.seller;

import java.sql.SQLException;

import controleurs.seller.ControleurValiderAjoutLivre;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;

public class SellerAddBookPane extends HBox {
    private ChaineLibrairie modele;

    private TextField idLivre = new TextField();
    private TextField titreLivre = new TextField();
    private TextField prix = new TextField();  // TODO : autoriser seulement des int/Integer
    private TextField anneePubli = new TextField();
    private TextField nomAuteur = new TextField();
    private TextField deces = new TextField();
    private TextField naissance = new TextField();
    private TextField idAuteur = new TextField();
    private TextField editeur = new TextField();
    private TextField classification = new TextField();
    private TextField idClassification = new TextField();
    private TextField nbPages = new TextField();

    public SellerAddBookPane(ChaineLibrairie modele) {
        this.modele = modele;

        this.setSpacing(10);
        this.setPadding(new Insets(20, 20, 10, 20));

        this.getChildren().addAll(
            this.getCenterLeft(),
            this.getCenterRight()
        );
    }

    private VBox getCenterLeft() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 20, 10, 20));

        Label titre1 = new Label("Identifiant du livre :");
    
        Label titre2 = new Label("Titre du livre :");

        Label titre3 = new Label("Nombre de pages :");

        Label titre4 = new Label("Année de publication :");

        Label titre5 = new Label("Prix :");

        Label titre6 = new Label("Nom de l'auteur :");

        Label titre7 = new Label("Date de naissance de l'auteur :");

        vbox.getChildren().addAll(
            titre1, this.idLivre,
            titre2, this.titreLivre,
            titre3, this.nbPages,
            titre4, this.anneePubli,
            titre5, this.prix,
            titre6, this.nomAuteur,
            titre7, this.naissance
        );
        return vbox;
    }

    private VBox getCenterRight() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 20, 10, 20));

        Label titre8 = new Label("Date de décès de l'auteur (-1 si toujours en vie) :");

        Label titre9 = new Label("Identifiant de l'auteur :");
        this.idAuteur.setDisable(true);
        String idAuteurBD = null;
        try {
            idAuteurBD = this.modele.getLivreBD().getIdAuteur(this.nomAuteur.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ((idAuteurBD == null || idAuteurBD.isEmpty()) && !this.nomAuteur.getText().isEmpty()) {
            this.idAuteur.setDisable(false);
            this.idAuteur.setText("");
        } else if (idAuteurBD != null && !idAuteurBD.isEmpty()) {
            this.idAuteur.setText(idAuteurBD);
            this.idAuteur.setDisable(true);
        } else {
            this.idAuteur.setDisable(true);
        }

        Label titre10 = new Label("Nom de l'éditeur :");

        Label titre11 = new Label("Nom de classification :");

        Label titre12 = new Label("Identifiant de classification :");
        this.idClassification.setDisable(true);
        String idClassificationBD = null;
        try {
            idClassificationBD = this.modele.getLivreBD().getIdDewey(this.classification.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ((idClassificationBD == null || idClassificationBD.isEmpty()) && !this.classification.getText().isEmpty()) {
            this.idClassification.setDisable(false);
            this.idClassification.setText("");
        } else if (idClassificationBD != null && !idClassificationBD.isEmpty()) {
            this.idClassification.setText(idClassificationBD);
            this.idClassification.setDisable(true);
        } else {
            this.idClassification.setDisable(true);
            this.idClassification.setText("");
        }
    
        Button valider = new Button("Valider");
        valider.setOnAction(new ControleurValiderAjoutLivre(this, this.modele));

        vbox.getChildren().addAll(
            titre8, this.deces,
            titre9, this.idAuteur,
            titre10, this.editeur,
            titre11, this.classification,
            titre12, this.idClassification,
            valider
        );
        return vbox;
    }

    public void resetTextField(){
        this.idLivre.setText("");
        this.titreLivre.setText("");
        this.prix.setText("");
        this.anneePubli.setText("");
        this.nbPages.setText("");
        this.nomAuteur.setText("");
        this.naissance.setText("");
        this.deces.setText("");
        this.idAuteur.setText("");
        this.editeur.setText("");
        this.classification.setText("");
        this.idClassification.setText("");
    }

    /**
     * Mettre à jour la page de l'ajout d'un livre.
     */
    public void miseAJourAffichage() {
        this.getChildren().setAll(
            this.getCenterLeft(),
            this.getCenterRight()
        );
    }

    public void showPopUpLivreAjoute() {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Livre Express");
        alerte.setHeaderText("Livre ajouté avec succès");
        alerte.show();
    }

    public TextField getIdLivre(){ 
        return this.idLivre;
    }

    public TextField getTitreLivre(){
        return this.titreLivre;
    }

    public TextField getPrix(){
        return this.prix;
    }

    public TextField getAnneePubli(){
        return this.anneePubli;
    }

    public TextField getNomAuteur(){
        return this.nomAuteur;
    }

    public TextField getNaissance(){
        return this.naissance;
    }

    public TextField getDeces(){
        return this.deces;
    }

    public TextField getIdAuteur(){
        return this.idAuteur;
    }
    
    public TextField getEditeur(){
        return this.editeur;
    }

    public TextField getClassification(){
        return this.classification;
    }

    public TextField getIdClassification(){
        return this.idClassification;
    }
    
    public TextField getNbPages() {
        return this.nbPages;
    }
}

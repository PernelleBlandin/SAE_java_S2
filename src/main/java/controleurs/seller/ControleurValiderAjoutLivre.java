package controleurs.seller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modeles.ChaineLibrairie;
import vue._components.alerts.AlertErreur;
import vue._components.alerts.AlertErreurException;
import vue._components.numberField.NumberField;
import vue.seller.SellerAddBookPane;

/**
 * Contrôleur pour valider l'ajout d'un livre dans la librairie.
 */
public class ControleurValiderAjoutLivre implements EventHandler<ActionEvent> {
    /** Pane pour l'ajouter d'un livre */
    private SellerAddBookPane sellerAddBookPane;
    /** Le modèle du livre */
    private ChaineLibrairie modele;
    
    /**
     * Constructeur du contrôleur pour valider l'ajout d'un livre.
     * @param sellerAddBookPane La pane pour l'ajouter d'un livre
     * @param modele Le modèle de la librairie
     */
    public ControleurValiderAjoutLivre(SellerAddBookPane sellerAddBookPane, ChaineLibrairie modele) {
        this.sellerAddBookPane = sellerAddBookPane;
        this.modele = modele;
    }
    
    @Override
    /**
     * Récupère un événement lors d'un clic sur "valider" et retourne sur la page.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        if (this.sellerAddBookPane.getIdLivre().getText().length() != 0 && this.sellerAddBookPane.getTitreLivre().getText().length() != 0 && this.sellerAddBookPane.getPrix().getText().length() != 0 && this.sellerAddBookPane.getAnneePubli().getText().length() != 0 && this.sellerAddBookPane.getNomAuteur().getText().length() != 0 && this.sellerAddBookPane.getEditeur().getText().length() != 0 && this.sellerAddBookPane.getClassification().getText().length() != 0){ 
            String isbn = this.sellerAddBookPane.getIdLivre().getText();
            String titre = this.sellerAddBookPane.getTitreLivre().getText();

            NumberField fieldNbPages = this.sellerAddBookPane.getNbPages();
            NumberField fieldAnneePubli = this.sellerAddBookPane.getAnneePubli();
            NumberField fieldDeces = this.sellerAddBookPane.getDeces();
            NumberField fieldNaissance = this.sellerAddBookPane.getNaissance();
            NumberField fieldPrix = this.sellerAddBookPane.getPrix();

            if (!fieldNbPages.isValid() || !fieldAnneePubli.isValid() || !fieldDeces.isValid() || !fieldNaissance.isValid() || !fieldPrix.isValid()) {
                new AlertErreur("Mercid d'indiquer des nombres dans les champs correspondant.");
                return;
            }

            int nbPages = fieldNbPages.getValeur();
            int anneeDePublication = fieldAnneePubli.getValeur();
            int prix = fieldPrix.getValeur();
            
            String auteurNom = this.sellerAddBookPane.getNomAuteur().getText();
            int naissance = fieldNaissance.getValeur();
            int deces = fieldDeces.getValeur();
            
            String editeurNom = this.sellerAddBookPane.getEditeur().getText();
            String classificationNom = this.sellerAddBookPane.getClassification().getText();
            String idClassification = this.sellerAddBookPane.getIdClassification().getText();
            String idAuteur = this.sellerAddBookPane.getIdAuteur().getText();

            if (idClassification.length() > 3) {
                new AlertErreur("L'identifiant de la classification ne doit pas dépasser 3 caractères.");
                return;
            }

            try {
                if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null) {
                    if (idAuteur == null || idAuteur.trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livre Express");
                        alert.setHeaderText("Champ manquant");
                        alert.setContentText("Veuillez saisir un identifiant pour l'auteur.");
                        alert.showAndWait();
                        this.sellerAddBookPane.miseAJourAffichage();
                        return;
                    }
                }

                if (this.modele.getLivreBD().getIdDewey(classificationNom) == null) {
                    if (idClassification == null || idClassification.trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Livre Express");
                        alert.setHeaderText("Champ manquant");
                        alert.setContentText("Veuillez saisir un identifiant pour la classification.");
                        alert.showAndWait();
                        this.sellerAddBookPane.miseAJourAffichage();
                        return;
                    }
                }
            } catch (SQLException e) {
                new AlertErreurException("Erreur lors de la vérification des identifiants : ", e.getMessage());
                return;
            }
            try {
                if (this.modele.getLivreBD().obtenirLivre(isbn) != null) {
                    new AlertErreur("Un livre avec cet identifiant existe déjà.");
                    return;
                }
            } catch (SQLException e) {
                new AlertErreurException("Erreur lors de la vérification de l'identifiant du livre : ", e.getMessage());
                return;
            }
            try {
                if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, idClassification, naissance, deces);
                } else if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) != null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, naissance, deces);
                } else if (this.modele.getLivreBD().getIdAuteur(auteurNom) != null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idClassification);
                } else {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom);
                }
            } catch (SQLException e) {
                new AlertErreurException("Erreur lors de l'ajout du livre : ", e.getMessage());
                return;
            } 

            this.sellerAddBookPane.resetTextField();
            this.sellerAddBookPane.miseAJourAffichage();
            this.sellerAddBookPane.showPopUpLivreAjoute();
        } else {
            new AlertErreur("Merci de remplir tous les champs.");
            return;
        }
    }
}
package controleurs.seller;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modeles.ChaineLibrairie;
import vue.seller.SellerAddBookPane;

public class ControleurValider implements EventHandler<ActionEvent> {
    
    private SellerAddBookPane sellerAddBookPane;
    private ChaineLibrairie modele;
    
    public ControleurValider(SellerAddBookPane sellerAddBookPane, ChaineLibrairie modele) {
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
            int nbPages = 0;
            int anneeDePublication = 0;
            double prix = 0.0;
            
            try {
                nbPages = Integer.parseInt(this.sellerAddBookPane.getNbPages().getText());
                anneeDePublication = Integer.parseInt(this.sellerAddBookPane.getAnneePubli().getText());
                prix = Double.parseDouble(this.sellerAddBookPane.getPrix().getText());
            } catch (NumberFormatException e) {
                // TODO erreur handle exception
            }
            
            String auteurNom = this.sellerAddBookPane.getNomAuteur().getText();
            int naissance = 0;
            int deces = -1;
            
            try {
                naissance = Integer.parseInt(this.sellerAddBookPane.getNaissance().getText());
                deces = Integer.parseInt(this.sellerAddBookPane.getDeces().getText());
            } catch (NumberFormatException e) {
                // TODO erreur handle exception
            }
            
            String editeurNom = this.sellerAddBookPane.getEditeur().getText();
            String classificationNom = this.sellerAddBookPane.getClassification().getText();
            String idClassifications = this.sellerAddBookPane.getIdClassification().getText();
            String idAuteur = this.sellerAddBookPane.getIdAuteur().getText();

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
                    if (idClassifications == null || idClassifications.trim().isEmpty()) {
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
                // TODO
            }
            
            try {
                if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, idClassifications, naissance, deces);
                } else if (this.modele.getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) != null) {
                    this.modele.getLivreBD().ajouteLivreAuteurNonExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, naissance, deces);
                } else if (this.modele.getLivreBD().getIdAuteur(auteurNom) != null && 
                    this.modele.getLivreBD().getIdDewey(this.sellerAddBookPane.getClassification().getText()) == null) {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idClassifications);
                } else {
                    this.modele.getLivreBD().ajouteLivreAuteurExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom);
                }

                this.sellerAddBookPane.showPopUpLivreAjoute();
                this.sellerAddBookPane.resetTextField();
            } catch (SQLException e) {
                // TODO
            }
        }
        
        this.sellerAddBookPane.miseAJourAffichage();
    }
}
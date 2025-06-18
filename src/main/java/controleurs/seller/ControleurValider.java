package controleurs.seller;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.seller.SellerAddBookView;

public class ControleurValider implements EventHandler<ActionEvent> {
    
    private SellerAddBookView sellerAddBookView;
    
    public ControleurValider(SellerAddBookView vue) {
        this.sellerAddBookView = vue;
    }
    
    @Override
    /**
     * Récupère un événement lors d'un clic sur "valider" et retourne sur la page.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        if (this.sellerAddBookView.getIdLivre().getText().length() != 0 && 
            this.sellerAddBookView.getTitreLivre().getText().length() != 0 && 
            this.sellerAddBookView.getPrix().getText().length() != 0 && 
            this.sellerAddBookView.getAnneePubli().getText().length() != 0 && 
            this.sellerAddBookView.getAuteur().getText().length() != 0 && 
            this.sellerAddBookView.getEditeur().getText().length() != 0 && 
            this.sellerAddBookView.getClassification().getText().length() != 0) {
            
            String isbn = this.sellerAddBookView.getIdLivre().getText();
            String titre = this.sellerAddBookView.getTitreLivre().getText();
            int nbPages = 0;
            int anneeDePublication = 0;
            double prix = 0.0;
            
            try {
                nbPages = Integer.parseInt(this.sellerAddBookView.getNbPages().getText());
                anneeDePublication = Integer.parseInt(this.sellerAddBookView.getAnneePubli().getText());
                prix = Double.parseDouble(this.sellerAddBookView.getPrix().getText());
            } catch (NumberFormatException e) {
                // TODO erreur handle exception
            }
            
            String auteurNom = null;
            int naissance = -1;
            int deces = -1;
            
            if(this.sellerAddBookView.getAuteur().getText() != null){
                String[] infoAuteurs = this.sellerAddBookView.getAuteur().getText().split(",");
                if(infoAuteurs.length == 3){
                    for (int i = 0; i < infoAuteurs.length; i++) {
                        infoAuteurs[i] = infoAuteurs[i].trim();
                    }
                    if (infoAuteurs.length != 3) {
                        // TODO erreur handle exception
                    }
                    auteurNom = infoAuteurs[0];
                    naissance = Integer.parseInt(infoAuteurs[1]);
                    deces = Integer.parseInt(infoAuteurs[2]);
                }
            }
            
            String editeurNom = this.sellerAddBookView.getEditeur().getText();
            String classificationNom = this.sellerAddBookView.getClassification().getText();
            String idClassifications = this.sellerAddBookView.getIdClassification().getText();
            String idAuteur = this.sellerAddBookView.getIdAuteur().getText();
            
            try {
                if (this.sellerAddBookView.getModele().getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.sellerAddBookView.getModele().getLivreBD().getIdDewey(this.sellerAddBookView.getClassification().getText()) == null) {
                    this.sellerAddBookView.getModele().getLivreBD().ajouteLivreAuteurNonExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, idClassifications, naissance, deces);
                    this.sellerAddBookView.popUpLivreAjoute().showAndWait();
                    this.sellerAddBookView.reset();
                } else
                
                if (this.sellerAddBookView.getModele().getLivreBD().getIdAuteur(auteurNom) == null && 
                    this.sellerAddBookView.getModele().getLivreBD().getIdDewey(this.sellerAddBookView.getClassification().getText()) != null) {
                    this.sellerAddBookView.getModele().getLivreBD().ajouteLivreAuteurNonExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idAuteur, naissance, deces);
                    this.sellerAddBookView.popUpLivreAjoute().showAndWait();
                    this.sellerAddBookView.reset();
                } else 
                
                if (this.sellerAddBookView.getModele().getLivreBD().getIdAuteur(auteurNom) != null && 
                    this.sellerAddBookView.getModele().getLivreBD().getIdDewey(this.sellerAddBookView.getClassification().getText()) == null) {
                    this.sellerAddBookView.getModele().getLivreBD().ajouteLivreAuteurExistantClassificationNonExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom, idClassifications);
                    this.sellerAddBookView.popUpLivreAjoute().showAndWait();
                    this.sellerAddBookView.reset();
                } else {
                    this.sellerAddBookView.getModele().getLivreBD().ajouteLivreAuteurExistantClassificationExistante(isbn, titre, nbPages, anneeDePublication, prix, auteurNom, classificationNom, editeurNom);
                    this.sellerAddBookView.popUpLivreAjoute().showAndWait();
                    this.sellerAddBookView.reset();
                }
            } catch (SQLException e) {
                // TODO erreur handle exception
            }
        }
        
        this.sellerAddBookView.miseAJourAffichage();
    }
}

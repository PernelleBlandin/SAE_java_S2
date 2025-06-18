package vue.admin;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;


public class FenetreGestionVendeurs extends VBox {

    /** La fenêtre principale AdminView */
    private AdminView fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin pris en paramètre */
    private Magasin magasin;

    public FenetreGestionVendeurs(AdminView fenetrePrin, ChaineLibrairie modele, Magasin magasin) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
        this.magasin = magasin;
        this.setSpacing(50);
        this.getChildren().addAll(this.titre(), this.listeVendeurs());
    }
 
      
    private Label titre() { 
        Label titre = new Label("Vendeurs de " + this.magasin.getNom());
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre; 
    }
      
      
    private VBox listeVendeurs() {
    
        VBox laListe =new VBox(10);
        try { 
            for(Vendeur unVendeur : this.modele.getVendeurBD().obtenirListeVendeurParMagasin(magasin.getId())) {
            Image imgPoubelle = new Image("/images/trashcan.png");
            ImageView viewPoubelle = new ImageView(imgPoubelle);
            viewPoubelle.setFitHeight(35);
            viewPoubelle.setFitWidth(35);

            BorderPane ligneVendeur = new BorderPane();
            Label leVendeur = new Label(unVendeur.getNom() + unVendeur.getPrenom());
            HBox lesBtn = new HBox(10);

            Button btnSupprimerVendeur = new Button();
            btnSupprimerVendeur.setGraphic(viewPoubelle);


            //btnVendeursDuMag.setOnAction(new ControleurVoirVendeurs(mag));
            //btnSupprimerMag.setOnAction(new ControleurSupprMag(mag));
        
            ligneVendeur.setLeft(leVendeur);
            ligneVendeur.setRight(btnSupprimerVendeur);

            laListe.getChildren().add(ligneVendeur);

        }

            
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        

        return laListe;
    }
        
    
}

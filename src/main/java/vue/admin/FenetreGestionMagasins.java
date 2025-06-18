package vue.admin;

import modeles.ChaineLibrairie;
import modeles.Magasin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

 public class FenetreGestionMagasins extends VBox {

    /** La fenêtre principale AdminView */
    private AdminView fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;

    public FenetreGestionMagasins(AdminView fenetrePrin, ChaineLibrairie modele) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
    }

    
        
    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale pour voir les magasins.
     * @return lesMag La VBox
     */
    public VBox fenetreGestionMagasins() {
        VBox lesMag = new VBox(10);

        Label titre = new Label("Magasins");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lesMag.getChildren().add(titre);

        
        try { 
            for(Magasin unMag : this.modele.getMagasinBD().obtenirListeMagasin()) {
            Image imgVendeurs = new Image("/images/multiple_sellers_silhouette.png");
            ImageView viewVendeurs = new ImageView(imgVendeurs);
            Image imgPoubelle = new Image("/images/trashcan.png");
            ImageView viewPoubelle = new ImageView(imgPoubelle);
            viewVendeurs.setFitHeight(35);
            viewVendeurs.setFitWidth(35);
            viewPoubelle.setFitHeight(35);
            viewPoubelle.setFitWidth(35);

            BorderPane ligneMag = new BorderPane();
            HBox lesBtn = new HBox(10);
            Label leMag = new Label(unMag.getNom() + " - (" + unMag.getVille() + ")");

            Button btnVendeursDuMag = new Button();
            btnVendeursDuMag.setGraphic(viewVendeurs);
            Button btnSupprimerMag = new Button();
            btnSupprimerMag.setGraphic(viewPoubelle);

            lesBtn.getChildren().addAll(btnVendeursDuMag, btnSupprimerMag);

            //btnVendeursDuMag.setOnAction(new ControleurVoirVendeurs(mag));
            //btnSupprimerMag.setOnAction(new ControleurSupprMag(mag));
        
            ligneMag.setLeft(leMag);
            ligneMag.setRight(lesBtn);

            lesMag.getChildren().add(ligneMag);

        }

            
        } catch (Exception e) {
            //TODO: handle exception
        }
        
        

        return lesMag;
    }

    

 }

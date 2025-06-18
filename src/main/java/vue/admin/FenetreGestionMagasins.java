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
        this.setSpacing(50);
        this.getChildren().addAll(this.titre(), this.listeMagasins());
    }



    private Label titre() {
        Label titre = new Label("Magasins");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return titre; 
    }

    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale pour voir les magasins.
     * @return laListe La VBox
     */
    private VBox listeMagasins() {
        VBox laListe = new VBox(10);

        
        try { 
            for(Magasin unMag : this.modele.getMagasinBD().obtenirListeMagasin()) {

                Image imgStock = new Image("/images/book.png");
                ImageView viewStock = new ImageView(imgStock);
                Image imgVendeurs = new Image("/images/multiple_sellers_silhouette.png");
                ImageView viewVendeurs = new ImageView(imgVendeurs);
                Image imgPoubelle = new Image("/images/trashcan.png");
                ImageView viewPoubelle = new ImageView(imgPoubelle);
                
                viewVendeurs.setFitHeight(35);
                viewVendeurs.setFitWidth(35);
                viewPoubelle.setFitHeight(35);
                viewPoubelle.setFitWidth(35);
                viewStock.setFitHeight(35);
                viewStock.setFitWidth(35);

                BorderPane ligneMag = new BorderPane();
                HBox lesBtn = new HBox(10);
                Label leMag = new Label(unMag.getNom() + " - (" + unMag.getVille() + ")");

                Button btnVendeursDuMag = new Button();
                btnVendeursDuMag.setGraphic(viewVendeurs);
                Button btnSupprimerMag = new Button();
                btnSupprimerMag.setGraphic(viewPoubelle);
                Button btnStock = new Button();
                btnStock.setGraphic(viewStock);

                lesBtn.getChildren().addAll(btnVendeursDuMag, btnSupprimerMag, btnStock);

                //btnVendeursDuMag.setOnAction(new ControleurVoirVendeurs(mag));
                //btnSupprimerMag.setOnAction(new ControleurSupprMag(mag));
                //btnStock.setOnAction(new ControleurBoutonStockk(mag));
            
                ligneMag.setLeft(leMag);
                ligneMag.setRight(lesBtn);

                laListe.getChildren().add(ligneMag);


            }
        }
        
         catch (Exception e) {
            // TODO: handle exception
        }

        return laListe;
 }
}
package vue._components;

import controleurs.customers.ControleurInfoLivre;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;

public class BookCardComponentSeller extends BorderPane {
    private ChaineLibrairie modele;
    private Livre livre;
    private int quantite;

    public BookCardComponentSeller(ChaineLibrairie modele, Livre livre, int quantite) {
        super();
        
        this.modele = modele;
        this.livre = livre;
        this.quantite = quantite;
        

        this.setMinWidth(100);
        this.setPrefWidth(100);

        this.setStyle("-fx-border-color: black");
        this.setPadding(new Insets(10));

        // Positions des widgets

        ImageView top = this.top();
        this.setTop(top);

        VBox center = this.center();
        this.setCenter(center);

        HBox bottom = this.bottom();
        this.setBottom(bottom);
    }

    public int getQuantite() {
        return this.quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void miseAJourAffichage() {
        VBox center = this.center();
        this.setCenter(center);

        HBox bottom = this.bottom();
        this.setBottom(bottom);
    }

    private ImageView top() {
        ImageView bookImage = new ImageView("./images/unknownBook.png");
        bookImage.setFitHeight(60);
        bookImage.setFitWidth(60);
        return bookImage;
    }

    private VBox center() {
        VBox vboxDetails = new VBox();
        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vboxDetails.getChildren().addAll(
            labelTitre,
            new Label(livre.joinNomAuteurs()),
            new Label(String.format("%.2f€ - %d en stock", livre.getPrix(), this.quantite))
        );
        return vboxDetails;
    }

    private HBox bottom() {
        HBox hboxBoutons = new HBox();
        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);
        infoButton.setOnAction(new ControleurInfoLivre(livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView("./images/info.png");
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(infoButton);
        return hboxBoutons;
    }
}

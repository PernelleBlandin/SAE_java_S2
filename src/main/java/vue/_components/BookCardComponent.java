package vue._components;

import controleurs.ControleurAjouterPanier;
import controleurs.ControleurInfoLivre;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;

public class BookCardComponent extends BorderPane {
    private ChaineLibrairie modele;
    private Livre livre;
    private int quantite;

    public BookCardComponent(ChaineLibrairie modele, Livre livre, int quantite) {
        super();

        this.modele = modele;
        this.livre = livre;
        this.quantite = quantite;

        this.setMinWidth(250);
        this.setPrefWidth(250);

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

        // Bouton ajout panier
        Button addPanierButton = new Button("Ajouter au panier");
        if (this.quantite == 0) {
            addPanierButton.setDisable(true);
        } else {
            addPanierButton.setOnAction(new ControleurAjouterPanier(this, this.modele, this.livre));
        }

        addPanierButton.setPrefHeight(30);
        addPanierButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(addPanierButton, Priority.ALWAYS);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);
        infoButton.setOnAction(new ControleurInfoLivre(livre));

        // Configurer l'icône
        ImageView infoIcon = new ImageView("./images/info.png");
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(addPanierButton, infoButton);
        return hboxBoutons;
    }
}

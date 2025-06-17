package vue;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;
import modeles.Magasin;
import modeles.MagasinBD;

/** Une bibliothèque de composants */
public class BibliothequeComposants {
    /** Constructeur privé car bibliothèque */
    public BibliothequeComposants() {};

    /**
     * Obtenir une barre de recherche.
     * @param placeholder Le texte affiché sur la barre.
     * @return La barre de recherche.
     */
    public static TextField getSearchBar(String placeholder) {
        TextField root = new TextField();
        root.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        root.setPromptText(placeholder);
        root.setMinHeight(50);

        return root;
    }

    public static BorderPane getBookCard(Livre livre, Magasin magasin, MagasinBD magasinBD) throws SQLException {
        BorderPane root = new BorderPane();
        root.setMinWidth(250);
        root.setPrefWidth(250);
        
        root.setStyle("-fx-border-color: black");
        root.setPadding(new Insets(10));

        ImageView bookImage = new ImageView("./images/unknownBook.png");
        bookImage.setFitHeight(100);
        bookImage.setFitWidth(100);
        root.setTop(bookImage);

        int enStock = magasinBD.obtenirStockLivre(magasin.getId(), livre.getISBN());

        VBox vboxDetails = new VBox();
        Label labelTitre = new Label(livre.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vboxDetails.getChildren().addAll(
            labelTitre,
            new Label(livre.joinNomAuteurs()),
            new Label(String.format("%.2f€ - %d en stock", livre.getPrix(), enStock))
        );
        root.setCenter(vboxDetails);

        HBox hboxBoutons = new HBox();
        hboxBoutons.setSpacing(5);
        hboxBoutons.setAlignment(javafx.geometry.Pos.CENTER);

        // Bouton ajout panier
        Button addPanierButton = new Button("Ajouter au panier");
        if (enStock == 0) addPanierButton.setDisable(true);

        addPanierButton.setPrefHeight(30);
        addPanierButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(addPanierButton, Priority.ALWAYS);

        // Bouton d'info
        Button infoButton = new Button();
        infoButton.setPrefSize(30, 30);

        // Configurer l'icône
        ImageView infoIcon = new ImageView("./images/info.png");
        infoIcon.setFitHeight(24);
        infoIcon.setFitWidth(24);
        infoButton.setGraphic(infoIcon);

        hboxBoutons.getChildren().addAll(addPanierButton, infoButton);

        root.setBottom(hboxBoutons);

        return root;
    }
}

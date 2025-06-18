package vue.customers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurVoirPlus;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Livre;
import modeles.Magasin;

/** La vue de l'accueil d'un client */
public class CustomerHomePane extends VBox {
    /** La vue principal */
    private CustomerScene customerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param customerScene La vue principal.
     * @param modele Le modèle.
     */
    public CustomerHomePane(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;

        this.setSpacing(10);
        this.setPadding(new Insets(10, 20, 10, 20));

        this.miseAJourAffichage();
    }

    public void miseAJourAffichage() {
        Client client = this.modele.getClientActuel();

        // Début
        Label welcome = new Label(String.format("Bienvenue %s ! 👋", client.toString()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);

        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            // TODO: handle exception
        }

        Label magasinLabel = new Label("Votre magasin");
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        ComboBox<Magasin> magasinComboBox = new ComboBox<>();
        magasinComboBox.getItems().addAll(listeMagasins);
        magasinComboBox.setValue(client.getMagasin());
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);

        // Recommendations
        VBox recommendationsVBox = this.getRecommendations();

        // Meilleures ventes
        VBox meilleuresVentesVBox = this.getMeilleuresVentes();

        this.getChildren().addAll(welcome, magasinLabel, magasinComboBox, recommendationsVBox, meilleuresVentesVBox);
    }

    /**
     * Obtenir le titre d'une section, avec un bouton "Voir tout".
     * @param titreSection Le titre de la section.
     * @param titreListe Le titre du menu après le bouton "Voir tout".
     * @return La section, avec son titre et un bouton "Voir tout".
     */
    private GridPane getSectionTitle(String titreSection, String titreListe, List<Livre> listeLivres) {
        GridPane section = new GridPane();
        section.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label(titreSection);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        section.add(titleLabel, 0, 0);
        
        Button viewAllButton = new Button("Voir tout");
        viewAllButton.setOnAction(new ControleurVoirPlus(this.customerScene, titreListe, listeLivres));
        GridPane.setHalignment(viewAllButton, HPos.RIGHT);
        section.add(viewAllButton, 1, 0);
        
        return section;
    }

    /**
     * Obtenir la VBox des recommendations.
     * @return La VBox des recommendations.
     */
    private VBox getRecommendations() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Client client = this.modele.getClientActuel();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.onVousRecommande(client);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        GridPane recommendationsVBox = this.getSectionTitle("Livre Express vous recommande", "Recommandations", livres);

        HBox livresRecommendes = new HBox();
        livresRecommendes.setSpacing(20);

        for (int i = 0; i < livres.size() && i < 4; i++) {
            Livre livre = livres.get(i);
            BorderPane bookCard = this.customerScene.createOrGetCardComponent(livre);
            
            HBox.setHgrow(bookCard, Priority.ALWAYS);
            livresRecommendes.getChildren().add(bookCard);
        }

        vbox.getChildren().addAll(recommendationsVBox, livresRecommendes);
        return vbox;
    }

    /**
     * Obtenir la VBox des meilleures ventes.
     * @return La VBox des meilleures ventes.
     */
    private VBox getMeilleuresVentes() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        List<Livre> livres = new ArrayList<>();
        try {
            livres = this.modele.getLivreBD().obtenirLivresMeilleuresVentes();
        } catch (SQLException e) {
            // TODO: handle exception
        }

        GridPane recommendationsVBox = this.getSectionTitle("Meilleures Ventes", "Liste des meilleures ventes", livres);

        HBox topLivresVentes = new HBox();
        topLivresVentes.setSpacing(20);

        for (int i = 0; i < livres.size() && i < 4; i++) {
            Livre livre = livres.get(i);
            BorderPane bookCard = this.customerScene.createOrGetCardComponent(livre);
            
            HBox.setHgrow(bookCard, Priority.ALWAYS);
            topLivresVentes.getChildren().add(bookCard);
        }

        vbox.getChildren().addAll(recommendationsVBox, topLivresVentes);
        return vbox;
    }
}

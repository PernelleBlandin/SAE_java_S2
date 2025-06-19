package vue.customers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.customers.ControleurChangerMagasin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Livre;
import modeles.Magasin;
import vue._components.SectionVoirPlusLivresPane;
import vue._components.alerts.AlertErreurException;

/** La vue de l'accueil d'un client */
public class CustomerHomePane extends VBox {
    /** La scène de la page client */
    private CustomerScene customerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La liste des livres recommandés */
    private List<Livre> recommendedLivres;
    /** La liste des meilleures ventes */
    private List<Livre> meilleursVentesLivres;

    /**
     * Initialiser le pane de l'accueil d'un client.
     * @param customerScene La scène de la page client
     * @param modele Le modèle.
     */
    public CustomerHomePane(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;

        this.initRecommendedLivres();
        this.initMeilleuresVentesLivres();

        this.setSpacing(10);
        this.setPadding(new Insets(10, 20, 10, 20));

        Client client = this.modele.getClientActuel();
        this.getChildren().addAll(
            this.getTitleLabel(client),
            this.getMagasinsList(client),
            this.getRecommendations(),
            this.getMeilleuresVentes()
        );
    }

    /**
     * Récupérer la liste des livres recommendés, et le stocker dans les variables de la classe.
     */
    private void initRecommendedLivres() {
        Client client = this.modele.getClientActuel();

        this.recommendedLivres = new ArrayList<>();
        try {
            this.recommendedLivres = this.modele.onVousRecommande(client);
        } catch (SQLException e) {
            new AlertErreurException("La liste des livres recommendés n'a pas pu être recupérée.", e.getMessage());
        }
    }

    /**
     * Récupérer la liste des meilleures ventes de livres, et le stocker dans les variables de la classe.
     */
    private void initMeilleuresVentesLivres() {
        this.meilleursVentesLivres = new ArrayList<>();
        try {
            this.meilleursVentesLivres = this.modele.getLivreBD().obtenirLivresMeilleuresVentes();
        } catch (SQLException e) {
            new AlertErreurException("La liste des meilleures ventes n'a pas pu être recupérée.", e.getMessage());
        }
    }

    /**
     * Obtenir le titre, sous forme d'un label, de la page.
     * @param client Le client.
     * @return Le titre de la page
     */
    private Label getTitleLabel(Client client) {
        Label welcome = new Label(String.format("Bienvenue %s ! 👋", client.toString()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);

        return welcome;
    }

    /**
     * Obtenir la liste des magasins, avec un titre et un combo-box pour le changer.
     * @param client Un client.
     * @return La liste des magasins et le titre de la VBox.
     */
    private VBox getMagasinsList(Client client) {
        VBox magasinsVBox = new VBox();
        magasinsVBox.setSpacing(10);

        // Label
        Label magasinLabel = new Label("Votre magasin");
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // Combo-box avec liste magasin
        List<Magasin> listeMagasins = new ArrayList<>();
        try {
            listeMagasins = this.modele.getMagasinBD().obtenirListeMagasin();
        } catch (SQLException e) {
            new AlertErreurException("La liste des magasins n'a pas pu être récupérée.", e.getMessage());
        }

        ComboBox<Magasin> magasinComboBox = new ComboBox<>();
        magasinComboBox.getItems().addAll(listeMagasins);
        magasinComboBox.setValue(client.getMagasin());
        magasinComboBox.setMaxWidth(Double.MAX_VALUE);

        magasinComboBox.setOnAction(new ControleurChangerMagasin(this.customerScene, this.modele));

        magasinsVBox.getChildren().addAll(magasinLabel, magasinComboBox);
        return magasinsVBox;
    }

    /**
     * Obtenir la VBox des recommendations.
     * @return La VBox des recommendations.
     */
    private SectionVoirPlusLivresPane getRecommendations() {
        return new SectionVoirPlusLivresPane(
            this.customerScene,
            "Livre Express vous recommande",
            "Recommandations",
            this.recommendedLivres,
            4
        );
    }

    /**
     * Obtenir la VBox des meilleures ventes.
     * @return La VBox des meilleures ventes.
     */
    private SectionVoirPlusLivresPane getMeilleuresVentes() {
        return new SectionVoirPlusLivresPane(
            this.customerScene,
            "Meilleures Ventes",
            "Liste des meilleures ventes",
            this.meilleursVentesLivres,
            4
        );
    }

    /**
     * Mettre à jour l'affichage de la pane.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(2, this.getRecommendations());
        this.getChildren().set(3, this.getMeilleuresVentes());
    }
}

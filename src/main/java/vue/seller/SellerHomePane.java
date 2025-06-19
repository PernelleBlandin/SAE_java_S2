package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurVoirPlus;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.alerts.AlertErreurException;

/** La page d'acceuil de la page vendeur */
public class SellerHomePane extends VBox {
    /** La scène de la page vendeur */
    private SellerScene sellerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La liste des meilleures ventes */
    private List<Livre> meilleursVentesLivres;

    /**
     * Initialiser le pane de l'accueil d'un vendeur.
     * @param sellerScene La scène de la page vendeur.
     * @param modele Le modèle.
     */
    public SellerHomePane(SellerScene sellerScene, ChaineLibrairie modele) {
        this.sellerScene = sellerScene;
        this.modele = modele;

        this.initMeilleuresVentesLivres();

        this.setSpacing(10);
        this.setPadding(new Insets(10, 20, 10, 20));

        this.getChildren().addAll(
            this.getTitleVBox(),
            this.getMeilleuresVentes()
        );
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

    private VBox getTitleVBox() {
        VBox vbox = new VBox();

        Vendeur vendeur = this.modele.getVendeurActuel();

        Label welcome = new Label(String.format("Bienvenue %s ! 👋", vendeur.toString()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);
        vbox.getChildren().add(welcome);

        Magasin magasin = vendeur.getMagasin();
        Label magasinLabel = new Label(String.format("Magasin : %s", magasin.toString()));
        magasinLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        magasinLabel.setMaxWidth(Double.MAX_VALUE);
        magasinLabel.setAlignment(Pos.CENTER);
        vbox.getChildren().add(magasinLabel);

        return vbox;
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
        viewAllButton.setOnAction(new ControleurVoirPlus(this.sellerScene, titreListe, listeLivres));
        GridPane.setHalignment(viewAllButton, HPos.RIGHT);
        section.add(viewAllButton, 1, 0);
        
        return section;
    }

    /**
     * Obtenir la VBox des meilleures ventes.
     * @return La VBox des meilleures ventes.
     */
    private VBox getMeilleuresVentes() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);

        GridPane recommendationsVBox = this.getSectionTitle("Meilleures Ventes", "Liste des meilleures ventes", this.meilleursVentesLivres);

        HBox topLivresVentes = new HBox();
        topLivresVentes.setSpacing(20);

        for (int i = 0; i < this.meilleursVentesLivres.size() && i < 3; i++) {
            Livre livre = this.meilleursVentesLivres.get(i);
            BorderPane bookCard = this.sellerScene.createOrGetCardComponent(livre);
            
            HBox.setHgrow(bookCard, Priority.ALWAYS);
            topLivresVentes.getChildren().add(bookCard);
        }

        vbox.getChildren().addAll(recommendationsVBox, topLivresVentes);
        return vbox;
    }

    /**
     * Mettre à jour l'affichage de la pane.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(1, this.getMeilleuresVentes());
    }
}

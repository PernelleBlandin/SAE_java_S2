package vue.admin;

import java.util.List;

import controleurs.admin.ControleurBoutonAjouteMagasin;
import controleurs.admin.ControleurBoutonRetourMagasin;
import controleurs.admin.ControleurBoutonSupprimerMagasinVendeur;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.BaseListElementsWithSearchPane;

/** Liste des magasins à afficher à l'administrateur */
public class AdminMagasinsVendeursPane extends BaseListElementsWithSearchPane<Vendeur> {
    /**  La scène admin */
    private AdminScene adminScene;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le magasin */
    private Magasin magasin;

    /**
     * Constructeur de la pane des vendeurs d'un magasin.
     * @param listeVendeurs La liste des vendeurs à afficher dans la pane.
     * @param adminScene La scène de la page administrateur.
     * @param modele Le modèle de la librairie.
     * @param magasin Le magasin pour lequel on affiche les vendeurs.
     */
    public AdminMagasinsVendeursPane(List<Vendeur> listeVendeurs, AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        super("Vendeurs de " + magasin.getNom(), listeVendeurs, 5, 1, "Rechercher un vendeur...");

        this.adminScene = adminScene;
        this.modele = modele;
        this.magasin = magasin;

        this.addComponents();
    }

    @Override
    /**
     * Ajouter les composants à la pane.
     */
    public void addComponents() {
        this.getChildren().addAll(
            this.getHeaderPane(),
            this.searchBar,
            this.listeElements.size() >= 1 ? this.getListeLivresPane() : this.aucunResultatBox(),
            this.getNavigationsBoutonsPane()
        );
    }

    /**
     * Obtenir le titre et le bouton "Ajouter Magasin" de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public BorderPane getHeaderPane() {
        BorderPane header = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurBoutonRetourMagasin(this.adminScene));
        header.setLeft(backButton);

        Label titre = new Label("Vendeurs de " + this.magasin.getNom());
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        header.setCenter(titre);

        Button btnAjouteMagasin = new Button("Ajouter un vendeur");
        btnAjouteMagasin.setOnAction(new ControleurBoutonAjouteMagasin(this.adminScene));
        header.setRight(btnAjouteMagasin);

        return header;
    }

    /**
     * Obtenir la VBox contenant la liste des "cartes" des vendeurs.
     * @param vendeur Le vendeur à afficher.
     * @return La VBox contenant la liste des "cartes" des vendeurs.
     */
    public BorderPane getElementComponent(Vendeur vendeur) {
        Image imgPoubelle = new Image("/images/trashcan.png");
        ImageView viewPoubelle = new ImageView(imgPoubelle);
        viewPoubelle.setFitHeight(35);
        viewPoubelle.setFitWidth(35);

        BorderPane ligneVendeur = new BorderPane();
        ligneVendeur.setPadding(new Insets(10, 10, 10, 10));
        ligneVendeur.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Label leVendeur = new Label(vendeur.toString());
        leVendeur.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button btnSupprimerVendeur = new Button();
        btnSupprimerVendeur.setGraphic(viewPoubelle);
        btnSupprimerVendeur.setOnAction(new ControleurBoutonSupprimerMagasinVendeur(this.adminScene, this.modele, this.magasin, vendeur));

        ligneVendeur.setLeft(leVendeur);
        ligneVendeur.setRight(btnSupprimerVendeur);

        return ligneVendeur;
    }    
}

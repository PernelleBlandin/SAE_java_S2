package vue.seller;

import java.util.List;

import controleurs.seller.ControleurAcceuilVendeur;
import controleurs.seller.ControleurNavigationVendeur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue._components.bookCard.BookCardComponentSeller;

/** Page d'une liste de livres */
public class SellerListBook {
    /** La vue principal */
    private SellerHomeView sellerHomeView;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    /** VBox Central */
    private VBox center;

    private int curPage;
    private String titre;
    private List<Livre> listeLivres;

    /**
     * Initialiser la vue de l'accueil d'un vendeur.
     * @param sellerHomeView La vue de l'accueil du vendeur.
     * @param modele Le modèle.
     * @param listeLivres Une liste de livres.
     */
    public SellerListBook(SellerHomeView sellerHomeView, ChaineLibrairie modele, String titre, List<Livre> listeLivres) {
        this.sellerHomeView = sellerHomeView;
        this.modele = modele;

        this.curPage = 0;

        this.titre = titre;
        this.listeLivres = listeLivres;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        HBox header = this.sellerHomeView.getHeader();
        this.root.setTop(header);

        this.center = this.getCenter();
        this.root.setCenter(center);
        
        this.scene = new Scene(this.root);
    }

    /**
     * Obtenir la page actuelle.
     * @return La page actuelle.
     */
    public int getCurPage() {
        return this.curPage;
    }

    /**
     * Définir la page actuelle.
     * @param page La nouvelle page.
     */
    public void setCurPage(int page) {
        this.curPage = page;
    }
    
    /**
     * Obtenir l'élement central.
     * @return L'élement central de la page.
     */
    public VBox getCenter() {
        VBox center = new VBox();

        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilVendeur(this.sellerHomeView.getApp()));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label(this.titre);
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        borderPaneTitre.setCenter(labelTitre);
        
        center.getChildren().add(borderPaneTitre);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.curPage) + (intLigne * nbColonnes) + intColonne;
                if (index >= this.listeLivres.size()) break;

                Livre livre = this.listeLivres.get(index);

                BorderPane bookCard = new BookCardComponentSeller(this.modele, livre, 3);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            center.getChildren().add(hboxLigne);
        }

        int maxPages = Math.ceilDiv(this.listeLivres.size(), nbElementsParPage);

        // Boutons navigations

        HBox hboxBoutons = new HBox();

        Button previousButton = new Button("Précédent");
        if (this.curPage == 0) previousButton.setDisable(true);
        previousButton.setOnAction(new ControleurNavigationVendeur(this));
        previousButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Label currentPageLabel = new Label(String.format("Page %d sur %d", this.curPage + 1, maxPages));
        currentPageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button nextButton = new Button("Suivant");
        if ((this.curPage + 1) >= maxPages) nextButton.setDisable(true);
        nextButton.setOnAction(new ControleurNavigationVendeur(this));
        nextButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        hboxBoutons.setSpacing(10);
        hboxBoutons.setAlignment(Pos.CENTER);
        hboxBoutons.getChildren().addAll(previousButton, currentPageLabel, nextButton);

        center.getChildren().add(hboxBoutons);
        
        center.setSpacing(15);
        center.setPadding(new Insets(10, 20, 10, 20));

        return center;
    }

    /**
     * Mettre à jour l'affichage
     */
    public void miseAJourAffichage() {
        this.center = this.getCenter();
        this.root.setCenter(this.center);
    }

    /**
     * Obtenir la scène de l'accueil d'un client.
     * @return La scène de l'accueil d'un client.
     */
    public Scene getScene() {
        return this.scene;
    }
}
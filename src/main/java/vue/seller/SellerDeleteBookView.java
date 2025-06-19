package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurDeconnexion;
import controleurs.customers.ControleurAcceuilClient;
import controleurs.seller.ControleurAcceuilVendeur;
import controleurs.seller.ControleurPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.AppIHM;
import vue._components.BaseListBooksPane;
import vue._components.SearchBar;
import vue._components.bookCard.BookCardDeleteComponentSeller;

/**
 * Vue pour la suppression d'un livre côté vendeur.
 * Affiche le header et le menu latéral gauche identiques aux autres pages vendeur.
 */
public class SellerDeleteBookView extends BaseListBooksPane{

    /**
     * Référence vers l'application principale.
     */
    private AppIHM app;

    /**
     * Référence vers le modèle de la chaîne de librairies.
     */
    private ChaineLibrairie modele;

    /**
     * Scène JavaFX associée à cette vue.
     */
    private Scene scene;

    /**
     * Layout principal de la page.
     */
    private BorderPane root;

    /**
     * Menu latéral gauche de la page.
     */
    private VBox left;

    /**
     * Contenu central de la page.
     */
    private VBox center;

    /**
     * Instance de la vue SellerHomeView.
     */
    private SellerHomeView sellerHomeView;

    /**
     * Constructeur de la vue SellerDeleteBookView.
     * @param app L'application principale
     * @param modele Le modèle de la chaîne de librairies
     */
    public SellerDeleteBookView(AppIHM app, ChaineLibrairie modele) throws SQLException {
        super("Supprimer un livre", listeLivres(modele));
        this.app = app;
        this.modele = modele;
        this.sellerHomeView = new SellerHomeView(app, modele);

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        HBox header = this.getHeader();
        this.root.setTop(header);

        this.center = this.getCenter();
        this.root.setCenter(center);

        this.left = this.getLeft();
        this.root.setLeft(left);

        this.scene = new Scene(this.root);
    }

    /**
     * Récupère la liste des livres depuis le modèle.
     * Si une exception SQL survient, retourne une liste vide.
     *
     * @param modele Le modèle de la chaîne de librairies
     * @return La liste des livres, ou une liste vide en cas d'erreur SQL
     */
    private static List<Livre> listeLivres(ChaineLibrairie modele) {
        try {
            return modele.getLivreBD().obtenirListeLivre();
        } catch (SQLException e) {
            // TODO handle exception
        }
        return new ArrayList<>();
    }

    /**
     * Génère le header de la page avec le logo, la barre de recherche et le bouton déconnexion.
     * @return Le HBox du header
     */
    public HBox getHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView("/images/logo.png");
        logo.setFitWidth(304.6);
        logo.setFitHeight(91.2);

        Button buttonLogo = new Button();
        buttonLogo.setAlignment(Pos.CENTER);
        buttonLogo.setStyle("-fx-background-color: transparent;");
        buttonLogo.setGraphic(logo);
        buttonLogo.setOnAction(new ControleurAcceuilVendeur(this.app));

        SearchBar searchBar = new SearchBar("Rechercher un livre...");

        Button deconnexionButton = new Button("Déconnexion");
        deconnexionButton.setMinSize(120, 50);
        deconnexionButton.setOnAction(new ControleurDeconnexion(this.app));

        header.getChildren().addAll(buttonLogo, searchBar, deconnexionButton);
        header.setSpacing(10);
        header.setPadding(new Insets(10, 20, 10, 0));

        return header;
    }

    /**
     * Génère le contenu central de la page pour la suppression d'un livre.
     * Actuellement, il est vide et doit être implémenté.
     * @return Le VBox du contenu central
     */
    public VBox getCenter() {
        VBox center = new VBox(30);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(60, 40, 40, 40));

        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        // Supposons que getListeLivres() retourne la liste des livres à afficher
        List<Livre> listeLivres = this.getListeLivres();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.getCurPage()) + (intLigne * nbColonnes) + intColonne;
                if(index >= listeLivres.size()){
                    break;
                }
                Livre livre = listeLivres.get(index);

            BookCardDeleteComponentSeller bookCard = new BookCardDeleteComponentSeller(livre, 1, this.modele, this);
            HBox.setHgrow(bookCard, javafx.scene.layout.Priority.ALWAYS);
            hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

            center.getChildren().add(listeLivresVBox);
            return center;
        }
        

    /**
     * Génère le menu latéral gauche avec les boutons de navigation vendeur.
     * @return Le VBox du menu gauche
     */
    public VBox getLeft() {
        VBox left = new VBox(70);

        Button ajouterLivre = new Button("Ajouter un livre");
        ajouterLivre.setOnAction(new ControleurPage(this.app));

        Button supprimerLivre = new Button("Supprimer un livre");
        supprimerLivre.setOnAction(new ControleurPage(this.app));
        supprimerLivre.setDisable(true);

        Button majQteLivre = new Button("Mettre à jour la quantité d'un livre");
        majQteLivre.setOnAction(new ControleurPage(this.app));

        Button transfertLivre = new Button("Transférer un livre");
        transfertLivre.setOnAction(new ControleurPage(this.app));

        Button rpClient = new Button("Agir en tant que client");
        rpClient.setOnAction(new ControleurPage(this.app));

        left.getChildren().addAll(ajouterLivre, supprimerLivre, majQteLivre, transfertLivre, rpClient);
        left.setPadding(new Insets(30, 50, 0, 15));
        left.setAlignment(Pos.TOP_CENTER);
        return left;
    }

    /**
     * Retourne la scène associée à cette vue.
     * @return La scène associée à cette vue.
     */
    public Scene getSceneSeller() {
        return this.scene;
    }

    /**
     * Retourne l'application principale.
     * @return L'application AppIHM
     */
    public AppIHM getApp() {
        return this.app;
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    protected BorderPane getTitleAndBackButtonPane() {
        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilVendeur(this.app));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label(this.getTitre());
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        borderPaneTitre.setCenter(labelTitre);
        return borderPaneTitre;
    }

    /**
     * Obtenir la liste des livres à afficher dans la pane.
     * @return La liste des livres.
     */
    protected VBox getListeLivresPane() {
        VBox listeLivresVBox = new VBox();
        listeLivresVBox.setSpacing(20);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        List<Livre> listeLivres = this.getListeLivres();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.getCurPage()) + (intLigne * nbColonnes) + intColonne;
                if (index >= listeLivres.size()) break;

                Livre livre = listeLivres.get(index);

                BookCardDeleteComponentSeller bookCard = new BookCardDeleteComponentSeller(livre, 1, this.modele, this);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            listeLivresVBox.getChildren().add(hboxLigne);
        }

        return listeLivresVBox;
    }

    /**
     * Mettre à jour l'affichage de la vue.
     * Cette méthode est appelée pour rafraîchir l'affichage après une action utilisateur.
     */
    public void miseAJourAffichage() {
        this.setListeLivres(listeLivres(this.modele));
        super.miseAJourAffichage();
    }
}

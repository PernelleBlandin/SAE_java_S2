package vue.customers;

import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.BibliothequeComposants;
import vue.MAJVueInterface;

/** Page d'une liste de livres */
public class CustomerListBook implements MAJVueInterface {
    /** La vue principal */
    private CustomerHomeView customerView;
    /** Le modèle */
    private ChaineLibrairie modele;

    /** La scène de la vue */
    private Scene scene;

    /** La scène principal */
    private BorderPane root;
    /** VBox Central */
    private VBox center;

    private int curPage;
    private List<Livre> listeLivres;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param customerView La vue de l'accueil du client.
     * @param modele Le modèle.
     * @param listeLivres Une liste de livres.
     */
    public CustomerListBook(CustomerHomeView customerView, ChaineLibrairie modele, List<Livre> listeLivres) {
        this.customerView = customerView;
        this.modele = modele;

        this.curPage = 0;
        this.listeLivres = listeLivres;

        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #ffffff;");

        HBox header = this.customerView.getHeader();
        this.root.setTop(header);

        this.center = this.getCenter();
        this.root.setCenter(center);
        
        this.scene = new Scene(this.root);
    }
    
    /**
     * Obtenir l'élement central.
     * @return L'élement central de la page.
     */
    public VBox getCenter() {
        VBox center = new VBox();

        int nbLignes = 2;
        int nbColonnes = 5;
        int nbElementsParPage = nbLignes * nbColonnes;

        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.curPage) + (intLigne * nbColonnes) + intColonne;
                if (index >= this.listeLivres.size()) break;

                Livre livre = this.listeLivres.get(index);
                try {
                    BorderPane bookCard = BibliothequeComposants.getBookCard(livre, this.modele, this);
                    hboxLigne.getChildren().add(bookCard);
                } catch (SQLException e) {
                    // TODO: handle exception
                }
            }
            center.getChildren().add(hboxLigne);
        }
        
        center.setSpacing(10);
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
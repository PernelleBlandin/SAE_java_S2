package vue.customers;

import java.sql.SQLException;
import java.util.List;

import controleurs.ControleurAcceuilClient;
import controleurs.ControleurNavigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.DetailLivre;
import modeles.Livre;
import modeles.LivreIntrouvableException;
import modeles.Magasin;
import modeles.Panier;
import vue._components.bookCard.CustomerBookCardComponent;

/** Page d'une liste de livres */
public class CustomerListBookPane extends VBox {
    /** La vue principal */
    private CustomerScene customerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    private int curPage;
    private String titre;
    private List<Livre> listeLivres;

    /**
     * Initialiser la vue de l'accueil d'un client.
     * @param customerScene La vue de l'accueil du client.
     * @param modele Le modèle.
     * @param listeLivres Une liste de livres.
     */
    public CustomerListBookPane(CustomerScene customerScene, ChaineLibrairie modele, String titre, List<Livre> listeLivres) {
        this.customerScene = customerScene;
        this.modele = modele;

        this.curPage = 0;

        this.titre = titre;
        this.listeLivres = listeLivres;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));

        this.miseAJourAffichage();
    }
    
    /**
     * Obtenir l'élement central.
     */
    public void miseAJourAffichage() {
        this.getChildren().clear();
        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilClient(this.customerScene));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label(this.titre);
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);

        borderPaneTitre.setCenter(labelTitre);
        
        this.getChildren().add(borderPaneTitre);

        int nbLignes = 2;
        int nbColonnes = 4;
        int nbElementsParPage = nbLignes * nbColonnes;

        Panier panier = this.modele.getClientActuel().getPanier();
        Magasin magasin = this.modele.getClientActuel().getMagasin();
        for (int intLigne = 0; intLigne < nbLignes; intLigne++) {
            HBox hboxLigne = new HBox();
            hboxLigne.setSpacing(15);

            for (int intColonne = 0; intColonne < nbColonnes; intColonne++) {
                int index = (nbElementsParPage * this.curPage) + (intLigne * nbColonnes) + intColonne;
                if (index >= this.listeLivres.size()) break;

                Livre livre = this.listeLivres.get(index);
                
                int quantiteStock = 0;
                try {
                    quantiteStock = this.modele.getMagasinBD().obtenirStockLivre(magasin.getId(), livre.getISBN());
                    if (quantiteStock >= 1) {
                        DetailLivre detailLivre = panier.getDetailLivre(livre);
                        quantiteStock -= detailLivre.getQuantite();
                    }
                } catch (LivreIntrouvableException e) {
                    // On ignore l'exception ici
                } catch (SQLException e) {
                    // TODO: handle exception
                }

                BorderPane bookCard = new CustomerBookCardComponent(livre, quantiteStock, this.modele);
                HBox.setHgrow(bookCard, Priority.ALWAYS);
                hboxLigne.getChildren().add(bookCard);
            }
            this.getChildren().add(hboxLigne);
        }

        int maxPages = Math.ceilDiv(this.listeLivres.size(), nbElementsParPage);

        // Boutons navigations

        HBox hboxBoutons = new HBox();

        Button previousButton = new Button("Précédent");
        if (this.curPage == 0) previousButton.setDisable(true);
        previousButton.setOnAction(new ControleurNavigation(this));
        previousButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Label currentPageLabel = new Label(String.format("Page %d sur %d", this.curPage + 1, maxPages));
        currentPageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button nextButton = new Button("Suivant");
        if ((this.curPage + 1) >= maxPages) nextButton.setDisable(true);
        nextButton.setOnAction(new ControleurNavigation(this));
        nextButton.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        hboxBoutons.setSpacing(10);
        hboxBoutons.setAlignment(Pos.CENTER);
        hboxBoutons.getChildren().addAll(previousButton, currentPageLabel, nextButton);

        this.getChildren().add(hboxBoutons);
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
}
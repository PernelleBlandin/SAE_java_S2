package vue.seller;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.BaseMagasinsStockPane;

/** Pane pour l'affichage du stock magasin */
public class SellerMagasinStockPane extends BaseMagasinsStockPane {
    /**
     * Constructeur de la pane des livres en stock d'un magasin.
     * @param listeLivres La liste des livres à afficher dans la pane.
     * @param nbLignes Le nombre de lignes à afficher dans la liste.
     * @param sellerScene La scène de la page vendeur.
     * @param modele Le modèle de la librairie.
     * @param magasin Le magasin pour lequel on affiche les stocks des livres.
     */
    public SellerMagasinStockPane(List<Livre> listeLivres, int nbLignes, SellerScene sellerScene, ChaineLibrairie modele, Magasin magasin) {
        super(listeLivres, nbLignes, modele, sellerScene, magasin);

        this.addComponents();
    }

    public Label getHeaderPane() {
        Label title = new Label("Stock du magasin " + this.getMagasin().getNom());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setPadding(new Insets(10, 0, 10, 10));
        return title;
    }
}

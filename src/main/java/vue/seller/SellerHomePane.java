package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.SectionVoirPlusLivresPane;
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

    /**
     * Obtenir la VBox du titre de la page d'accueil du vendeur.
     * @return La VBox du titre de la page d'accueil du vendeur.
     */
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
     * Obtenir la VBox des meilleures ventes.
     * @return La VBox des meilleures ventes.
     */
    private SectionVoirPlusLivresPane getMeilleuresVentes() {
        return new SectionVoirPlusLivresPane(
            this.sellerScene,
            "Meilleures Ventes",
            "Liste des meilleures ventes",
            this.meilleursVentesLivres,
            3
        );
    }

    /**
     * Mettre à jour l'affichage de la pane.
     */
    public void miseAJourAffichage() {
        this.getChildren().set(1, this.getMeilleuresVentes());
    }
}

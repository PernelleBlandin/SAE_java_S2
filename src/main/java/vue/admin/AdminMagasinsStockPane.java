package vue.admin;

import java.util.List;

import controleurs.admin.ControleurBoutonRetourMagasin;
import javafx.scene.layout.BorderPane;
import modeles.ChaineLibrairie;
import modeles.Livre;
import modeles.Magasin;
import vue._components.BaseMagasinsStockPane;
import vue._components.TitleAndBackButtonPane;

/** Pane pour l'affichage du stock magasin */
public class AdminMagasinsStockPane extends BaseMagasinsStockPane {
    /**
     * Constructeur de la pane des livres en stock d'un magasin.
     * @param listeLivres La liste des livres à afficher dans la pane.
     * @param nbLignes Le nombre de lignes à afficher dans la liste.
     * @param adminScene La scène de la page administrateur.
     * @param modele Le modèle de la librairie.
     * @param magasin Le magasin pour lequel on affiche les stocks des livres.
     */
    public AdminMagasinsStockPane(List<Livre> listeLivres, int nbLignes, AdminScene adminScene, ChaineLibrairie modele, Magasin magasin) {
        super(listeLivres, nbLignes, modele, adminScene, magasin);

        this.addComponents();
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public BorderPane getHeaderPane() {
        AdminScene adminScene = (AdminScene) this.getPageScene();
        return new TitleAndBackButtonPane(this.getTitre(), new ControleurBoutonRetourMagasin(adminScene));
    }
}

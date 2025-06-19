package vue.seller;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.ChaineLibrairie;
import modeles.Livre;

public class SellerDeleteBookPane extends VBox {
    private ChaineLibrairie modele;
    public SellerDeleteBookPane(ChaineLibrairie modele) {
        this.modele = modele;

        this.setSpacing(30);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(60, 40, 40, 40));

        this.getChildren().add(this.getListeLivresPane());
    }

    private VBox getListeLivresPane() {
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
        
        return listeLivresVBox;
    }
}

package vue.admin;

import modeles.ChaineLibrairie;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class FenetreFacture extends BorderPane {

    /** La fenêtre principale AdminView */
    private AdminView fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;

    public FenetreFacture(AdminView fenetrePrin, ChaineLibrairie modele) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;
    }

    
    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale en mode facture.
     * @return centre La VBox
     */
    //A finir d'implementer avec le popup et récup les donner de la fact
    public VBox fenetreFacture() {
        VBox centre = new VBox(40);

        Label sousTitre = new Label("Exporter des factures");


        HBox lesTF = new HBox(30);
        TextField moisTF = new TextField();
        moisTF.setPromptText("Mois");

        TextField anneeTF = new TextField();
        anneeTF.setPromptText("Année");
        lesTF.getChildren().addAll(moisTF, anneeTF);
        lesTF.setAlignment(Pos.CENTER);
        

        Button btnExporter = new Button("Exporter");

        centre.getChildren().addAll(sousTitre, lesTF, btnExporter);
        centre.setAlignment(Pos.TOP_CENTER);

        return centre;
    }

}
package vue.admin;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;

public class AdminFacturesPane extends VBox {
    /** La fenêtre principale AdminScene */
    private AdminScene fenetrePrin;
    /** Le modèle */
    private ChaineLibrairie modele;

    public AdminFacturesPane(AdminScene fenetrePrin, ChaineLibrairie modele) {
        this.fenetrePrin = fenetrePrin;
        this.modele = modele;

        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(50);

        this.getChildren().addAll(this.titre(), this.lesTF(), this.btnExporter());
    }

    private Label titre() {
        Label titre = new Label("Exporter des factures");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18)); 
        return titre;
    }
    
    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale en mode facture.
     * @return centre La VBox
     */
    // TODO: A finir d'implementer avec le popup et récup les donner de la fact
    private HBox lesTF() {
        HBox lesTF = new HBox(30);
        TextField moisTF = new TextField();
        moisTF.setPromptText("Mois");

        TextField anneeTF = new TextField();
        anneeTF.setPromptText("Année");

        lesTF.getChildren().addAll(moisTF, anneeTF);
        lesTF.setAlignment(Pos.CENTER);

        return lesTF;
    }
        
    private Button btnExporter() {
        Button btnExporter = new Button("Exporter");
        return btnExporter;
    }
}
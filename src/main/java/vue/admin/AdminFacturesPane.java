package vue.admin;

import controleurs.admin.ControleurExporterFactures;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import vue._components.numberField.NumberField;

/** Pane de l'exportation des factures dans la page administrateur */
public class AdminFacturesPane extends VBox {
    /** Le modèle */
    private ChaineLibrairie modele;

    private NumberField moisTF;
    private NumberField anneeTF;

    /**
     * Initiailiser la pane des factures dans la page administrateur.
     * @param modele Le modèle.
     */
    public AdminFacturesPane(ChaineLibrairie modele) {
        this.modele = modele;

        this.setSpacing(50);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(15, 20, 10, 15));

        this.getChildren().addAll(
            this.titre(), 
            this.lesTF(),
            this.btnExporter()
        );
    }

    /**
     * Obtenir le champ de saisie du mois.
     * @return Le champ de saisie du mois.
     */
    public NumberField getMoisTF() {
        return this.moisTF;
    }

    /**
     * Obtenir le champ de saisie de l'année.
     * @return Le champ de saisie de l'année.
     */
    public NumberField getAnneeTF() {
        return this.anneeTF;
    }

    /**
     * Obtenir le titre du menu.
     * @return Le label du titre du menu.
     */
    private Label titre() {
        Label titre = new Label("Exporter des factures");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18)); 
        return titre;
    }
    
    /**
     * VBox à mettre au centre du BorderPane quand on change sa partie centrale en mode facture.
     * @return centre La VBox
     */
    private HBox lesTF() {
        HBox lesTF = new HBox(30);
        this.moisTF = new NumberField();
        this.moisTF.setPromptText("Mois");

        this.anneeTF = new NumberField();
        anneeTF.setPromptText("Année");

        lesTF.getChildren().addAll(moisTF, anneeTF);
        lesTF.setAlignment(Pos.CENTER);

        return lesTF;
    }
    
    /**
     * Obtenir le bouton "Exporter".
     * @return Le bouton "Exporter".
     */
    private Button btnExporter() {
        Button btnExporter = new Button("Exporter");
        btnExporter.disableProperty().bind(this.moisTF.textProperty().isEmpty().or(this.anneeTF.textProperty().isEmpty()));
        btnExporter.setOnAction(new ControleurExporterFactures(this, this.modele));
        return btnExporter;
    }
}
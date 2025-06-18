package vue.customers;

import java.util.List;

import controleurs.customers.ControleurAcceuilClient;
import controleurs.customers.ControleurRetirerLivrePanier;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.DetailLivre;
import modeles.Panier;

public class CustomerPanierPane extends VBox {
    /** La vue principal */
    private CustomerScene customerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    public CustomerPanierPane(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));

        Panier panier = this.modele.getClientActuel().getPanier();
        this.getChildren().addAll(
            this.getTitre(),
            this.getTableView(panier),
            this.getLabelTotal(panier),
            this.getPaiementPart(panier)
        );
    }

    private BorderPane getTitre() {
        BorderPane borderPaneTitre = new BorderPane();

        Button backButton = new Button("Retour");
        backButton.setOnAction(new ControleurAcceuilClient(this.customerScene));
        borderPaneTitre.setLeft(backButton);

        Label labelTitre = new Label("Panier client");
        labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitre.setMaxWidth(Double.MAX_VALUE);
        labelTitre.setAlignment(Pos.CENTER);
        borderPaneTitre.setCenter(labelTitre);

        return borderPaneTitre;
    }
    
    private TableView<DetailLivre> getTableView(Panier panier) {
        List<DetailLivre> detailsLivres = panier.getDetailLivres();
    
        TableView<DetailLivre> tableView = new TableView<>();
        tableView.setMaxHeight(300);

        TableColumn<DetailLivre, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivre().getISBN()));
        isbnColumn.setPrefWidth(100);

        TableColumn<DetailLivre, String> titreColumn = new TableColumn<>("Titre");
        titreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivre().getTitre()));
        titreColumn.setPrefWidth(500);

        TableColumn<DetailLivre, Integer> qteColumn = new TableColumn<>("Quantité");
        qteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
        qteColumn.setPrefWidth(50);

        TableColumn<DetailLivre, Double> prixColumn = new TableColumn<>("Prix (€)");
        prixColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getLivre().getPrix()).asObject());
        prixColumn.setPrefWidth(50);

        TableColumn<DetailLivre, Double> totalColumn = new TableColumn<>("Total (€)");
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getLivre().getPrix() * cellData.getValue().getQuantite()).asObject());
        totalColumn.setPrefWidth(50);

        // https://stackoverflow.com/questions/44660241/how-to-add-data-to-table-view-when-click-on-button-on-another-page
        TableColumn<DetailLivre, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(55);
        actionColumn.setCellFactory(param->  new TableCell<DetailLivre, String>() {
            final Button deleteButton = new Button("Supprimer");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DetailLivre detailLivre = getTableView().getItems().get(getIndex());

                    // https://stackoverflow.com/questions/1084112/access-this-from-java-anonymous-class
                    deleteButton.setOnAction(new ControleurRetirerLivrePanier(CustomerPanierPane.this, modele, detailLivre));
                    setGraphic(deleteButton);
                }
            }
        });

        // On l'ajoute un par un, car il y a un warning sinon
        tableView.getColumns().add(isbnColumn);
        tableView.getColumns().add(titreColumn);
        tableView.getColumns().add(qteColumn);
        tableView.getColumns().add(prixColumn);
        tableView.getColumns().add(totalColumn);
        tableView.getColumns().add(actionColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableView.getItems().addAll(detailsLivres);
        return tableView;
    }

    private Label getLabelTotal(Panier panier) {
        Label labelTotal = new Label(String.format("Total : %.2f€", panier.getTotalPanier()));
        labelTotal.setMaxWidth(Double.MAX_VALUE);
        labelTotal.setAlignment(Pos.BASELINE_RIGHT);
        return labelTotal;
    }

    private VBox getPaiementPart(Panier panier) {
        VBox paiementVBox = new VBox();
        paiementVBox.setSpacing(20);

        // TiltedPane Livraison
        TitledPane titledPaneLivraison = new TitledPane();
        titledPaneLivraison.setCollapsible(false);
        titledPaneLivraison.setText("Livraison");

        ToggleGroup toggleGroupLivraison = new ToggleGroup();
        HBox hboxModeLivraison = new HBox();
        hboxModeLivraison.setSpacing(30);
        hboxModeLivraison.setAlignment(Pos.CENTER);

        RadioButton radioButtonMagasin = new RadioButton("En magasin");
        radioButtonMagasin.setToggleGroup(toggleGroupLivraison);

        RadioButton radioButtonLivraison = new RadioButton("En livraison");
        radioButtonLivraison.setToggleGroup(toggleGroupLivraison);

        hboxModeLivraison.getChildren().addAll(radioButtonMagasin,radioButtonLivraison);
        titledPaneLivraison.setContent(hboxModeLivraison);

        // Bouton Payer
        Button buttonPayer = new Button("Payer");
        
        List<DetailLivre> detailsLivres = panier.getDetailLivres();
        if (detailsLivres.size() == 0) {
            buttonPayer.setDisable(true);
        } else {
            buttonPayer.disableProperty().bind(toggleGroupLivraison.selectedToggleProperty().isNull());
        }

        paiementVBox.getChildren().addAll(titledPaneLivraison, buttonPayer);
        paiementVBox.setAlignment(Pos.CENTER_RIGHT);

        return paiementVBox;
    }

    public void miseAJourAffichage() {
        Panier panier = this.modele.getClientActuel().getPanier();

        this.getChildren().set(1, this.getTableView(panier));
        this.getChildren().set(2, this.getLabelTotal(panier));
        this.getChildren().set(3, this.getPaiementPart(panier));
    }
}

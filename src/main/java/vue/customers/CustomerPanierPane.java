package vue.customers;

import java.util.List;

import controleurs.ControleurAcceuil;
import controleurs.customers.ControleurBoutonPayer;
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
import modeles.ChaineLibrairie;
import modeles.DetailLivre;
import modeles.Panier;
import vue._components.TitleAndBackButtonPane;

/** La pane pour afficher le panier client */
public class CustomerPanierPane extends VBox {
    /** La scène de la page client. */
    private CustomerScene customerScene;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser le widget du panier.
     * @param customerScene La scène de la page client. 
     * @param modele Le modèle.
     */
    public CustomerPanierPane(CustomerScene customerScene, ChaineLibrairie modele) {
        this.customerScene = customerScene;
        this.modele = modele;

        this.setSpacing(15);
        this.setPadding(new Insets(10, 20, 10, 20));

        Panier panier = this.modele.getClientActuel().getPanier();
        this.getChildren().addAll(
            this.getTitre(),
            this.produitsIndisponiblesBox(panier),
            this.getTableView(panier),
            this.getLabelTotal(panier),
            this.getPaiementPart(panier)
        );
    }

    /**
     * Obtenir le widget du titre.
     * @return Le titre de la page.
     */
    private BorderPane getTitre() {
        return new TitleAndBackButtonPane("Panier client", new ControleurAcceuil(this.customerScene));
    }
    
    /**
     * Obtenir une VBox, qui contient un message si des produits présent dans le panier ne sont plus en stock dans le magasin du client.
     * @param panier Le panier client.
     * @return La VBox contenant si nécessaire un message si produits indisponibles.
     */
    private VBox produitsIndisponiblesBox(Panier panier) {
        VBox vbox = new VBox();

        List<String> produitsPlusEnStock = panier.getProduitPlusEnStock(this.modele.getMagasinBD());
        if (produitsPlusEnStock.size() >= 1) {
            vbox.setMaxWidth(Double.MAX_VALUE);
            vbox.setPadding(new Insets(15, 10, 15, 10));
            vbox.setStyle("-fx-background-color: #FFCCCB; -fx-border-color: red; -fx-border-width: 5px; -fx-border-style: solid;");

            vbox.getChildren().add(new Label("Certains produits de votre panier ne sont plus disponibles :"));
            for (String produit: produitsPlusEnStock) {
                vbox.getChildren().add(new Label(String.format("- %s", produit)));
            }
        }

        return vbox;
    }

    /**
     * Obtenir la table des livres présents dans le panier.
     * @param panier Le panier client.
     * @return La table des livres présents dans le panier client.
     */
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

    /**
     * Obtenir la VBox du paiement (mode livraison) + bouton payer.
     * @param panier Le panier client.
     * @return La VBox du paiement (mode livraison) + bouton payer
     */
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
        buttonPayer.setOnAction(new ControleurBoutonPayer(this.customerScene, this.modele, toggleGroupLivraison));

        if (!panier.estCommandable(this.modele.getMagasinBD())) {
            buttonPayer.setDisable(true);
        } else {
            buttonPayer.disableProperty().bind(toggleGroupLivraison.selectedToggleProperty().isNull());
        }

        paiementVBox.getChildren().addAll(titledPaneLivraison, buttonPayer);
        paiementVBox.setAlignment(Pos.CENTER_RIGHT);

        return paiementVBox;
    }

    /**
     * Mettre à jour la page du panier.
     */
    public void miseAJourAffichage() {
        Panier panier = this.modele.getClientActuel().getPanier();

        this.getChildren().set(1, this.produitsIndisponiblesBox(panier));
        this.getChildren().set(2, this.getTableView(panier));
        this.getChildren().set(3, this.getLabelTotal(panier));
        this.getChildren().set(4, this.getPaiementPart(panier));
    }
}

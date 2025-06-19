package vue.seller;

import java.util.List;

import controleurs.seller.ControleurVoirClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Client;
import vue.AppIHM;
import vue._components.BaseListElementsWithSearchPane;

/**
 * La pane affichant la liste des clients à afficher au vendeur.
 */
public class SellerViewAsCustomerPane extends BaseListElementsWithSearchPane<Client> {
    /** L'application */
    private AppIHM appIHM;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser la pane affichant la liste des clients à afficher au vendeur.
     * @param listeClients La liste des clients.
     * @param sellerScene La scène de la page vendeur.
     */
    public SellerViewAsCustomerPane(List<Client> listeClients, SellerScene sellerScene, AppIHM appIHM, ChaineLibrairie modele) {
        super("Liste des clients", listeClients, 6, 1, "Rechercher un client...");

        this.appIHM = appIHM;
        this.modele = modele;

        this.addComponents();
    }

    /**
     * Obtenir le titre et le bouton retour de la pane.
     * @return Le BorderPane contenant ses deux informations.
     */
    public Label getHeaderPane() {
        Label headerLabel = new Label(this.getTitre());
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        headerLabel.setAlignment(Pos.CENTER);
        return headerLabel;
    }

    /**
     * Obtenir le composant pour afficher un client dans la liste.
     * @param client Le client à afficher.
     */
    public BorderPane getElementComponent(Client client) {
        BorderPane ligneClient = new BorderPane();
        ligneClient.setPadding(new Insets(10, 10, 10, 10));
        ligneClient.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Label clientLabel = new Label(String.format("%s (%s)", client.toString(), client.getMagasin().toString()));
        clientLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Button boutonVoirClient = new Button("Se connecter en tant que ce client");
        boutonVoirClient.setOnAction(new ControleurVoirClient(this.appIHM, this.modele, client));

        ligneClient.setLeft(clientLabel);
        ligneClient.setRight(boutonVoirClient);

        return ligneClient;
    }
}
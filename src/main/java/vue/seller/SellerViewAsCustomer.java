package vue.seller;

import javafx.scene.control.TextField;
import modeles.ChaineLibrairie;
import vue.customers.CustomerHomePane;

/**
 * La vue pour changer le magasin du vendeur.
 */
public class SellerViewAsCustomer {
    /** Le modèle */
    private ChaineLibrairie modele;
    /** La pane du home de la page vendeur */
    private CustomerHomePane customerHomePane;

    /** L'identifiant client */
    private TextField idClient;

    /**
     * Constructeur de la vue pour changer le magasin du vendeur.
     * @param modele Le modèle de données de la librairie.
     * @param customerHomePane La pane du home de la page vendeur.
     */
    public SellerViewAsCustomer(ChaineLibrairie modele, CustomerHomePane customerHomePane){
        this.modele = modele;
        this.customerHomePane = customerHomePane;
        this.idClient = new TextField();

    }
    // public void modeClient(){
    //     Button boutonModeClient = new Button();
    //     boutonModeClient.setOnAction(ControleurModeClient()); //TODO faire controleur


    // }
     
}
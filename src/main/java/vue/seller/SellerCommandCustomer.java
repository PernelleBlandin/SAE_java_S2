package vue.seller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controleurs.customers.ControleurChangerMagasin;
import controleurs.seller.ControleurChangerMagasinSeller;
import controleurs.seller.ControleurValiderAjoutLivre;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Magasin;
import modeles.Vendeur;
import vue._components.alerts.AlertErreurException;
import vue._components.numberField.NumberField;
import vue.customers.CustomerHomePane;

public class SellerCommandCustomer{
    private ChaineLibrairie modele;
    private CustomerHomePane customerHomePane;

    private TextField idClient;
    public SellerCommandCustomer(ChaineLibrairie modele,CustomerHomePane customerHomePane){
        this.modele = modele;
        this.customerHomePane = customerHomePane;
        this.idClient = new TextField();

    }
    // public void modeClient(){
    //     Button boutonModeClient = new Button();
    //     boutonModeClient.setOnAction(ControleurModeClient()); //TODO faire controleur


    // }
     
}
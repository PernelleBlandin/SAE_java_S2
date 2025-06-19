package controleurs.seller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Livre;
import vue.seller.SellerDeleteBookListPane;
import vue.seller.SellerTransferBook;
import vue._components.alerts.AlertYesNo;

public class ControleurTransfererLivre implements EventHandler<ActionEvent>{
    private SellerTransferBook transferBook;
    private ChaineLibrairie modele;
    private Livre livre;

    //TODO A FAIRE


    public ControleurTransfererLivre(SellerTransferBook sellerTransferBook, ChaineLibrairie modele, Livre livre){

    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton "Transférer".
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {}
}
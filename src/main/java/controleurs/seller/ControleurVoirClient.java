package controleurs.seller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Client;
import vue.AppIHM;

/**
 * Le contrôleur pour se connecter en tant qu'un autre client dans la page vendeur.
 */
public class ControleurVoirClient implements EventHandler<ActionEvent> {
    /** L'application */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;
    /** Le client */
    private Client client;

    /**
     * Initialiser le contrôleur pour voir les détails d'un client.
     * @param app L'application principale.
     * @param modele Le modèle de la librairie.
     * @param client Le client à voir.
     */
    public ControleurVoirClient(AppIHM app, ChaineLibrairie modele, Client client) {
        this.app = app;
        this.modele = modele;
        this.client = client;
    }

    @Override
    /**
     * Recevoir un événement lors d'un clic sur le bouton pour se connecter en tant qu'un client de la librairie.
     * @param event Un événement.
     */
    public void handle(ActionEvent event) {
        this.modele.setClientActuel(this.client);
        this.app.showCustomer();
    }
}


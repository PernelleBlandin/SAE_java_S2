package controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modeles.ChaineLibrairie;
import modeles.Client;
import modeles.Vendeur;
import vue.AppIHM;

/** Le contrôleur de déconnexion */
public class ControleurDeconnexion implements EventHandler<ActionEvent> {
    /** L'application principale */
    private AppIHM app;
    /** Le modèle */
    private ChaineLibrairie modele;

    /**
     * Initialiser le contrôleur de deconnexion.
     * @param app L'application principale.
     * @param modele Le modèle.
     */
    public ControleurDeconnexion(AppIHM app, ChaineLibrairie modele) {
        this.app = app;
        this.modele = modele;
    }

    @Override
    /**
     * Récupère un événèment lors d'un clic sur un bouton et retourne sur la page de connexion.
     * @param event Un évènement
     */
    public void handle(ActionEvent event) {
        Client client = this.modele.getClientActuel();
        Vendeur vendeur = this.modele.getVendeurActuel();

        if (vendeur != null) {
            if (client != null) {
                this.modele.setClientActuel(null);
                this.app.showSeller();
                return;
            }

            this.modele.setVendeurActuel(null);
        } else if (client != null) {
            this.modele.setClientActuel(null);
        }

        this.app.showConnexion();
    }
}
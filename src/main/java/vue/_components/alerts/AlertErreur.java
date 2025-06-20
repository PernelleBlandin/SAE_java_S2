package vue._components.alerts;

import javafx.scene.control.Alert;

/** Alerte d'erreur */
public class AlertErreur extends Alert {
    /** La description de l'alerte */
    protected String description;

    /**
     * Initialiser une alerte en cas d'erreur et l'afficher.
     * @param description La description de l'alerte
     */
    public AlertErreur(String description) {
        super(AlertType.ERROR);

        this.description = description;

        this.setAlertData();
        this.show();
    }

    /**
     * Définir les informations indiquées dans l'alerte.
     */
    public void setAlertData() {
        this.setTitle("Erreur");
        this.setHeaderText("Une erreur s'est produite !");
        this.setContentText(this.description);
    }
}
package vue._components.alerts;

import javafx.scene.control.Alert;

/** Alerte d'informations */
public class AlertInfo extends Alert {
    /** Le type d'alerte */
    private String titre;
    /** La description de l'alerte */
    private String description;

    /**
     * Initialiser l'alerte d'informations avec les informations indiquées.
     * @param titre Le titre de l'alerte
     * @param description La description de l'alerte
     */
    public AlertInfo(String titre, String description) {
        super(AlertType.INFORMATION);

        this.titre = titre;
        this.description = description;
        
        this.setAlertData();
        this.show();
    }

    /**
     * Définir les informations indiquées dans l'alerte.
     */
    public void setAlertData() {
        this.setTitle(this.titre);
        this.setHeaderText(this.titre);
        this.setContentText(this.description);
    }
    
}

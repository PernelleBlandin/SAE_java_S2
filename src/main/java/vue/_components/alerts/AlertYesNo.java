package vue._components.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Alerte contenant un bouton "Oui" et "Non."
 */
public class AlertYesNo extends Alert {
    /** Le titre de la fenêtre */
    private String windowTitle;
    /** Le titre de l'alerte */
    private String headerTitle;
    /** La description de l'alerte */
    private String description;

    /**
     * Initialiser une alerte "Oui/Non"
     * @param windowTitle Le titre de la fenêtre
     * @param headerTitle Le titre de l'alerte
     * @param description La description de l'alerte
     */
    public AlertYesNo(String windowTitle, String headerTitle, String description) {
        super(AlertType.NONE);

        this.windowTitle = windowTitle;
        this.headerTitle = headerTitle;
        this.description = description;

        this.setAlertData();
    }

    /**
     * Définir les informations indiquées dans l'alerte.
     */
    public void setAlertData() {
        this.setTitle(this.windowTitle);
        this.setHeaderText(this.headerTitle);
        this.setContentText(this.description);
 
        ButtonType yes = new ButtonType("Oui");
        ButtonType no = new ButtonType("Non");
        this.getButtonTypes().setAll(yes, no);
    }
}

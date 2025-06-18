package vue._components.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertYesNo extends Alert {
    private String windowTitle;
    private String headerTitle;
    private String description;
    public AlertYesNo(String windowTitle, String headerTitle, String description) {
        super(AlertType.NONE);

        this.windowTitle = windowTitle;
        this.headerTitle = headerTitle;
        this.description = description;

        this.setAlertData();
    }

    public void setAlertData() {
        this.setTitle(this.windowTitle);
        this.setHeaderText(this.headerTitle);
        this.setContentText(this.description);
 
        ButtonType yes = new ButtonType("Oui");
        ButtonType no = new ButtonType("Non");
        this.getButtonTypes().setAll(yes, no);
    }
}

package vue._components.alerts;

/** Alerte d'erreur lors d'une exception*/
public class AlertErreurException extends AlertErreur {
    private String exceptionMessage;

    /**
     * Initialiser une alerte en cas d'exception et l'afficher.
     * @param description La description de l'alerte
     * @param exceptionMessage Une exception
     */
    public AlertErreurException(String description, String exceptionMessage) {
        super(description);

        this.exceptionMessage = exceptionMessage;

        this.setAlertData();
    }

    /**
     * Définir les informations indiquées dans l'alerte.
     */
    public void setAlertData() {
        super.setAlertData();

        this.setContentText(this.description + "\n" + this.exceptionMessage);
    }
}
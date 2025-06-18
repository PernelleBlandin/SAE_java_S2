package vue._components.alerts;

/** Alerte d'erreur lors d'une exception*/
public class AlertErreurException extends AlertErreur {
    private Exception exception;

    /**
     * Initialiser une alerte en cas d'exception et l'afficher.
     * @param description La description de l'alerte
     * @param exception Une exception
     */
    public AlertErreurException(String description, Exception exception) {
        super(description);

        this.exception = exception;

        this.setAlertData();
    }

    /**
     * Définir les informations indiquées dans l'alerte.
     */
    public void setAlertData() {
        super.setAlertData();

        this.setContentText(this.description + "\n" + this.exception.getMessage());
    }
}
package vue._components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Alert;
import modeles.Livre;

public class AlertInfoLivre extends Alert {
    private Livre livre;
    public AlertInfoLivre(Livre livre) {
        super(AlertType.INFORMATION);

        this.livre = livre;
        
        this.setAlertData();
    }

    public void setAlertData() {
        this.setTitle(String.format("Informations sur le livre %s", livre.getTitre()));
        this.setHeaderText(null);

        Integer nbPagesLivre = livre.getNbPages();
        String nbPagesString = nbPagesLivre == null ? "Inconnu" : String.valueOf(nbPagesLivre);

        List<String> detailsLivres = new ArrayList<>(Arrays.asList(
            String.format("Titre : %s", livre.getTitre()),
            String.format("Auteur : %s", livre.joinNomAuteurs()),
            String.format("Prix : %.2f€", livre.getPrix()),
            String.format("Classification : %s", livre.joinClassifications()),
            String.format("Éditeur : %s", livre.joinNomEditeurs()),
            String.format("Nombre de pages : %s", nbPagesString))
        );
        this.setContentText(String.join("\n", detailsLivres));
    }
}

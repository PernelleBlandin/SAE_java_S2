import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; 

/** Un vendeur */
public class Vendeur extends Personnel {
    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param magasin Le magasin rattaché au vendeur
     */
    public Vendeur(int id, String nom, String prenom, Magasin magasin) {
        super(id, nom, prenom);
        this.magasin = magasin;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Définir le magasin pour un vendeur.
     * @param magasin Le nouveau magasin du client.
     */
    public Vendeur(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        super(id, nom, prenom, adresse, codePostal, ville, "Vendeur");
    }
}

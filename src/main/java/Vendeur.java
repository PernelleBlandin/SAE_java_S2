import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Un vendeur */
public class Vendeur extends Personne {
    private Magasin magasin;
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

    

    /**
     * Obtenir un magasin
     * @return Un magasin
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Définir le magasin pour un vendeur.
     * @param magasin Le nouveau magasin du client.
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
    
    public void majStock(){}

    public void accesStock(){}

    public void passerCommande(){}

    public void transfertLivre(){}
}

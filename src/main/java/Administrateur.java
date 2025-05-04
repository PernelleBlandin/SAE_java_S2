/** Un administrateur */
public class Administrateur extends Personne {
    /**
     * Créer un administrateur.
     * @param id L'identifiant de l'administrateur.
     * @param nom Le nom du prénom de l'administrateur.
     * @param prenom Le prénom de l'administrateur.
     * @param adresse L'adresse de l'administrateur.
     * @param codePostal Le code postal de l'administrateur.
     * @param ville La ville de l'administrateur.
     */
    public Administrateur(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        super(id, nom, prenom, adresse, codePostal, ville);
    }
}

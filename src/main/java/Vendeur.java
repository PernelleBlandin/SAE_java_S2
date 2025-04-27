public class Vendeur extends Personnel {
    /**
     * Créer un vendeur.
     * @param id L'identifiant du vendeur.
     * @param nom Le nom du prénom du vendeur.
     * @param prenom Le prénom du vendeur.
     * @param adresse L'adresse du vendeur.
     * @param codePostal Le code postal du vendeur.
     * @param ville La ville du vendeur.
     */
    public Vendeur(int id, String nom, String prenom, String adresse, String codePostal, String ville) {
        super(id, nom, prenom, adresse, codePostal, ville, "Vendeur");
    }
}

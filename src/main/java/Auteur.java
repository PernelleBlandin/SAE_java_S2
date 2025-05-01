/** Un auteur */
public class Auteur extends Identifiable {
    private Integer anneenais;
    private Integer anneedeces;

    // TODO: Voir si on laisse les ids

    /**
     * Créer un auteur.
     * @param id Son identifiant.
     * @param nom Son nom.
     * @param anneenais Son année de naissance.
     * @param anneedeces Son année de décès.
     */
    public Auteur(String id, String nom, Integer anneenais, Integer anneedeces){
        super(id, nom);
        this.anneenais = anneenais;
        this.anneedeces = anneedeces;
    }

    /**
     * Obtenir l'année de naissance de l'auteur.
     * @return L'année de naissance de l'auteur, ou null si non défini.
     */
    public Integer getAnneeNais() {
        return this.anneenais;
    }

    /**
     * Obtenir l'année de décès de l'auteur.
     * @return L'année de décès de l'auteur, ou null si non défini.
     */
    public Integer getAneeeDeces() {
        return this.anneedeces;
    }
 }
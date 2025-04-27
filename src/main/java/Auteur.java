public class Auteur extends Identifiable {
    private Integer anneenais;
    private Integer anneedeces;

    // TODO: Voir si on laisse les ids
    public Auteur(String id, String nom, Integer anneenais, Integer anneedeces){
        super(id, nom);
        this.anneenais = anneenais;
        this.anneedeces = anneedeces;
    }

    public Integer getAnneeNais() {
        return this.anneenais;
    }

    public Integer getAneeeDeces() {
        return this.anneedeces;
    }
 }
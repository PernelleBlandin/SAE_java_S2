import java.util.List;

public class Auteur extends Identifiable {
    private Integer anneenais;
    private Integer anneedeces;
    private List<Livre> livres;// a modifier sur le mcd

    public Auteur(String id, String nom, Integer anneenais, Integer anneedeces){
        super(id, nom);
        this.anneenais = anneenais;
        this.anneedeces = anneedeces;
    }
}
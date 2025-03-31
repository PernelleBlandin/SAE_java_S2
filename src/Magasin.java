public class Magasin extends Identifiable{
    private String ville;

    public Magasin(String ville, String id, String nom){
        super(id, nom);
        this.ville = ville;
    }
    
}

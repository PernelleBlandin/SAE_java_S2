import java.util.List;
import java.util.ArrayList;

public class ChaineLibrairie {
    private List<Magasin> magasins;
    public ChaineLibrairie() {
        this.magasins = new ArrayList<>();
    }

    public void ajouteMagasin(Magasin magasin) {
        this.magasins.add(magasin);
    }
}
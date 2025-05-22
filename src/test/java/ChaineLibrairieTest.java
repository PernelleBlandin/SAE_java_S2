import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class ChaineLibrairieTest {
    private Livre livre = new Livre(
        "9780768939866",
        "Angélique",
        213,
        2023,
        11.99,
        new HashSet<>(Arrays.asList("Guillaume Musso")),
        new HashSet<>(Arrays.asList("Lgf")),
        new HashSet<>(Arrays.asList("Roman"))
    );

    private Magasin magasinMarseille = new Magasin("2", "Cap au Sud", "Marseille");
    
    private DetailLivre detailCommande1 = new DetailLivre(this.livre, 1, 2, 11.99);
    private Commande commande1 = new Commande(1, Date.valueOf("2025-09-01"), 'N', 'M', this.magasinMarseille, new ArrayList<>(Arrays.asList(this.detailCommande1)));
    
    @Test
    public void testsGenererCorpsCommandeTextuel() {
        List<DetailLivre> listeCommandesVides = new ArrayList<>();
        assertEquals(new ArrayList<>(), ChaineLibrairie.genererCorpsCommandeTextuel(listeCommandesVides, 100));

        List<DetailLivre> detailCommandesC1 = commande1.getDetailCommandes();
        List<String> listeAttendus = new ArrayList<>(Arrays.asList(
            "       ISBN                               Titre                              Qte    Prix   Total",
            " 1 9780768939866 Angélique                                                     2  11,99€  23,98€",
            "                                                                                         -------",
            "                                                                                          23,98€"

        ));
        assertEquals(listeAttendus, ChaineLibrairie.genererCorpsCommandeTextuel(detailCommandesC1, 100));
    }
}

import java.util.List;

public class Livre {
    private String isbn;
    private String titre;
    private int nbpages;
    private int datepubli;
    private double prix;
    private List<Auteur> auteurs;
    private List<Editeur> editeurs;
    private List<Classification> classifications;

    public Livre(String isbn, String titre, int nbpages, int datepubli, double prix) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbpages = nbpages;
        this.datepubli = datepubli;
        this.prix = prix;
    }
}
import java.util.ArrayList;
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

    /**
     * Créer un livre.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     * @param nbpages Le nombre de pages du livre.
     * @param datepubli La date de publication du livre.
     * @param prix Le prix du livre.
     */
    public Livre(String isbn, String titre, int nbpages, int datepubli, double prix) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbpages = nbpages;
        this.datepubli = datepubli;
        this.prix = prix;
        // TODO: A modifier
        this.auteurs = new ArrayList<>();
        this.editeurs = new ArrayList<>();
        this.classifications = new ArrayList<>();
    }

    /**
     * Obtenir l'ISBN d'un livre.
     * @return L'ISBN du livre sous forme d'une chaîne de caractères.
     */
    public String getISBN() {
        return this.isbn;
    }

    /**
     * Obtenir le titre d'un livre.
     * @return Le titre du livre.
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Obtenir le nombre de pages d'un livre.
     * @return Le nombres de pages du livre.
     */
    public int getNbPages() {
        return this.nbpages;
    }

    /**
     * Obtenir la date de publication d'un livre.
     * @return La date de publication du livre.
     */
    public int getDatePubli() {
        return this.datepubli;
    }

    /**
     * Obtenir le prix d'un livre.
     * @return Le prix du livre.
     */
    public double getPrix() {
        return this.prix;
    }

    /**
     * Obtenir la liste des auteurs d'un livre.
     * @return La liste des auteurs du livre.
     */
    public List<Auteur> getAuteurs() {
        return this.auteurs;
    }

    /**
     * Obtenir la liste des éditeurs d'un livre.
     * @return La liste des éditeurs du livre.
     */
    public List<Editeur> getEditeurs() {
        return this.editeurs;
    }

    /**
     * Obtenir la liste des classifications d'un livre.
     * @return La liste des classifications du livre.
     */
    public List<Classification> getClassifications() {
        return this.classifications;
    }

    /**
     * Obtenir le livre sous forme d'une chaîne de caractères (Titre (par auteur)).
     * @return Le livre sous forme d'une chaîne de caractères.
     */
    @Override
    public String toString() {
        List<String> auteursNom = new ArrayList<>();
        for (Auteur auteur: this.auteurs) {
            auteursNom.add(auteur.getNom());
        }
        return String.format("%s (par %s)", this.titre, String.join(", ", auteursNom));
    }
}
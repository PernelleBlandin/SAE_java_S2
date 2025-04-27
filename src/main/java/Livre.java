import java.util.List;
import java.util.stream.Collectors;

public class Livre {
    private String isbn;
    private String titre;
    private Integer nbpages;
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
     * @param auteurs Les auteurs du livre.
     * @param editeurs Les éditeurs du livre.
     * @param classifications Les classifications du livre.
     */
    public Livre(String isbn, String titre, Integer nbpages, int datepubli, double prix, List<Auteur> auteurs, List<Editeur> editeurs, List<Classification> classifications) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbpages = nbpages;
        this.datepubli = datepubli;
        this.prix = prix;
        this.auteurs = auteurs;
        this.editeurs = editeurs;
        this.classifications = classifications;
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
    public Integer getNbPages() {
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
     * Obtenir les auteurs d'un livre sous forme d'une chaîne de caractères.
     * Source : https://stackoverflow.com/questions/1751844/java-convert-liststring-to-a-joind-string
     * @return Une chaîne de caractères avec les auteurs du livre, délimité par une virgule.
     */
    public String joinNomAuteurs() {
        if (this.auteurs.size() == 0) return "Inconnu";
        return this.auteurs.stream().map(Auteur::getNom).collect(Collectors.joining(", "));
    }

    /**
     * Obtenir les éditeurs d'un livre sous forme d'une chaîne de caractères.
     * Source : https://stackoverflow.com/questions/1751844/java-convert-liststring-to-a-joind-string
     * @return Une chaîne de caractères avec les éditeurs du livre, délimité par une virgule.
     */
    public String joinNomEditeurs() {
        if (this.editeurs.size() == 0) return "Inconnu";
        return this.editeurs.stream().map(Editeur::getNom).collect(Collectors.joining(", "));
    }

    /**
     * Obtenir les classifications d'un livre sous forme d'une chaîne de caractères.
     * Source : https://stackoverflow.com/questions/1751844/java-convert-liststring-to-a-joind-string
     * @return Une chaîne de caractères avec les classifications du livre, délimité par une virgule.
     */
    public String joinClassifications() {
        if (this.classifications.size() == 0) return "Inconnu";
        return this.classifications.stream().map(Classification::getNom).collect(Collectors.joining(", "));
    }

    /**
     * Indique si un livre est inclu dans une recherche donnée.
     * @param recherche Une recherche utilisateur.
     * @return true si le livre est inclu dans la recherche donnée, sinon false.
     */
    public boolean estIncluDansRecherche(String recherche) {
        String titreString = this.getTitre().toLowerCase();
        String auteursString = this.joinNomAuteurs().toLowerCase();
        String editeursString = this.joinNomEditeurs().toLowerCase();
        String classificationsString = this.joinNomEditeurs().toLowerCase();

        return titreString.contains(recherche) || auteursString.contains(recherche) || editeursString.contains(recherche) || classificationsString.contains(recherche);
    }

    /**
     * Obtenir sous forme d'une chaîne de caractères un livre.
     * La chaîne de caractères contient le titre, les auteurs et le prix du livre.
     * @return La chaîne de caractères avec les informations principales du livre.
     */
    @Override
    public String toString() {
        return String.format("%s | par %s | %.2f€", this.getTitre(), this.joinNomAuteurs(), this.getPrix());
    }

    /**
     * Savoir si un objet est égal à un livre.
     * @param o Un objet.
     * @return true si l'objet est égal à ce livre, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (o == this) return true;

        if (!(o instanceof Livre)) return false;

        Livre livre2 = (Livre) o;
        return this.getISBN().equals(livre2.getISBN());
    }
}
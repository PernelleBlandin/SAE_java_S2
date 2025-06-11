import java.util.HashSet;
import java.util.Set;

/** Un livre */
public class Livre {
    private String isbn;
    private String titre;
    private Integer nbpages;
    private Integer datepubli;
    private Double prix;
    private Set<String> auteurs;
    private Set<String> editeurs;
    private Set<String> classifications;

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
    public Livre(String isbn, String titre, Integer nbpages, Integer datepubli, Double prix, Set<String> auteurs, Set<String> editeurs, Set<String> classifications) {
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
     * Créer un livre partiel, utilisé pour l'édition de factures.
     * @param isbn L'ISBN du livre.
     * @param titre Le titre du livre.
     */
    public Livre(String isbn, String titre) {
        this.isbn = isbn;
        this.titre = titre;
        
        this.nbpages = null;
        this.datepubli = null;
        this.prix = null;
        this.auteurs = new HashSet<>();
        this.editeurs =new HashSet<>();
        this.classifications = new HashSet<>();
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
    public Integer getDatePubli() {
        return this.datepubli;
    }

    /**
     * Obtenir le prix d'un livre.
     * @return Le prix du livre.
     */
    public Double getPrix() {
        return this.prix;
    }

    /**
     * Obtenir la liste des auteurs d'un livre.
     * @return La liste des auteurs du livre.
     */
    public Set<String> getAuteurs() {
        return this.auteurs;
    }

    /**
     * Obtenir la liste des éditeurs d'un livre.
     * @return La liste des éditeurs du livre.
     */
    public Set<String> getEditeurs() {
        return this.editeurs;
    }

    /**
     * Obtenir la liste des classifications d'un livre.
     * @return La liste des classifications du livre.
     */
    public Set<String> getClassifications() {
        return this.classifications;
    }

    /**
     * Obtenir les auteurs d'un livre sous forme d'une chaîne de caractères.
     * @return Une chaîne de caractères avec les auteurs du livre, délimité par une virgule.
     */
    public String joinNomAuteurs() {
        if (this.auteurs.size() == 0) return "Inconnu";
        return String.join(", ", this.auteurs);
    }

    /**
     * Obtenir les éditeurs d'un livre sous forme d'une chaîne de caractères.
     * @return Une chaîne de caractères avec les éditeurs du livre, délimité par une virgule.
     */
    public String joinNomEditeurs() {
        if (this.editeurs.size() == 0) return "Inconnu";
        return String.join(", ", this.editeurs);
    }

    /**
     * Obtenir les classifications d'un livre sous forme d'une chaîne de caractères.
     * @return Une chaîne de caractères avec les classifications du livre, délimité par une virgule.
     */
    public String joinClassifications() {
        if (this.classifications.size() == 0) return "Inconnu";
        return String.join(", ", this.classifications);
    }

    /**
     * Indique si un livre est inclu dans une recherche donnée.
     * Aide : http://w3schools.com/java/ref_string_split.asp
     * @param recherche Une recherche utilisateur.
     * @return true si le livre est inclu dans la recherche donnée, sinon false.
     */
    public boolean estIncluDansRecherche(String recherche) {
        String titreString = this.getTitre().toLowerCase();
        String auteursString = this.joinNomAuteurs().toLowerCase();
        String editeursString = this.joinNomEditeurs().toLowerCase();
        String classificationsString = this.joinClassifications().toLowerCase();

        String[] mots = recherche.toLowerCase().split(" ");
        for (String mot: mots) {
            if (!(titreString.contains(mot) || auteursString.contains(mot) || editeursString.contains(mot) || classificationsString.contains(mot))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtenir sous forme d'une chaîne de caractères un livre.
     * La chaîne de caractères contient le titre, les auteurs et le prix du livre.
     * @return La chaîne de caractères avec les informations principales du livre.
     */
    @Override
    public String toString() {
        return String.format("%s | par %s", this.getTitre(), this.joinNomAuteurs());
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

    /**
     * Redéfinition du hashcode d'un livre.
     * @return Le hashcode du livre. 
     */
    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }
}
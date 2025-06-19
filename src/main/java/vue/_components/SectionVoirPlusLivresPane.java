package vue._components;

import java.util.List;

import controleurs.ControleurVoirPlus;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modeles.Livre;
import vue.SceneListBooksInterface;

/**
 * Une section de livres avec un bouton "Voir plus", pour voir tous les livres.
 */
public class SectionVoirPlusLivresPane extends VBox {
    /** Le titre de la section */
    private String titreSection;
    /** Le titre de la liste de livres */
    private String titreListe;
    /** La scène */
    private SceneListBooksInterface scene;
    /** La liste des livres à afficher dans la section */
    private List<Livre> listeLivres;
    /** Le nombre de livres à afficher dans la section */
    private int livresCount;

    /**
     * Constructeur pour initialiser la section de livres avec un bouton "Voir plus".
     * @param scene La scène 
     * @param titreSection Le titre de la section
     * @param titreListe  Le titre de la liste de livres
     * @param listeLivres La liste des livres à afficher
     * @param livresCount Le nombre de livres à afficher dans la section
     */
    public SectionVoirPlusLivresPane(SceneListBooksInterface scene, String titreSection, String titreListe, List<Livre> listeLivres, int livresCount) {
        this.scene = scene;
        this.titreSection = titreSection;
        this.titreListe = titreListe;
        this.listeLivres = listeLivres;
        this.livresCount = livresCount;

        this.setSpacing(20);

        this.getChildren().addAll(
            this.getTitle(),
            this.getBooksBox()
        );
    }

    /**
     * Obtenir la section titre.
     * @return Une GridPane contenant le titre de la section et un bouton pour voir plus
     */
    public GridPane getTitle() {
        GridPane section = new GridPane();
        section.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label(this.titreSection);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        section.add(titleLabel, 0, 0);
        
        Button viewAllButton = new Button("Voir tout");
        viewAllButton.setOnAction(new ControleurVoirPlus(this.scene, this.titreListe, this.listeLivres));
        GridPane.setHalignment(viewAllButton, HPos.RIGHT);
        section.add(viewAllButton, 1, 0);
        
        return section;
    }

    /**
     * Obtenir la HBox contenant les livres.
     * @return Une HBox contenant les cartes des livres
     */
    public HBox getBooksBox() {
        HBox booksBox = new HBox();
        booksBox.setSpacing(20);

        for (int i = 0; i < this.listeLivres.size() && i < this.livresCount; i++) {
            Livre livre = this.listeLivres.get(i);
            BorderPane bookCard = this.scene.createOrGetCardComponent(livre);
            
            HBox.setHgrow(bookCard, Priority.ALWAYS);
            booksBox.getChildren().add(bookCard);
        }
        return booksBox;
    }

    /**
     * Obtenir la carte d'un livre.
     * @param livre Un livre
     * @return Un composant de carte pour le livre
     */
    public BorderPane getBookCard(Livre livre) {
        return this.scene.createOrGetCardComponent(livre);
    }
}

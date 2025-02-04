import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


/**
 * Classe représentant une colonne de jeu
 * @version 1.1             1.2 avec les rectangles? ?
 * @author Raphaël Charozé
 */
public class ColonneDeJeu extends StackPane {
    Rectangle fond;
    Polygon triangle;
    VBox vBox;
    double HEIGHT = 400.;
    double WIDTH = 100.;

    private int nbPionsNoir = 0;
    private int nbPionsBlanc = 0;


    protected ColonneDeJeu(){
        //super();



        // Création du fond
        fond = new Rectangle();
        fond.setFill(Color.MAROON);
        fond.setHeight(HEIGHT);
        fond.setWidth(WIDTH);
        this.getChildren().add(fond);

        // Création du triangle
        triangle = new Polygon();
        triangle.setFill(Color.BLUE);

        // Définition des sommets du triangle
        triangle.getPoints().addAll(
                0.0, 0.0,         // Coin supérieur gauche
                WIDTH, 0.0,       // Coin supérieur droit
                WIDTH / 2, HEIGHT // Pointe inférieure centrale
        );
        this.getChildren().add(triangle);

        //Création de la VBox
        vBox= new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(vBox);


        //Platform.runLater(this::updateGraphics);


    }



    protected void updateRectangleSimple(){ // Changer et mettre à qui ajouter les pions ? Pas forcemmenet ce sera directement fait dans le "tour" de la personne
            String imageUrl = "file:Assets/rond_noir.png";
            this.getChildren().add(new ImageView(new Image(imageUrl)));


    }
    /**
     * Modifie le contenu interne du rectangle en fonction du joueur qui a joué, puis met à jour le contenu graphique
     * Attention : ne vérifie pas que le coup est valide par rapport au lancer de dés !!!
     */
    protected void updateRectangle(BgPane bgPane, Joueur j, boolean firstClicked) throws Exception{
        if (j == Joueur.BLANC){ // Si le joueur est blanc
            if (firstClicked){
                if (this.nbPionsBlanc == 0) throw new Exception("Pas de pions blancs dans cette colonne");
                this.nbPionsBlanc--;
            }else{
                if (this.nbPionsNoir > 1) throw new Exception("Il y a déjà des pions noirs dans cette colonne");
                if (this.nbPionsNoir == 1) ajouterPionPrison("NOIR");
                this.nbPionsBlanc++;
            }
        }else{
            if (firstClicked){
                if (this.nbPionsNoir == 0) throw new Exception("Pas de pions noirs dans cette colonne");
                this.nbPionsNoir--;
            }else{
                if (this.nbPionsBlanc > 1) throw new Exception("Il y a déjà des pions blancs dans cette colonne");
                if (this.nbPionsBlanc == 1) ajouterPionPrison("BLANC");
                this.nbPionsNoir++;
            }
        }

        Platform.runLater(this::updateGraphics);

    }

    /**
     * Modifie le contenu graphique du bouton en fonction du nombre de pions
     */
    private void updateGraphics(){
        this.getChildren().clear();
        int nbButtonToAdd;
        String imageUrl;
        if (this.nbPionsBlanc > 0){
            nbButtonToAdd = this.nbPionsBlanc;
            imageUrl = "file:Assets/rond_blanc.png";
        }
        else{
            nbButtonToAdd = this.nbPionsNoir;
            imageUrl = "file:Assets/rond_noir.png";
        }

        if (nbButtonToAdd > 5){
            this.getChildren().add(new ImageView(new Image(imageUrl)));
            this.getChildren().add(new Label(String.valueOf(nbButtonToAdd)));

        }else {
            for (int i = 0; i < nbButtonToAdd; i++) {
                this.getChildren().add(new ImageView(new Image(imageUrl)));
            }
        }
    }

    /**
     * Ajoute un pion dans la prison
     */
    private void ajouterPionPrison(String couleur) {
        if (couleur.equals("BLANC")) this.nbPionsBlanc--;
        else this.nbPionsNoir--;

        //TODO : ajouter a la prison, prison est une ColonneDeJeu, mais instancié où ?

    }





}

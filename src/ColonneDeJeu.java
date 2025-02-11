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
    double HEIGHT = 370.;
    double WIDTH = 90.;
    ImageView rond;

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    private int row;

    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    private int col;

    public int getNbPionsNoir() {
        return nbPionsNoir;
    }

    public int getNbPionsBlanc() {
        return nbPionsBlanc;
    }

    private int nbPionsNoir = 0;
    private int nbPionsBlanc = 0;


    protected ColonneDeJeu(){
        //super();

        // Création du fond
        fond = new Rectangle();
        fond.setFill(Color.BURLYWOOD);
        fond.setHeight(HEIGHT);
        fond.setWidth(WIDTH);
        this.getChildren().add(fond);

        // Création du triangle
        triangle = new Polygon();
        triangle.setFill(Color.STEELBLUE);

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

        this.updateGraphics();

        vBox.setOnMouseClicked((e) -> {
            try {
                if (Main.JEU.getCol1() == null) {
                    Main.JEU.setCol1(this);
                } else if (Main.JEU.getCol2() == null) {
                    Main.JEU.setCol2(this);
                }
                if (Main.JEU.getCol1() != null && Main.JEU.getCol2() != null) {
                    Main.JEU.bougerPion();

                    Main.JEU.setCol1(null);
                    Main.JEU.setCol2(null);

                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        
    }



    protected void updateRectangleSimple(){ // Changer et mettre à qui ajouter les pions ? Pas forcemmenet ce sera directement fait dans le "tour" de la personne
            String imageUrl = "file:Assets/rond_noir.png";
            this.getChildren().add(new ImageView(new Image(imageUrl)));


    }
    /**
     * Modifie le contenu interne du rectangle en fonction du joueur qui a joué, puis met à jour le contenu graphique
     * Attention : ne vérifie pas que le coup est valide par rapport au lancer de dés !!!
     */
    protected void updateRectangle(Joueur j, ColonneDeJeu col2) {
        if (j == Joueur.BLANC) { // Si le joueur est blanc
            if (this.nbPionsBlanc == 0) {
                System.out.println("Pas de pions blancs dans cette colonne");
            } else {
                if (col2.nbPionsNoir > 1) {
                    System.out.println("Il y a déjà des pions noirs dans cette colonne");
                } else if (col2.nbPionsNoir == 1) {
                    this.nbPionsBlanc--;
                    col2.nbPionsBlanc++;
                    col2.nbPionsNoir--;
                    Main.JEU.getPlateau().getPrisonNoir().setColNoir(Main.JEU.getPlateau().getPrisonNoir().getNbPionsNoir() + 1);

                } else {
                    this.nbPionsBlanc--;
                    col2.nbPionsBlanc++;
                }
            }
        } else {
            if (this.nbPionsNoir == 0) {
                System.out.println("Pas de pions noirs dans cette colonne");
            } else {
                if (col2.nbPionsBlanc > 1) {
                    System.out.println("Il y a déjà des pions blancs dans cette colonne");
                } else if (col2.nbPionsBlanc == 1) {
                    this.nbPionsNoir--;
                    col2.nbPionsNoir++;
                    col2.nbPionsBlanc--;
                    Main.JEU.getPlateau().getPrisonBlanc().setColBlanc(Main.JEU.getPlateau().getPrisonBlanc().getNbPionsBlanc() + 1);
                } else {
                    this.nbPionsNoir--;
                    col2.nbPionsNoir++;
                }
            }
        }


        this.updateGraphics();
        col2.updateGraphics();

    }

    /**
     * Modifie le contenu graphique du bouton en fonction du nombre de pions
     */
    private void updateGraphics(){
        this.vBox.getChildren().clear();
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
            rond = new ImageView(new Image(imageUrl));
            rond.setFitWidth(this.WIDTH-20);
            rond.setPreserveRatio(true);
            this.vBox.getChildren().add(rond);

            Label label = new Label(String.valueOf(nbButtonToAdd));
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-font-size: 50px");
            if (row == 1) label.rotateProperty().set(180);

            this.vBox.getChildren().add(label);

        }else {
            for (int i = 0; i < nbButtonToAdd; i++) {
                rond = new ImageView(new Image(imageUrl));
                rond.setFitWidth(this.WIDTH-20);
                rond.setPreserveRatio(true);
                this.vBox.getChildren().add(rond);
            }
        }
    }


//    public void setNbPionsNoir(int nb){
//        nbPionsNoir = nb;
//    }
//
//    public void setNbPionsBlanc(int nb){
//        nbPionsBlanc = nb;
//    }

    /**
     * Les deux méthodes qui suivent permettent de remplir une colonne avec un nombre {@param nb}souhaité de pions, blancs ou noirs
     */
    public void setColBlanc(int nb){
        /**for(int i =0;i <nb;i++){
            setRondBlanc();
            this.vBox.getChildren().add(rondBlanc);
        }*/
        nbPionsBlanc = nb ;
        this.updateGraphics();
    }

    public void setColNoir(int nb){
        /**for(int i =0;i <nb;i++){
            setRondNoir();
            this.vBox.getChildren().add(rondNoir);
        }*/
        nbPionsNoir = nb;
        this.updateGraphics();
    }
}

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;



/**
 * classe générant le visuel su jeu et qui gère les différentes actions
 * @author Ruben Knafo
 */
public class BgPane extends BorderPane {
    public static final int NBCOL = 12;
    public static final int NBROW = 2;
    TextField textDe = new TextField();
    GridPane plateau;
    ColonneDeJeu colonneDeJeu;

    public BgPane() {
        plateau = new GridPane();

        for(int col = 0 ; col< NBCOL ; col ++){
            for(int row = 0; row< NBROW; row ++){
                colonneDeJeu = new ColonneDeJeu();
                if(row==0) {
                    if (col%2==0) colonneDeJeu.triangle.setFill((Color.BLACK));
                }else{
                    if (col%2==1) colonneDeJeu.triangle.setFill((Color.BLACK));
                    colonneDeJeu.setRotate(180);
                }
                GridPane.setRowIndex(colonneDeJeu, row);
                GridPane.setColumnIndex(colonneDeJeu, col);
                plateau.getChildren().addAll(colonneDeJeu);


            }
        }
        setBordPlateau(plateau);
        setUpGame(plateau);

        this.setCenter(plateau);
        this.setRight(textDe);

    }
    /**
     * Permet de récuper un élément d'un gridPane en fonction de ses coordonées
     */
    public ColonneDeJeu getColonneDeJeu(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex == row && colIndex == column) {
                return (ColonneDeJeu) node;
            }
        }
        return null;
    }

    /**
     * Méthode qui ajoute les contours noirs au plateau
     */

    public void setBordPlateau(GridPane gp){
        for(int col = 0 ; col< NBCOL ; col ++) {
            for (int row = 0; row < NBROW; row++) {
                getColonneDeJeu(gp,row,col).setStyle("-fx-border-color:  black; -fx-border-width: 10 0 0 0;");
            }
        }
        getColonneDeJeu(gp,0,6).setStyle("-fx-border-color:  black; -fx-border-width: 10 0 0 5;");
        getColonneDeJeu(gp,0,5).setStyle("-fx-border-color:  black; -fx-border-width: 10 5 0 0;");
        getColonneDeJeu(gp,1,5).setStyle("-fx-border-color:  black; -fx-border-width: 10 0 0 5;"); // la ligne d'en bas est retournée
        getColonneDeJeu(gp,1,6).setStyle("-fx-border-color:  black; -fx-border-width: 10 5 0 0;");
        getColonneDeJeu(gp,0,0).setStyle("-fx-border-color:  black; -fx-border-width: 10 0 0 10;");
        getColonneDeJeu(gp,0,11).setStyle("-fx-border-color:  black; -fx-border-width: 10 10 0 0;");
        getColonneDeJeu(gp,1,11).setStyle("-fx-border-color:  black; -fx-border-width: 10 0 0 10;");
        getColonneDeJeu(gp,1,0).setStyle("-fx-border-color:  black; -fx-border-width: 10 10 0 0;");

    }

    /**
     * Méthode qui dispose le plateau de jeu dans la disposition de base
     */
    public void setUpGame(GridPane gp) {
        getColonneDeJeu(gp,0,0).setColBlanc(5);
        getColonneDeJeu(gp,0,4).setColNoir(3);
        getColonneDeJeu(gp,0,6).setColNoir(5);
        getColonneDeJeu(gp,0,11).setColBlanc(2);
        getColonneDeJeu(gp,1,0).setColNoir(5);
        getColonneDeJeu(gp,1,4).setColBlanc(3);
        getColonneDeJeu(gp,1,6).setColBlanc(5);
        getColonneDeJeu(gp,1,11).setColNoir(2);







    }

}







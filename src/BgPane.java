import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    ImageView rondBlanc;
    ImageView rondNoir;
    GridPane plateau;
    ColonneDeJeu colonneDeJeu;


    public void setUpGame() {


    }
    public void placer(ImageView iv, GridPane gp, ColonneDeJeu colonneDeJeu){
        GridPane.setRowIndex(iv, GridPane.getRowIndex(colonneDeJeu));
        GridPane.setColumnIndex(iv, GridPane.getColumnIndex(colonneDeJeu));
        iv.setFitWidth(50);
        iv.setPreserveRatio(true);

        colonneDeJeu.getChildren().addAll(iv);
    }




    public BgPane() {
        plateau = new GridPane();

        for(int col = 0 ; col< NBCOL ; col ++){
            for(int row = 0; row< NBROW; row ++){
                rondBlanc = new ImageView(new Image("file:Assets/rond_blanc.png"));
                rondNoir = new ImageView(new Image("file:Assets/rond_noir.png"));
                colonneDeJeu = new ColonneDeJeu();
                rondBlanc.setFitWidth(colonneDeJeu.WIDTH-20);
                rondNoir.setFitWidth(colonneDeJeu.HEIGHT-20);
                rondBlanc.setPreserveRatio(true);
                rondNoir.setPreserveRatio(true);
                if(row==0) {
                    if (col%2==0) colonneDeJeu.triangle.setFill((Color.BLACK));
                }else{
                    if (col%2==1) colonneDeJeu.triangle.setFill((Color.BLACK));
                    colonneDeJeu.setRotate(180);
                }
                GridPane.setRowIndex(colonneDeJeu, row);
                GridPane.setColumnIndex(colonneDeJeu, col);
                plateau.getChildren().addAll(colonneDeJeu);
                if(row==1) {
                    getColonneDeJeu(plateau,row,col).vBox.getChildren().add(rondBlanc);

                }

            }
        }
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

}







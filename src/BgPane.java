import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

/**
 * classe générant le visuel su jeu et qui gère les différentes actions
 * @author ruben
 */
public class BgPane extends BorderPane {
    public static final int NBLIGNE = 6;
    TextField textDe = new TextField();

    public static class PlateauBgPane extends GridPane{
        public PlateauBgPane(){
            Image im = new Image("file:Assets/plateau.png");
            BackgroundImage bG_Plateau = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true));
            this.setBackground(new Background(bG_Plateau));
            for(int i = 0; i < NBLIGNE*2; i++){
                ColonneDeJeu col = new ColonneDeJeu();
                GridPane.setRowIndex(col,i/6);
                GridPane.setColumnIndex(col,i%6);
                this.getChildren().add(col);

            }

        }

    }


    PlateauBgPane plateau = new PlateauBgPane();

    public BgPane(){
        this.setCenter(plateau);




    }

}

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;


/**
 * classe générant le visuel su jeu et qui gère les différentes actions
 * @author Ruben Knafo
 */
public class BgPane extends BorderPane {
    public static final int NBCOL = 12;
    public static final int NBROW = 2;
    GridPane plateau;
    ColonneDeJeu colonneDeJeu;

    public ColonneDeJeu getPrisonNoir() {
        return prisonNoir;
    }

    public ColonneDeJeu getPrisonBlanc() {
        return prisonBlanc;
    }

    private ColonneDeJeu prisonBlanc;
    private ColonneDeJeu prisonNoir;


    public BgPane() {
        plateau = new GridPane();

        for(int col = 0 ; col< NBCOL ; col ++){
            for(int row = 0; row< NBROW; row ++){
                colonneDeJeu = new ColonneDeJeu();
                colonneDeJeu.setCol(col);
                colonneDeJeu.setRow(row);
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


        VBox prisons = new VBox();
        prisonBlanc = new ColonneDeJeu();
        prisonBlanc.fond.setFill(Color.WHITE);
        prisonBlanc.triangle.setFill(Color.WHITE);
        prisonBlanc.setColBlanc(0);
        prisonNoir = new ColonneDeJeu();
        prisonNoir.fond.setFill(Color.WHITE);
        prisonNoir.triangle.setFill(Color.WHITE);
        prisonNoir.setColNoir(0);

        prisonNoir.setRow(100);
        prisonBlanc.setRow(100);
        prisonNoir.setCol(100);
        prisonBlanc.setCol(100);

        prisons.getChildren().addAll(prisonBlanc,prisonNoir);


        this.setRight(prisons);


        VBox dice = new VBox();
        Label de1 = new Label("Dé 1: pas lancé");
        Label de2 = new Label("Dé 2: pas lancé");
        Button lancer = new Button("Lancer les dés");

        lancer.setOnAction(e -> {
            if (Main.JEU.getResteDes().isEmpty()) {
                Main.JEU.lancerDes();
                de1.setText("Dé 1 : "+Main.JEU.valeurDes()[0]);
                de2.setText("Dé 2 : "+Main.JEU.valeurDes()[1]);

                ArrayList<Integer> nouvelleListe = new ArrayList<>();
                nouvelleListe.add(Main.JEU.valeurDes()[0]);
                nouvelleListe.add(Main.JEU.valeurDes()[1]);
                if (Main.JEU.valeurDes()[0] == Main.JEU.valeurDes()[1]) {
                    nouvelleListe.add(Main.JEU.valeurDes()[0]);
                    nouvelleListe.add(Main.JEU.valeurDes()[1]);
                }

                Main.JEU.setDesLances(true);
                Main.JEU.setResteDes(nouvelleListe);
            }
        });

        dice.getChildren().addAll(de1,de2,lancer);

        this.setLeft(dice);

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







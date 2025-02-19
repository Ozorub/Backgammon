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
    GridPane grille;
    ColonneDeJeu colonneDeJeu;
    VBox dice = new VBox();
    VBox prisons = new VBox();
    private ColonneDeJeu prisonBlanc;
    private ColonneDeJeu prisonNoir;

    public ColonneDeJeu getPrisonNoir() {
        return prisonNoir;
    }

    public ColonneDeJeu getPrisonBlanc() {
        return prisonBlanc;
    }



    public BgPane() {
        grille = new GridPane();

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
                grille.getChildren().addAll(colonneDeJeu);


            }
        }
        setBordPlateau(grille);
        setUpEndGame(grille);
        //setUpGame(grille);

        this.setCenter(grille);

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

        dice.getChildren().addAll(de1,de2,lancer, new Label("Les blancs commencent"));

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

    public void setUpEndGame(GridPane gp){
        getColonneDeJeu(gp,1,6).setColBlanc(2);
        getColonneDeJeu(gp,1,7).setColBlanc(2);
        getColonneDeJeu(gp,1,8).setColBlanc(2);
//        getColonneDeJeu(gp,1,9).setColBlanc(2);
//        getColonneDeJeu(gp,1,10).setColBlanc(2);
//        getColonneDeJeu(gp,1,11).setColBlanc(2);
        getColonneDeJeu(gp,0,6).setColNoir(2);
        getColonneDeJeu(gp,0,7).setColNoir(2);
        getColonneDeJeu(gp,0,8).setColNoir(2);
        getColonneDeJeu(gp,0,9).setColNoir(2);
        getColonneDeJeu(gp,0,10).setColNoir(2);
        getColonneDeJeu(gp,0,11).setColNoir(2);
    }

}







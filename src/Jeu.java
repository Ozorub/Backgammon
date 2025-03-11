import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Jeu
 *
 * @author Raphaël Charozé
 * @version 1.0
 */
public class Jeu {
    private final Joueur j1 = Joueur.BLANC;
    private final Joueur j2 = Joueur.NOIR;
    private static final PaireDeDes des = new PaireDeDes();
    private boolean desLances = false;
    private ArrayList<Integer> resteDes = new ArrayList<>();
    private Joueur currentJoueur = j1;
    private ColonneDeJeu col1 = null;
    private ColonneDeJeu col2 = null;
    private final BgPane plateau = new BgPane();
    int coutDuMouv;
    static Boolean isEndGameWhite = false;
    static Boolean isEndGameBlack = false;
    private int nbPionsBlancGauche = 0;
    private int nbPionsNoirGauche = 0;
    static boolean isSpecialEndGameWhite = false;
    static boolean isSpecialEndGameBlack = false;


    /**
     * Constructeur de la classe Jeu
     */
    public Jeu() {
    }

    /**
     * Méthode principale pour lancer le jeu
     */
    public void jouer() {
        boolean finDuJeu = false;
        currentJoueur = j2;

        while (!finDuJeu) {

            boolean finDuTour = false;
            System.out.println("Tour de " + currentJoueur);
            currentJoueur = (currentJoueur == j1) ? j2 : j1; //changement de joueur
            // int[] lancer = des.lancerLesDes(); //lancer des dés


            while (!finDuTour) {
                //finDuTour = !canPlay(currentJoueur, lancer);
            }
            //not really useful need to check usefulness
        }

        //TODO : sortir du jeu
    }


    public Joueur getJ1() {
        return j1;
    }


    void bougerPion() {
        try {

            System.out.println("Tour de " + currentJoueur);
            System.out.println("Encore a jouer: " + resteDes.toString());
            ColonneDeJeu col1 = Main.JEU.getCol1();
            ColonneDeJeu col2 = Main.JEU.getCol2();

            //Aucun pions ailleurs que dans sa zone(pas en prison non plus)
            if (!isEndGameWhite) {
                boolean intermediare = false;
                for (int col = 0; col < BgPane.NBCOL; col++) {
                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsBlanc() == 0) {
                        intermediare= true;
                    }
                }if(plateau.getColonneDeJeu(plateau.grille, 1, 0).getNbPionsBlanc() == 0
                        && plateau.getColonneDeJeu(plateau.grille, 1, 1).getNbPionsBlanc() == 0
                        && plateau.getColonneDeJeu(plateau.grille, 1, 2).getNbPionsBlanc() == 0
                        && plateau.getColonneDeJeu(plateau.grille, 1, 3).getNbPionsBlanc() == 0
                        && plateau.getColonneDeJeu(plateau.grille, 1, 4).getNbPionsBlanc() == 0
                        && plateau.getColonneDeJeu(plateau.grille, 1, 5).getNbPionsBlanc() == 0
                        && plateau.getPrisonBlanc().getNbPionsBlanc() == 0
                        && intermediare){
                    isEndGameWhite = true;
                }
            }
            if (!isEndGameBlack) {
                boolean intermediaire = false ;
                for (int col = 1; col < BgPane.NBCOL; col++) {
                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsBlanc() == 0) {
                        intermediaire = true ;
                    }
                    if(plateau.getColonneDeJeu(plateau.grille, 0, 0).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 1).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 2).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 3).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 4).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 5).getNbPionsNoir() == 0
                            && plateau.getPrisonNoir().getNbPionsNoir() == 0
                            && intermediaire){
                        isEndGameBlack = true;
                    }
                }
            }


            if (desLances) {
                if (currentJoueur == Joueur.BLANC) {
                    if (!isEndGameWhite) {
                        if (col1.getRow() == 100 || col2.getRow() == 100 || ((col1.getRow() < col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() < col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() > col2.getCol())))) { // inGame


                            deplacerPion(currentJoueur);

                        }else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    } else {
                        //Endgame
                        if (col1.getNbPionsBlanc() == 0) {
                            for (int col = col1.getCol(); col > col1.getCol() - 6; col--) {
                                nbPionsBlancGauche += plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsBlanc();
                            }
                            if (nbPionsBlancGauche == 0) { // choix de conception : il faut quand même appuyer sur le "bon endroit" mais ça enlève le pion le moins à droite possible
                                System.out.println("Je vais supprimer un pion");

                                boolean firstTime2 = true;
                                for (int col = col1.getCol(); col < col1.getCol() + Math.min(5, BgPane.NBCOL - col1.getCol()); col++) {
                                    if (plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsBlanc() > 0 && firstTime2) {
                                        supprPion(currentJoueur, plateau.getColonneDeJeu(plateau.grille, 1, col));
                                        firstTime2 = false;
                                    }
                                }

                            }
                            int indexCol = 6;
                            boolean colEmpty = true;
                            while(colEmpty){

                                if (plateau.getColonneDeJeu(plateau.grille, 1, indexCol).getNbPionsBlanc() != 0 ){
                                    colEmpty = false;

                                    setCol2(plateau.getColonneDeJeu(plateau.grille, 1,  indexCol+12-col1.getCol())); // Col1 et Col2 ne sont pas dépendantes, il est donc possible de reSet col2 en premier, lui donnant la valeur "de la première colonne non vide à droite + la valeur de la col1"
                                    setCol1(plateau.getColonneDeJeu(plateau.grille, 1, indexCol));

                                    deplacerPion(currentJoueur);
                                }

                                indexCol ++;
                            }

                        }
                        supprPion(currentJoueur);

                    }
                } else if (currentJoueur == Joueur.NOIR) {
                    if (!isEndGameBlack) {

                        if (col1.getRow() == 100 || col2.getRow() == 100 || ((col1.getRow() > col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() > col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() < col2.getCol())))) { // inGame

                            deplacerPion(currentJoueur);

                        }

                             else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    } else {
                        //Endgame

                        if (col1.getNbPionsNoir() == 0) {
                            for (int col = col1.getCol(); col > col1.getCol() - 6; col--) {
                                nbPionsNoirGauche += plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsNoir();
                            }
                            if (nbPionsNoirGauche == 0) { // choix de conception : il faut quand même appuyer sur le "bon endroit" mais ça enlève le pion le moins à droite possible
                                boolean firstTime2 = true;
                                for (int col = col1.getCol(); col < col1.getCol() + Math.min(5, BgPane.NBCOL - col1.getCol()); col++) {
                                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsNoir() > 0 && firstTime2) {
                                        supprPion(currentJoueur, plateau.getColonneDeJeu(plateau.grille, 0, col));
                                        firstTime2 = false;
                                    }
                                }



                            }
                            int indexCol = 6;
                            boolean colEmpty = true;
                            while(colEmpty){

                                if (plateau.getColonneDeJeu(plateau.grille, 0, indexCol).getNbPionsNoir() != 0 ){
                                    colEmpty = false;

                                    setCol2(plateau.getColonneDeJeu(plateau.grille, 0,  indexCol+12-col1.getCol())); // Col1 et Col2 ne sont pas dépendantes, il est donc possible de reSet col2 en premier, lui donnant la valeur "de la première colonne non vide à droite + la valeur de la col1"
                                    setCol1(plateau.getColonneDeJeu(plateau.grille, 0, indexCol));

                                    deplacerPion(currentJoueur);
                                }

                                indexCol ++;
                            }
                        }
                        supprPion(currentJoueur);
                    }
                }

            } else {
                System.out.println("Vous devez lancer les dés");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public void deplacerPion(Joueur j) {
        System.out.println("Je rentre dans la méthode deplacer pion");
        if(col1.getCol()==100){ // gestion de la prison
            coutDuMouv = 12- col2.getCol();
            System.out.println("Je suis en prison triste vie");
            if(currentJoueur == Joueur.NOIR && col2.getNbPionsBlanc() <=1 && resteDes.contains(coutDuMouv) && col2.getRow()==1 ){
                col1.updateColonne(currentJoueur,col2,1);
            } else if (currentJoueur == Joueur.BLANC && col2.getNbPionsNoir() <=1 && resteDes.contains(coutDuMouv) && col2.getRow() == 0 ) {
                col1.updateColonne(currentJoueur,col2,1);
            }
        }

        else if (col1.getRow() == col2.getRow()) {
            coutDuMouv = Math.abs(col1.getCol() - col2.getCol());
        } else {
            coutDuMouv = col1.getCol() + col2.getCol() + 1;
        }

        if (resteDes.contains(coutDuMouv)) {
            col1.updateColonne(currentJoueur, col2, 1);
            coutDuMouv = 0;
            if (resteDes.isEmpty()) {
                if (j == Joueur.BLANC)
                    plateau.dice.getChildren().set(3, new Label("Tour des noirs")); // Le 3 corespond à sa place dans le VBox de la partie gauche du BorderPane
                else plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
                desLances = false;
                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
            }
        }
    }


    public void supprPion(Joueur j) {
        coutDuMouv = 6 - (col1.getCol() - BgPane.NBCOL / 2);
        if (resteDes.contains(coutDuMouv)) {

            col1.updateColonne(currentJoueur, col1, 0);
            coutDuMouv = 0;
            if (resteDes.isEmpty()) {
                if (j == Joueur.BLANC)
                    plateau.dice.getChildren().set(3, new Label("Tour des noirs")); // Le 3 corespond à sa place dans le VBox de la partie gauche du BorderPane
                else plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
                desLances = false;
                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
            }
        } else {

            System.out.println("Recommence, Tu ne peux pas prendre de pions ici");
        }
    }

    public void supprPion(Joueur j, ColonneDeJeu colonneDeJeu) {
        coutDuMouv = 6 - (col1.getCol() - BgPane.NBCOL / 2);
        if (resteDes.contains(coutDuMouv)) {

            colonneDeJeu.updateColonne(currentJoueur, col1, 0);
            coutDuMouv = 0;
            if (resteDes.isEmpty()) {
                if (j == Joueur.BLANC)
                    plateau.dice.getChildren().set(3, new Label("Tour des noirs")); // Le 3 corespond à sa place dans le VBox de la partie gauche du BorderPane
                else plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
                desLances = false;
                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
            }
        } else {
            //System.out.println("Recommence, Tu ne peux pas prendre de pions ici");
        }
    }

    void lancerDes() {
        des.lancerLesDes();
    }

    public static int[] valeurDes() {
        return des.valeursDesDes();
    }

    public BgPane getPlateau() {
        return plateau;
    }

    public ColonneDeJeu getCol1() {
        return col1;
    }

    public ColonneDeJeu getCol2() {
        return col2;
    }

    public void setCol1(ColonneDeJeu col) {
        col1 = col;
    }

    public void setCol2(ColonneDeJeu col) {
        col2 = col;
    }

    public void setDesLances(boolean desLances) {
        this.desLances = desLances;
    }

    public void setResteDes(ArrayList<Integer> des) {
        this.resteDes = des;
    }


    public List<Integer> getResteDes() {
        return resteDes;
    }

    public int getCoutDuMouv() {
        return coutDuMouv;
    }

    public int getNbPionsBlancGauche() {
        return nbPionsBlancGauche;
    }

    public void setNbPionsBlancGauche(int nbPionsBlancGauche) {
        this.nbPionsBlancGauche = nbPionsBlancGauche;
    }

    public int getNbPionsNoirGauche() {
        return nbPionsNoirGauche;
    }

    public void setNbPionsNoirGauche(int nbPionsNoirGauche) {
        this.nbPionsNoirGauche = nbPionsNoirGauche;
    }


    public static void setIsEndGameWhite(Boolean isEndGameWhite) {
        Jeu.isEndGameWhite = isEndGameWhite;
    }


    public static void setIsEndGameBlack(Boolean isEndGameBlack) {
        Jeu.isEndGameBlack = isEndGameBlack;


    }


    public static void setIsSpecialEndGameBlack(boolean isSpecialEndGameBlack) {
        Jeu.isSpecialEndGameBlack = isSpecialEndGameBlack;
    }


    public static void setIsSpecialEndGameWhite(boolean isSpecialEndGameWhite) {
        Jeu.isSpecialEndGameWhite = isSpecialEndGameWhite;
    }
}

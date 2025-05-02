import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

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
    private Runnable onPionBougeCallback;
    private boolean iaALanceLesDes = false;
    private boolean isFisrtMouvOfTheGame = true;


    /**
     * Constructeur de la classe Jeu
     */
    public Jeu() {
    }

//    /**
//     * Méthode principale pour lancer le jeu
//     */
//    public void jouer() {
//        boolean finDuJeu = false;
//        currentJoueur = j2;
//
//        while (!finDuJeu) {
//
//            boolean finDuTour = false;
//            System.out.println("Tour de " + currentJoueur);
//            currentJoueur = (currentJoueur == j1) ? j2 : j1; //changement de joueur
//            // int[] lancer = des.lancerLesDes(); //lancer des dés
//
//
//            while (!finDuTour) {
//                //finDuTour = !canPlay(currentJoueur, lancer);
//            }
//            //not really useful need to check usefulness
//        }
//
//        //TODO : sortir du jeu
//    }


    void bougerPion() {
        try {

            if(noMoreWhite()||noMoreBlack()){
                Label lab = new Label(currentJoueur + "a gagné la partie");
                plateau.dice.getChildren().add(lab);

            }
            System.out.println("Tour de " + currentJoueur);
            System.out.println("Encore a jouer: " + resteDes.toString());
            ColonneDeJeu col1 = Main.JEU.getCol1();
            ColonneDeJeu col2 = Main.JEU.getCol2();
            System.out.println("1er ColDeJeu : " + col1 + ", 2e colDeJeu : "+ col2);


//            System.out.println(testEndgameWhite() + "   c'est le test de l'endgame");

            //Aucun pions ailleurs que dans sa zone(pas en prison non plus)
            if (!isEndGameWhite) {
                if(testEndgameWhite()) isEndGameWhite = true;
            }
            if (!isEndGameBlack) {
                    if(testEndgameBlack()) isEndGameBlack = true;
                }


            if (desLances) {
                if (currentJoueur == Joueur.BLANC) {
                    if(plateau.getPrisonBlanc().getNbPionsBlanc() !=0){
                        System.out.println("Prisosososososososososoos");
                        deplacerPionPrison(currentJoueur);
                    }

                    else if (!isEndGameWhite) {

                        if ((col1.getRow() < col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() < col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() > col2.getCol()))) { // inGame

                            deplacerPion(currentJoueur);

                        }else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    } else {
                        //Endgame
                        System.out.println( " ON EST LAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA?");
                        System.out.println(col1.getNbPionsBlanc() + " Pions blancs");
                        if (col1.getNbPionsBlanc() == 0) {
                            for (int col = col1.getCol(); col >= 6; --col) {
                                System.out.println("A la colone " + col + " il y a " + plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsBlanc() + " pions blancs");
                                nbPionsBlancGauche += plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsBlanc();
                            }
                            System.out.println(nbPionsBlancGauche + " Nb pions blancs à gauche");
                            if (nbPionsBlancGauche == 0) { // choix de conception : il faut quand même appuyer sur le "bon endroit" mais ça enlève le pion le plus à droite possible
                                System.out.println("Je vais supprimer un pion");

                                boolean firstTime2 = true;
                                for (int col = col1.getCol(); col < col1.getCol() + Math.min(5, BgPane.NBCOL - col1.getCol()); col++) {
                                    if (plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsBlanc() > 0 && firstTime2) {
                                        supprPion(currentJoueur, plateau.getColonneDeJeu(plateau.grille, 1, col));
                                        firstTime2 = false;
                                    }
                                }

                            }
                            else{
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

                        }
                        supprPion(currentJoueur);

                    }
                } else if (currentJoueur == Joueur.NOIR) {

                    if(plateau.getPrisonNoir().getNbPionsNoir() != 0 ){
                        deplacerPionPrison(currentJoueur);
                    }
                    else if (!isEndGameBlack) {
                        if ((col1.getRow() > col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() > col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() < col2.getCol()))) { // inGame

                            deplacerPion(currentJoueur);
                        }
                             else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    } else {
                        //Endgame
                        System.out.println( " ON EST AUSSI LAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA?");
                        System.out.println(col1.getNbPionsBlanc() + " Pions NOIRs");
                        if (col1.getNbPionsNoir() == 0) {
                            for (int col = col1.getCol(); col >=  6; --col) {
                                System.out.println("A la colone " + col + " il y a " + plateau.getColonneDeJeu(plateau.grille, 1, col).getNbPionsNoir() + " pions noirs");

                                nbPionsNoirGauche += plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsNoir();
                            }
                            System.out.println(nbPionsNoirGauche + " Nb pions noirs à gauche");

                            if (nbPionsNoirGauche == 0) { // choix de conception : il faut quand même appuyer sur le "bon endroit" mais ça enlève le pion le moins à droite possible
                                boolean firstTime2 = true;
                                for (int col = col1.getCol(); col < col1.getCol() + Math.min(5, BgPane.NBCOL - col1.getCol()); col++) {
                                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsNoir() > 0 && firstTime2) {
                                        supprPion(currentJoueur, plateau.getColonneDeJeu(plateau.grille, 0, col));
                                        firstTime2 = false;
                                    }
                                }



                            }
                            else{
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
                        }
                        supprPion(currentJoueur);
                    }
                }

            } else {
                System.out.println("Vous devez lancer les dés");
            }

            if (onPionBougeCallback != null) {
                onPionBougeCallback.run();
                onPionBougeCallback = null; // on le remet à null pour ne pas le réutiliser
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public void deplacerPion(Joueur j) {
        //System.out.println("Je rentre dans la méthode deplacer pion");
        if (col1.getRow() == col2.getRow()) {
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
                System.out.println("player have switched");
                if (Main.isJoueurBlancIA || Main.isJoueurNoirIA) {
                    makeAiPlay();
                }
            }
        }


    }

    public void makeAiPlay() {
        System.out.println("Avant de jouer, j'ai lancé les dès : " + iaALanceLesDes);
        if (!iaALanceLesDes) {
            getPlateau().getLancer().fire();
            iaALanceLesDes = true;
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            if (!resteDes.isEmpty()) {
                col1 = null;
                col2 = null;
                try {
                    Main.ia.makeAmove();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Rejoue après 1 seconde
                pause.playFromStart();
            } else {
                // Les dés sont vides, on arrête
                iaALanceLesDes = false;
                System.out.println("Les dés sont épuisés, IA a terminé son tour.");
            }
        });

        pause.play();

        if ((currentJoueur == Joueur.BLANC && Main.isJoueurBlancIA)
                || (currentJoueur == Joueur.NOIR && Main.isJoueurNoirIA)) {

            try {
                Main.ia.makeAmove();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            // Petite pause pour laisser le temps d'affichage du coup
            PauseTransition pause1 = new PauseTransition(Duration.seconds(0.5));
            pause1.setOnFinished(event -> nextTurn());
            pause1.play();
        }

    }

    public void nextTurn() {
        currentJoueur = (currentJoueur == j1) ? j2 : j1; //changement de joueur

        if ((currentJoueur == Joueur.BLANC && Main.isJoueurBlancIA) ||
                (currentJoueur == Joueur.NOIR && Main.isJoueurNoirIA)) {
            makeAiPlay();
        }
    }


    public void deplacerPionPrison(Joueur j){
        if(col1.getCol()==100){ // gestion de la prison
            coutDuMouv = 12- col2.getCol();
            System.out.println("Je suis en prison triste vie");
            if(currentJoueur == Joueur.NOIR && col2.getNbPionsBlanc() <=1 && resteDes.contains(coutDuMouv) && col2.getRow()==1 ){
                col1.updateColonne(currentJoueur,col2,1);
            } else if (currentJoueur == Joueur.BLANC && col2.getNbPionsNoir() <=1 && resteDes.contains(coutDuMouv) && col2.getRow() == 0 ) {
                col1.updateColonne(currentJoueur,col2,1);
            }
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
//            //TEST
//            synchronized (IA.getLock()) {
//                if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
//                        ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
//                    IA.setHasAiAlreadyPlayed(false);
//                    IA.getLock().notifyAll();
//                }
//            }
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
            //TEST
//            synchronized (IA.getLock()) {
//                if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
//                        ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
//                    IA.setHasAiAlreadyPlayed(false);
//                    IA.getLock().notifyAll();
//                }
//            }
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
            //TEST
//            synchronized (IA.getLock()) {
//                if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
//                        ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
//                    IA.setHasAiAlreadyPlayed(false);
//                    IA.getLock().notifyAll();
//                }
//            }
        }
    }

    public boolean testEndgameWhite(){
        return (plateau.getColonneDeJeu(plateau.grille, 1, 0).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 1).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 2).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 3).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 4).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 5).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 0).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 1).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 2).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 3).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 4).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 5).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 6).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 7).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 8).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 9).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 10).getNbPionsBlanc() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 11).getNbPionsBlanc() == 0
                && plateau.getPrisonBlanc().getNbPionsBlanc() == 0);

    }

    public boolean testEndgameBlack(){
        return plateau.getColonneDeJeu(plateau.grille, 0, 0).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 1).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 2).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 3).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 4).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 0, 5).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 0).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 1).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 2).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 3).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 4).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 5).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 6).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 7).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 8).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 9).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 10).getNbPionsNoir() == 0
                && plateau.getColonneDeJeu(plateau.grille, 1, 11).getNbPionsNoir() == 0
                && plateau.getPrisonNoir().getNbPionsNoir() == 0;
    }

    public boolean noMoreWhite() {
        int nbWhite = 0;
        for (int row = 0; row < BgPane.NBROW; row++) {
            for (int col = 0; col < BgPane.NBCOL; col++) {
                nbWhite += plateau.getColonneDeJeu(plateau.grille, row, col).getNbPionsBlanc();
            }
        }
        return nbWhite == 0;
    }
    public boolean noMoreBlack(){
            int nbBlack = 0;
            for (int row = 0 ; row < BgPane.NBROW;row++){
                for (int col = 0 ; col < BgPane.NBCOL ; col++){
                    nbBlack += plateau.getColonneDeJeu(plateau.grille,row,col).getNbPionsNoir();
                }
            }
            return nbBlack == 0;
    }

    //A modifier si l'on souhaite un peu factoriser le code

//    public void updateMouvPion(Joueur j, int coutDuMouv, int action, ColonneDeJeu colonneDeJeu) {
//        if (resteDes.contains(coutDuMouv)) {
//            colonneDeJeu.updateColonne(currentJoueur, col1, action);
//            coutDuMouv = 0;
//            if (resteDes.isEmpty()) {
//                if (j == Joueur.BLANC)
//                    plateau.dice.getChildren().set(3, new Label("Tour des noirs")); // Le 3 corespond à sa place dans le VBox de la partie gauche du BorderPane
//                else plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
//                desLances = false;
//                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
//            }
//        }
//    }

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



    public Joueur getCurrentJoueur() {
        return currentJoueur;
    }

    public void setCurrentJoueur(Joueur j) {
        this.currentJoueur = j;
    }

    public Joueur getJ1() {
        return j1;
    }
    public Joueur getJ2() {
        return j2;
    }
    public boolean isDesLances() {
        return desLances;
    }

}

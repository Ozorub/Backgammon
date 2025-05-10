import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.lang.Integer.*;
import static java.lang.Thread.sleep;
import static java.util.Collections.max;

public abstract class IA extends JoueurClass {

    //private List<ColonneDeJeu[]> coups = new ArrayList<>();
    private final Jeu jeu = Main.JEU;
    private final Joueur j1 = Joueur.BLANC;
    private final Joueur j2 = Joueur.NOIR;
    private Joueur currentJoueur = j1;
    private int currentDepth = 0;

    private int MAX_DEPTH = 0;

    public IA() {

    }

    public void makeAmove() throws InterruptedException {
        sleep(100);

       // while (!jeu.getResteDes().isEmpty()) {

            System.out.println("YEAHHHHqqqqqqq" + "Reste dès : " + jeu.getResteDes());
            CoupsPossibles coupsPossiblesClass = new CoupsPossibles();
            List<ColonneDeJeu[]> coupsPossibles = coupsPossiblesClass.calculCoupsPossibles(jeu.getCurrentJoueur(), jeu.getResteDes());

//            if (coupsPossibles.isEmpty()) {
//                System.out.println("Aucun coup possible !");
//                Main.JEU.setDesLances(false);
//                Main.JEU.setCurrentJoueur(Main.JEU.getCurrentJoueur() == Joueur.BLANC ? Main.JEU.getJ2() : Main.JEU.getJ1());
//                return;
//            }

            System.out.println("Coups possibles générés : " + coupsPossibles.size());
            for (ColonneDeJeu[] coup : coupsPossibles) {
                System.out.printf("De (%d,%d) -> À (%d,%d)%n",
                        coup[0].getRow(), coup[0].getCol(),
                        coup[1].getRow(), coup[1].getCol()
                );
            }
            ColonneDeJeu[] bestMove = getBestMove(coupsPossibles);
            System.out.println("voici le best move" + Arrays.toString(bestMove));

            if (bestMove.length != 0) {
                try {

                    if (jeu.getCol1() == null) {

                        jeu.setCol1(bestMove[0]);
                        jeu.getCol1().fond.setFill(Color.VIOLET);

                    }
                    if (jeu.getCol2() == null) {

                        jeu.setCol2(bestMove[1]);
                        jeu.getCol1().fond.setFill(Color.BURLYWOOD);

                    }
                    if (jeu.getCol1() != null && jeu.getCol2() != null) {

                        //CountDownLatch latch = new CountDownLatch(1);
                        //jeu.setOnPionBougeCallback(latch::countDown);
                        jeu.bougerPion();
                        //latch.await(); //  Attend que le déplacement soit fini

                        jeu.setCol1(null);
                        jeu.setCol2(null);


                    }


                } catch (Exception exception) {
                    exception.printStackTrace();
                }
           // }
        }
        currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;


//        System.out.printf("Best move : %s\n",bestMove[0].toString());
//        MouseEvent clickEvent = new MouseEvent(
//                MouseEvent.MOUSE_CLICKED,
//                0, 0, 0, 0, // position
//                MouseButton.PRIMARY,
//                1,          // nombre de clics
//                false, false, false, false,
//                true, false, false, true,
//                false, false,
//                null
//        );
//
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            bestMove[0].fireEvent(clickEvent);
//            bestMove[1].fireEvent(clickEvent);
//            latch.countDown();
//        });
//
//        latch.await();
    }


    public ColonneDeJeu[] getBestMove(List<ColonneDeJeu[]> coutsPossible){
        if (coutsPossible == null || coutsPossible.isEmpty()) {
            System.out.println("Aucun coup reçu dans getBestMove !");
            return new ColonneDeJeu[0]; // ou null selon le cas
        }

        currentDepth = 0;

        return alphaBetaDecision(coutsPossible,new RepPlateau(Main.JEU.getPlateau()),Main.JEU.getCurrentJoueur());
    }

    public abstract int calculGain(RepPlateau plateau, int[] dep_arr, boolean isWhite);

    public ColonneDeJeu[] minMaxdecision(List<ColonneDeJeu[]> coupsPossibles, RepPlateau plateau, Joueur j) {
        currentDepth++;
        ArrayList<Integer> values = new ArrayList<>(coupsPossibles.size());
        coupsPossibles.forEach(coupWesh -> {
            int rowD = coupWesh[0].getRow();
            int colD = coupWesh[0].getCol();
            int rowA = coupWesh[1].getRow();
            int colA = coupWesh[1].getCol();

            values.add(minValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR));
        });
        return coupsPossibles.get(values.indexOf(max(values))); // on retourne le coup possible correspondant au max des valeurs que "MIN" renvoi"
    }

    public int minValue(RepPlateau plateau, Joueur j) {
        currentDepth++;
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau)) return calculGain(plateau, new int[]{0,0,0,0}, j == Joueur.BLANC); // TODO : regler le dep_arr qui est null

        //on trouve le minimum des coups possibles
        int v = Integer.MAX_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[1];
            int colD = coup[2];
            int rowA = coup[3];
            int colA = coup[4];

            v = min(v, (maxValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR)));
        }
        return v;
    }

    public int maxValue(RepPlateau plateau, Joueur j) {
        currentDepth++;
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau)) return calculGain(plateau, new int[]{0,0,0,0}, j == Joueur.BLANC); // TODO : regler le dep_arr qui est null

        //on trouve le minimum des coups possibles
        int v = Integer.MIN_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[1];
            int colD = coup[2];
            int rowA = coup[3];
            int colA = coup[4];

            v = Integer.max(v, (minValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR)));
        }
        return v;
    }


    public ColonneDeJeu[] alphaBetaDecision(List<ColonneDeJeu[]> coupsPossibles, RepPlateau plateau, Joueur j) {
        currentDepth++;

        if(j == Joueur.BLANC){
            switch (Main.jBlancLevel) {
                case 1:
                    MAX_DEPTH = IA_easy_baby.getMaxDepth();
                    break;
                case 2:
                    MAX_DEPTH = IA_intermediate.getMaxDepth();
                    break;
                case 3:
                    MAX_DEPTH = IA_hard.getMaxDepth();
                    break;
            }
        }
        else if(j == Joueur.NOIR){
            switch (Main.jNoirLevel) {
                case 1:
                    MAX_DEPTH = IA_easy_baby.getMaxDepth();
                    break;
                case 2:
                    MAX_DEPTH = IA_intermediate.getMaxDepth();
                    break;
                case 3:
                    MAX_DEPTH = IA_hard.getMaxDepth();
                    break;
            }
        }

        ArrayList<Integer> values = new ArrayList<>(coupsPossibles.size());
        coupsPossibles.forEach(coupWesh -> {
            int rowD = coupWesh[0].getRow();
            int colD = coupWesh[0].getCol();
            int rowA = coupWesh[1].getRow();
            int colA = coupWesh[1].getCol();

            values.add(maxValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR, Integer.MIN_VALUE, Integer.MAX_VALUE));
        });
        return coupsPossibles.get(values.indexOf(max(values)));
    }

    public int minValue(RepPlateau plateau, Joueur j, int alpha, int beta) {
        currentDepth++;
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau)) return calculGain(plateau, new int[]{0,0,0,0}, j == Joueur.BLANC); // TODO : regler le dep_arr qui est null

        //on trouve le minimum des coups possibles
        int v = Integer.MAX_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[0];
            int colD = coup[1];
            int rowA = coup[2];
            int colA = coup[3];

            v = Integer.min(v, (maxValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR, alpha, beta)));
            if (v <= alpha) return v;
            beta = Integer.min(beta, v);
        }
        return v;
    }

    public int maxValue(RepPlateau plateau, Joueur j, int alpha, int beta) {
        currentDepth++;
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau)) return calculGain(plateau, new int[]{0,0,0,0}, j == Joueur.BLANC); // TODO : regler le dep_arr qui est null

        //on trouve le minimum des coups possibles
        int v = Integer.MIN_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[0];
            int colD = coup[1];
            int rowA = coup[2];
            int colA = coup[3];

            v = Integer.max(v, (minValue(plateau.deplacementPion(rowD, colD, rowA, colA, j == Joueur.BLANC), j == Joueur.NOIR ? Joueur.BLANC : Joueur.NOIR, alpha, beta)));
            if (v >= beta) return v;
            alpha = Integer.max(alpha, v);
        }
        return v;
    }

    public boolean terminalTest(List<int[]> coupsPossible, RepPlateau plateau) {
        //System.out.println("MAXDEPTH : " + MAX_DEPTH);
        return coupsPossible.isEmpty() || plateau.whiteWin() || plateau.blackWin() || currentDepth >= MAX_DEPTH;

    }

    public void setCurrentJoueur(Joueur currentJoueur) {
        this.currentJoueur = currentJoueur;
    }

    //    public static Object getLock() {
//        return lock;
//    }
//
//    public static void setHasAiAlreadyPlayed(boolean hasAiAlreadyPlayed) {
//        IA.hasAiAlreadyPlayed = hasAiAlreadyPlayed;
//    }
    public void calculerEtAfficherCoups() {
        CoupsPossibles coupsPossiblesClass = new CoupsPossibles();
        List<ColonneDeJeu[]> coupsPossibles = coupsPossiblesClass.calculCoupsPossibles(jeu.getCurrentJoueur(), jeu.getResteDes());

        System.out.println("Coups possibles générés : " + coupsPossibles.size());
        for (ColonneDeJeu[] coup : coupsPossibles) {
            System.out.printf("De (%d,%d) -> À (%d,%d)%n",
                    coup[0].getRow(), coup[0].getCol(),
                    coup[1].getRow(), coup[1].getCol()
            );
        }
    }

}

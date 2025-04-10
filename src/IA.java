import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.min;
import static java.lang.Thread.sleep;
import static java.util.Collections.max;

public abstract class IA extends JoueurClass{

    //private static final Object lock = new Object();
    private int nbOfMoves = 0;
    private List<ColonneDeJeu[]> couts = new ArrayList<>();

    public IA(){
        new Thread(() -> {
            while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
                            ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {

                            Platform.runLater(() -> {
                                try {
                                    makeAmove();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
            }
        }).start();
    };

    public void makeAmove() throws InterruptedException {

        int nbCoutsPossible = 0;
        if(nbOfMoves == 0){
            Main.JEU.lancerDes(); //un move a chaque fois ... TODO : les deux !

            Main.JEU.setDesLances(true);

            ArrayList<Integer> nouvelleListe = new ArrayList<>();
            nouvelleListe.add(Jeu.valeurDes()[0]);
            nouvelleListe.add(Jeu.valeurDes()[1]);
            if (Jeu.valeurDes()[0] == Jeu.valeurDes()[1]) {
                nouvelleListe.add(Jeu.valeurDes()[0]);
                nouvelleListe.add(Jeu.valeurDes()[1]);
            }

            nbCoutsPossible = nouvelleListe.size();
            Main.JEU.setResteDes(nouvelleListe);


        }

        couts = new CoupsPossibles().calculCoupsPossibles(Main.JEU.getCurrentJoueur(), Main.JEU.getResteDes());

        if (!couts.isEmpty() && nbOfMoves <= nbCoutsPossible) {

            ColonneDeJeu[] bestMove = getBestMove(couts);

            Main.JEU.setCol1(bestMove[0]);
            Main.JEU.setCol2(bestMove[1]);


//            if (Main.JEU.getCol1().getRow() == Main.JEU.getCol2().getRow()) {
//                Main.JEU.coutDuMouv = Math.abs(Main.JEU.getCol1().getCol() - Main.JEU.getCol2().getCol());
//            } else {
//                Main.JEU.coutDuMouv = Main.JEU.getCol1().getCol() + Main.JEU.getCol2().getCol() + 1;
//            }
//
//            Main.JEU.getCol1().updateColonne(Main.JEU.getCurrentJoueur(), Main.JEU.getCol2(), 1);

            Main.JEU.bougerPion();
            nbOfMoves++;

            Main.JEU.setCol1(null);
            Main.JEU.setCol2(null);

        }
        else {
            Main.JEU.setCurrentJoueur(Main.JEU.getCurrentJoueur() == Main.JEU.getJ1()?Main.JEU.getJ2():Main.JEU.getJ1());
            nbOfMoves = 0;
            Main.JEU.setDesLances(false);
        }
    }

    abstract ColonneDeJeu[] getBestMove(List<ColonneDeJeu[]> coutsPossible);

    public abstract int calculGain(RepPlateau plateau, int[] dep_arr, boolean isWhite);

    public ColonneDeJeu[] minMaxdecision(List<ColonneDeJeu[]> coupsPossibles, RepPlateau plateau, Joueur j){
        ArrayList<Integer> values = new ArrayList<>(coupsPossibles.size());
        coupsPossibles.forEach(coupWesh -> {
            int rowD = coupWesh[0].getRow() ;
            int colD = coupWesh[0].getCol() ;
            int rowA = coupWesh[1].getRow() ;
            int colA = coupWesh[1].getCol() ;

            values.add(minValue(plateau.deplacementPion(rowD, colD,rowA,colA,j == Joueur.BLANC),j == Joueur.NOIR?Joueur.BLANC:Joueur.NOIR));
        });
        return coupsPossibles.get(values.indexOf(max(values))); // on retourne le coup possible correspondant au max des valeurs que "MIN" renvoi"
    }

    public int minValue(RepPlateau plateau, Joueur j){
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau))  return calculGain(plateau,null, j == Joueur.BLANC);

        //on trouve le minimum des coups possibles
        int v = Integer.MAX_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[1];
            int colD = coup[2];
            int rowA = coup[3];
            int colA = coup[4];

            v = min(v,(maxValue(plateau.deplacementPion(rowD, colD,rowA,colA,j == Joueur.BLANC),j == Joueur.NOIR?Joueur.BLANC:Joueur.NOIR)));
        }
        return v;
    }

    public int maxValue(RepPlateau plateau, Joueur j){
        //calcul de tous les coups possibles
        List<int[]> coupsPossibles = new ArrayList<>();
        PaireDeDes.ALL_DES_POSSIBLES.forEach(des -> coupsPossibles.addAll(new CoupsPossibles().coupsPossibleRepPlateau(j, plateau, des)));

        //on vérifie si on est dans un etat terminal
        if (terminalTest(coupsPossibles, plateau))  return 0; //TODO :calcul de la valeur de l'état terminal

        //on trouve le minimum des coups possibles
        int v = Integer.MIN_VALUE;
        for (int[] coup : coupsPossibles) {
            int rowD = coup[1];
            int colD = coup[2];
            int rowA = coup[3];
            int colA = coup[4];

            v = Integer.max(v,(minValue(plateau.deplacementPion(rowD, colD,rowA,colA,j == Joueur.BLANC),j == Joueur.NOIR?Joueur.BLANC:Joueur.NOIR)));
        }
        return v;
    }

    public boolean terminalTest(List<int[]> coupsPossible, RepPlateau plateau){
        if (coupsPossible.isEmpty() || plateau.whiteWin() || plateau.blackWin()){
            return true;
        }
        return false;

    }


//    public static Object getLock() {
//        return lock;
//    }
//
//    public static void setHasAiAlreadyPlayed(boolean hasAiAlreadyPlayed) {
//        IA.hasAiAlreadyPlayed = hasAiAlreadyPlayed;
//    }

}

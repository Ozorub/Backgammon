import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.min;
import static java.lang.Thread.sleep;
import static java.util.Collections.max;

public abstract class IA extends JoueurClass{

    //private List<ColonneDeJeu[]> coups = new ArrayList<>();
    private final CoupsPossibles coupsPossiblesClass = new CoupsPossibles();

    public IA() {


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
                    || Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
                try {
                    makeAmove();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

//        new Thread(()->{
//            while(true){
//                try {
//                    sleep(1000);
//                    if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
//                            ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
//                        makeAmove();
//                    }
//
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();

//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(1), event -> {
//                    if (Main.JEU.getCurrentJoueur() == Joueur.BLANC && Main.joueur_blanc instanceof IA
//                            ||Main.JEU.getCurrentJoueur() == Joueur.NOIR && Main.joueur_noir instanceof IA) {
//                        try {
//                            makeAmove();
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                })
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();

    }

    public void makeAmove() throws InterruptedException {
        if (!Main.JEU.isDesLances()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                Main.JEU.getPlateau().getLancer().fire();
                latch.countDown();
            });
            //latch.await();
        }

        System.out.println("YEAHHHHqqqqqqq");
        List<ColonneDeJeu[]> coupsPossibles = coupsPossiblesClass.calculCoupsPossibles(Main.JEU.getCurrentJoueur(),Main.JEU.getResteDes());

        ColonneDeJeu[] bestMove = getBestMove(coupsPossibles);

        System.out.printf("Best move : %s\n",bestMove[0].toString());
        MouseEvent clickEvent = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, // position
                MouseButton.PRIMARY,
                1,          // nombre de clics
                false, false, false, false,
                true, false, false, true,
                false, false,
                null
        );

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            bestMove[0].fireEvent(clickEvent);
            bestMove[1].fireEvent(clickEvent);
            latch.countDown();
        });

        latch.await();
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

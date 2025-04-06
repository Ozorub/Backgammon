import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public abstract class IA extends JoueurClass{
    CoupsPossibles cp;

    public IA(){
        new Thread(() -> {
            while (true) {
                try {
                    sleep(1000);
                    cp = new CoupsPossibles(); // TODO je ne sais pas trop s'il faut mettre ça la
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

                } catch (InterruptedException e) {
                    System.out.println("oh ohhhhh");
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    };

    public void makeAmove() throws InterruptedException {

        Main.JEU.lancerDes(); //un move a chaque fois ... TODO : les deux !

        ArrayList<Integer> nouvelleListe = new ArrayList<>();
        nouvelleListe.add(Jeu.valeurDes()[0]);
        nouvelleListe.add(Jeu.valeurDes()[1]);
        if (Jeu.valeurDes()[0] == Jeu.valeurDes()[1]) {
            nouvelleListe.add(Jeu.valeurDes()[0]);
            nouvelleListe.add(Jeu.valeurDes()[1]);
        }

        List<ColonneDeJeu[]> couts = new CoupsPossibles().calculCoupsPossibles(Main.JEU.getCurrentJoueur(), nouvelleListe);

        if (!couts.isEmpty()) {

            Main.JEU.setDesLances(true);
            Main.JEU.setResteDes(nouvelleListe);

            ColonneDeJeu[] bestMove = getBestMove(couts);

            Main.JEU.setCol1(bestMove[0]);
            Main.JEU.setCol2(bestMove[1]);

            Main.JEU.bougerPion();

            Main.JEU.setCol1(null);
            Main.JEU.setCol2(null);

        }
        else {
            //TODO : retourner au joueur suivant
        }
    }

    abstract ColonneDeJeu[] getBestMove(List<ColonneDeJeu[]> coutsPossible);

    public abstract int calculGain(RepPlateau plateau, ColonneDeJeu[] dep_arr, boolean isWhite);


}

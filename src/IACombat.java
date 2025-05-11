import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IACombat {

    public static void main(String[] args){

        int[] easyVSinter = {0,0}; // nb of times easy won, nb of times inter won
        int[] easyVShard = {0,0}; // nb of times easy won, nb of times hard won
        int[] interVShard = {0,0}; // nb of times inter won, nb of times hard won

        for (int rounds = 0; rounds < 50; rounds++) { //50 rounds où chaque IA affronte toutes les autres IA
            for (int i = 0; i < 3; i++) {
                for (int j = i+1; j < 3; j++) {
                    System.out.println("Combat entre IA de niveau " + i + " et IA de niveau " + j);
                    boolean result = combat(i, j);
                    if (result) {
                        if (i == 0 && j == 1) {
                            easyVSinter[0]++;
                        } else if (i == 0 && j == 2) {
                            easyVShard[0]++;
                        } else if (i == 1 && j == 2) {
                            interVShard[0]++;
                        }
                    } else {
                        if (i == 0 && j == 1) {
                            easyVSinter[1]++;
                        } else if (i == 0 && j == 2) {
                            easyVShard[1]++;
                        } else if (i == 1 && j == 2) {
                            interVShard[1]++;
                        }
                    }
                    System.out.println("Résultat du combat: " + result);
                }
            }
        }

        System.out.println("\n \n \n --------------------------- \n \n \n");
        System.out.println("Résultats des combats:");
        System.out.println("IA facile vs IA intermédiaire: " + easyVSinter[0] + " victoires pour l'IA facile, " + easyVSinter[1] + " victoires pour l'IA intermédiaire");
        System.out.println("IA facile vs IA difficile: " + easyVShard[0] + " victoires pour l'IA facile, " + easyVShard[1] + " victoires pour l'IA difficile");
        System.out.println("IA intermédiaire vs IA difficile: " + interVShard[0] + " victoires pour l'IA intermédiaire, " + interVShard[1] + " victoires pour l'IA difficile");
        System.out.println("Fin des combats.");
    }


    private static boolean combat(int IALevel1, int IALevel2){
        IA ia1 = null;
        IA ia2 = null;
        switch (IALevel1) {
            case 0 -> ia1 = new IA_easy_baby();
            case 1 -> ia1 = new IA_intermediate();
            case 2 -> ia1 = new IA_hard();
            default -> throw new IllegalArgumentException("Invalid level: " + IALevel1);
        }

        switch (IALevel2) {
            case 0 -> ia2 = new IA_easy_baby();
            case 1 -> ia2 = new IA_intermediate();
            case 2 -> ia2 = new IA_hard();
            default -> throw new IllegalArgumentException("Invalid level: " + IALevel2);
        }

        return simulateCombat(ia1, ia2); // Placeholder for actual combat result
    }


    private static boolean simulateCombat(IA ia1, IA ia2) {
        RepPlateau plateau = new RepPlateau();

        plateau.getCell(0,0).setNbPionsBlancs(5);
        plateau.getCell(0,4).setNbPionsNoirs(3);
        plateau.getCell(0,6).setNbPionsNoirs(5);
        plateau.getCell(0,11).setNbPionsBlancs(2);
        plateau.getCell(1,0).setNbPionsNoirs(5);
        plateau.getCell(1,4).setNbPionsBlancs(3);
        plateau.getCell(1,6).setNbPionsBlancs(5);
        plateau.getCell(1,11).setNbPionsNoirs(2);

        Joueur j = Joueur.BLANC;

        while (!(plateau.blackWin() || plateau.whiteWin())) {

            int[] des = new PaireDeDes().lancerLesDes();
            List<int[]> coupsPossibles = new CoupsPossibles(true).coupsPossibleRepPlateau(j, plateau, des);
            while (!coupsPossibles.isEmpty()) {
                int[] bestMove = j==Joueur.BLANC? ia1.alphaBetaDecisionRepPlateau(coupsPossibles, plateau, j) : ia2.alphaBetaDecisionRepPlateau(coupsPossibles, plateau, j);
                plateau.deplacementPion(bestMove[0], bestMove[1], bestMove[2], bestMove[3], j == Joueur.BLANC);

                int coutDuMouv = 0;
                if (bestMove[0] == bestMove[2]) { // if row are identical
                    coutDuMouv = Math.abs(bestMove[1] - bestMove[3]);
                } else if (bestMove[1] <= 15 || bestMove[3] <= 15) { // if rows are not identical, but not prison or endgame
                    coutDuMouv = bestMove[1] + bestMove[3] + 1;
                }
                else{ //prison or endgame
                    coutDuMouv = 6 - bestMove[1];
                }
                List<Integer> desTemp = new ArrayList<>();
                for (int i = 0; i < des.length; i++) {
                    if (des[i] != coutDuMouv) {
                        desTemp.add(des[i]);
                    }
                }
                des = desTemp.stream().mapToInt(i -> i).toArray();
                coupsPossibles = new CoupsPossibles(true).coupsPossibleRepPlateau(j, plateau, des);
            }
            j =  (j == Joueur.BLANC) ? Joueur.NOIR : Joueur.BLANC;
        }
        return plateau.whiteWin();
    }

}



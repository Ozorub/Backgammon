import java.sql.SQLOutput;

public class IACombat {

    public static void main(String[] args){

        int[] easyVSinter = {0,0}; // nb of times easy won, nb of times inter won
        int[] easyVShard = {0,0}; // nb of times easy won, nb of times hard won
        int[] interVShard = {0,0}; // nb of times inter won, nb of times hard won

        for (int rounds = 0; rounds < 50; rounds++) { //50 rounds où chaque IA affronte toutes les autres IA
            for (int i = 0; i < 3; i++) {
                for (int j = i+1; j < 3; j++) {
                    System.out.println("Combat entre IA de niveau " + i + " et IA de niveau " + j);
                    int result = combat(i, j);
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


    private static int combat(int IALevel1, int IALevel2){
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

        // Simulate the combat between the two AI players
        // Return the result of the combat

        return 0; // Placeholder for actual combat result
    }

}



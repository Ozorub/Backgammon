import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe permettant de simuler une paire de dés
 * @version 1.0
 * @author Raphaël Charozé
 */
public class PaireDeDes {


    public final static List<int[]> ALL_DES_POSSIBLES = new ArrayList<>(Arrays.asList(
            new int[]{1, 1, 1, 1}, new int[]{1, 2}, new int[]{1, 3}, new int[]{1, 4}, new int[]{1, 5}, new int[]{1, 6},
            new int[]{2, 2, 2, 2}, new int[]{2, 3}, new int[]{2, 4}, new int[]{2, 5}, new int[]{2, 6},
            new int[]{3, 3, 3, 3}, new int[]{3, 4}, new int[]{3, 5}, new int[]{3, 6},
            new int[]{4, 4, 4, 4}, new int[]{4, 5}, new int[]{4, 6},
            new int[]{5, 5, 5, 5}, new int[]{5, 6},
            new int[]{6, 6, 6, 6}

    ));

    /**
     * Classe interne permettant de simuler un dé
     * @version 1.0
     * @author Raphaël Charozé
     */
    private class De{
        private int valeur;

        private De(){
            valeur = (int)(Math.random()*6)+1;
        }

        private int lancerLeDe(){
            valeur = (int)(Math.random()*6)+1;
            return valeur;
        }
    }

    private final De de1 = new De();
    private final De de2 = new De();

    /**
     * Retourne la valeur des deux dés, sans lancer, sous forme de tableau
     * @return int[]
     */
    public int[] valeursDesDes(){
        return new int[]{de1.valeur, de2.valeur};
    }

    /**
     * Retourne la valeur des deux dés, avec lancer, sous forme de tableau
     * @return int[]
     */
    public int[] lancerLesDes(){
        return new int[]{de1.lancerLeDe(), de2.lancerLeDe()};
    }

}

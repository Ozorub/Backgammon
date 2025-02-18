/**
 * Classe permettant de simuler une paire de dés
 * @version 1.0
 * @author Raphaël Charozé
 */
public class PaireDeDes {

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

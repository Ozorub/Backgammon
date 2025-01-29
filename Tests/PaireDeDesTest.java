import org.junit.Test;

import static org.junit.Assert.assertTrue;

//class pour tester la classe PaireDeDes
public class PaireDeDesTest {
    //test pour vérifier que les valeurs des dés sont bien comprises entre 1 et 6
    @Test
    public void testValeursDesDes(){
        PaireDeDes paireDeDes = new PaireDeDes();
        int[] valeurs = paireDeDes.valeursDesDes();
        assertTrue(valeurs[0] >= 1 && valeurs[0] <= 6);
        assertTrue(valeurs[1] >= 1 && valeurs[1] <= 6);
    }

    //test pour vérifier que les valeurs des dés sont bien comprises entre 1 et 6
    @Test
    public void testLancerLesDes(){
        PaireDeDes paireDeDes = new PaireDeDes();
        int[] valeurs = paireDeDes.lancerLesDes();
        assertTrue(valeurs[0] >= 1 && valeurs[0] <= 6);
        assertTrue(valeurs[1] >= 1 && valeurs[1] <= 6);
    }
}

import javax.swing.*;
import java.util.List;

public class IA_hard extends IA{

    public static int getMaxDepth(){
        return 10;
    }

    public IA_hard() {
        super();
    }

    @Override
    public int calculGain(RepPlateau plateau, int[] dep_arr, boolean isWhite) {
        int gain = 0;
        int colArr = dep_arr[3];
        int rowArr = dep_arr[2];
        int rowDep = dep_arr[0];
        int colDep = dep_arr[1];

        if (plateau.getCell(rowArr, colArr).getNbPionsBlancs() == 0 && plateau.getCell(rowArr, colArr).getNbPionsNoirs() == 0) {
            gain += -1; // Pas terrible de d'avoir une colonne avec un seul pion
        }
        if (isWhite) {
            if (plateau.getCell(rowArr, colArr).getNbPionsNoirs() == 1) {
                gain += 2; // mettre en prison l'adversaire, c'est super
            }
            if (plateau.getCell(rowArr, colArr).getNbPionsBlancs() == 1) {
                gain += 1; // Ajouter un pion à une colonne avec un seul pion de sa couleur est bien
            }
            if(rowDep == 0){
                gain += 1;
            }
            if (rowDep == 0 && colDep > 5){
                gain += 1;
            }

        } else {
            if (plateau.getCell(rowArr, colArr).getNbPionsBlancs() == 1) {
                gain += 2; // mettre en prison l'adversaire, c'est super
            }
            if (plateau.getCell(rowArr, colArr).getNbPionsNoirs() == 1) {
                gain += 1; // Ajouter un pion à une colonne avec un seul pion de sa couleur est bien
            }
            if(rowDep == 1){
                gain += 1;
            }
            if (rowDep == 1 && colDep > 5){
                gain += 1;
            }
        }
        return gain;
    }
}

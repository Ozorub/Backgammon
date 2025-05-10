import javafx.beans.property.ReadOnlyBooleanWrapper;

import java.util.List;

public class IA_easy_baby extends IA{

    public static int getMaxDepth(){
        return 1;
    }


    public IA_easy_baby(){
        super();
    }

    @Override
    public int calculGain(RepPlateau plateau, int[] dep_arr, boolean isWhite) {
        int gain = 0;
        int colArr = dep_arr[3];
        int rowArr = dep_arr[2];
        if (isWhite) {
            if (plateau.getCell(rowArr, colArr).getNbPionsNoirs() == 1) {
                gain += 2; // mettre en prison l'adversaire, c'est super
            }
        } else {
            if (plateau.getCell(rowArr, colArr).getNbPionsBlancs() == 1) {
                gain += 2; // mettre en prison l'adversaire, c'est super
            }
        }
        return gain;
    }




}

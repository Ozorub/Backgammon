import javax.swing.*;
import java.util.List;

public class IA_hard extends IA{

    public IA_hard() {
        super();
    }

    @Override
    ColonneDeJeu[] getBestMove(List<ColonneDeJeu[]> coutsPossible) {
        return new ColonneDeJeu[0];
    }

    @Override
    public int calculGain(RepPlateau plateau, ColonneDeJeu[] dep_arr, boolean isWhite) {
        return 0;
    }
}

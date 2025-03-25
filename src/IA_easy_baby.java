import java.util.List;

public class IA_easy_baby extends IA{

    public IA_easy_baby(){
        super();
    }

    @Override
    ColonneDeJeu[] getBestMove(List<ColonneDeJeu[]> coutsPossible) {
        return coutsPossible.getFirst();
    }
}

import java.util.ArrayList;
import java.util.List;

public class CoutsPossibles {

    private final ArrayList<ColonneDeJeu> allCols;

    public CoutsPossibles() {
        allCols = Main.JEU.getPlateau().getAllColonnesDeJeu();
    }

    /**
     * Calcul les couts possibles pour un joueur
     * @param joueur
     * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
     */
    public List<int[]> calculCoutsPossibles(Joueur joueur){

        Jeu jeu = Main.JEU;

        ArrayList<int[]> coutsPossibles = new ArrayList<>();

        //gestion pion prison
        if (jeu.getPlateau().getPrisonNoir().getNbPionsNoir() != 0 && joueur == Joueur.NOIR){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonNoir(), joueur));
        }
        if (jeu.getPlateau().getPrisonBlanc().getNbPionsBlanc() != 0 && joueur == Joueur.BLANC){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonBlanc(), joueur));
        }

        //cas des endgames
        if (jeu.isEndGameBlack && joueur == Joueur.NOIR){
            coutsPossibles.addAll(coutsPossibles(null, joueur));
        }

        else if (jeu.isEndGameWhite && joueur == Joueur.BLANC){
            coutsPossibles.addAll(coutsPossibles(null, joueur));
        }

        //autrement
        else{
            allCols.forEach(col -> coutsPossibles.addAll(coutsPossibles(col, joueur)));
        }


        return coutsPossibles;
    }

    /**
     * Calcul les couts possibles pour une colonne
     *
     * @param col
     * @param joueur
     * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
     */
    private List<int[]> coutsPossibles(ColonneDeJeu col, Joueur joueur){

        ArrayList<int[]> coutsPossibles = new ArrayList<>();

        if (col.getCol() == 100){ //prison TODO

        }

        else if (col == null){ //endgame TODO

        }

       else{ //autre : cas général
           Main.JEU.getResteDes().forEach(i ->{ //pour toutes les valeurs de dés restantes
               allCols.forEach(col2 ->{ //pour toutes les colonnes
                   if (col2.getCol() != col.getCol()){ //si la colonne de départ est différente de la colonne d'arrivée
                       if (col2.getCol() == col.getCol() + i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé NOT DONE TODO
                           if (col2.getNbPionsNoir() <= 1 && joueur == Joueur.BLANC || col2.getNbPionsBlanc() <= 1 && joueur == Joueur.NOIR){ //si la colonne d'arrivée contient 1 pion ou moins
                               coutsPossibles.add(new int[]{col.getCol(), col2.getCol()}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                           }
                       }
                   }
               });
            });
        }

        return coutsPossibles;
    }

}

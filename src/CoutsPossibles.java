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
    public List<ColonneDeJeu[]> calculCoutsPossibles(Joueur joueur){

        Jeu jeu = Main.JEU;

        ArrayList<ColonneDeJeu[]> coutsPossibles = new ArrayList<>();

        //gestion pion prison
        if (jeu.getPlateau().getPrisonNoir().getNbPionsNoir() != 0 && joueur == Joueur.NOIR){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonNoir(), joueur));
        }
        else if (jeu.getPlateau().getPrisonBlanc().getNbPionsBlanc() != 0 && joueur == Joueur.BLANC){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonBlanc(), joueur));
        }

        //cas des endgames
        else if (jeu.isEndGameBlack && joueur == Joueur.NOIR){
            coutsPossibles.addAll(coutsPossibles(null, joueur));
        }

        else if (jeu.isEndGameWhite && joueur == Joueur.BLANC){
            coutsPossibles.addAll(coutsPossibles(null, joueur));
        }

        //autrement
        else{
            allCols.forEach(col -> {
                if (col.getNbPionsNoir() > 0 && joueur == Joueur.NOIR || col.getNbPionsBlanc() > 0 && joueur == Joueur.BLANC){
                    coutsPossibles.addAll(coutsPossibles(col, joueur));
                }
            });
        }

        coutsPossibles.forEach(coutsPossible ->
            System.out.println("Cout possible : " + coutsPossible[0].toString() + " -> " + coutsPossible[1].toString())
        );
        if (coutsPossibles.isEmpty()){
            System.out.println("Aucun cout possible");
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
    private List<ColonneDeJeu[]> coutsPossibles(ColonneDeJeu col, Joueur joueur){

        /*
        TODO :
        - gérer les cas particuliers (endgame)
         */

        ArrayList<ColonneDeJeu[]> coutsPossibles = new ArrayList<>();

        if (col.getCol() == 100){ //prison
            for (int i = 12; i < 24; i++) {
                if (joueur == Joueur.BLANC){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 0 && colonne.getNbPionsNoir() <= 1 && Main.JEU.getResteDes().contains(12 - colonne.getCol())){
                        coutsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                    }
                }
                else if(joueur == Joueur.NOIR){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 1 && colonne.getNbPionsBlanc() <= 1 && Main.JEU.getResteDes().contains(12 - colonne.getCol())){
                        coutsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                    }
                }
            }
            return coutsPossibles;
        }

        else if (col == null){ //endgame TODO
            return coutsPossibles;
        }

       else{ //autre : cas général
           Main.JEU.getResteDes().forEach(i ->{ //pour toutes les valeurs de dés restantes
               allCols.forEach(col2 ->{ //pour toutes les colonnes
                   if ((col2.getCol() != col.getCol() || col.getRow() != col2.getRow())
                           && ((col2.getNbPionsNoir() <= 1 && joueur == Joueur.BLANC) || (col2.getNbPionsBlanc() <= 1 && joueur == Joueur.NOIR))){ //si la colonne de départ est différente de la colonne d'arrivée

                       if (joueur == Joueur.BLANC){ //si le joueur est blanc
                           if (col.getRow() == col2.getRow()) {
                               if (col.getRow() == 1) {
                                   if (col2.getCol() == col.getCol() + i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                           coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                                   }
                               }
                               else { //col.getRow() == 0
                                   if (col2.getCol() == col.getCol() - i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                       coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                                   }
                               }
                           } else {
                               if (col2.getRow() == 1 && col.getRow() == 0 && i == col.getCol() + col2.getCol() + 1 ){
                                   coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                               }
                           }
                       }else if (joueur == Joueur.NOIR){ //si le joueur est noir
                           if (col.getRow() == col2.getRow()) {
                               if (col.getRow() == 1) {
                                   if (col2.getCol() == col.getCol() - i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                       coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                                   }
                               }
                               else { //col.getRow() == 0
                                   if (col2.getCol() == col.getCol() + i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                       coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                                   }
                               }
                           } else {
                               if (col2.getRow() == 0 && col.getRow() == 1 && i == col.getCol() + col2.getCol() + 1 ){
                                   coutsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                               }
                           }
                       }

                   }
               });
            });
        }

        return coutsPossibles;
    }

}

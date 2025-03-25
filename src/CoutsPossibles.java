import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoutsPossibles {

    private final ArrayList<ColonneDeJeu> allCols;
    private static final ColonneDeJeu END_COL = new ColonneDeJeu(200,200);

    public CoutsPossibles() {
        allCols = Main.JEU.getPlateau().getAllColonnesDeJeu();
    }



    /**
     * Calcul les couts possibles pour un joueur
     * @param joueur le joueur pour lequel on veut calculer les couts possibles (le joueur courant).
     * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
     */
    public List<ColonneDeJeu[]> calculCoutsPossibles(Joueur joueur, List<Integer> desAJouer){

        Jeu jeu = Main.JEU;

        ArrayList<ColonneDeJeu[]> coutsPossibles = new ArrayList<>();

        //gestion pion prison
        if (jeu.getPlateau().getPrisonNoir().getNbPionsNoir() != 0 && joueur == Joueur.NOIR){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonNoir(), joueur,desAJouer));
        }
        else if (jeu.getPlateau().getPrisonBlanc().getNbPionsBlanc() != 0 && joueur == Joueur.BLANC){
            coutsPossibles.addAll(coutsPossibles(jeu.getPlateau().getPrisonBlanc(), joueur,desAJouer));
        }

        //cas des endgames
        else if (Jeu.isEndGameBlack && joueur == Joueur.NOIR){
            desAJouer.forEach(de -> {
                ColonneDeJeu colonne = allCols.get(24 - 2*de);
                if (colonne.getNbPionsNoir() > 0){
                    coutsPossibles.add(new ColonneDeJeu[]{colonne, END_COL});
                }
                else{
                    ColonneDeJeu melenchon = allCols.get(22); // le plus a gauche possible
                    for (int i = 12; i < 24; i += 2){
                        if (allCols.get(i).getNbPionsNoir() > 0){
                            melenchon = allCols.get(i);
                            break;
                        }
                    }
                    if (melenchon.getCol() < 12 - de){
                        coutsPossibles.addAll(coutsPossibles(melenchon, joueur, Collections.singletonList(de)));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coutsPossibles.add(new ColonneDeJeu[]{melenchon, END_COL});
                    }
                }
            });
        }

        else if (Jeu.isEndGameWhite && joueur == Joueur.BLANC){
            desAJouer.forEach(de -> {
                ColonneDeJeu colonne = allCols.get(25 - 2*de);
                if (colonne.getNbPionsBlanc() > 0){
                    coutsPossibles.add(new ColonneDeJeu[]{colonne, END_COL});
                }
                else{
                    ColonneDeJeu melenchon = allCols.get(23); // le plus a gauche possible
                    for (int i = 13; i < 24; i += 2){
                        if (allCols.get(i).getNbPionsBlanc() > 0){
                            melenchon = allCols.get(i);
                            break;
                        }
                    }
                    if (melenchon.getCol() < 12 - de){
                        coutsPossibles.addAll(coutsPossibles(melenchon, joueur, Collections.singletonList(de)));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coutsPossibles.add(new ColonneDeJeu[]{melenchon, END_COL});
                    }
                }
            });
        }

        //autrement
        else{
            allCols.forEach(col -> {
                if (col.getNbPionsNoir() > 0 && joueur == Joueur.NOIR || col.getNbPionsBlanc() > 0 && joueur == Joueur.BLANC){
                    coutsPossibles.addAll(coutsPossibles(col, joueur,desAJouer));
                }
            });
        }

        return coutsPossibles;
    }

    /**
     * Calcul les couts possibles pour une colonne
     *
     * @param col colonne à étudier
     * @param joueur joueur
     * @param desAJouer liste des dés restants
     * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
     */
    private List<ColonneDeJeu[]> coutsPossibles(ColonneDeJeu col, Joueur joueur, List<Integer> desAJouer){

        ArrayList<ColonneDeJeu[]> coutsPossibles = new ArrayList<>();

        if (col.getCol() == 100){ //prison
            for (int i = 12; i < 24; i++) {
                if (joueur == Joueur.BLANC){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 0 && colonne.getNbPionsNoir() <= 1 && desAJouer.contains(12 - colonne.getCol())){
                        coutsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                    }
                }
                else if(joueur == Joueur.NOIR){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 1 && colonne.getNbPionsBlanc() <= 1 && desAJouer.contains(12 - colonne.getCol())){
                        coutsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le cout {colonne de départ, colonne d'arrivée}
                    }
                }
            }
            return coutsPossibles;
        }
        

       else{ //autre : cas général
           desAJouer.forEach(i ->{ //pour toutes les valeurs de dés restantes
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
                       }else { //si le joueur est noir
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoupsPossibles {

    private final ArrayList<ColonneDeJeu> allCols;
    private static final ColonneDeJeu END_COL = new ColonneDeJeu(200,200);

    public CoupsPossibles() {
        allCols = Main.JEU.getPlateau().getAllColonnesDeJeu();
    }



    /**
     * Calcul les coups possibles pour un joueur
                            * @param joueur le joueur pour lequel on veut calculer les coups possibles (le joueur courant).
                            * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
                            */
    public List<ColonneDeJeu[]> calculCoupsPossibles(Joueur joueur, List<Integer> desAJouer){

        Jeu jeu = Main.JEU;

        ArrayList<ColonneDeJeu[]> coupsPossibles = new ArrayList<>();

        //gestion pion prison
        if (jeu.getPlateau().getPrisonNoir().getNbPionsNoir() != 0 && joueur == Joueur.NOIR){
            coupsPossibles.addAll(coupsPossibles(jeu.getPlateau().getPrisonNoir(), joueur,desAJouer));
        }
        else if (jeu.getPlateau().getPrisonBlanc().getNbPionsBlanc() != 0 && joueur == Joueur.BLANC){
            coupsPossibles.addAll(coupsPossibles(jeu.getPlateau().getPrisonBlanc(), joueur,desAJouer));
        }

        //cas des endgames
        else if (Jeu.isEndGameBlack && joueur == Joueur.NOIR){
            desAJouer.forEach(de -> {
                ColonneDeJeu colonne = allCols.get(24 - 2*de);
                if (colonne.getNbPionsNoir() > 0){
                    coupsPossibles.add(new ColonneDeJeu[]{colonne, END_COL});
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
                        coupsPossibles.addAll(coupsPossibles(melenchon, joueur, Collections.singletonList(de)));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coupsPossibles.add(new ColonneDeJeu[]{melenchon, END_COL});
                    }
                }
            });
        }

        else if (Jeu.isEndGameWhite && joueur == Joueur.BLANC){
            desAJouer.forEach(de -> {
                ColonneDeJeu colonne = allCols.get(25 - 2*de);
                if (colonne.getNbPionsBlanc() > 0){
                    coupsPossibles.add(new ColonneDeJeu[]{colonne, END_COL});
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
                        coupsPossibles.addAll(coupsPossibles(melenchon, joueur, Collections.singletonList(de)));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coupsPossibles.add(new ColonneDeJeu[]{melenchon, END_COL});
                    }
                }
            });
        }

        //autrement
        else{
            allCols.forEach(col -> {
                if (col.getNbPionsNoir() > 0 && joueur == Joueur.NOIR || col.getNbPionsBlanc() > 0 && joueur == Joueur.BLANC){
                    coupsPossibles.addAll(coupsPossibles(col, joueur,desAJouer));
                }
            });
        }

        return coupsPossibles;
    }

    /**
     * Calcul les coups possibles pour une colonne
     *
     * @param col colonne à étudier
     * @param joueur joueur
     * @param desAJouer liste des dés restants
     * @return une liste de tableaux d'entiers, chaque tableau contient 2 entiers, le premier est la colonne de départ, le second la colonne d'arrivée
     */
    private List<ColonneDeJeu[]> coupsPossibles(ColonneDeJeu col, Joueur joueur, List<Integer> desAJouer){

        ArrayList<ColonneDeJeu[]> coupsPossibles = new ArrayList<>();

        if (col.getCol() == 100){ //prison
            for (int i = 12; i < 24; i++) {
                if (joueur == Joueur.BLANC){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 0 && colonne.getNbPionsNoir() <= 1 && desAJouer.contains(12 - colonne.getCol())){
                        coupsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                    }
                }
                else if(joueur == Joueur.NOIR){
                    ColonneDeJeu colonne = allCols.get(i);
                    if(colonne.getRow() == 1 && colonne.getNbPionsBlanc() <= 1 && desAJouer.contains(12 - colonne.getCol())){
                        coupsPossibles.add(new ColonneDeJeu[]{col, colonne}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                    }
                }
            }
            return coupsPossibles;
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
                                        coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                                else { //col.getRow() == 0
                                    if (col2.getCol() == col.getCol() - i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                        coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                            } else {
                                if (col2.getRow() == 1 && col.getRow() == 0 && i == col.getCol() + col2.getCol() + 1 ){
                                    coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                }
                            }
                        }else { //si le joueur est noir
                            if (col.getRow() == col2.getRow()) {
                                if (col.getRow() == 1) {
                                    if (col2.getCol() == col.getCol() - i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                        coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                                else { //col.getRow() == 0
                                    if (col2.getCol() == col.getCol() + i ){ //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                        coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                            } else {
                                if (col2.getRow() == 0 && col.getRow() == 1 && i == col.getCol() + col2.getCol() + 1 ){
                                    coupsPossibles.add(new ColonneDeJeu[]{col, col2}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                }
                            }
                        }
                    }
                });
            });
        }

        return coupsPossibles;
    }

    public List<int[]> coupsPossibleRepPlateau(Joueur j, RepPlateau plateau, int[] des){

                ArrayList<int[]> coupsPossibles = new ArrayList<>();

                //gestion pion prison
                if (plateau.getPrisonNoirs().getNbPionsNoirs() != 0 && j == Joueur.NOIR){
                    coupsPossibles.addAll(coupsPossiblesColRepPlateau(plateau.getPrisonNoirs(),plateau,j,des));
                }
                else if (plateau.getPrisonBlancs().getNbPionsBlancs() != 0 && j == Joueur.BLANC){
                    coupsPossibles.addAll(coupsPossiblesColRepPlateau(plateau.getPrisonBlancs(),plateau, j,des));
                }

                //cas des endgames
                else if (plateau.isEndGameBlack() && j == Joueur.NOIR){
                    for (int de:des) {
                        RepPlateau.Cellule colonne = plateau.getCell(0,12 - de);
                        if (colonne.getNbPionsNoirs() > 0){
                            coupsPossibles.add(new int[]{colonne.getRow(),colonne.getCol(), 50, 50});
                }
                else{
                    RepPlateau.Cellule melenchon = plateau.getCell(0,6); // le plus a gauche possible
                    for (int i = 6; i < 12; i ++){
                        if ((melenchon = plateau.getCell(0,i)).getNbPionsNoirs() > 0){
                            break;
                        }
                    }
                    if (melenchon.getCol() < 12 - de){
                        coupsPossibles.addAll(coupsPossiblesColRepPlateau(melenchon,plateau, j, new int[]{de}));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coupsPossibles.add(new int[]{melenchon.getRow(),melenchon.getCol(), 50, 50});
                    }
                }
            }
        }

        else if (plateau.isEndGameWhite() && j == Joueur.BLANC){
            for (int de:des) {
                RepPlateau.Cellule colonne = plateau.getCell(1,12 - de);
                if (colonne.getNbPionsBlancs() > 0){
                    coupsPossibles.add(new int[]{colonne.getRow(),colonne.getCol(), 50, 50});
                }
                else{
                    RepPlateau.Cellule melenchon = plateau.getCell(1,6); // le plus a gauche possible
                    for (int i = 6; i < 12; i ++){
                        if ((melenchon = plateau.getCell(1,i)).getNbPionsBlancs() > 0){
                            break;
                        }
                    }
                    if (melenchon.getCol() < 12 - de){
                        coupsPossibles.addAll(coupsPossiblesColRepPlateau(melenchon,plateau, j, new int[]{de}));
                    }
                    else if (melenchon.getCol() > 12 - de){
                        coupsPossibles.add(new int[]{melenchon.getRow(),melenchon.getCol(), 50, 50});
                    }
                }
            }
        }

        //autrement
        else{
            for (int row = 0; row < 2; row++){
                for (int column = 0; column < 13; column++) {

                    RepPlateau.Cellule col = plateau.getCell(row, column);

                    if ((col.getNbPionsNoirs() > 0 && j == Joueur.NOIR) || (col.getNbPionsBlancs() > 0 && j == Joueur.BLANC)){
                        coupsPossibles.addAll(coupsPossiblesColRepPlateau(col,plateau,j,des));
                    }
                }
            }
        }

        return coupsPossibles;
    }

    private List<int[]> coupsPossiblesColRepPlateau(RepPlateau.Cellule col, RepPlateau plateau, Joueur joueur, int[] desAJouer){

        ArrayList<int[]> coupsPossibles = new ArrayList<>();

        if (col.equals(plateau.getPrisonBlancs()) || col.equals(plateau.getPrisonNoirs())){ //prison
            for (int j = 0; j < 2 ; j++) {
                for (int i = 6; i < 13; i++) {
                    if (joueur == Joueur.BLANC){
                        RepPlateau.Cellule colonne = plateau.getCell(j,i);
                        if(j == 0 && colonne.getNbPionsNoirs() <= 1 && Arrays.stream(desAJouer).anyMatch( de -> de == 12 - colonne.getCol())){
                            coupsPossibles.add(new int[]{col.getRow(), col.getCol(), j, i});
                        }
                    }
                    else if(joueur == Joueur.NOIR){
                        RepPlateau.Cellule colonne = plateau.getCell(j,i);
                        if(j == 0 && colonne.getNbPionsBlancs() <= 1 && Arrays.stream(desAJouer).anyMatch( de -> de == 12 - colonne.getCol())){
                            coupsPossibles.add(new int[]{col.getRow(), col.getCol(), j, i});
                        }
                    }
                }
            }
            return coupsPossibles;
        }


        else{ //autre : cas général
            for (int i : desAJouer) { //pour toutes les valeurs de dés restantes

                for (int row = 0; row < 2; row++){ //pour toutes les colonnes
                    for (int column = 0; column < 13; column++) {

                        RepPlateau.Cellule col2 = plateau.getCell(row, column);

                        if ((col2.getCol() != col.getCol() || col.getRow() != col2.getRow())
                                && ((col2.getNbPionsNoirs() <= 1 && joueur == Joueur.BLANC) || (col2.getNbPionsBlancs() <= 1 && joueur == Joueur.NOIR))) { //si la colonne de départ est différente de la colonne d'arrivée

                            if (joueur == Joueur.BLANC) { //si le joueur est blanc
                                if (col.getRow() == col2.getRow()) {
                                    if (col.getRow() == 1) {
                                        if (col2.getCol() == col.getCol() + i) { //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                            coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                        }
                                    } else { //col.getRow() == 0
                                        if (col2.getCol() == col.getCol() - i) { //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                            coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                        }
                                    }
                                } else {
                                    if (col2.getRow() == 1 && col.getRow() == 0 && i == col.getCol() + col2.getCol() + 1) {
                                        coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                            } else { //si le joueur est noir
                                if (col.getRow() == col2.getRow()) {
                                    if (col.getRow() == 1) {
                                        if (col2.getCol() == col.getCol() - i) { //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                            coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                        }
                                    } else { //col.getRow() == 0
                                        if (col2.getCol() == col.getCol() + i) { //si la colonne d'arrivée est la colonne de départ + la valeur du dé
                                            coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                        }
                                    }
                                } else {
                                    if (col2.getRow() == 0 && col.getRow() == 1 && i == col.getCol() + col2.getCol() + 1) {
                                        coupsPossibles.add(new int[]{col.getRow(),col.getCol(),col2.getRow(),col2.getCol()}); //on ajoute le coup {colonne de départ, colonne d'arrivée}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return coupsPossibles;
    }

}

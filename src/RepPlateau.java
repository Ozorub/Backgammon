import javafx.scene.control.Cell;

import java.util.List;

public class RepPlateau {
    private Cellule[][] plateau = new Cellule[BgPane.NBROW][BgPane.NBCOL];
    private Cellule prisonBlancs;
    private Cellule prisonNoirs;

    public static class Cellule{
        private int nbPionsBlancs;
        private int nbPionsNoirs;
        private int row = -1;
        private int col = -1;

        public Cellule(int nbPionsBlancs, int nbPionsNoirs){
            this.nbPionsBlancs = nbPionsBlancs;
            this.nbPionsNoirs = nbPionsNoirs;
        }

        public void setCell(boolean white, int nbPions){
            if (white) nbPionsBlancs = nbPions;
            else nbPionsNoirs = nbPions ;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }


        @Override
        public String toString(){

            return "nombre de pions dans cette colonne : Blancs " + nbPionsBlancs +  " Noirs " + nbPionsNoirs;
        }
        public int getNbPionsBlancs() {
            return nbPionsBlancs;
        }

        public void setNbPionsBlancs(int nbPionsBlancs) {
            this.nbPionsBlancs = nbPionsBlancs;
        }

        public int getNbPionsNoirs() {
            return nbPionsNoirs;
        }

        public void setNbPionsNoirs(int nbPionsNoirs) {
            this.nbPionsNoirs = nbPionsNoirs;
        }



    }

    public RepPlateau(Cellule[][] plateau, Cellule prisonBlancs, Cellule prisonNoirs){
        this.plateau = plateau;
        this.prisonBlancs = prisonBlancs;
        this.prisonNoirs = prisonNoirs;
        this.setCelluleRowAndCol();
    }

    public RepPlateau(){
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                Cellule cel = new Cellule(0,0);
                plateau[row][col] = cel;
            }
        }
        prisonBlancs = new Cellule(0,0);
        prisonNoirs = new Cellule(0,0);
        this.setCelluleRowAndCol();
    }

    public RepPlateau(BgPane bgPane){
        List<ColonneDeJeu> colDeJeu = bgPane.getAllColonnesDeJeu();
        for (int col = 0 ; col < BgPane.NBCOL ; col++){
            for (int row = 0 ; row < BgPane.NBROW;row++){
                Cellule cel = new Cellule(colDeJeu.get(col + row ).getNbPionsBlanc(),colDeJeu.get(col + row ).getNbPionsNoir());
                plateau[row][col] = cel;

                plateau[row][col].row = row;
                plateau[row][col].col = col;
            }
        }
        prisonBlancs = new Cellule(bgPane.getPrisonBlanc().getNbPionsBlanc(),bgPane.getPrisonBlanc().getNbPionsNoir());
        prisonNoirs = new Cellule(bgPane.getPrisonNoir().getNbPionsBlanc(),bgPane.getPrisonNoir().getNbPionsNoir());
    }

    private void setCelluleRowAndCol(){
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                plateau[row][col].row = row;
                plateau[row][col].col = col;
            }
        }
    }

    public void setColPlateau(int row, int col, boolean white, int nbPions){
        plateau[row][col].setCell(white, nbPions);
    }

    public RepPlateau deplacementPion(int rowD, int colD, int rowA, int colA, boolean white){
        RepPlateau repPlateau = this;
        if(white) {
            if (rowA == 50 && colA == 50) {
                repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsBlancs);
            }
            else {
                repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsBlancs);
                repPlateau.setColPlateau(rowA, colA, white, ++repPlateau.getCell(rowA, colA).nbPionsBlancs);
            }
        }
        else {
            if (rowA == 50 && colA == 50) {
                repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsNoirs);
            }
            else {
                repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsNoirs);
                repPlateau.setColPlateau(rowA, colA, white, ++repPlateau.getCell(rowA, colA).nbPionsNoirs);
            }
        }

        return repPlateau;

    }

    public Cellule getCell(int row, int col){
        return plateau[row][col];
    }

    public boolean whiteWin(){
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                if(getCell(row,col).getNbPionsBlancs() != 0) return false;
            }
        }
        return true;
    }
    public boolean blackWin(){
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                if(getCell(row,col).getNbPionsNoirs() != 0) return false;
            }
        }
        return true;
    }

    public Cellule getPrisonBlancs() {
        return prisonBlancs;
    }

    public Cellule getPrisonNoirs() {
        return prisonNoirs;
    }


    public boolean isEndGameBlack(){
        if (prisonNoirs.getNbPionsNoirs() != 0) return false;
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                if(getCell(row,col).getNbPionsNoirs() != 0 && (row == 1 || col < 6)) return false;
            }
        }
        return true;
    }

    public boolean isEndGameWhite(){
        if (prisonBlancs.getNbPionsBlancs() != 0) return false;
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                if(getCell(row,col).getNbPionsBlancs() != 0 && (row == 0 || col < 6)) return false;
            }
        }
        return true;
    }

}

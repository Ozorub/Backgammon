import javafx.scene.control.Cell;

public class RepPlateau {
    private Cellule[][] plateau = new Cellule[BgPane.NBROW][BgPane.NBCOL];

    public static class Cellule{
        private int nbPionsBlancs;
        private int nbPionsNoirs;

        public Cellule(int nbPionsBlancs, int nbPionsNoirs){
            this.nbPionsBlancs = nbPionsBlancs;
            this.nbPionsNoirs = nbPionsNoirs;
        }

        public void setCell(boolean white, int nbPions){
            if (white) nbPionsBlancs = nbPions;
            else nbPionsNoirs = nbPions ;
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

    public RepPlateau(Cellule[][] plateau){
        this.plateau = plateau;
    }

    public RepPlateau(){
        for (int row = 0 ; row < BgPane.NBROW;row++){
            for (int col = 0 ; col < BgPane.NBCOL ; col++){
                Cellule cel = new Cellule(0,0);
                plateau[row][col] = cel;
            }
        }
    }

    public void setColPlateau(int row, int col, boolean white, int nbPions){
        plateau[row][col].setCell(white, nbPions);
    }

    public RepPlateau deplacementPion(int rowD, int colD, int rowA, int colA, boolean white){
        RepPlateau repPlateau = this;
        if(white) {
            repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsBlancs);
            repPlateau.setColPlateau(rowA, colA, white, ++repPlateau.getCell(rowA, colA).nbPionsBlancs);
        }
        else {
            repPlateau.setColPlateau(rowD, colD, white, --repPlateau.getCell(rowD, colD).nbPionsNoirs);
            repPlateau.setColPlateau(rowA, colA, white, ++repPlateau.getCell(rowA, colA).nbPionsNoirs);
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


}

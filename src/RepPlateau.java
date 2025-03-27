public class RepPlateau {
    private Cellule[][] plateau;

    private class Cellule{
        private int nbPionsBlancs;
        private int nbPionsNoirs;

        public Cellule(int nbPionsBlancs, int nbPionsNoirs){
            this.nbPionsBlancs = nbPionsBlancs;
            this.nbPionsNoirs = nbPionsNoirs;
        }


    }

    public RepPlateau(Cellule[][] plateau){
        this.plateau = plateau;
    }

}

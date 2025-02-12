import java.util.ArrayList;
import java.util.List;

/**
 * Classe Jeu
 * @version 1.0
 * @author Raphaël Charozé
 */
public class Jeu {
    private final Joueur j1 = Joueur.BLANC;
    private final Joueur j2 = Joueur.NOIR;

    private PaireDeDes des = new PaireDeDes();

    public void setDesLances(boolean desLances) {
        this.desLances = desLances;
    }
    public void setResteDes(ArrayList<Integer> des) {
        this.resteDes = des;
    }

    private boolean desLances = false;

    public List<Integer> getResteDes() {
        return resteDes;
    }

    private ArrayList<Integer> resteDes = new ArrayList<>();

    private Joueur currentJoueur = j1;

    private ColonneDeJeu col1 = null;
    private ColonneDeJeu col2 = null;

    public int getCoutDuMouv() {
        return coutDuMouv;
    }

    int coutDuMouv;


    private final BgPane plateau = new BgPane();

    /**
     * Constructeur de la classe Jeu
     */
    public Jeu(){}

    /**
     * Méthode principale pour lancer le jeu
     */
    public void jouer(){
        boolean finDuJeu = false;
        currentJoueur = j2;

        while(!finDuJeu){

            boolean finDuTour = false;
            System.out.println("Tour de " + currentJoueur);
            currentJoueur = (currentJoueur == j1)?j2:j1; //changement de joueur
            int[] lancer = des.lancerLesDes(); //lancer des dés

            while(!finDuTour) {
                //finDuTour = !canPlay(currentJoueur, lancer);
            }
            //not really useful need to check usefulness
        }

        //TODO : sortir du jeu
    }

    void bougerPion(){
        try {

            System.out.println("Tour de " + currentJoueur);
            System.out.println("Encore a jouer: " + resteDes.toString());
            ColonneDeJeu col1 = Main.JEU.getCol1();
            ColonneDeJeu col2 = Main.JEU.getCol2();

            if (desLances) {
                if (currentJoueur == Joueur.BLANC){
                    if (col1.getRow() != 100 && col2.getRow() != 100 && (col1.getRow() < col2.getRow()
                            || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() < col2.getCol())
                            || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() > col2.getCol()))){


                        if (col1.getRow() == col2.getRow()) {
                            coutDuMouv = Math.abs(col1.getCol() - col2.getCol());
                        }else{
                            coutDuMouv = col1.getCol() + col2.getCol() + 1;
                        }

                        if (resteDes.contains(coutDuMouv)) {
                            col1.updateRectangle(currentJoueur, col2);
                            if (resteDes.isEmpty()) {
                                desLances = false;
                                currentJoueur = (currentJoueur == Joueur.BLANC)?j2:j1;
                            }
                        }
                    }else if(col1.getRow() == 100){
                        //TODO : gestion des pions sur la prison
                    }else{
                        System.out.println("Mauvais sens de jeu");
                    }
                }else if (currentJoueur == Joueur.NOIR){
                    if (col1.getRow() != 100 && col2.getRow() != 100 && (col1.getRow() > col2.getRow()
                            || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() > col2.getCol())
                            || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() < col2.getCol()))){

                        int coutDuMouv;
                        if (col1.getRow() == col2.getRow()) {
                            coutDuMouv = Math.abs(col1.getCol() - col2.getCol());
                        }else{
                            coutDuMouv = col1.getCol() + col2.getCol() + 1;
                        }

                        if (resteDes.contains(coutDuMouv)) {
                            col1.updateRectangle(currentJoueur, col2);
                            if (resteDes.isEmpty()) {
                                desLances = false;
                                currentJoueur = (currentJoueur == Joueur.BLANC)?j2:j1;

                            }
                        }
                    }else if(col1.getRow() == 100){
                        //TODO : gestion des pions sur la prison
                    }else{
                        System.out.println("Mauvais sens de jeu");
                    }
                }
            }else{
                System.out.println("Vous devez lancer les dés");
            }

            }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void lancerDes(){
        des.lancerLesDes();
    }

    public int[] valeurDes(){
        return des.valeursDesDes();
    }

    public BgPane getPlateau() {
        return plateau;
    }

    public ColonneDeJeu getCol1() {
        return col1;
    }

    public ColonneDeJeu getCol2() {
        return col2;
    }

    public void setCol1(ColonneDeJeu col) {
        col1 = col;
    }

    public void setCol2(ColonneDeJeu col) {
        col2 = col;
    }

}

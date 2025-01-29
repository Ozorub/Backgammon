/**
 * Classe Jeu
 * @version 1.0
 * @author Raphaël Charozé
 */
public class Jeu {
    private final Joueur j1 = Joueur.BLANC;
    private final Joueur j2 = Joueur.NOIR;

    private PaireDeDes des = new PaireDeDes();
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
        Joueur currentJoueur = j2;

        while(!finDuJeu){

            boolean finDuTour = false;

            currentJoueur = (currentJoueur == j1)?j2:j1; //changement de joueur
            int[] lancer = des.lancerLesDes(); //lancer des dés

            while(!finDuTour) {
                finDuTour = !canPlay(currentJoueur, lancer);
            }


            if(playerWon(currentJoueur)){
                finDuJeu = true;
            }
        }

        //TODO : sortir du jeu
    }

    private boolean canPlay(Joueur joueur, int[] des){
        return false; //TODO
    }

    private boolean playerWon(Joueur joueur){
        return false; //TODO
    }

}

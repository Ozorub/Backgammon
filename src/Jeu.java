import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Jeu
 *
 * @author Raphaël Charozé
 * @version 1.0
 */
public class Jeu {
    private final Joueur j1 = Joueur.BLANC;
    private final Joueur j2 = Joueur.NOIR;
    private PaireDeDes des = new PaireDeDes();
    private boolean desLances = false;
    private ArrayList<Integer> resteDes = new ArrayList<>();
    private Joueur currentJoueur = j1;
    private ColonneDeJeu col1 = null;
    private ColonneDeJeu col2 = null;
    private final BgPane plateau = new BgPane();
    int coutDuMouv;
    static Boolean isEndGameWhite = false;
    static Boolean isEndGameBlack = false;


    /**
     * Constructeur de la classe Jeu
     */
    public Jeu() {
    }

    /**
     * Méthode principale pour lancer le jeu
     */
    public void jouer() {
        boolean finDuJeu = false;
        currentJoueur = j2;

        while (!finDuJeu) {

            boolean finDuTour = false;
            System.out.println("Tour de " + currentJoueur);
            currentJoueur = (currentJoueur == j1) ? j2 : j1; //changement de joueur
            int[] lancer = des.lancerLesDes(); //lancer des dés




            while (!finDuTour) {
                //finDuTour = !canPlay(currentJoueur, lancer);
            }
            //not really useful need to check usefulness
        }

        //TODO : sortir du jeu
    }


    void bougerPion() {
        try {

            System.out.println("Tour de " + currentJoueur);
            System.out.println("Encore a jouer: " + resteDes.toString());
            ColonneDeJeu col1 = Main.JEU.getCol1();
            ColonneDeJeu col2 = Main.JEU.getCol2();

            //Aucun pions ailleurs que dans sa zone(pas en prison non plus)
            if(!isEndGameWhite) {
                for (int col = 0; col < BgPane.NBCOL; col++) {
                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 0).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 1).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 2).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 3).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 4).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 1, 5).getNbPionsBlanc() == 0
                            && plateau.getPrisonBlanc().getNbPionsBlanc() == 0) {
                        isEndGameWhite = true;
                    }
                }
            }
            if(!isEndGameBlack) {
                for (int col = 1; col < BgPane.NBCOL; col++) {
                    if (plateau.getColonneDeJeu(plateau.grille, 0, col).getNbPionsBlanc() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 0).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 1).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 2).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 3).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 4).getNbPionsNoir() == 0
                            && plateau.getColonneDeJeu(plateau.grille, 0, 5).getNbPionsNoir() == 0
                            && plateau.getPrisonNoir().getNbPionsNoir() == 0) {
                        isEndGameBlack = true;
                    }
                }
            }


            if (desLances) {
                if (currentJoueur == Joueur.BLANC) {
                    if(!isEndGameWhite) {

                        if (col1.getRow() != 100 && col2.getRow() != 100 && (col1.getRow() < col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() < col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() > col2.getCol()))) { // inGame


                            if (col1.getRow() == col2.getRow()) {
                                coutDuMouv = Math.abs(col1.getCol() - col2.getCol());
                            } else {
                                coutDuMouv = col1.getCol() + col2.getCol() + 1;
                            }

                            if (resteDes.contains(coutDuMouv)) {
                                col1.updateColonne(currentJoueur, col2, 1);
                                coutDuMouv = 0;
                                if (resteDes.isEmpty()) {
                                    plateau.dice.getChildren().set(3, new Label("Tour des noirs")); // Le 3 corespond à sa place dans le VBox de la partie gauche du BorderPane
                                    desLances = false;
                                    currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
                                }
                            }
                        } else if (col1.getRow() == 100) {
                            //TODO : gestion des pions sur la prison
                        }else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    }else{
                        //Endgame
                        coutDuMouv = (col1.getCol()-BgPane.NBCOL/2)+1;
                        if (resteDes.contains(coutDuMouv)) {

                            col1.updateColonne(currentJoueur, col1, 0);
                            coutDuMouv = 0;
                            if (resteDes.isEmpty()) {
                                plateau.dice.getChildren().set(3, new Label("Tour des noirs"));
                                desLances = false;
                                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
                            }
                        }else{
                            System.out.println("Recommence, Tu ne peux pas prendre de pions ici");
                        }

                    }
                } else if (currentJoueur == Joueur.NOIR) {
                    if(!isEndGameBlack) {

                        if (col1.getRow() != 100 && col2.getRow() != 100 && (col1.getRow() > col2.getRow()
                                || (col1.getRow() == 1 && col2.getRow() == 1 && col1.getCol() > col2.getCol())
                                || (col1.getRow() == 0 && col2.getRow() == 0 && col1.getCol() < col2.getCol()))) { // inGame

                            if (col1.getRow() == col2.getRow()) {
                                coutDuMouv = Math.abs(col1.getCol() - col2.getCol());
                            } else {
                                coutDuMouv = col1.getCol() + col2.getCol() + 1;
                            }

                            if (resteDes.contains(coutDuMouv)) {
                                col1.updateColonne(currentJoueur, col2, 1);
                                coutDuMouv = 0;
                                if (resteDes.isEmpty()) {
                                    plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
                                    desLances = false;
                                    currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;

                                }
                            }
                        } else if (col1.getRow() == 100) {
                            //TODO : gestion des pions sur la prison
                        } else {
                            System.out.println("Mauvais sens de jeu");
                        }
                    }else{
                        //Endgame
                        coutDuMouv = (col1.getCol()-BgPane.NBCOL/2)+1;
                        if (resteDes.contains(coutDuMouv)) {
                            col1.updateColonne(currentJoueur, col1, 0);
                            coutDuMouv = 0;
                            if (resteDes.isEmpty()) {
                                plateau.dice.getChildren().set(3, new Label("Tour des blancs"));
                                desLances = false;
                                currentJoueur = (currentJoueur == Joueur.BLANC) ? j2 : j1;
                            }
                        }else{
                            System.out.println("Recommence, Tu ne peux pas prendre de pions ici");
                        }

                    }
                }

            } else {
                System.out.println("Vous devez lancer les dés");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    void lancerDes() {
        des.lancerLesDes();
    }

    public int[] valeurDes() {
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

    public void setDesLances(boolean desLances) {
        this.desLances = desLances;
    }

    public void setResteDes(ArrayList<Integer> des) {
        this.resteDes = des;
    }


    public List<Integer> getResteDes() {
        return resteDes;
    }

    public int getCoutDuMouv() {
        return coutDuMouv;
    }

}

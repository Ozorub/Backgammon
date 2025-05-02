//import javafx.scene.control.Cell;
//
//import java.util.ArrayList;
//
//public class JeuMath {
//    private final Joueur j1 = Joueur.BLANC;
//    private final Joueur j2 = Joueur.NOIR;
//    private static final PaireDeDes des = new PaireDeDes();
//    private boolean desLances = false;
//    private ArrayList<Integer> resteDes = new ArrayList<>();
//    private Joueur currentJoueur = j1;
//    int coutDuMouv;
//    static Boolean isEndGameWhite = false;
//    static Boolean isEndGameBlack = false;
//    private final RepPlateau repPlateau = new RepPlateau();
//    private int nbPionsBlancGauche = 0;
//    private int nbPionsNoirGauche = 0;
//    private RepPlateau.Cellule cel1 = null;
//    private RepPlateau.Cellule cel2 = null;
//
//    public JeuMath(){
//
//    }
//
//    public void bougerPion(){
//        try {
//            System.out.println("Tour de " + currentJoueur);
//            System.out.println("Encore a jouer: " + resteDes.toString());
//            cel1 = Main.JEUMATH.getCel1();
//            cel2 = Main.JEUMATH.getCel2();
//
//            if(!isEndGameWhite){
//                boolean intermediaire = false;
//                for(int col = 0; col < BgPane.NBCOL;col++){
//                    if(repPlateau.getCell(0,col).getNbPionsBlancs() == 0){
//                        intermediaire = true;
//                    }
//                }if(repPlateau.getCell(1,0).getNbPionsBlancs() == 0
//                        && repPlateau.getCell(1, 1).getNbPionsBlancs() == 0
//                        && repPlateau.getCell(1, 2).getNbPionsBlancs() == 0
//                        && repPlateau.getCell(1, 3).getNbPionsBlancs() == 0
//                        && repPlateau.getCell(1, 4).getNbPionsBlancs() == 0
//                        && repPlateau.getCell(1, 5).getNbPionsBlancs() == 0
//                        && repPlateau.getPrisonBlancs().getNbPionsBlancs() == 0
//                        && intermediaire){
//                    isEndGameWhite = true;
//
//                }
//            }
//            if(!isEndGameBlack){
//                boolean intermediaire = false;
//                for(int col = 0; col < BgPane.NBCOL;col++){
//                    if(repPlateau.getCell(1,col).getNbPionsNoirs() == 0){
//                        intermediaire = true;
//                    }
//                }if(repPlateau.getCell(0,0).getNbPionsNoirs() == 0
//                        && repPlateau.getCell(0, 1).getNbPionsNoirs() == 0
//                        && repPlateau.getCell(0, 2).getNbPionsNoirs() == 0
//                        && repPlateau.getCell(0, 3).getNbPionsNoirs() == 0
//                        && repPlateau.getCell(0, 4).getNbPionsNoirs() == 0
//                        && repPlateau.getCell(0, 5).getNbPionsNoirs() == 0
//                        && repPlateau.getPrisonNoirs().getNbPionsNoirs() == 0
//                        && intermediaire){
//                    isEndGameBlack = true;
//
//                }
//            }
//
//            if(desLances){
//                if(currentJoueur == Joueur.BLANC){
//                    if(repPlateau.getPrisonBlancs().getNbPionsBlancs() != 0 ){
//                        System.out.println("Prisosososososososososoos");
//                        deplacerPionPrison(currentJoueur);
//                    }
////                    else if(!isEndGameWhite){
////                        if(()
////                        ||()
////                        ||()){
////
////                        }
////                    }
//                }
//            }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private void deplacerPionPrison(Joueur currentJoueur) {
//        //TODO
//    }
//
//
//    public RepPlateau.Cellule getCel1() {
//        return cel1;
//    }
//
//    public void setCel1(RepPlateau.Cellule cel1) {
//        this.cel1 = cel1;
//    }
//
//    public RepPlateau.Cellule getCel2() {
//        return cel2;
//    }
//
//    public void setCel2(RepPlateau.Cellule cel2) {
//        this.cel2 = cel2;
//    }
//
//
//}

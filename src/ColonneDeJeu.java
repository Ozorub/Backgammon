import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Classe représentant une colonne de jeu
 * @version 1.1
 * @author Raphaël Charozé
 */
public class ColonneDeJeu extends Button {

    VBox vbox = new VBox();

    private int nbPionsNoir = 0;
    private int nbPionsBlanc = 0;

    /**
     * Modifie le contenu interne du bouton en fonction du joueur qui a joué, puis met à jour le contenu graphique
     * Attention : ne vérifie pas que le coup est valide par rapport au lancer de dés !!!
     */
    protected void updateButton(Joueur j, boolean firstClicked) throws Exception{
        if (j == Joueur.BLANC){ // Si le joueur est blanc
            if (firstClicked){
                if (this.nbPionsBlanc == 0) throw new Exception("Pas de pions blancs dans cette colonne");
                this.nbPionsBlanc--;
            }else{
                if (this.nbPionsNoir > 1) throw new Exception("Il y a déjà des pions noirs dans cette colonne");
                if (this.nbPionsNoir == 1) ajouterPionPrison("NOIR");
                this.nbPionsBlanc++;
            }
        }else{
            if (firstClicked){
                if (this.nbPionsNoir == 0) throw new Exception("Pas de pions noirs dans cette colonne");
                this.nbPionsNoir--;
            }else{
                if (this.nbPionsBlanc > 1) throw new Exception("Il y a déjà des pions blancs dans cette colonne");
                if (this.nbPionsBlanc == 1) ajouterPionPrison("BLANC");
                this.nbPionsNoir++;
            }
        }

        Platform.runLater(this::updateGraphics);

    }

    /**
     * Modifie le contenu graphique du bouton en fonction du nombre de pions
     */
    private void updateGraphics(){
        this.vbox.getChildren().clear();
        int nbButtonToAdd;
        String imageUrl;
        if (this.nbPionsBlanc > 0){
            nbButtonToAdd = this.nbPionsBlanc;
            imageUrl = "pionBlanc.png";
        }
        else{
            nbButtonToAdd = this.nbPionsNoir;
            imageUrl = "pionNoir.png";
        }

        if (nbButtonToAdd > 5){
            this.vbox.getChildren().add(new ImageView(new Image(imageUrl)));
            this.vbox.getChildren().add(new Label(String.valueOf(nbButtonToAdd)));

        }else {
            for (int i = 0; i < nbButtonToAdd; i++) {
                this.vbox.getChildren().add(new ImageView(new Image(imageUrl)));
            }
        }
    }

    /**
     * Ajoute un pion dans la prison
     */
    private void ajouterPionPrison(String couleur) {
        if (couleur.equals("BLANC")) this.nbPionsBlanc--;
        else this.nbPionsNoir--;

        //TODO : ajouter a la prison, prison est une ColonneDeJeu, mais instancié où ?

    }

    protected ColonneDeJeu(){
        super();
        this.setGraphic(vbox);
        Platform.runLater(this::updateGraphics);
    }



}

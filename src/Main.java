import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static Jeu JEU;

    @Override
    public void start(Stage stage){
        stage.setTitle("Backgammon du tonnerre");
        BorderPane root = new BorderPane();
        Scene scene1 = new Scene(root);
        root.setTop(new Label("Sélection joueur"));

        // Create radio buttons j1
        RadioButton option1j1 = new RadioButton("Humain");
        RadioButton option2j1 = new RadioButton("IA facile");
        RadioButton option3j1 = new RadioButton("IA intermédiaire");
        RadioButton option4j1 = new RadioButton("IA difficile");

        // Group radio buttons so only one can be selected at a time
        ToggleGroup group1 = new ToggleGroup();
        option1j1.setToggleGroup(group1);
        option2j1.setToggleGroup(group1);
        option3j1.setToggleGroup(group1);
        option4j1.setToggleGroup(group1);


        VBox layout1 = new VBox(10, option1j1, option2j1, option3j1, option4j1);
        root.setLeft(layout1);

        // create radio buttons j2
        RadioButton option1j2 = new RadioButton("Humain");
        RadioButton option2j2 = new RadioButton("IA facile");
        RadioButton option3j2 = new RadioButton("IA intermédiaire");
        RadioButton option4j2 = new RadioButton("IA difficile");

        // Group radio buttons so only one can be selected at a time
        ToggleGroup group2 = new ToggleGroup();
        option1j2.setToggleGroup(group2);
        option2j2.setToggleGroup(group2);
        option3j2.setToggleGroup(group2);
        option4j2.setToggleGroup(group2);


        VBox layout2 = new VBox(10, option1j2, option2j2, option3j2, option4j2);
        root.setRight(layout2);

        Button ok = new Button("valider les choix.");
        ok.setOnMouseClicked((e)->{
            stage.close();
            Joueur joueur1 = Joueur.BLANC;
            Joueur joueur2 = Joueur.NOIR;
            ouvrirJeu();
        });

        root.setBottom(ok);

        stage.setScene(scene1);

        stage.setResizable(false);
        stage.sizeToScene();

        stage.show();

    }

    private void ouvrirJeu(){

        Stage stage = new Stage();

        stage.setTitle("Backgammon du tonnerre");

        JEU = new Jeu();
        Scene scene = new Scene(JEU.getPlateau());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}
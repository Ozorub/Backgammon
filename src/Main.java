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
    public static JoueurClass joueur_blanc;
    public static JoueurClass joueur_noir;

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
        option1j1.setUserData("Humain");
        option2j1.setUserData("IA facile");
        option3j1.setUserData("IA intermédiaire");
        option4j1.setUserData("IA difficile");

        // Group radio buttons so only one can be selected at a time
        ToggleGroup group1 = new ToggleGroup();
        option1j1.setToggleGroup(group1);
        option2j1.setToggleGroup(group1);
        option3j1.setToggleGroup(group1);
        option4j1.setToggleGroup(group1);

        group1.selectToggle(option1j1);

        Label label1 = new Label("Joueur 1");
        VBox layout1 = new VBox(10,label1, option1j1, option2j1, option3j1, option4j1);
        root.setLeft(layout1);

        // create radio buttons j2
        RadioButton option1j2 = new RadioButton("Humain");
        RadioButton option2j2 = new RadioButton("IA facile");
        RadioButton option3j2 = new RadioButton("IA intermédiaire");
        RadioButton option4j2 = new RadioButton("IA difficile");
        option1j2.setUserData("Humain");
        option2j2.setUserData("IA facile");
        option3j2.setUserData("IA intermédiaire");
        option4j2.setUserData("IA difficile");

        // Group radio buttons so only one can be selected at a time
        ToggleGroup group2 = new ToggleGroup();
        option1j2.setToggleGroup(group2);
        option2j2.setToggleGroup(group2);
        option3j2.setToggleGroup(group2);
        option4j2.setToggleGroup(group2);

        group2.selectToggle(option1j2);

        Label label2 = new Label("Joueur 2");
        VBox layout2 = new VBox(10,label2, option1j2, option2j2, option3j2, option4j2);
        root.setRight(layout2);

        Button ok = new Button("valider les choix.");
        ok.setOnMouseClicked((_)->{
            stage.close();

            ouvrirJeu();

            joueur_blanc = extractJoueurFromToggle(group1);
            joueur_noir = extractJoueurFromToggle(group2);
        });

        root.setBottom(ok);

        stage.setScene(scene1);

        stage.setResizable(false);
        stage.sizeToScene();

        stage.show();

    }

    private JoueurClass extractJoueurFromToggle(ToggleGroup group2) {
        return switch (group2.getSelectedToggle().getUserData().toString()) {
            case "IA facile" -> new IA_easy_baby();
            case "IA intermédiaire" -> new IA_intermediate();
            case "IA difficile" -> new IA_hard();
            default -> new Human();
        };
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
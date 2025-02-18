import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Jeu JEU;

    @Override
    public void start(Stage stage){
        stage.setTitle("Backgammon du tonnerre");
        //BgPane mPane = new BgPane();
        //Scene scene = new Scene(mPane);

        JEU = new Jeu();
        Scene scene = new Scene(JEU.getPlateau());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();


    }

    public static void main(String[] args){

        launch(args);

        Thread t = new Thread(() -> JEU.jouer());
        JEU.jouer();
        //t.start();

    }
}
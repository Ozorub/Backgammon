import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Backgammon du tonnerre");
        BgPane mPane = new BgPane();
        Scene scene = new Scene(mPane);

        stage.setResizable(true);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();


    }

    public static void main(String[] args){
        launch(args);
        System.out.println("Hello world");
    }
}
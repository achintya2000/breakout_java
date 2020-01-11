package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Achintya's Breakout Game");
        primaryStage.show();
    }

    // Initialize javaFX gui
    public static void main (String[] args) {
        launch(args);
    }
}

package breakout;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "Achintya's Breakout Game";
    private Brick simpleBrick = new Brick(500, 500, 100, 50, Color.GRAY, 1, "simple");
    private Brick multiBrick = new Brick(625,500, 100, 50, Color.BLUEVIOLET, 3, "multi");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.AZURE);

        ObservableList list = root.getChildren();
        list.add(writeText("Hello World", 45,200,200));
        list.add(simpleBrick);
        list.add(multiBrick);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Text writeText(String message, int font, int x, int y) {
        Text text = new Text();
        text.setFont(new Font(font));
        text.setX(x);
        text.setY(y);
        text.setText(message);
        return text;
    }

    // Initialize javaFX gui
    public static void main (String[] args) {
        launch(args);
    }
}

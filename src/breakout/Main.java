package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final String TITLE = "Achintya's Breakout Game";
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Brick simpleBrick = new Brick(500, 500, 100, 50, Color.GRAY, 1, "simple");
    private Brick multiBrick = new Brick(625,500, 100, 50, Color.BLUEVIOLET, 3, "multi");
    private Brick gamePaddle = new Brick(350, 700, 70, 20, Color.BLACK, 3, "player");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.AZURE);

        ObservableList list = root.getChildren();
        list.add(writeText("Hello World", 45,200,200));
        list.add(simpleBrick);
        list.add(multiBrick);
        list.add(gamePaddle);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add a game loop to timeline to play
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    gamePaddle.setLayoutX(gamePaddle.getLayoutX() + 20);
                } else if (event.getCode() == KeyCode.LEFT) {
                    gamePaddle.setLayoutX(gamePaddle.getLayoutX() - 20);
                }
            }
        });
    }

    private void step(double elapsedTime) {
        
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

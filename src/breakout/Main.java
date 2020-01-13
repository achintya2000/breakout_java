package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {

    public static final String TITLE = "Achintya's Breakout Game";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final String BALL_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";

    static Random random = new Random();
    public static int BALL_SPEED_X = 80 + random.nextInt(20);
    public static int BALL_SPEED_Y = -80 - random.nextInt(20);
    public static double BALL_SPEED_TOTAL = Math.sqrt(Math.pow(BALL_SPEED_Y, 2) + Math.pow(BALL_SPEED_X,2));

    private GamePaddle gamePaddle = new GamePaddle("player", 3, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), 300, 500);
    private Ball ball = new Ball("ball", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE)), 300, 400);

    Levels levelGenerator = new Levels();
    TilePane tilePane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.AZURE);

        ObservableList list = root.getChildren();
        //list.add(writeText("Hello World", 45,200,200));
        //list.add(simpleBrick);
        //list.add(multiBrick);
        list.add(gamePaddle);
        list.add(ball);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        levelGenerator.drawLevel1(root);
        //tilePane = (TilePane) root.getChildren().get(2);
        // Add a game loop to timeline to play
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (gamePaddle.getBoundsInParent().getMinX() <= 0) {
                    if (event.getCode() == KeyCode.RIGHT) {
                        gamePaddle.setX(gamePaddle.getX() + 20);
                    }
                } else if (gamePaddle.getBoundsInParent().getMinX() >= WIDTH - gamePaddle.getBoundsInLocal().getWidth()) {
                    if (event.getCode() == KeyCode.LEFT) {
                        gamePaddle.setX(gamePaddle.getX() - 20);
                    }
                } else {
                    if (event.getCode() == KeyCode.RIGHT) {
                        gamePaddle.setX(gamePaddle.getX() + 20);
                    }
                    if (event.getCode() == KeyCode.LEFT) {
                        gamePaddle.setX(gamePaddle.getX() - 20);
                    }
                }
                if (event.getCode() == KeyCode.R) {
                    ball.resetBall(300, 400);
                }
            }
        }
        );
    }

    private void step(double elapsedTime) {
        //System.out.println(gamePaddle.getBoundsInParent());
        ball.setX(ball.getX() + BALL_SPEED_X * elapsedTime);
        ball.setY(ball.getY() + BALL_SPEED_Y * elapsedTime);

        if (ball.getBoundsInParent().getMaxX() >= WIDTH || ball.getBoundsInParent().getMinX() <= 0) {
            BALL_SPEED_X *= -1;
        }

        if (ball.getBoundsInParent().getMinY() <= 0) {
            BALL_SPEED_Y *= -1;
        }

        if (ball.getBoundsInParent().getMaxY() >= HEIGHT) {

        }

        if (ball.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
//            if(gamePaddle.getBoundsInParent().getMaxX() - 15 < ball.getBoundsInParent().getCenterX()) {
//                BALL_SPEED_Y *= -.9;
//                BALL_SPEED_X *= -1;
//            }

            BALL_SPEED_Y *= -1;
        }

        for (SimpleBrick sB : levelGenerator.brickList) {
            if (sB.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                BALL_SPEED_X *= -1;
                BALL_SPEED_Y *= -1;
            }
        }

//        Shape intersection = Shape.intersect(gamePaddle, ball);
//        if (intersection.getBoundsInLocal().getWidth() != -1) {
//            double paddle_left = gamePaddle.getBoundsInParent().getMinX();
//            double paddle_right = gamePaddle.getBoundsInParent().getMaxX();
//            double ball_center = ball.getCenterX();
//            boolean left = paddle_left - ball_center > 0;
//            boolean right = paddle_right - ball_center < 0;
//            if (left || right) {
//                ball.setCenterY(ball.getCenterY() + 5);
//                BALL_SPEED_X *= -1;
//            }
//            BALL_SPEED_Y *= -1;
//        }

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

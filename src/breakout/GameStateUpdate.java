package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameStateUpdate extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final String BALL_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String BRICK_IMAGE_1 = "brick1.gif";
    public static final String EXTRA_BALL = "extraballpower.gif";
    public static final String LEVEL_1 = "./resources/level1.txt";
    public static final String LEVEL_2 = "./resources/level2.txt";
    private static final String LEVEL_3 = "./resources/level3.txt";

    //static Random random = new Random();
    public static double BALL_SPEED_X = 0;
    public static double BALL_SPEED_Y = 150;

    public static final int POWER_UP_VELOCITY = 80;

    private GamePaddle gamePaddle = new GamePaddle("player", 3, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), 300, 500);
    private Ball ball = new Ball("ball", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE)), 300, 400);

    private ArrayList<PowerUp> powerUpManager = new ArrayList<PowerUp>();

    //private int numOfNull = 0;

    Levels levelGenerator = new Levels();
    UIElements uiElementsGenerator = new UIElements();

    //Group root = new Group();
    Scene scene;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //scene = levelGenerator.drawLevel1(root);
        //Scene scene = new Scene(root, WIDTH, HEIGHT);
        //scene.setFill(Color.AZURE);

        //ObservableList list = root.getChildren();
        //list.add(writeText("Hello World", 45,200,200));
        //list.add(simpleBrick);
        //list.add(multiBrick);
        //list.add(gamePaddle);
        //list.add(ball);

        //primaryStage.setTitle(TITLE);
        //primaryStage.setScene(scene);
        //scene = levelGenerator.drawLevel2(ball, gamePaddle);
        //scene = levelGenerator.drawALevel(ball, gamePaddle, "./resources/level1.txt");
        scene = uiElementsGenerator.createMainSplashScreen();


        scene.setOnKeyPressed(e -> {
            try {
                handle(e.getCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        //levelGenerator.readTextFile();
        //levelGenerator.drawLevel1(root);
        //levelGenerator.drawLevel2(root);
        //tilePane = (TilePane) root.getChildren().get(2);
        // Add a game loop to timeline to play
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double elapsedTime) throws IOException {

        updateBallState(elapsedTime);

        determineBallDirectionFromHittingPaddle();

        reverseBallDirectionFromHittingWall();

        if (ball.getBoundsInParent().getMaxY() >= HEIGHT) {
            resetBall();
            gamePaddle.lives--;
            uiElementsGenerator.updateText(UIElements.scoreText, "Lives left: " + gamePaddle.lives);
        }


        if (levelGenerator.brickList != null) {
            for (Sprite sB : levelGenerator.brickList) {
                if (sB.getImage() != null) {
                    if (sB.getBoundsInParent().intersects(ball.getBoundsInParent())) {

                        double centerBallX = ball.getBoundsInParent().getCenterX();
                        double centerBallY = ball.getBoundsInParent().getCenterY();

//                        if ((centerBallX <= sB.getBoundsInParent().getMinX()
//                                && centerBallY <= sB.getBoundsInParent().getMinY()) ||
//                                (centerBallX >= sB.getBoundsInParent().getMaxX())
//                                        && centerBallY <= sB.getBoundsInParent().getMinY()) {
//                            BALL_SPEED_X *= -1;
//                        } else {
//                            BALL_SPEED_Y *= -1;
//                        }
                        if (centerBallX > ball.getBoundsInParent().getMinX() && centerBallX < ball.getBoundsInParent().getMaxX()) {
                            System.out.println(centerBallX);
                            System.out.println(ball.getBoundsInParent().getMinX());
                            System.out.println(ball.getBoundsInParent().getMaxX());
                            BALL_SPEED_Y *= -1;
                        } else {
                            System.out.println(centerBallX);
                            System.out.println();
                            BALL_SPEED_X *= -1;
                        }

                        if (sB.type.equals("simpleBrick")) {
                            sB.setImage(null);
                        } else if (sB.type.equals("lifeBrick")) {
                            sB.setImage(null);
                            PowerUp lifeUp = new PowerUp("powerUpLife",
                                    1,
                                    new Image(this.getClass().getClassLoader().getResourceAsStream(EXTRA_BALL)),
                                    sB.getBoundsInParent().getCenterX(),
                                    sB.getBoundsInParent().getCenterY());
                            //sB.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(EXTRA_BALL)));
                            levelGenerator.returnGroup().getChildren().add(lifeUp);
                            //lifeUp.moveDown(lifeUp, elapsedTime);
                            powerUpManager.add(lifeUp);
                        } else if (sB.type.equals("multiBrick")) {
                            sB.lives--;
                            if (sB.lives == 1) {
                                sB.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1)));
                            } else if (sB.lives == 0) {
                                sB.setImage(null);
                            }
                        } else if (sB.type.equals("bombBrick")) {
                            gamePaddle.lives -- ;
                            sB.setImage(null);
                            uiElementsGenerator.updateText(UIElements.scoreText, "Lives left: " + gamePaddle.lives);
                        }

    //                    sB.lives--;
    //                    if (sB.lives == 0 && sB.type.equals("simpleBrick") || sB.type.equals("multiBrick")) {
    //                        sB.setImage(null);
    //                    } else if (sB.lives == 1 && sB.type.equals("multiBrick")) {
    //                        sB.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1)));
    //                    } else if (sB.lives == 0 && sB.type.equals("lifeBrick")) {
    //                        sB.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(EXTRA_BALL)));
    //                    }
                    }
                }
            }
        }

        if (powerUpManager != null) {
            for (PowerUp pU : powerUpManager) {
                pU.setY(pU.getY() + POWER_UP_VELOCITY * elapsedTime);
                if (pU.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
                    if(pU.getImage() != null) {
                        if (pU.type.equals("powerUpLife")) {
                            gamePaddle.lives++;
                            uiElementsGenerator.updateText(UIElements.scoreText, "Lives left: " + gamePaddle.lives);
                        }
                    }
                    pU.setImage(null);
                }
            }
        }

        createLevelWhenPreviousSucceeded();

        if (gamePaddle.lives == 0) {
            gamePaddle.lives = 3;
            scene = uiElementsGenerator.createFailureScreen();
            primaryStage.setScene(scene);
            scene.setOnKeyPressed(e -> {
                try {
                    handle(e.getCode());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

    }

    public void updateBallState(double elapsedTime) {
        ball.setX(ball.getX() + BALL_SPEED_X * elapsedTime);
        ball.setY(ball.getY() + BALL_SPEED_Y * elapsedTime);
    }

    public void determineBallDirectionFromHittingPaddle() {
        if (ball.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
            double BALL_SPEED_XY = Math.sqrt(BALL_SPEED_Y * BALL_SPEED_Y + BALL_SPEED_X * BALL_SPEED_X);
            double posX = (ball.getBoundsInParent().getCenterX() - gamePaddle.getBoundsInParent().getCenterX()) / (gamePaddle.getBoundsInLocal().getWidth() / 2);
            double influence = 0.75;

            BALL_SPEED_X = BALL_SPEED_XY * posX * influence;
            BALL_SPEED_Y = Math.sqrt(BALL_SPEED_XY * BALL_SPEED_XY - BALL_SPEED_X * BALL_SPEED_X) * (BALL_SPEED_Y > 0 ? -1 : 1);
        }
    }

    public void reverseBallDirectionFromHittingWall() {
        if (ball.getBoundsInParent().getMaxX() >= WIDTH || ball.getBoundsInParent().getMinX() <= 0) {
            BALL_SPEED_X *= -1;
        }

        if (ball.getBoundsInParent().getMinY() <= 0) {
            BALL_SPEED_Y *= -1;
        }
    }

    public void resetBall() {
        ball.resetBallLocation(300, 450);
        BALL_SPEED_X = 0;
        BALL_SPEED_Y = 150;
    }

    public void createLevelWhenPreviousSucceeded() throws IOException {
        if (levelGenerator.brickList != null) {
            int sizeOfLevel = levelGenerator.brickList.size();
            int numOfNull = (int) levelGenerator.brickList.stream().filter(p -> p.getImage() == null).count();

            if (numOfNull == sizeOfLevel) {
                resetBall();
                if (sizeOfLevel == 15) {
                    scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_2);
                } else if (sizeOfLevel == 20) {
                    scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_3);
                }
                primaryStage.setScene(scene);
                scene.setOnKeyPressed(e -> {
                    try {
                        handle(e.getCode());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }

    public void handle(KeyCode event) throws IOException {
        if (gamePaddle.getBoundsInParent().getMinX() <= 0) {
            if (event == KeyCode.RIGHT) {
                gamePaddle.setX(gamePaddle.getX() + 20);
            }
        } else if (gamePaddle.getBoundsInParent().getMinX() >= WIDTH - gamePaddle.getBoundsInLocal().getWidth()) {
            if (event == KeyCode.LEFT) {
                gamePaddle.setX(gamePaddle.getX() - 20);
            }
        } else {
            if (event == KeyCode.RIGHT) {
                gamePaddle.setX(gamePaddle.getX() + 20);
            }
            if (event == KeyCode.LEFT) {
                gamePaddle.setX(gamePaddle.getX() - 20);
            }
        }
        if (event == KeyCode.R) {
            resetBall();
        }

        if (event == KeyCode.ENTER) {
            scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_1);
            primaryStage.setScene(scene);
            scene.setOnKeyPressed(e -> {
                try {
                    handle(e.getCode());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (event == KeyCode.Q) {
            scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_1);
            primaryStage.setScene(scene);
            scene.setOnKeyPressed(e -> {
                try {
                    handle(e.getCode());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (event == KeyCode.ESCAPE) {
            Platform.exit();
        }

    }


    // Initialize javaFX gui
    public static void main (String[] args) {
        launch(args);
    }

}

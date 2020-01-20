package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;

public class GameStateUpdate extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int POWER_UP_VELOCITY = 80;
    public static final int GOD_MODE_LIVES_MODIFIER = 1000000;
    public static double BALL_SPEED_X = 0;
    public static double BALL_SPEED_Y = 150;

    public static final String BALL_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String BRICK_IMAGE_1 = "brick1.gif";
    public static final String EXTRA_BALL = "extraballpower.gif";
    public static final String LONG_PADDLE = "pointspower.gif";
    public static final String SLOW_BALL = "sizepower.gif";
    public static final String LEVEL_1 = "./resources/level1.txt";
    public static final String LEVEL_2 = "./resources/level2.txt";
    private static final String LEVEL_3 = "./resources/level3.txt";

    Stage primaryStage;
    Levels levelGenerator = new Levels();
    UIElements uiElementsGenerator = new UIElements();
    Scene scene;

    private GamePaddle gamePaddle = new GamePaddle("player", 3, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), 300, 500, 0);
    private Ball ball = new Ball("ball", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE)), 300, 400);
    private ArrayList<PowerUp> powerUpManager = new ArrayList<>();

    // Initialize javaFX gui
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        scene = uiElementsGenerator.createMainSplashScreen();

        scene.setOnKeyPressed(e -> {
            try {
                handleKeyPress(e.getCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

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

        updateBallMovementState(elapsedTime);

        updateBallStateFromPaddle();

        updateBallStateFromWall();

        updateBallStateFromBricks();

        updatePlayerStateFromBallOutofBounds();

        updatePowerUpState(elapsedTime);

        updateLevelOrWinState();

        updateLossState();

    }

    private void updateBallMovementState(double elapsedTime) {
        ball.setX(ball.getX() + BALL_SPEED_X * elapsedTime);
        ball.setY(ball.getY() + BALL_SPEED_Y * elapsedTime);
    }

    private void updateBallStateFromPaddle() {
        if (ball.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
            double BALL_SPEED_XY = Math.sqrt(BALL_SPEED_Y * BALL_SPEED_Y + BALL_SPEED_X * BALL_SPEED_X);
            double posX = (ball.getBoundsInParent().getCenterX() - gamePaddle.getBoundsInParent().getCenterX()) / (gamePaddle.getBoundsInLocal().getWidth() / 2);
            double influence = 0.75;

            BALL_SPEED_X = BALL_SPEED_XY * posX * influence;
            BALL_SPEED_Y = Math.sqrt(BALL_SPEED_XY * BALL_SPEED_XY - BALL_SPEED_X * BALL_SPEED_X) * (BALL_SPEED_Y > 0 ? -1 : 1);
        }
    }

    private void updateBallStateFromWall() {
        if (ball.getBoundsInParent().getMaxX() >= WIDTH || ball.getBoundsInParent().getMinX() <= 0) {
            BALL_SPEED_X *= -1;
        }

        if (ball.getBoundsInParent().getMinY() <= 0) {
            BALL_SPEED_Y *= -1;
        }
    }

    private void updateBallStateFromBricks() {
        if (levelGenerator.brickList != null) {
            for (Sprite sB : levelGenerator.brickList) {
                if (sB.getImage() != null) {
                    if (sB.getBoundsInParent().intersects(ball.getBoundsInParent())) {

                        updateScoreText();

                        double centerBallX = ball.getBoundsInParent().getCenterX();

                        if (centerBallX > ball.getBoundsInParent().getMinX() && centerBallX < ball.getBoundsInParent().getMaxX()) {
                            BALL_SPEED_Y *= -1;
                        } else {
                            BALL_SPEED_X *= -1;
                        }

                        if (sB.type.equals("simpleBrick")) {
                            sB.setImage(null);
                            PowerUp powerUp = randomlyCreatePowerUp(sB);
                            if (powerUp != null) {
                                levelGenerator.returnGroup().getChildren().add(powerUp);
                                powerUpManager.add(powerUp);
                            }
                        } else if (sB.type.equals("lifeBrick")) {
                            sB.setImage(null);
                            PowerUp lifeUp = new PowerUp("powerUpLife",
                                    1,
                                    new Image(this.getClass().getClassLoader().getResourceAsStream(EXTRA_BALL)),
                                    sB.getBoundsInParent().getCenterX(),
                                    sB.getBoundsInParent().getCenterY());
                            levelGenerator.returnGroup().getChildren().add(lifeUp);
                            powerUpManager.add(lifeUp);
                        } else if (sB.type.equals("multiBrick")) {
                            sB.lives--;
                            if (sB.lives == 1) {
                                sB.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1)));
                            } else if (sB.lives == 0) {
                                sB.setImage(null);
                                PowerUp powerUp = randomlyCreatePowerUp(sB);
                                if (powerUp != null) {
                                    levelGenerator.returnGroup().getChildren().add(powerUp);
                                    powerUpManager.add(powerUp);
                                }
                            }
                        } else if (sB.type.equals("bombBrick")) {
                            gamePaddle.lives--;
                            sB.setImage(null);
                            updateLifeText();
                        }
                    }
                }
            }
        }
    }

    private void updatePlayerStateFromBallOutofBounds() {
        if (ball.getBoundsInParent().getMaxY() >= HEIGHT) {
            resetBall();
            resetPaddle();
            gamePaddle.lives--;
            updateLifeText();
        }
    }

    private void updatePowerUpState(double elapsedTime) {
        if (powerUpManager != null) {
            for (PowerUp pU : powerUpManager) {
                pU.setY(pU.getY() + POWER_UP_VELOCITY * elapsedTime);
                if (pU.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
                    if (pU.getImage() != null) {
                        switch (pU.type) {
                            case "powerUpLife":
                                gamePaddle.lives++;
                                updateLifeText();
                                break;
                            case "sizeUP":
                                gamePaddle = levelGenerator.makePaddleLarge((Group) scene.getRoot(), gamePaddle);
                                break;
                            case "ballSlow":
                                BALL_SPEED_Y *= .8;
                                BALL_SPEED_X *= .8;
                                break;
                        }
                    }
                    pU.setImage(null);
                }
            }
        }
    }

    private void updateLevelOrWinState() throws IOException {
        if (levelGenerator.brickList != null) {
            int sizeOfLevel = levelGenerator.brickList.size();
            int numOfNull = (int) levelGenerator.brickList.stream().filter(p -> p.getImage() == null).count();

            if (numOfNull == sizeOfLevel) {
                resetBall();
                if (numOfNull == 30) {
                    uiElementsGenerator.setHighestScore(gamePaddle.score);
                    scene = uiElementsGenerator.createEndSplashScreen(gamePaddle.score);
                    updateScene();
                } else if (numOfNull == 25) {
                    scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_3);
                } else if (numOfNull == 20) {
                    scene = levelGenerator.drawALevel(ball, gamePaddle, LEVEL_2);
                }
                updateScene();
            }
        }
    }

    private void updateLossState() {
        if (gamePaddle.lives == 0) {
            gamePaddle.lives = 3;
            scene = uiElementsGenerator.createFailureScreen(gamePaddle.score);
            updateScene();
        }
    }

    private void resetBall() {
        ball.setBallLocation(300, 400);
        BALL_SPEED_X = 0;
        BALL_SPEED_Y = 150;
    }

    private void resetPaddle() {
        gamePaddle.setPaddleLocation(300, 500);
    }

    private void updateLifeText() {
        uiElementsGenerator.updateText(UIElements.lifeText, "Lives left: " + gamePaddle.lives);
    }

    private void updateScoreText() {
        gamePaddle.score += 100;
        uiElementsGenerator.updateText(UIElements.scoreText, "Score: " + gamePaddle.score);
    }

    private void updateScene() {
        primaryStage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            try {
                handleKeyPress(e.getCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void updateGameLevelScene(String level1) throws IOException {
        scene = levelGenerator.drawALevel(ball, gamePaddle, level1);
        updateScene();
    }

    private void handleKeyPress(KeyCode event) throws IOException {
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
            resetPaddle();
        }

        if (event == KeyCode.ENTER) {
            gamePaddle.lives = 3;
            gamePaddle.score = 0;
            updateGameLevelScene(LEVEL_1);
        }

        if (event.isDigitKey()) {
            if (event == KeyCode.DIGIT1) {
                updateGameLevelScene(LEVEL_1);
            } else if (event == KeyCode.DIGIT2) {
                updateGameLevelScene(LEVEL_2);
            } else {
                updateGameLevelScene(LEVEL_3);
            }
        }

        if (event == KeyCode.S) {
            modifyBrickLives();
        }

        if (event == KeyCode.B) {
            removeBombBrick();
        }

        if (event == KeyCode.G) {
            removeBombBrick();
            gamePaddle.lives += GOD_MODE_LIVES_MODIFIER;
            updateLifeText();
        }

        if (event == KeyCode.ESCAPE) {
            Platform.exit();
        }
        if (event == KeyCode.W) {
            uiElementsGenerator.setHighestScore(gamePaddle.score);
            scene = uiElementsGenerator.createEndSplashScreen(gamePaddle.score);
            updateScene();
        }
    }

    private PowerUp randomlyCreatePowerUp(Sprite sB) {
        if (Math.random() < 0.2) {
            PowerUp sizeUP = new PowerUp("sizeUP",
                    1,
                    new Image(this.getClass().getClassLoader().getResourceAsStream(LONG_PADDLE)),
                    sB.getBoundsInParent().getCenterX(),
                    sB.getBoundsInParent().getCenterY());
            return sizeUP;
        } else if (Math.random() < 0.3) {
            PowerUp ballSlow = new PowerUp("ballSlow",
                    1,
                    new Image(this.getClass().getClassLoader().getResourceAsStream(SLOW_BALL)),
                    sB.getBoundsInParent().getCenterX(),
                    sB.getBoundsInParent().getCenterY());
            return ballSlow;
        } else {
            return null;
        }
    }

    private void removeBombBrick() {
        for (Sprite sB : levelGenerator.brickList) {
            if (sB.type.equals("bombBrick")) {
                sB.setImage(null);
            }
        }
    }

    private void modifyBrickLives() {
        for (Sprite sB : levelGenerator.brickList) {
            if (sB.type.equals("multiBrick")) {
                sB.lives = 1;
            }
        }
    }
}
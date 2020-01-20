package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Purpose: The GameStateUpdate file is the main class of the breakout project. It maintains the overall state of the game
 * through time and call upon various function from within and outside this class to do so. The design goal of this
 * class was to manage all data, information, and visuals that were affected by change. It takes of the movement of
 * key objects like the ball and paddle. It also manages collisions and determines the direction the ball must go as
 * a result of those collisions. Other state changes the class manages include generating power ups when certain
 * conditions are met and providing the results of obtaining those power ups as well. It also manages text changes
 * to update lives and score. Most importantly it transitions between levels and splash screens when appropriate.
 * All of the functionality mentioned above is not managed directly here. The purpose of the other classes was to
 * divide the problem in to smaller functional goals and all those come together and interact together in this class.
 * Depends on all other classes in breakout package as well as File IO, AudioSystem, and standard JavaFx classes such
 * as Group, Scene, and Stage.
 * We assume that all files that are needed exist in the resources. If there was no level text files or audio the class
 * would fail.
 * This class should be used to contain all info about the game that progresses through time. An example on how to use
 * it would be to include methods that involve animations or updates in the step so that they can be displayed properly
 * to the user. Also it is ideal to include logic that creates instances of other classes on demand (like power ups) here
 * and to keep track of their functions by looking through them in the step function since it's run forever.
 */

public class GameStateUpdate extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int POWER_UP_VELOCITY = 80;
    public static final int GOD_MODE_LIVES_MODIFIER = 1000000;
    public static int PADDLE_TRANSLATION_AMOUNT = 20;
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
    public static final String LEVEL_3 = "./resources/level3.txt";
    public static final String SOUND_CLIP = "./resources/pong_beep.wav";
    public static final String BACKGROUND_CLIP = "./resources/music.wav";


    Stage primaryStage;
    Levels levelGenerator = new Levels();
    UIElements uiElementsGenerator = new UIElements();
    Scene scene;

    private GamePaddle gamePaddle = new GamePaddle("player", 3, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), 300, 500, 0);
    private Ball ball = new Ball("ball", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE)), 300, 400);
    private ArrayList<PowerUp> powerUpManager = new ArrayList<>();

    /**
     * Initialize JavaFX GUI
     * @param args Standard java syntax.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The JavaFx program starts here.
     * @param primaryStage A primary stage is required to hold all the subsequent Group and Scene info.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            playBackGroundMusic();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
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
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        });
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * The step function is called repeatedly forever and is therefore the hub of all the game logic.
     * Functions are called by their descriptive name to perform tasks to keep the game progressing.
     * @param elapsedTime Time element important to do animation.
     * @throws IOException Thrown because one of the methods below also throws i/o exception.
     */
    private void step(double elapsedTime) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        updateBallMovementState(elapsedTime);

        updateBallStateFromPaddle();

        updateBallStateFromWall();

        updateBallStateFromBricks();

        updatePlayerStateFromBallOutofBounds();

        updatePowerUpState(elapsedTime);

        updateLevelOrWinState();

        updateLossState();

    }

    /**
     * This function animates the ball movement.
     * @param elapsedTime Time required to perform animation
     */
    private void updateBallMovementState(double elapsedTime) {
        ball.setX(ball.getX() + BALL_SPEED_X * elapsedTime);
        ball.setY(ball.getY() + BALL_SPEED_Y * elapsedTime);
    }

    /**
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void updateBallStateFromPaddle() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (ball.getBoundsInParent().intersects(gamePaddle.getBoundsInParent())) {
            playSound();
            double BALL_SPEED_XY = Math.sqrt(BALL_SPEED_Y * BALL_SPEED_Y + BALL_SPEED_X * BALL_SPEED_X);
            double posX = (ball.getBoundsInParent().getCenterX() - gamePaddle.getBoundsInParent().getCenterX()) / (gamePaddle.getBoundsInLocal().getWidth() / 2);
            double influence = 0.75;

            BALL_SPEED_X = BALL_SPEED_XY * posX * influence;
            BALL_SPEED_Y = Math.sqrt(BALL_SPEED_XY * BALL_SPEED_XY - BALL_SPEED_X * BALL_SPEED_X) * (BALL_SPEED_Y > 0 ? -1 : 1);
        }
    }

    /**
     * This function reverses ball direction depending on which wall is hit.
     */
    private void updateBallStateFromWall() {
        if (ball.getBoundsInParent().getMaxX() >= WIDTH || ball.getBoundsInParent().getMinX() <= 0) {
            BALL_SPEED_X *= -1;
        }

        if (ball.getBoundsInParent().getMinY() <= 0) {
            BALL_SPEED_Y *= -1;
        }
    }

    /**
     * This function manages ball and brick collision interaction. It determines the direction to ball should
     * bounce back to, updates score text, and randomly generates PowerUp objects.
     */
    private void updateBallStateFromBricks() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (levelGenerator.brickList != null) {
            for (Sprite sB : levelGenerator.brickList) {
                if (sB.getImage() != null) {
                    if (sB.getBoundsInParent().intersects(ball.getBoundsInParent())) {

                        updateScoreText();
                        playSound();
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

    /**
     * If the ball goes out of bounds this function is called to reset the ball and paddle and update lives remaining.
     */
    private void updatePlayerStateFromBallOutofBounds() {
        if (ball.getBoundsInParent().getMaxY() >= HEIGHT) {
            resetBall();
            resetPaddle();
            gamePaddle.lives--;
            updateLifeText();
        }
    }

    /**
     * This function manages the interaction between paddle and power up (if they come into contact) and all
     * the specific functionality that comes with each power up.
     * @param elapsedTime Time is required to animate power ups moving down.
     */
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

    /**
     * This function manages the level scenes and update levels based on which ones have already been completed.
     * @throws IOException Thrown error for i/o exception.
     */
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

    /**
     * Function called to check if player is out of lives. If so it goes to failure screen.
     */
    private void updateLossState() {
        if (gamePaddle.lives == 0) {
            gamePaddle.lives = 3;
            scene = uiElementsGenerator.createFailureScreen(gamePaddle.score);
            updateScene();
        }
    }

    /**
     * Function to reset location of ball.
     */
    private void resetBall() {
        ball.setBallLocation(300, 400);
        BALL_SPEED_X = 0;
        BALL_SPEED_Y = 150;
    }

    /**
     * Function to reset location of paddle.
     */
    private void resetPaddle() {
        gamePaddle.setPaddleLocation(300, 500);
    }

    /**
     * Function to update lives left.
     */
    private void updateLifeText() {
        uiElementsGenerator.updateText(UIElements.lifeText, "Lives left: " + gamePaddle.lives);
    }

    /**
     * Function to updated player score.
     */
    private void updateScoreText() {
        gamePaddle.score += 100;
        uiElementsGenerator.updateText(UIElements.scoreText, "Score: " + gamePaddle.score);
    }

    /**
     * This function creates a new scene using the Levels class and includes new brick config, ball, and paddle.
     * @param level Constant path to level file needed to create a level.
     * @throws IOException Thrown error for i/o exception.
     */
    private void updateGameLevelScene(String level) throws IOException {
        scene = levelGenerator.drawALevel(ball, gamePaddle, level);
        updateScene();
    }

    /**
     * This function manages actually making the stage switch to a new scene and attaches the event handler
     * to the new scene so that you don't lose keyboard functionality.
     */
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

    /**
     * This function manages all keyboard input.
     * @param event Keyboard input event.
     * @throws IOException Thrown error for i/o exception.
     */
    private void handleKeyPress(KeyCode event) throws IOException {
        if (gamePaddle.getBoundsInParent().getMinX() <= 0) {
            if (event == KeyCode.RIGHT) {
                gamePaddle.setX(gamePaddle.getX() + PADDLE_TRANSLATION_AMOUNT);
            }
        } else if (gamePaddle.getBoundsInParent().getMinX() >= WIDTH - gamePaddle.getBoundsInLocal().getWidth()) {
            if (event == KeyCode.LEFT) {
                gamePaddle.setX(gamePaddle.getX() - PADDLE_TRANSLATION_AMOUNT);
            }
        } else {
            if (event == KeyCode.RIGHT) {
                gamePaddle.setX(gamePaddle.getX() + PADDLE_TRANSLATION_AMOUNT);
            }
            if (event == KeyCode.LEFT) {
                gamePaddle.setX(gamePaddle.getX() - PADDLE_TRANSLATION_AMOUNT);
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
        if (event == KeyCode.L) {
            gamePaddle.lives++;
            updateLifeText();
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

        if (event == KeyCode.Z) {
            PADDLE_TRANSLATION_AMOUNT = 30;
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

    /**
     * This function is used to randomly generate (or not generate) a power up when a brick is destroyed.
     * @param sB Parameter used to get x and y location needed to spawn a power up.
     * @return Returns PowerUp object of randomly specified type.
     */
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

    /**
     * Called as part of a cheat code to remove bomb bricks from the level.
     */
    private void removeBombBrick() {
        for (Sprite sB : levelGenerator.brickList) {
            if (sB.type.equals("bombBrick")) {
                sB.setImage(null);
            }
        }
    }

    /**
     * Called as part of a cheat code to set all brick lives to 1 so they only take 1 hit to destroy.
     */
    private void modifyBrickLives() {
        for (Sprite sB : levelGenerator.brickList) {
            if (sB.type.equals("multiBrick")) {
                sB.lives = 1;
            }
        }
    }

    /**
     * Plays the wav file.
     * @throws LineUnavailableException Required to play sound.
     * @throws IOException Required to play sound.
     * @throws UnsupportedAudioFileException Required to play sound.
     */
    private void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(SOUND_CLIP)));
        clip.start();
    }

    /**
     * Plays wav file for background music.
     * @throws LineUnavailableException Required to play sound.
     * @throws IOException Required to play sound.
     * @throws UnsupportedAudioFileException Required to play sound.
     */
    private void playBackGroundMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(BACKGROUND_CLIP)));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
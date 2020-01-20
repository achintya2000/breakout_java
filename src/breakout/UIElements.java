package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Scanner;

/**
 * Purpose: This class is used to generate all the UI elements that are used in the game.
 * We assume that the file with the high score does exist and has a high score or else the class may not work.
 * We also assume we have initialized the proper window dimensions in the other classes or else the text may not
 * appear in the ideal location.
 * This depends on Scanner, Group, Scene, Text, and Color.
 * An example of how to use it would be to create an instance of UIElements. Then you can call methods that
 * return scenes and display those scenes in the stage. Also you can use this class to update text in Text objects.
 * First simply use the createText function to create a Text object in your group. Then use updateText to update the
 * String that is displayed there.
 */
public class UIElements {
    public static final String TITLE = "Achintya's Breakout Game";
    public static Text lifeText;
    public static Text scoreText;
    private static final String HIGHEST_SCORE_FILE = "./resources/highScore.txt";

    /**
     * The main splashscreen welcomes the player and tells them how to play.
     * Assume that proper location coordinates are given or else text won't appear in the right location.
     * @return A scene object containing all main splashscreen information.
     */
    public Scene createMainSplashScreen() {
        Group root = new Group();
        addText(root, TITLE, 30, 150, 50);
        addText(root, "Use arrow keys to move left and right to break bricks!", 20, 100, 150);
        addText(root, "Press Enter to begin", 20, 200, 400);
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    /**
     * The failure screen appears when the player runs out of lives. It displays their score.
     * Assume that proper coordinates are given for location otherwise text won't appear properly.
     * @param score The score of the player.
     * @return A scene object containing all failure screen information.
     */
    public Scene createFailureScreen(int score) {
        Group root = new Group();
        addText(root, "Unfortunately you lost!", 20, 200, 150);
        addText(root, "Your score was: " + score, 20, 200, 250);
        addText(root, "Press Enter to try again.", 20, 200, 400);
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    /**
     * The end splashscreen appears if the player has won and beat all the levels in the game.
     * It also shows the highest ever score so that they will be tempted to replay to beat the high score.
     * Assume that file exists in that location with the highest score or else function won't work.
     * @param score The score obtained by the player in the current game run.
     * @return A scene containing all information if player has won.
     * @throws FileNotFoundException Returned if high score text file is not found.
     */
    public Scene createEndSplashScreen(int score) throws FileNotFoundException {
        Group root = new Group();
        addText(root, "Congratulations You Won!", 20, 200, 150);
        addText(root, "You're final score was: " + score, 20, 200, 250);
        addText(root, "The highest ever score was: " + getHighestScore(), 20, 200, 350);
        addText(root, "Press Enter to play Again.", 20, 200, 400);
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    /**
     * Text objects are created in this method for modularity purposes. They are used in Levels to attach a text
     * object to a scene so that they may be easily updated information changes.
     * Assume proper coordinates are given or else it won't display in the scene.
     * @param s String passed in that is displayed in the scene.
     * @param font Set the size of the font.
     * @param x The x parameter represents horizontal location and is used to set location on the scene.
     * @param y The y parameter represents vertical location and is used to set location on the scene.
     * @return Returns a Text object containing all the info about the text created.
     */
    public Text createText(String s, int font, int x, int y) {
        Text text = new Text();
        text.setFill(Color.WHITE);
        text.setFont(new Font(font));
        text.setX(x);
        text.setY(y);
        text.setText(s);
        return text;
    }

    /**
     * This method is used to update the string displayed in a text object.
     * Assume proper object is passed or else text will not change.
     * @param t Text object to determine which object to change.
     * @param s String that you want to update the text object to now contain.
     */
    public void updateText(Text t, String s) {
        t.setText(s);
    }

    /**
     * This method is used externally when the game finished to set the highest score in the text
     * file if the current game score is truly the high score.
     * Assume file is found and has an int or else highest score won't update.
     * @param gameScore Current game score.
     * @throws IOException Thrown if high score text file is not found.
     */
    public void setHighestScore(int gameScore) throws IOException {
        Scanner scan = new Scanner(new File(HIGHEST_SCORE_FILE));

        int highScore = scan.nextInt();
        System.out.println(highScore);

        if (gameScore > highScore) {
            PrintWriter wr = new PrintWriter(HIGHEST_SCORE_FILE);
            wr.print(gameScore);
            wr.close();
        }
    }

    /**
     * Get the highest score from the text file.
     * @return Integer representing the highest score.
     * @throws FileNotFoundException Thrown if high score text file is not found.
     */
    private int getHighestScore() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(HIGHEST_SCORE_FILE));
        return scan.nextInt();
    }

    /**
     * Method used to reduce duplicated code and is used to add a text object to a group.
     * @param root Group to add Text object to.
     * @param title String that text object should contain.
     * @param i Font
     * @param i2 X location
     * @param i3 Y location
     */
    private void addText(Group root, String title, int i, int i2, int i3) {
        root.getChildren().add(createText(title, i, i2, i3));
    }

}

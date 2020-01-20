package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Scanner;

/**
 * This class is used to
 */
public class UIElements {
    public static final String TITLE = "Achintya's Breakout Game";
    public static Text lifeText;
    public static Text scoreText;
    private static final String HIGHEST_SCORE_FILE = "./resources/highScore.txt";

    public Scene createMainSplashScreen() {
        Group root = new Group();
        addText(root, TITLE, 30, 150, 50);
        addText(root, "Use arrow keys to move left and right to break bricks!", 20, 100, 150);
        addText(root, "Press Enter to begin", 20, 200, 400);
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    public Scene createFailureScreen(int score) {
        Group root = new Group();
        addText(root, "Unfortunately you lost!", 20, 200, 150);
        addText(root, "Your score was: " + score, 20, 200, 250);
        addText(root, "Press Enter to try again.", 20, 200, 400);
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

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

    public Text createText(String s, int font, int x, int y) {
        Text text = new Text();
        text.setFill(Color.WHITE);
        text.setFont(new Font(font));
        text.setX(x);
        text.setY(y);
        text.setText(s);
        return text;
    }

    public void updateText(Text t, String s) {
        t.setText(s);
    }

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

    private int getHighestScore() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(HIGHEST_SCORE_FILE));
        return scan.nextInt();
    }

    private void addText(Group root, String title, int i, int i2, int i3) {
        root.getChildren().add(createText(title, i, i2, i3));
    }

}

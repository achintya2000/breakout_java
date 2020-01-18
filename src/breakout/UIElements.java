package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIElements {
    public static final String TITLE = "Achintya's Breakout Game";
    public static Text lifeText;
    public static Text scoreText;

    public Scene createMainSplashScreen() {
        Group root = new Group();
        root.getChildren().add(createText(TITLE, 30, 150, 50));
        root.getChildren().add(createText("Use arrow keys to move left and right to break bricks!", 20, 100, 150));
        root.getChildren().add(createText("Press Enter to begin", 20, 200, 400));
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    public Scene createFailureScreen() {
        Group root = new Group();
        root.getChildren().add(createText("Unfortunately you lost!", 20, 200, 150));
        root.getChildren().add(createText("Press Enter to try again.", 20, 200, 400));
        Scene scene = new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
        scene.setFill(Color.BLACK);
        return scene;
    }

    public Scene createEndSplashScreen(int score) {
        Group root = new Group();
        root.getChildren().add(createText("Congratulations You Won!", 20, 200, 150));
        root.getChildren().add(createText("You're final score was: " + score, 20, 200, 150));
        root.getChildren().add(createText("Press Enter to play Again.", 20, 200, 400));
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

}

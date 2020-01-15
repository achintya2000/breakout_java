package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;

public class UIElements {
    public static final String TITLE = "Achintya's Breakout Game";
    public static Text scoreText;

    public Scene createMainSplashScreen() {
        Group root = new Group();
        ObservableList list = root.getChildren();
        list.add(createText(TITLE,30, 150, 50));
        list.add(createText("Use arrow keys to move left and right to break bricks!",20, 100, 150));
        list.add(createText("Press Enter to begin",20, 200, 400));
//        list.add(writeText(TITLE, 30, 150, 50));
//        list.add(writeText("Use arrow keys to move left and right to break bricks!", 20, 100, 150));
//        list.add(writeText("Press Enter to begin", 20, 200, 400));

        return new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
    }

    public Scene createEndSplashScreen() {
        return null;
    }

//    public void keepScore(Scene scene, Text t, GamePaddle player) {
//
//        Text newMessage = updateText(t, "Score is: " + player.lives);
//        Group g = (Group) scene.getRoot();
//        g.getChildren().add(newMessage);
//    }

    public Text createText(String s, int font, int x, int y) {
        Text text = new Text();
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

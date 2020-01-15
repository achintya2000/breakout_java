package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIElements {
    public Scene createMainSplashScreen() {
        Group root = new Group();
        ObservableList list = root.getChildren();
        list.add(writeText("Breakout", 30, 250, 50));
        list.add(writeText("Use arrow keys to move left and right to break bricks!", 20, 100, 150));
        list.add(writeText("Press Enter to begin", 20, 200, 400));
        return new Scene(root, GameStateUpdate.WIDTH, GameStateUpdate.HEIGHT);
    }

    public Text writeText(String message, int font, int x, int y) {
        Text text = new Text();
        text.setFont(new Font(font));
        text.setX(x);
        text.setY(y);
        text.setText(message);
        return text;
    }
}

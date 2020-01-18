package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GamePaddle extends Sprite {

    int score = 0;

    GamePaddle(String name, int lives, Image image, int x, int y, int score) {
        super(name, lives, image);
        setX(x);
        setY(y);
        this.score = score;
    }
}

package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GamePaddle extends Sprite {

    boolean dead = false;

    GamePaddle(String name, int lives, Image image, int x, int y) {
        super(name, lives, image);
        setX(x);
        setY(y);
    }
}

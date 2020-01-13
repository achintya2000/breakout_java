package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimpleBrick extends Sprite {

    boolean dead = false;

    SimpleBrick(int x, int y, Image image, int lives, String type) {
        super(type, lives, image);
        setX(x);
        setY(y);
    }
}

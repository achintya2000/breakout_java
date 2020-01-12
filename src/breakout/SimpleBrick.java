package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimpleBrick extends Sprite {

    boolean dead = false;
    int numLives;

    SimpleBrick(int x, int y, Image image, Color color, int lives, String type) {
        super(type, lives, image);
        setTranslateX(x);
        setTranslateY(y);
        this.numLives = lives;
    }
}

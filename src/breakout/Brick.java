package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {

    boolean dead = false;
    int numLives;
    final String type;

    Brick(int x, int y, int w, int h, Color color, int lives, String type) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
        this.type = type;
        this.numLives = lives;
    }
}

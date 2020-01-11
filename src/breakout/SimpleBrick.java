package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimpleBrick extends Rectangle {
    boolean hit = false;

    SimpleBrick(int x, int y, int w, int h, Color color) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
    }
}

package breakout;

import javafx.scene.image.Image;

public class Ball extends Sprite {

    Ball(String name, int lives, Image image, int x, int y) {
        super(name, lives, image);
        setX(x);
        setY(y);
    }

    public void resetBallLocation(int x, int y) {
        setX(x);
        setY(y);
    }

}

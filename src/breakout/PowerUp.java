package breakout;

import javafx.scene.image.Image;

public class PowerUp extends Sprite {

    double spawnX;
    double spawnY;

    PowerUp(String name, int lives, Image image, double x, double y) {
        super(name, lives, image);
        this.spawnX = x;
        this.spawnY = y;
        setX(spawnX);
        setY(spawnY);
    }

}

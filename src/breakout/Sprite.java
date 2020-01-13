package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {
    String type = "";
    int lives;

    Sprite(String name, int lives, Image image) {
        super(image);
        this.type = name;
        this.lives = lives;
    }

}

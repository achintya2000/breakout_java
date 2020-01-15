package breakout;

import javafx.scene.Group;
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

    public void addToGroup(Group group, PowerUp pU) {
        group.getChildren().add(pU);
    }

}

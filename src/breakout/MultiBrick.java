package breakout;

import javafx.scene.image.Image;

/**
 * This class was added for naming convention. The class MultiBrick is named as so because it is extremely
 * versatile and modular. All the brick objects including simple bricks, many hit bricks, life bricks and bomb bricks
 * use the MultiBrick class as their blueprint.
 */
public class MultiBrick extends Sprite {

    MultiBrick(String name, int lives, Image image) {
        super(name, lives, image);
    }

}

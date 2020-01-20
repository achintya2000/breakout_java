package breakout;

import javafx.scene.image.Image;

/**
 * Purpose: This class was added for naming convention. The class MultiBrick is named as so because it is extremely
 * versatile and modular. All the brick objects including simple bricks, many hit bricks, life bricks and bomb bricks
 * use the MultiBrick class as their blueprint.
 * Depends on Sprite and therefore ImageView.
 * Assume all variables passed to constructor are proper.
 * An example of how to use this would be to make an object of it in the levels class when you detect from the text file
 * that a brick must be created.
 */
public class MultiBrick extends Sprite {

    MultiBrick(String name, int lives, Image image) {
        super(name, lives, image);
    }

}

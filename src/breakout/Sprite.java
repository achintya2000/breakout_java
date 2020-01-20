package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Sprite class is the top class in my hierarchy. It extends ImageView and I chose to do this because
 * there are common fields among all its child classes which can be used as a common blueprint that can be extended
 * to all child classes. This is extremely important and I specifically chose to do this to meet Object Oriented
 * Programming design standards that encourage common functionality/data between multiple child classes to be
 * extracted and moved up in the hierarchy for a common parent class. Using ImageView as my template was specifically
 * chosen over Shapes like Rectangle because I can then keep all the objects in my game under just one parent instead
 * including both Rectangles and Circles Shapes for example.
 */
public class Sprite extends ImageView {
    String type = "";
    int lives;

    /**
     * This is the default constructor for the
     * @param name The string value that is used to identify the Sprite object.
     * @param lives The number of lives for any Sprite (or child) object.
     * @param image The .gif file that is used to display the Sprite in the scene.
     */
    Sprite(String name, int lives, Image image) {
        super(image);
        this.type = name;
        this.lives = lives;
    }

}

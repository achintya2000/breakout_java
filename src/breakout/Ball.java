package breakout;

import javafx.scene.image.Image;

/**
 * The Ball class extends Sprite to add ball specific functionality, such as determining where to spawn it, while also
 * keeping the Sprite class information.
 */
public class Ball extends Sprite {

    /**
     * The default constructor of the Ball adds functionality to determine where to spawn the ball as well as
     *
     * @param name The name of the Ball is passed to the parent Sprite class.
     * @param lives The number of lives for the ball is passed to the parent Sprite class.
     * @param image The Image used by the Ball is passed to the parent Sprite class.
     * @param x The x parameter represents horizontal direction in the window's scene and is used to
     *          set the ball's default location on the scene.
     * @param y The y parameter represents vertical direction in the window's scene and is used to set
     *          the ball's default location on the scene.
     */
    Ball(String name, int lives, Image image, int x, int y) {
        super(name, lives, image);
        setX(x);
        setY(y);
    }

    /**
     * This method is used to set the game ball to a specific location.
     * @param x The x parameter represents horizontal direction in the window's scene and is used to
     *          set the ball's location on the scene.
     * @param y The y parameter represents vertical direction in the window's scene and is ued to set
     *          ball's location on the scene.
     */
    public void setBallLocation(int x, int y) {
        setX(x);
        setY(y);
    }

}

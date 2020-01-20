package breakout;

import javafx.scene.image.Image;

/**
 * Purpose: The Ball class extends Sprite to add ball specific functionality, such as determining where to spawn it, while also
 * keeping the Sprite class information.
 * Depends on Sprite and in turn ImageView.
 * We assume that all values and images passed to the constructor exist and are of proper types.
 * Ex: We make an object out of this class in game state update and use set ball location to reset its location after
 * it goes off screen.
 */
public class Ball extends Sprite {

    /**
     * The default constructor of the Ball adds functionality to determine where to spawn the ball as well as
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
     * We assume we pass proper x and y values or else it will cause the ball to go outside the window.
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

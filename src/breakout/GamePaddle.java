package breakout;

import javafx.scene.image.Image;

/**
 * The game paddle class is a child of Sprite and represents the player on the screen. Therefore in addition to the
 * standard Sprite fields it has unique parameters that represent the player score.
 */
public class GamePaddle extends Sprite {
    /**
     * This the default constructor for the game paddle class.
     * @param name Name string used to identify the object is sent to parent Sprite.
     * @param lives Number of lives that represents the player is also sent to parent Sprite.
     * @param image The image used to represent the object is sent to the parent Sprite.
     * @param x The x parameter represents horizontal location and is used to set location on the scene.
     * @param y The y parameter represents vertical location and is used to set location on the scene.
     * @param score The score represents the player's score and is attached to the game paddle as it
     *              represents the player.
     */
    int score;
    GamePaddle(String name, int lives, Image image, int x, int y, int score) {
        super(name, lives, image);
        setX(x);
        setY(y);
        this.score = score;
    }

    /**
     * Set the location of the paddle to a specific location on the screen.
     * @param x The x parameter represents horizontal direction in the window's scene and is used to
     *          set the paddle's location on the scene.
     * @param y The y parameter represents vertical direction in the window's scene and is used to
     *          set the paddle's location on the scene.
     */
    public void setPaddleLocation(int x, int y) {
        setX(x);
        setY(y);
    }
}

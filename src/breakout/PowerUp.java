package breakout;

import javafx.scene.image.Image;

/**
 * The PowerUp class was extended from Sprite to create a common blueprint for all the different types of power ups in
 * the game. It is used mainly to create a power up manager array in Game State Update that manages the state of all
 * the power ups in the game.
 */
public class PowerUp extends Sprite {

    double spawnX;
    double spawnY;

    /**
     * This is the default constructor for the powerUp class.
     * @param name The name is used to identify the object and sent to the Sprite parent.
     * @param lives Lives are used to manage the existence of the power up and are sent to the parent Sprite class.
     * @param image Images are used to display the picture of the power up on the scene and are send to parent Sprite.s
     * @param x The x parameter represents horizontal location and is used to set location on the scene.
     * @param y The y parameter represents vertical location and is used to set location on the scene.
     */
    PowerUp(String name, int lives, Image image, double x, double y) {
        super(name, lives, image);
        this.spawnX = x;
        this.spawnY = y;
        setX(spawnX);
        setY(spawnY);
    }
}

package breakout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Purpose: The levels class contains all data and information required to generate a level from a text file.
 * The assumptions used are that the level files are formatted correctly and actually exist.
 * Depends on File IO packages, TilePane, Group, UIElements, GamePaddle, Ball, and Scene.
 * Example: Create object of Class - Levels l = new Levels(). Provide a ball, game paddle and path to
 * level file and you can use the function makeLevel to return a scene that displays all that info.
 * If making a new level you don't need to update this file. Update GameStateUpdate to add an else if
 * case to updateLevelOrWinState and that is all that's needed to create levels.
 */
public class Levels {
    private static final String BRICK_IMAGE_1 = "brick1.gif";
    private static final String BRICK_IMAGE_2 = "brick2.gif";
    private static final String BRICK_IMAGE_3 = "brick10.gif";
    private static final String BRICK_IMAGE_4 = "brick4.gif";
    private static final String PADDLE_IMAGE_LARGE = "paddle_long.gif";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    ArrayList<Sprite> brickList;

    TilePane tilePane;
    Group root;

    UIElements uiElementsGenerator = new UIElements();

    /**
     * This is a highly modular function that can take in a path to any text file that is formatted properly and
     * generate a level from it. Tile Panes are used to automatically render the bricks in an organized nx5 grid.
     * Score and lives information is also attached to the scene for the user.
     * Assume that proper ball and paddle passed so they may be reattached to scene. Also assume proper files are found
     * for the bricks or else levels will just be blank in the game.
     * @param ball The ball object that is created and must be rendered in the scene.
     * @param gamePaddle The game paddle object that is created and must be rendered in the scene.
     * @param path Path to the level text file which you need to draw.
     * @return A scene object that you can use to set what content is displayed to the user.
     * @throws IOException Thrown if level text file is not found.
     */
    public Scene drawALevel(Ball ball, GamePaddle gamePaddle, String path) throws IOException {
        root = new Group();
        gamePaddle.setPaddleLocation(300, 500);
        ball.setBallLocation(300, 400);
        root.getChildren().add(gamePaddle);
        root.getChildren().add(ball);

        ArrayList<String> levelContent = readTextFile(path);
        brickList = new ArrayList<>();

        for (String s : levelContent) {
            if (s.equals("S")) {
                brickList.add(new MultiBrick("simpleBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1))));
            } else if (s.equals("M")) {
                brickList.add(new MultiBrick("multiBrick", 2, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_2))));
            } else if (s.equals("L")) {
                brickList.add(new MultiBrick("lifeBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_4))));
            } else if (s.equals("B")) {
                brickList.add(new MultiBrick("bombBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_3))));
            }

        }

        tilePane = new TilePane();
        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);

        UIElements.lifeText = uiElementsGenerator.createText("Lives left: " + gamePaddle.lives, 20, 25, 550);
        root.getChildren().add(UIElements.lifeText);
        UIElements.scoreText = uiElementsGenerator.createText("Score: " + gamePaddle.score, 20, 450, 550);
        root.getChildren().add(UIElements.scoreText);

        Scene newScene = new Scene(root, HEIGHT, WIDTH);
        newScene.setFill(Color.BLACK);

        return newScene;
    }

    /**
     * Returns the group where the rest of level content is held if another object needs to be added to the same scene.
     * Assume a group has been created through the makeLevel function or else returned group will be null.
     * @return Group object where rest of level content is held.
     */
    public Group returnGroup() {
        return root;
    }

    /**
     * This is function is called when you obtain the big paddle power up that greatly increases the size of your paddle.
     * Assume correct file is found for game paddle big or else nothing will display to user. Also assume correct group
     * and game paddle passed or else the old paddle won't be removed from the scene.
     * @param root Main group that contains level info so the new object can be added there.
     * @param gamePaddle The existing game paddle object that needs to be replaced by the big version.
     * @return A new game paddle object that is a bigger version of regular paddle.
     */
    public GamePaddle makePaddleLarge(Group root, GamePaddle gamePaddle) {
        String name = gamePaddle.type;
        int lives = gamePaddle.lives;
        root.getChildren().removeAll(gamePaddle);
        GamePaddle gamePaddleBig = new GamePaddle(name, lives, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE_LARGE)), 300, 500, gamePaddle.score);
        root.getChildren().add(gamePaddleBig);
        return gamePaddleBig;
    }

    /**
     * Function that reads information from a text file.
     * @param path String path to the level text file.
     * @return Returns an array list of all the individual strings delimited by spaces that represent a brick.
     * @throws IOException Thrown if file is not found.
     */
    private ArrayList<String> readTextFile(String path) throws IOException {
        String content = Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
        return new ArrayList<>(Arrays.asList(content.split(" ")));
    }
}

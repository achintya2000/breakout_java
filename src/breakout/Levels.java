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

public class Levels {
    public static final String BRICK_IMAGE_1 = "brick1.gif";
    public static final String BRICK_IMAGE_2 = "brick2.gif";
    public static final String BRICK_IMAGE_3 = "brick10.gif";
    public static final String BRICK_IMAGE_4 = "brick4.gif";
    public static final String BALL_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    ArrayList<Sprite> brickList;

    //TilePane tilePane = new TilePane();
    TilePane tilePane;
    Group root;

    UIElements uiElementsGenerator = new UIElements();

    public Scene drawALevel(Ball ball, GamePaddle gamePaddle, String path) throws IOException {
        root = new Group();
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

        UIElements.scoreText = uiElementsGenerator.createText("Lives left: " + gamePaddle.lives, 20, 25, 550);
        root.getChildren().add(UIElements.scoreText);

        Scene newScene = new Scene(root, HEIGHT, WIDTH);
        newScene.setFill(Color.BLACK);

        return newScene;
    }

    public Group returnGroup() {
        return root;
    }

    public ArrayList<String> readTextFile(String path) throws IOException {
        String content = Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
        ArrayList<String> levelString = new ArrayList<>();
        levelString.addAll(Arrays.asList(content.split(" ")));
        return levelString;
    }
}

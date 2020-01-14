package breakout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Levels {
    public static final String BRICK_IMAGE_1 = "brick1.gif";
    public static final String BRICK_IMAGE_2 = "brick2.gif";
    public static final String BRICK_IMAGE_3 = "brick3.gif";
    public static final String BRICK_IMAGE_4 = "brick4.gif";
    public static final String BALL_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    ArrayList<Sprite> brickList;

    //TilePane tilePane = new TilePane();
    TilePane tilePane;
    Group root;

    public Scene drawLevel1(Ball ball, GamePaddle gamePaddle){
        root = new Group();
        root.getChildren().add(gamePaddle);
        root.getChildren().add(ball);

        tilePane = new TilePane();

        brickList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            brickList.add(new MultiBrick("simpleBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1))));
        }

        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);

        root.getChildren().add(tilePane);

        Scene newScene = new Scene(root, HEIGHT, WIDTH);

        return newScene;
    }

    public Scene drawLevel2(Ball ball, GamePaddle gamePaddle) {
        root = new Group();
        root.getChildren().add(gamePaddle);
        root.getChildren().add(ball);

        tilePane = new TilePane();

        brickList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            brickList.add(new MultiBrick("simpleBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1))));
        }
        for (int i = 0; i < 3; i++) {
            brickList.add(new MultiBrick("multiBrick", 2, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_2))));
        }
        for (int i = 0; i < 2; i++) {
            brickList.add(new MultiBrick("lifeBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_4))));
        }

        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);

        Scene newScene = new Scene(root, HEIGHT, WIDTH);

        return newScene;
    }

    public Group returnGroup() {
        return root;
    }

    public ArrayList<String> readTextFile() throws IOException {
        String content = Files.readString(Paths.get("./resources/test.txt"), StandardCharsets.US_ASCII);
        ArrayList<String> test = new ArrayList<>();
        test.addAll(Arrays.asList(content.split(" ")));
        System.out.println(content);
        System.out.println(test);
        return test;
    }
}

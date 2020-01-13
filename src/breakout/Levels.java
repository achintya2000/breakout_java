package breakout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Levels {
    public static final String BRICK_IMAGE_1 = "brick1.gif";
    public static final String BRICK_IMAGE_2 = "brick2.gif";

    ArrayList<Sprite> brickList;

    public void drawLevel1(Group root){

        brickList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            brickList.add(new SimpleBrick(0, 0, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1)), 1, "simpleBrick"));
        }

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);

    }

}

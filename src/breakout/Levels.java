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
    public static final String BRICK_IMAGE_3 = "brick3.gif";

    ArrayList<Sprite> brickList;

    TilePane tilePane = new TilePane();

    public void drawLevel1(Group root){

        brickList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            brickList.add(new MultiBrick("simpleBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1))));
        }

        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);

    }

    public void drawLevel2(Group root) {
        brickList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            brickList.add(new MultiBrick("multiBrick", 2, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_2))));
        }
        for (int i = 0; i < 20; i++) {
            brickList.add(new MultiBrick("simpleBrick", 1, new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE_1))));
        }

        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);
    }

}

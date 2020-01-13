package breakout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Levels {
    public static final String PADDLE_IMAGE = "brick1.gif";

    ArrayList<SimpleBrick> brickList;

    public void drawLevel1(Group root){

        brickList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            brickList.add(new SimpleBrick(0, 0, new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), 1, "simpleBrick"));
        }

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);

        tilePane.getChildren().addAll(brickList);
        root.getChildren().add(tilePane);

//        SimpleBrick[] simpleSimpleBrickList = new SimpleBrick[20];
//        for (int i = 0; i < 20; i++) {
//            //simpleSimpleBrickList[i] = new SimpleBrick(0,0, 100,50, Color.BLUE, 1, "simple");
//        }
//
//        TilePane tilePane = new TilePane();
//        tilePane.setPadding(new Insets(20, 75, 20, 75));
//        tilePane.setVgap(25);
//        tilePane.setHgap(25);
//        tilePane.setPrefColumns(5);
//        for (int i = 0; i < 20; i++) {
//            tilePane.getChildren().add(simpleSimpleBrickList[i]);
//        }
//        root.getChildren().add(tilePane);
    }
}

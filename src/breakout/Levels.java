package breakout;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Levels {

    public void drawLevel1(Group root){
        Brick[] simpleBrickList = new Brick[20];
        for (int i = 0; i < 20; i++) {
            simpleBrickList[i] = new Brick(0,0, 100,50, Color.BLUE, 1, "simple");
        }

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(20, 75, 20, 75));
        tilePane.setVgap(25);
        tilePane.setHgap(25);
        tilePane.setPrefColumns(5);
        for (int i = 0; i < 20; i++) {
            tilePane.getChildren().add(simpleBrickList[i]);
        }
        root.getChildren().add(tilePane);
    }
}

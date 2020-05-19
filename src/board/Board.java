package board;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board
 */
public class Board extends GridPane {

    private Image ex, oh;

    /**
     * Initializes the board to a 3x3 square of
     */
    public Board(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
        super();

        ex = new Image(getClass().getClassLoader().getResourceAsStream("Icons/Ex.png"));
        oh = new Image(getClass().getClassLoader().getResourceAsStream("Icons/Oh.png"));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    ImageView im = new ImageView(ex);
                    im.fitWidthProperty().bind(width.divide(3));
                    im.fitHeightProperty().bind(height.divide(3));
                    add(im, i, j);
                } else {
                    ImageView im = new ImageView(oh);
                    im.fitWidthProperty().bind(width.divide(3));
                    im.fitHeightProperty().bind(height.divide(3));
                    add(im, i, j);
                }
            }
        }
    }
}

package gamePieces;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.GUI;

/**
 * Represents the game board
 */
public class Board extends GridPane {

    private Cell[][] cells;


    public Board(Pane parent) {
        super();
        setGridLinesVisible(true);
        setAlignment(Pos.CENTER);
//        minWidthProperty().bind(parent.minWidthProperty());
//        maxWidthProperty().bind(parent.minWidthProperty());
//        minHeightProperty().bind(parent.minHeightProperty());
//        maxHeightProperty().bind(parent.minHeightProperty());

//        ReadOnlyDoubleProperty width = widthProperty();
//        minHeightProperty().bind(width);
//        maxHeightProperty().bind(width);

//        setBorder(GUI.border);

        cells = new Cell[3][3];

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                Cell cell = new Cell(r,c, parent);
                cells[r][c] = cell;
                add(cell, r, c);
            }
        }
    }
}

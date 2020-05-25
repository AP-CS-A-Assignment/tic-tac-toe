package gamePieces;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board
 */
public class Board extends GridPane {

    private Cell[][] cells;


    public Board() {
        super();
        setGridLinesVisible(true);

        cells = new Cell[3][3];

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                Cell cell = new Cell(r,c);
                cells[r][c] = cell;
                add(cell, r, c);
            }
        }
    }
}

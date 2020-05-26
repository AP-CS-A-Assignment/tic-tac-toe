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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents the game board
 */
public class Board extends GridPane {

    private Cell[][] cells;
    private LinkedList<Cell> freeCells;


    public Board(Pane container) {
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
                Cell cell = new Cell(r,c, container);
                cells[r][c] = cell;
                add(cell, c, r);
            }
        }

        freeCells = new LinkedList<>();
    }

    public CellStates checkWin() {

        CellStates winState = CellStates.EMPTY;

        //Checks the colums for wins
        for (int c = 0; c < cells[0].length; c++) {
            boolean threeInARow = true;

            CellStates firstState  = cells[0][c].getState();

            for (int r = 1; r < cells.length; r++) {
                CellStates currentCellState = cells[r][c].getState();
                if (!currentCellState.equals(firstState) || currentCellState.equals(CellStates.EMPTY)) {
                    threeInARow = false;
                }
            }

            if (threeInARow) {
                winState = firstState;
            }

        }

        //checks the rows for wins
        for (int r = 0; r < cells.length; r++) {
            boolean threeInARow = true;

            CellStates firstState  = cells[r][0].getState();

            for (int c = 1; c < cells.length; c++) {
                CellStates currentCellState = cells[r][c].getState();
                if (!currentCellState.equals(firstState) || currentCellState.equals(CellStates.EMPTY)) {
                    threeInARow = false;
                }
            }

            if (threeInARow) {
                winState = firstState;
            }

        }

        CellStates diagonal = checkDiagonal();
        if (!diagonal.equals(CellStates.EMPTY)) {
            winState = diagonal;
        }

        return winState;

    }

    public CellStates checkDiagonal() {
        CellStates winState = CellStates.EMPTY;

        //checks the left diagonal
        boolean threeInARow = true;
        CellStates firstState  = cells[0][0].getState();

        for (int r = 1; r < cells.length; r++) {
            for (int c = 1; c < cells[0].length; c++) {

                if (r == c) {
                    CellStates currentCellState = cells[r][c].getState();
                    if (!currentCellState.equals(firstState) || currentCellState.equals(CellStates.EMPTY)) {
                        threeInARow = false;
                    }
                }

            }
        }

        if (threeInARow) {
            winState = firstState;
        }

        //checks the right diagonal
        threeInARow = true;
        firstState  = cells[0][cells.length - 1].getState();
        System.out.println("firstState = " + firstState);

        for (int r = 1; r < cells.length; r++) {
                System.out.println("r = " + r);
                CellStates currentCellState = cells[r][cells.length - 1 - r].getState();
                System.out.println("currentCellState = " + currentCellState);
                System.out.println();
                if (!currentCellState.equals(firstState) || currentCellState.equals(CellStates.EMPTY)) {
                    threeInARow = false;
                }

        }

        if (threeInARow) {
            winState = firstState;
        }

        return winState;
    }

    public void reset() {
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                cell1.reset();
            }
        }
    }

    public boolean isFull() {
        boolean full = true;
        freeCells.clear();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (cell1.getState().equals(CellStates.EMPTY)) {
                    full = false;
                    freeCells.add(cell1);
                }
            }
        }
        return full;
    }

    public LinkedList<Cell> getFreeCells() {
        return freeCells;
    }
}

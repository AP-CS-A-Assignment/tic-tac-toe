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

    /**
     * 2D {@link Cell} array that holds the cells of the {@link Board}
     */
    private Cell[][] cells;
    /**
     * List of Empty cells in the board
     */
    private LinkedList<Cell> freeCells;

    /**
     * Initializes the array of {@link Cell} to a 3x3 grod and adds them to the {@link Board} which extends {@link GridPane}
     * <br> Initializes the {@link LinkedList<Cell>} that holds the free cells
     * @param container
     */
    public Board(Pane container) {
        super();
        setGridLinesVisible(true);
        setAlignment(Pos.CENTER);

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

    /**
     * Checks for a win condition by checking if the cells in each row and column
     * are the same state and not Empty
     * @return The {@link CellStates} of the winning cells, and {@link CellStates}.Empty if there is no win.
     */
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

    /**
     * Checks the diagonals for a win condition
     * @return  The {@link CellStates} of the winning cells, and {@link CellStates}.Empty if there is no win.
     */
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

    /**
     * Resets all the {@link Cell}s of the {@link Board}
     */
    public void reset() {
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                cell1.reset();
            }
        }
    }

    /**
     * Checks if the {@link Board} has any empty spaces {@link Cell}s
     * <br>Also adds all the empty {@link Cell}s to the {@link LinkedList<Cell>} of free cells
     * @return true if the {@link Board} is full and false if there are empty cells
     */
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

    /**
     * @return List of Empty Cells in the {@link Board}
     */
    public LinkedList<Cell> getFreeCells() {
        return freeCells;
    }
}

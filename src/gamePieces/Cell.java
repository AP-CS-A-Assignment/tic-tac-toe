package gamePieces;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.GUI;


public class Cell extends StackPane {

    /**
     * The image of the x, o, or empty
     */
    private ImageView imageView;
    /**
     * Holds the position in the {@link Board} of the {@link Cell}
     */
    private int row, col;
    /**
     * Holds the state of the {@link Cell}, i.e. x, o, empty
     */
    private CellStates state;

    /**
     * Sets up the position of the cell, the {@link ImageView} in it, and the sizings
     * @param _row row position
     * @param _col column position
     * @param container Size of the topmost definitely sized pane
     */
    public Cell(int _row, int _col, Pane container) {
        row = _row;
        col = _col;

        state = CellStates.EMPTY;

        //The insets from the inside walls of the cells to the ImageViews
        int insets = 5;

        setPadding(new Insets(insets));

        imageView = new ImageView();
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty());
        imageView.fitWidthProperty().bind(container.minWidthProperty().subtract(50 + insets * 6).divide(3.0));
        setImage();

        //Calls onClick() when clicked
        setOnMouseClicked(event -> onClick(event));
        getChildren().add(imageView);
    }

    /**
     * Checks if its the player's turn and the cell is empty, then sets the image to an O rotated by some random 90 degree amount
     * @param m The event from the click
     */
    private void onClick(MouseEvent m) {
        if (state.equals(CellStates.EMPTY) && GUI.isLocalPLayersTurn()){
            state = CellStates.O;
            setImage();
            setRotate(90 * (int)(Math.random() * 4));
            GUI.main.moveMade();
        }
    }

    /**
     * Acts as the way for the opponent to click on a cell
     */
    public void click() {
        state = CellStates.X;
        setImage();
        setRotate(90 * (int)(Math.random() * 4));
    }

    /**
     * Empties the cell
     */
    public void reset() {
        state = CellStates.EMPTY;
        setImage();
    }

    /**
     * Sets the image to the one that represents the current state
     */
    private void setImage() {
        imageView.setImage(state.getImage());
    }

    /**
     * @return the row position of the Cell
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column position of the cell
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the current state of the Cell
     */
    public CellStates getState() {
        return state;
    }

    /**
     * @return String representation of the state
     */
    @Override
    public String toString() {
        return state.toString();
    }
}

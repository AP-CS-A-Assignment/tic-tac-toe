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

    private ImageView imageView;
    private int row, col;
    private CellStates state;

    public Cell(int _row, int _col, Pane container) {
        row = _row;
        col = _col;

        state = CellStates.EMPTY;

        //The insets from the inside walls of the cells to the ImageViews
        int insets = 5;

        setPadding(new Insets(insets));

        imageView = new ImageView();
//        setFitWidth(40);
//        setFitHeight(40);
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty());
        imageView.fitWidthProperty().bind(container.minWidthProperty().subtract(50 + insets * 6).divide(3.0));
        setImage();

        setPickOnBounds(true);
        setOnMouseClicked(event -> onClick(event));
        getChildren().add(imageView);
    }

    private void onClick(MouseEvent m) {
        if (state.equals(CellStates.EMPTY) && GUI.isLocalPLayersTurn()){
            state = CellStates.O;
            setImage();
            setRotate(90 * (int)(Math.random() * 4));
            GUI.main.moveMade();
        }
    }

    public void click() {
        state = CellStates.X;
        setImage();
        setRotate(90 * (int)(Math.random() * 4));
    }

    public void reset() {
        state = CellStates.EMPTY;
        setImage();
    }

    private void setImage() {
        imageView.setImage(state.getImage());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellStates getState() {
        return state;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}

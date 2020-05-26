package gamePieces;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.GUI;


public class Cell extends ImageView {

    public enum States {
        EMPTY("Icons/Empty.png"),
        X("Icons/Ex.png"),
        O("Icons/Oh.png");

        private Image image;

        States(String path) {
            try {
                image = new Image(States.class.getClassLoader().getResourceAsStream(path));
            } catch (NullPointerException npe) {
                System.out.println("Oops. It seems " + path + "does not exist.");
            }
        }

        public Image getImage() {
            return image;
        }
    }

    private int row, col;
    private States state;

    public Cell(int _row, int _col, Pane parent) {
        row = _row;
        col = _col;

        state = States.EMPTY;

//        setFitWidth(40);
//        setFitHeight(40);
        fitHeightProperty().bind(fitWidthProperty());
        fitWidthProperty().bind(parent.minWidthProperty().subtract(50).divide(3.0));
        setImage(state.getImage());

        setPickOnBounds(true);
        setOnMouseClicked(event -> onClick(event));
    }

    private void onClick(MouseEvent m) {
        if (state.equals(States.EMPTY) || state.equals(States.X)){
            state = States.O;
        } else {
            state = States.X;
        }
        setImage(state.getImage());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public States getState() {
        return state;
    }
}

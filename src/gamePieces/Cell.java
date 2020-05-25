package gamePieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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

    public Cell(int _row, int _col) {
        row = _row;
        col = _col;

        state = States.EMPTY;

        setFitHeight(50);
        setFitWidth(50);
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

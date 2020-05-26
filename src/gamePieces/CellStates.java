package gamePieces;

import javafx.scene.image.Image;

public enum CellStates {
    EMPTY("Icons/Empty.png"),
    X("Icons/Ex.png"),
    O("Icons/Oh.png");

    private Image image;

    CellStates(String path) {
        try {
            image = new Image(CellStates.class.getClassLoader().getResourceAsStream(path));
        } catch (NullPointerException npe) {
            System.out.println("Oops. It seems " + path + "does not exist.");
        }
    }

    public Image getImage() {
        return image;
    }
}

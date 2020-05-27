package gamePieces;

import javafx.scene.image.Image;

/**
 * Represents the states of the Cells and holds the images for each state
 */
public enum CellStates {
    /**
     * Empty state
     */
    EMPTY("Icons/Empty.png"),
    /**
     * Opponent state
     */
    X("Icons/Ex.png"),
    /**
     * Local Player state
     */
    O("Icons/Oh.png");

    private Image image;

    /**
     * Initializes the image in the state
     * @param path path to the image
     */
    CellStates(String path) {
        try {
            image = new Image(CellStates.class.getClassLoader().getResourceAsStream(path));
        } catch (NullPointerException npe) {
            System.out.println("Oops. It seems " + path + "does not exist.");
        }
    }

    /**
     * @return The Image representing the state
     */
    public Image getImage() {
        return image;
    }
}

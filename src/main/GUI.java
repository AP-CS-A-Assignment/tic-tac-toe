package main;

import board.Board;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class displays the main UI.
 */
public class GUI extends Application {
    /**
     * Launches the JavaFx application {@link GUI}
     * @param args
     */
    public static void main(String[] args) {
        GUI.launch(args);
    }

    private Stage stage;
    private Scene scene;
    private BorderPane root;
    private Board board;

    /**
     * Replaces the main method for JavaFx applications.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

                root = new BorderPane();

            scene = new Scene(root);
            stage.setScene(scene);

                    board = new Board(root.widthProperty(), root.heightProperty());
                    root.setCenter(board);

        setupStage();
        stage.show();
        stage.sizeToScene();
    }

    /**
     * Sets up the parameters for the Stage.
     *
     */
    private void setupStage() {
        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("Icons/TicTacToeJavaFxIcon.png")));
        stage.setMaxWidth(700);
        stage.setMaxHeight(700);
        stage.show();
    }

}

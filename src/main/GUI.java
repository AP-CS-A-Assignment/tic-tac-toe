package main;

import gamePieces.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This class displays the main UI and controls the game.
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
                private StackPane center;
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


                    center = new StackPane();


                        board = new Board();


                    center.getChildren().addAll(board);


                root.setCenter(center);
//                root.prefHeightProperty().bind(scene.heightProperty());
//                root.prefWidthProperty().bind(root.prefHeightProperty());

            stage.setScene(scene);

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
        stage.setWidth(700);
        stage.show();
    }

}

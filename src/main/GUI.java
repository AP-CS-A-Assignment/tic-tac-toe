package main;

import gamePieces.Board;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class displays the main UI and controls the game.
 */
public class GUI extends Application {
    public static Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM, Insets.EMPTY));

    /**
     * Launches the JavaFx application {@link GUI}
     * @param args
     */
    public static void main(String[] args) {
        GUI.launch(args);
    }

    private Stage stage;
        private Scene scene;
            private VBox root;
                private ToolBar toolBar;
                    private MenuBar menuBar;
                        private Menu settingsMenu;
                            private MenuItem closeItem;
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

                root = new VBox();
                root.setAlignment(Pos.TOP_CENTER);
//                root.setBorder(border);

            scene = new Scene(root);

                    menuBar = new MenuBar();

                        settingsMenu = new Menu("Settings");

                            closeItem = new MenuItem("Close");

                        settingsMenu.getItems().add(closeItem);

                    menuBar.getMenus().add(settingsMenu);


                    center = new StackPane();
                    center.setAlignment(Pos.CENTER);
//                    center.setPadding(new Insets(10));
//                    center.setBorder(border);

                        board = new Board(center);

//                        board.minWidthProperty().bind(centerWidth);
//                        board.maxWidthProperty().bind(centerWidth);


                    center.getChildren().addAll(board);
                    center.maxWidthProperty().bind(root.widthProperty());
                    center.minWidthProperty().bind(root.widthProperty());


                root.getChildren().addAll(menuBar, center);
                ReadOnlyDoubleProperty rootWidth = root.widthProperty();
                root.minHeightProperty().bind(stage.heightProperty().subtract(30).add(menuBar.getHeight()));
                root.maxHeightProperty().bind(stage.heightProperty().subtract(30).add(menuBar.getHeight()));
                root.minWidthProperty().bind(stage.widthProperty());
                root.maxWidthProperty().bind(stage.widthProperty());

            stage.setScene(scene);
//            stage.minHeightProperty().bind(rootWidth.add(30));
//            stage.maxHeightProperty().bind(rootWidth.add(30));
//            stage.minWidthProperty().bind(rootWidth);
//            stage.maxWidthProperty().bind(rootWidth);

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

        DoubleBinding width = stage.widthProperty().add(30);
        stage.minHeightProperty().bind(width);
        stage.maxHeightProperty().bind(width);
    }

}

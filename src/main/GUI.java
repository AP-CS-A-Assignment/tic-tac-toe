package main;

import gamePieces.Board;
import gamePieces.Cell;
import gamePieces.CellStates;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

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
            private VBox root;
                private ToolBar toolBar;
                    private MenuBar menuBar;
                        private Menu settingsMenu;
                private StackPane center;
                    private Board board;
                    private VBox winBox;
                        private TextFlow endText;
                            private Text congrats;
                            private Text winMessage;
                        private HBox buttonBox;
                            private Button playAgain;
                            private Button close;

    public static GUI main;
    private static boolean isLocalPlayersTurn;
    private Timeline opponentMoveDelayer;

    /**
     * Replaces the main method for JavaFx applications.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        main = this;
        isLocalPlayersTurn = true;

        opponentMoveDelayer = new Timeline(new KeyFrame(Duration.millis(1000)));
        opponentMoveDelayer.setOnFinished(event -> opponentsTurn());
        opponentMoveDelayer.setCycleCount(1);

        stage = primaryStage;

                root = new VBox();
                root.setAlignment(Pos.TOP_CENTER);
//                root.setBorder(border);

            scene = new Scene(root);

                    menuBar = new MenuBar();

                        settingsMenu = new Menu("Settings");

//                            MenuItem checkWinItem = new MenuItem("CheckWin");
//                            checkWinItem.setOnAction(event -> System.out.println(board.checkWin()));

                            MenuItem resetItem = new MenuItem("Reset Game");
                            resetItem.setOnAction(event -> onPlayAgain());

                        settingsMenu.getItems().addAll(resetItem);

                    menuBar.getMenus().add(settingsMenu);


                    center = new StackPane();
                    center.setAlignment(Pos.CENTER);
//                    center.setPadding(new Insets(10));
//                    center.setBorder(border);

                        board = new Board(center);

//                        board.minWidthProperty().bind(centerWidth);
//                        board.maxWidthProperty().bind(centerWidth);

                        winBox = new VBox();
                        winBox.setAlignment(Pos.CENTER);
                        winBox.setSpacing(20);
                        CornerRadii radii = new CornerRadii(10);
                        winBox.setBackground(new Background(new BackgroundFill(Color.BISQUE, radii, Insets.EMPTY)));
                        winBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, radii, BorderStroke.MEDIUM, Insets.EMPTY)));

                            endText = new TextFlow();
                            endText.setTextAlignment(TextAlignment.CENTER);
                            endText.setLineSpacing(20);

                                congrats = new Text("Congratulations\n");
                                congrats.setFont(Font.font("Comic Sans", 30));

                                winMessage = new Text("You lost!");
                                winMessage.setFont(Font.font("Times New Roman", 20));

                            endText.getChildren().addAll(congrats, winMessage);

                            buttonBox = new HBox(20);
                            buttonBox.setAlignment(Pos.CENTER);

                                playAgain = new Button("Play Again");
                                playAgain.setOnMouseClicked(event -> onPlayAgain());

                                close = new Button("Close");
                                close.setOnMouseClicked(event -> Platform.exit());
                                close.prefWidthProperty().bind(playAgain.widthProperty());

                            buttonBox.getChildren().addAll(playAgain, close);

                        winBox.getChildren().addAll(endText, buttonBox);
                        winBox.setMaxWidth(300);
                        int padding = 50;
//                        DoubleBinding innerWidth = endText.widthProperty().add(buttonBox.widthProperty()).add(padding);
                        DoubleBinding innerHeight = endText.heightProperty().add(buttonBox.heightProperty()).add(padding);
                        winBox.maxHeightProperty().bind(innerHeight);
//                        winBox.maxWidthProperty().bind(innerWidth);
                        winBox.setVisible(false);


                    center.getChildren().addAll(board, winBox);
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



    public void moveMade() {
        boolean win = checkWin();

        isLocalPlayersTurn = false;

        opponentMoveDelayer.play();

    }

    private boolean checkWin() {
        CellStates win = board.checkWin();
        String msg = "";

        boolean full = board.isFull();

        if (!win.equals(CellStates.EMPTY) || full) {
            switch (win) {
                case O:
                    msg = "You won!";
                    break;

                case X:
                    msg = "You lost!";
                    break;
            }

            if (full) {
                msg = "Draw!";
            }

            winMessage.setText(msg);
            winBox.setVisible(true);
            board.setMouseTransparent(true);
            opponentMoveDelayer.stop();
            return true;
        }
        return false;
    }

    private void opponentsTurn() {
        List<Cell> freeCells = board.getFreeCells();
        if (freeCells.size() > 0) {
            freeCells.get((int) (Math.random() * freeCells.size())).click();
            checkWin();
        }
        isLocalPlayersTurn = true;
    }

    public static boolean isLocalPLayersTurn() {
        return isLocalPlayersTurn;
    }

    private void onPlayAgain() {
        board.reset();
        board.setMouseTransparent(false);
        winBox.setVisible(false);
        isLocalPlayersTurn = true;
    }
}

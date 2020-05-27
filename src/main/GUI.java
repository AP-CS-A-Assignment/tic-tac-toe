package main;

import gamePieces.Board;
import gamePieces.Cell;
import gamePieces.CellStates;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
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

    //The scene graph of the GUI
    private Stage stage;
        private Scene scene;
            private VBox root;
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

    /**
     * Static instance of the main class that allows other classes to call methods in it
     */
    public static GUI main;

    /**
     * A flag that controls when the local player can make a move
     */
    private static boolean isLocalPlayersTurn;

    /**
     * A {@link Timeline} is used to run the opponent move process after a set delay while keeping the program from freezing in a loop
     */
    private Timeline opponentMoveDelayer;

    /**
     * Replaces the main method for JavaFx applications.
     * Initializes all the fields and the scene graph
     * @param primaryStage Window that the program runs in
     */
    @Override
    public void start(Stage primaryStage) {
        main = this;
        isLocalPlayersTurn = true;

        opponentMoveDelayer = new Timeline(new KeyFrame(Duration.millis(1000)));
        opponentMoveDelayer.setOnFinished(event -> opponentsTurn());
        opponentMoveDelayer.setCycleCount(1);

        stage = primaryStage;

                root = new VBox();
                root.setAlignment(Pos.TOP_CENTER);

            scene = new Scene(root);

                    menuBar = new MenuBar();

                        settingsMenu = new Menu("Settings");

                            MenuItem resetItem = new MenuItem("Reset Game");
                            resetItem.setOnAction(event -> onPlayAgain());

                        settingsMenu.getItems().addAll(resetItem);

                    menuBar.getMenus().add(settingsMenu);


                    center = new StackPane();
                    center.setAlignment(Pos.CENTER);

                        board = new Board(center);

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
                                //Binds the width of the less wide close button to the width of the wider play again button
                                close.prefWidthProperty().bind(playAgain.widthProperty());

                            buttonBox.getChildren().addAll(playAgain, close);

                        winBox.getChildren().addAll(endText, buttonBox);
                        winBox.setMaxWidth(300);
                        //Binds the height of the winBox to keep it at it's smallest size instead of growing to fill the center pane
                        int padding = 50;
                        DoubleBinding innerHeight = endText.heightProperty().add(buttonBox.heightProperty()).add(padding);
                        winBox.maxHeightProperty().bind(innerHeight);
                        winBox.setVisible(false);


                    center.getChildren().addAll(board, winBox);
                    //The bindings set the center stackPane to the width of the root
                    center.maxWidthProperty().bind(root.widthProperty());
                    center.minWidthProperty().bind(root.widthProperty());


                root.getChildren().addAll(menuBar, center);
                //All the bindings keep the root of the scene set to the correct size relative to the stage's size
                root.minHeightProperty().bind(stage.heightProperty().subtract(30).add(menuBar.getHeight()));
                root.maxHeightProperty().bind(stage.heightProperty().subtract(30).add(menuBar.getHeight()));
                root.minWidthProperty().bind(stage.widthProperty());
                root.maxWidthProperty().bind(stage.widthProperty());

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

        //Keeps the aspect ratio of the stage as a square plus 30px to the height to account for the title bar of the window
        DoubleBinding width = stage.widthProperty().add(30);
        stage.minHeightProperty().bind(width);
        stage.maxHeightProperty().bind(width);
    }

    /**
     * Called when a {@link Cell} in the {@link Board} has been clicked.
     * Checks if the player's move resulted in a win, then starts the {@link Timeline}
     * that runs the opponent move after a delay
     */
    public void moveMade() {
        boolean win = checkWin();

        isLocalPlayersTurn = false;

        opponentMoveDelayer.play();

    }

    /**
     * Flag that tells the opponent move's method whether the player has won already
     * in order to keep it from making another move after the win screen appears.<br>
     * --Should be eliminated with further tuning of the process--
     */
    private boolean won = false;

    /**
     * Calls {@link Board}.checkWin() and checks if a draw has occurred
     * and sets the win message accordingly.
     * <br> It then sets the won flag and shows the winBox
     * @return
     */
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
            won = true;
            return true;
        }
        return false;
    }

    /**
     * Runs the opponent's turn by randomly selecting an empty {@link Cell} from the board
     * and sets the isLocalPLayersTurn flag to allow the local player to make a move again.
     * <br>-Checks the won flag before making the move
     */
    private void opponentsTurn() {
        if (!won) {
            List<Cell> freeCells = board.getFreeCells();
            if (freeCells.size() > 0) {
                freeCells.get((int) (Math.random() * freeCells.size())).click();
                checkWin();
            }
        }
        isLocalPlayersTurn = true;
    }

    /**
     * @return the flag that represents whether is the local player's turn to make a move
     */
    public static boolean isLocalPLayersTurn() {
        return isLocalPlayersTurn;
    }

    /**
     * Method called when the play again {@link Button} is pressed fro the win Box.
     * <br> Resets the necessary fields and flags to reset the {@link Board} to its original state.
     */
    private void onPlayAgain() {
        board.reset();
        board.setMouseTransparent(false);
        winBox.setVisible(false);
        won = false;
        isLocalPlayersTurn = true;
    }
}

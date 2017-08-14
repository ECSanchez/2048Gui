/**
 * Name: Eduardo Sanchez
 * Login: cs8bwasi
 * Date:March 1, 2016
 * File: Gui2048.java
 * Sources of Help:
 * <p>
 * CSE8B assignment 8.
 * This is the class runs the Gui for the 2048 game
 * <p>
 * <p>
 * Name: Gui2048.java
 * Purpose: creates and updates Gui for the 2048 gameboard
 * <p>
 * Name: Gui2048.java
 * Purpose: creates and updates Gui for the 2048 gameboard
 * <p>
 * Name: Gui2048.java
 * Purpose: creates and updates Gui for the 2048 gameboard
 * <p>
 * Name: Gui2048.java
 * Purpose: creates and updates Gui for the 2048 gameboard
 * <p>
 * Name: Gui2048.java
 * Purpose: creates and updates Gui for the 2048 gameboard
 */

/**
 *  Name: Gui2048.java
 *  Purpose: creates and updates Gui for the 2048 gameboard
 *
 */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;

import java.util.*;
import java.io.*;

public class Gui2048 extends Application {
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
    //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
    //(1024, 2048, Higher)

  /*  // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_DARK = Color.rgb(249, 246, 242);
    // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
    // For tiles < 8*/

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(245, 250, 255, 0.35);
    private static final Color COLOR_2 = Color.rgb(235, 235, 250);//
    private static final Color COLOR_4 = Color.rgb(200, 220, 255);//
    private static final Color COLOR_8 = Color.rgb(185, 225, 220);//
    private static final Color COLOR_16 = Color.rgb(185, 240, 185);//
    private static final Color COLOR_32 = Color.rgb(220, 240, 180);//
    private static final Color COLOR_64 = Color.rgb(255, 245, 170);//
    private static final Color COLOR_128 = Color.rgb(255, 220, 170);//
    private static final Color COLOR_256 = Color.rgb(245, 190, 165);//
    private static final Color COLOR_512 = Color.rgb(255, 185, 180);
    private static final Color COLOR_1024 = Color.rgb(255, 165, 180);//
    private static final Color COLOR_2048 = Color.rgb(240, 170, 235);//
    private static final Color COLOR_OTHER = Color.rgb(214, 180, 255);
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
    private static final Color COLOR_DARK = Color.rgb(75, 70, 70);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
    // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
    // For tiles < 8

    private GridPane pane;

    /** Add your own Instance Variables here */
    private StackPane stack; //StackFrame of entire Gui
    private Rectangle[][] tileArray; //holds rectangles in an array for tiles
    private Text[][] textArray; //holds text in an array for tiles
    private Text score; //score of the game

    /*
    * Name: start
    * Purpose: creates the initial state of the Gui, calls eventHandler
    * Parameters: Stage - primaryStage - stage where Gui exists
    * Return: void
    * */
    @Override
    public void start(Stage primaryStage) {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // assign array to instance variables
        tileArray = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
        textArray = new Text[board.GRID_SIZE][board.GRID_SIZE];

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(197, 183, 170)");
        // Set the spacing between the Tiles
        pane.setHgap(15);
        pane.setVgap(15);

        /** Add your Code for the GUI Here */

        stack = new StackPane();
        stack.getChildren().addAll(pane);

        // 2048 name
        Text NameText = new Text();
        NameText.setText("2048");
        NameText.setFont(Font.font("Helvetica",
                FontWeight.SEMI_BOLD, 50));
        NameText.setFill(COLOR_DARK);
        pane.add(NameText, 0, 0);
        GridPane.setColumnSpan(NameText, board.GRID_SIZE / 2);
        GridPane.setHalignment(NameText, HPos.CENTER);

        // score
        score = new Text();
        score.setText("Score: " + String.valueOf(board.getScore()));
        score.setFont(Font.font("Helvetica",
                FontWeight.LIGHT, 35));
        score.setFill(COLOR_DARK);
        pane.add(score, board.GRID_SIZE / 2, 0);
        GridPane.setColumnSpan(score, board.GRID_SIZE / 2);
        GridPane.setHalignment(score, HPos.CENTER);

        // tile creation
        for (int i = 0; i < board.GRID_SIZE; i++) {
            for (int j = 0; j < board.GRID_SIZE; j++) {

                // retrieve values from gameboard 2048
                int boardVal = board.getGrid()[i][j];

                // creates tile
                Rectangle tile = new Rectangle();
                tile.setWidth(TILE_WIDTH);
                tile.setHeight(TILE_WIDTH);

                // creates text of tile
                Text vText = new Text();

                // assigns color to tile
                if (boardVal == 0)
                    tile.setFill(COLOR_EMPTY);
                else if (boardVal == 2)
                    tile.setFill(COLOR_2);
                else if (boardVal == 4)
                    tile.setFill(COLOR_4);
                else if (boardVal == 8)
                    tile.setFill(COLOR_8);
                else if (boardVal == 16)
                    tile.setFill(COLOR_16);
                else if (boardVal == 32)
                    tile.setFill(COLOR_32);
                else if (boardVal == 64)
                    tile.setFill(COLOR_64);
                else if (boardVal == 128)
                    tile.setFill(COLOR_128);
                else if (boardVal == 256)
                    tile.setFill(COLOR_256);
                else if (boardVal == 512)
                    tile.setFill(COLOR_512);
                else if (boardVal == 1024)
                    tile.setFill(COLOR_1024);
                else if (boardVal == 2048)
                    tile.setFill(COLOR_2048);
                else
                    tile.setFill(COLOR_OTHER);

                // assigns no text if boardVal is 0
                if (boardVal > 0) // if > 0 assign value
                    vText.setText(String.valueOf(boardVal));

                // assigns text color
                if (boardVal < 8)
                    vText.setFill(COLOR_VALUE_DARK);
                else
                    vText.setFill(COLOR_VALUE_DARK);

                // sets text style
                if (boardVal < 16)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_LOW));
                else if (boardVal > 8 && boardVal < 1024)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_MID));
                else if (boardVal > 512)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_HIGH));

                // assigns tile and text to an array
                tileArray[i][j] = tile;
                textArray[i][j] = vText;

                // adds elements to pane
                pane.add(tile, j, i + 1);
                pane.add(vText, j, i + 1);
                GridPane.setHalignment(vText, HPos.CENTER);
            }
        }
        // how to create a new scene
        Scene scene = new Scene(stack);
        primaryStage.setTitle("GUI2048");
        primaryStage.setScene(scene);
        primaryStage.show();

        // event handler for keypresses
        scene.setOnKeyPressed(new myKeyHandler());
    }

    /** Add your own Instance Methods Here */
    /*
    * inner class
    * Name: myKeyHandler
    * Purpose: event handling for Gui
    * */
    private class myKeyHandler implements EventHandler<KeyEvent> {

        /*
        * Name: handle
        * Purpose: takes in input and handles game logic
        * Parameters: KeyEvent e - input of user
        * Return: void
        * */
        @Override
        public void handle(KeyEvent e) {
            switch (e.getCode()) {
                case UP:
                    if (board.canMove(Direction.UP)) {
                        System.out.println("Moving Up");
                        board.move(Direction.UP);
                        board.addRandomTile();
                        updateBoard();
                        gameOver();
                    }
                    break;
                case DOWN:
                    if (board.canMove(Direction.DOWN)) {
                        System.out.println("Moving Down");
                        board.move(Direction.DOWN);
                        board.addRandomTile();
                        updateBoard();
                        gameOver();
                    }
                    break;
                case LEFT:
                    if (board.canMove(Direction.LEFT)) {
                        System.out.println("Moving Left");
                        board.move(Direction.LEFT);
                        board.addRandomTile();
                        updateBoard();
                        gameOver();
                    }
                    break;
                case RIGHT:
                    if (board.canMove(Direction.RIGHT)) {
                        System.out.println("Moving Right");
                        board.move(Direction.RIGHT);
                        board.addRandomTile();
                        updateBoard();
                        gameOver();
                    }
                    break;
                case S:
                    try {
                        board.saveBoard(outputBoard);
                        System.out.println("Saving game to " + outputBoard);
                    } catch (IOException ex) {
                        System.out.println("saveBoard threw an Exception");
                    }
                    break;
                case R:
                    if (!board.isGameOver()) {
                        System.out.println("Rotating Board Clockwise");
                        board.rotate(true);
                        updateBoard();
                    }
                default:
                    break;
            }
        }
    }

    /*
   * Name: updateBoard
   * Purpose: helper method that updates board state in gui
   * Parameters: none
   * return: void
   * */
    private void updateBoard() {

        score.setText("Score: " + String.valueOf(board.getScore()));
        score.setFont(Font.font("Helvetica",
                FontWeight.LIGHT, 35));
        score.setFill(COLOR_DARK);

        for (int i = 0; i < board.GRID_SIZE; i++) {
            for (int j = 0; j < board.GRID_SIZE; j++) {

                int boardVal = board.getGrid()[i][j];
                Rectangle tile = tileArray[i][j];
                Text vText = textArray[i][j];

                // assigns color to tile
                if (boardVal == 0)
                    tile.setFill(COLOR_EMPTY);
                else if (boardVal == 2)
                    tile.setFill(COLOR_2);
                else if (boardVal == 4)
                    tile.setFill(COLOR_4);
                else if (boardVal == 8)
                    tile.setFill(COLOR_8);
                else if (boardVal == 16)
                    tile.setFill(COLOR_16);
                else if (boardVal == 32)
                    tile.setFill(COLOR_32);
                else if (boardVal == 64)
                    tile.setFill(COLOR_64);
                else if (boardVal == 128)
                    tile.setFill(COLOR_128);
                else if (boardVal == 256)
                    tile.setFill(COLOR_256);
                else if (boardVal == 512)
                    tile.setFill(COLOR_512);
                else if (boardVal == 1024)
                    tile.setFill(COLOR_1024);
                else if (boardVal == 2048)
                    tile.setFill(COLOR_2048);
                else
                    tile.setFill(COLOR_OTHER);

                // assigns no text if boardVal is 0
                if (boardVal > 0) // if > 0 assign value
                    vText.setText(String.valueOf(boardVal));
                else
                    vText.setText("");

                // assigns color to text
                if (boardVal < 8)
                    vText.setFill(COLOR_VALUE_DARK);
                else
                    vText.setFill(COLOR_VALUE_DARK);

                // sets text style
                if (boardVal < 16)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_LOW));
                else if (boardVal > 8 && boardVal < 1024)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_MID));
                else if (boardVal > 512)
                    vText.setFont(Font.font("Helvetica",
                            FontWeight.MEDIUM, TEXT_SIZE_HIGH));

                // assigns tile and text to an array
                tileArray[i][j] = tile;
                textArray[i][j] = vText;
            }
        }
    }

    /*
    * Name: gameOver
    * Purpose: creates overlay and stops game once gameover is reached
    * Parameters: none
    * Return: void
    * */
    private void gameOver() {
        if (board.isGameOver()) {
            GridPane goPane = new GridPane();
            goPane.setAlignment(Pos.CENTER);
            goPane.setStyle("-fx-background-color: rgb(238, 228, 218, 0.73)");
            stack.getChildren().addAll(goPane);

            Text goText = new Text();
            goText.setText("Game Over");
            goText.setFont(Font.font("Helvetica",
                    FontWeight.EXTRA_BOLD, 80));
            goText.setFill(COLOR_DARK);
            stack.getChildren().addAll(goText);

            return;
        }
    }

    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args) {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if ((args.length % 2) != 0) {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-i")) {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            } else if (args[i].equals("-o")) {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            } else if (args[i].equals("-s")) {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            } else {   // Incorrect Argument
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if (outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if (boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try {
            if (inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        } catch (Exception e) {
            System.out.println(e.getClass().getName() +
                    " was thrown while creating a " +
                    "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                    "Constructor is broken or the file isn't " +
                    "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage() {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the " +
                "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " +
                "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " +
                "used to save the 2048 board");
        System.out.println("                If none specified then the " +
                "default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048" +
                "board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i" +
                "are used, then the size of the board");
        System.out.println("                will be determined by the input" +
                " file. The default size is 4.");
    }
}

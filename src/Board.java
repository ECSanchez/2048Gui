//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //

// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/*
 *  Name: Eduardo Sanchez
 *  Login: cs8bwasi
 *  Date:February 2, 2016
 *  File: GameManager.java
 *  Sources of Help:
 *
 *  CSE8B assignment 4.
 *  This is the class that contains the methods for GameManager
 *
 */

/*
 *  Name: Board
 *  Purpose: Hold methods that are used on the GameManager classq
 *
 */

/**
 * Sample Board
 * <p>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.awt.*;
import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    /*
    * Constructor
    * Name: Board
    * Purpose: Creates a new board from scratch
    * Parameters: int boardSize - specifies the size of the board
    *             Random random - random number generator for tile creation
    *
    * */
    public Board(int boardSize, Random random) {
        this.random = random;
        GRID_SIZE = boardSize;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        this.score = 0; //no score at start of game

        for (int i = 0; i < NUM_START_TILES; i++) {
            this.addRandomTile();
        }
    }

    /*
    * Constructor
    * Name: Board
    * Purpose: Creates a board based on an input file
    * Parameters: String inputBoard - name of input file
    *             Random random - random number generator for tile creation
    *
    * */
    public Board(String inputBoard, Random random) throws IOException {
        this.random = random;
        Scanner in = new Scanner(new File(inputBoard)); //scanner for save file
        GRID_SIZE = in.nextInt();
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        this.score = in.nextInt();

        while (in.hasNext()) // increments through the saved board file
        {
            for (int i = 0; i < GRID_SIZE; i++) // loops through 2D array
            {
                for (int j = 0; j < GRID_SIZE; j++) {
                    grid[i][j] = in.nextInt(); // adds board position to array
                }
            }

        }
    }

    /*
    * Name: saveBoard
    * Purpose: saves the current game board to a text file
    * Parameters: String outputBoard - name of output file
    * Return: void
    *
    * */
    public void saveBoard(String outputBoard) throws IOException {
        //printer to write current game state to a save file
        PrintWriter boardWrite = new PrintWriter(new File(outputBoard));
        boardWrite.println(GRID_SIZE); // prints grid size
        boardWrite.println(score); // prints score
        for (int i = 0; i < GRID_SIZE; i++) //loops and prints all values in grid
        {
            for (int j = 0; j < GRID_SIZE; j++) {
                boardWrite.print(grid[i][j]);
                boardWrite.print(" ");
            }
            boardWrite.println();
        }
        boardWrite.close();


    }

    /*
    * Name: addRandomTile
    * Purpose: adds a random tile (2 or 4 ) to a random empty space
    * Parameters:
    * Return: void
    *
    * */
    public void addRandomTile() {

        int count = 0;
        int k = 0;

        for (int i = 0; i < GRID_SIZE; i++) { // finds the number of empty tiles
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    count++;
                }
            }
        }

        if (count == 0) //if there are no empty spaces return
        {
            return;
        }

        int location = random.nextInt(count); // chooses location of new tile
        int value = random.nextInt(100); // generates random number between 0-99
        k = -1;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    k++; // gets the empty location
                }

                if (location == k) {
                    if (value < TWO_PROBABILITY) {
                        grid[i][j] = 2; // adds "2" tile
                        return;
                    } else {
                        grid[i][j] = 4; // adds "4" tile
                        return;
                    }
                }

            }
        }
    }

    // Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    // If rotateClockwise == true, rotates the board clockwise , else rotates
    // the board counter-clockwise
    /*
    * Name: rotate
    * Purpose: rotates board by 90 degrees clockwise or counter-clockwise
    * Parameters: boolean rotateClockwise - determines whether the board will
    *             be rotated clockwise if true or counter-clockwise if false
    * Return: void
    *
    * */
    public void rotate(boolean rotateClockwise) {

        int[][] tempGrid = new int[GRID_SIZE][GRID_SIZE];

        {
            for (int i = 0; i < GRID_SIZE; i++) { //creates a copy of grid{
                for (int j = 0; j < GRID_SIZE; j++) {
                    tempGrid[i][j] = grid[i][j];
                }
            }

            for (int i = 0; i < GRID_SIZE; i++) { // switches row and column
                for (int j = 0; j < GRID_SIZE; j++) {
                    grid[j][i] = tempGrid[i][j];

                }

            }

            if (rotateClockwise) //flips grid horizontally
                for (int j = 0; j < GRID_SIZE; j++) {
                    for (int i = 0; i < GRID_SIZE / 2; i++) {
                        tempGrid[j][i] = grid[j][i];
                        grid[j][i] = grid[j][GRID_SIZE - 1 - i];
                        grid[j][GRID_SIZE - 1 - i] = tempGrid[j][i];
                    }
                }

            else { //flips grid vertically
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE / 2; j++) {
                        tempGrid[j][i] = grid[j][i];
                        grid[j][i] = grid[GRID_SIZE - 1 - j][i];
                        grid[GRID_SIZE - 1 - j][i] = tempGrid[j][i];
                    }

                }

            }


        }


    }

    /*
    * Name: move
    * Purpose: moves game tiles in specified direction correctly
    *           also increments score
    * Parameters:  Direction direction - direction where movement will be made
    * Return: boolean didMove - variable that determines if a move has occurred
    *
    * */
    public boolean move(Direction direction) {
        boolean didMove = false;
        if (direction == Direction.UP) {
            moveUp();
            didMove = true;
        } else if (direction == Direction.DOWN) {
            moveDown();
            didMove = true;
        } else if (direction == Direction.LEFT) {
            moveLeft();
            didMove = true;
        } else if (direction == Direction.RIGHT) {
            moveRight();
            didMove = true;
        }
        return didMove;

    }

    /*
    * Name: moveUP
    * Purpose: helper method for move method that moves all tiles up
    * Parameters: none
    * Return: void
    *
    * */

    private void moveUp() {
        for (int j = 0; j < GRID_SIZE; j++) { //loops through row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops column loop
                for (int i = 0; i < GRID_SIZE - 1; i++) { //loops column
                    int pos1 = grid[i][j];
                    int pos2 = grid[i + 1][j];

                    if (pos1 == 0 && pos2 != 0) {
                        grid[i][j] = pos2;
                        grid[i + 1][j] = pos1;
                    }
                }
            }
        }

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int i = 0; i < GRID_SIZE - 1; i++) {
                int pos1 = grid[i][j];
                int pos2 = grid[i + 1][j];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    score += pos1 + pos2;
                    grid[i][j] = pos1 + pos2;
                    grid[i + 1][j] = 0;

                }
            }

        }

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int c = 0; c < GRID_SIZE - 1; c++) {
                for (int i = 0; i < GRID_SIZE - 1; i++) {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i + 1][j];

                    if (pos1 == 0 && pos2 != 0) {
                        grid[i][j] = pos2;
                        grid[i + 1][j] = pos1;
                    }
                }
            }
        }
    }

    /*
    * Name: moveDown
    * Purpose: helper method for move method that moves all tiles down
    * Parameters: none
    * Return: void
    *
    * */
    private void moveDown() {
        for (int j = 0; j < GRID_SIZE; j++) { //loops row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops loops column
                for (int i = GRID_SIZE - 1; i > 0; i--) //loops column
                {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i - 1][j];

                    if (pos1 == 0 && pos2 != 0) {
                        grid[i][j] = pos2;
                        grid[i - 1][j] = pos1;
                    }
                }
            }
        }

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int i = GRID_SIZE - 1; i > 0; i--) {
                int pos1 = grid[i][j];
                int pos2 = grid[i - 1][j];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    score += pos1 + pos2;
                    grid[i][j] = pos1 + pos2;
                    grid[i - 1][j] = 0;
                }
            }

        }

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int c = 0; c < GRID_SIZE - 1; c++) {
                for (int i = GRID_SIZE - 1; i > 0; i--) {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i - 1][j];

                    if (pos1 == 0 && pos2 != 0) {
                        grid[i][j] = pos2;
                        grid[i - 1][j] = pos1;
                    }
                }
            }
        }
    }

    /*
    * Name: moveLeft
    * Purpose: helper method for move method that moves all tiles left
    * Parameters: none
    * Return: void
    *
    * */

    private void moveLeft() {

        //shifts tiles into empty spots

        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops columns
                for (int j = 0; j < GRID_SIZE - 1; j++) //loops to next column
                {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i][j + 1];

                    if (pos1 == 0 && pos2 != 0) //switches tile to empty spot
                    {
                        grid[i][j] = pos2;
                        grid[i][j + 1] = pos1;
                    }
                }
            }
        }

        //combines adjacent tiles with priority from direction called

        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int j = 0; j < GRID_SIZE - 1; j++) //loops to next column
            {
                int pos1 = grid[i][j];
                int pos2 = grid[i][j + 1];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    score += pos1 + pos2;
                    grid[i][j] = pos1 + pos2;
                    grid[i][j + 1] = 0;
                }
            }

        }

        //loops through and gets rid of all zeros if any
        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops columns
                for (int j = 0; j < GRID_SIZE - 1; j++) //loops to next column
                {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i][j + 1];

                    if (pos1 == 0 && pos2 != 0) //switches tile to empty spot
                    {
                        grid[i][j] = pos2;
                        grid[i][j + 1] = pos1;
                    }
                }
            }
        }

    }

    /*
    * Name: moveRight
    * Purpose: helper method for move method that moves all tiles right
    * Parameters: none
    * Return: void
    *
    * */
    private void moveRight() {
        //shifts tiles into empty spots

        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops columns grid-1 times to completely fill
                for (int j = GRID_SIZE - 1; j > 0; j--) //loops to next column
                {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i][j - 1];

                    if (pos1 == 0 && pos2 != 0) //switches tile to empty spot
                    {
                        grid[i][j] = pos2;
                        grid[i][j - 1] = pos1;
                    }
                }
            }
        }

        //combines adjacent tiles with priority from direction called

        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int j = GRID_SIZE - 1; j > 0; j--) //loops to next column
            {
                int pos1 = grid[i][j];
                int pos2 = grid[i][j - 1];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    score += pos1 + pos2;
                    grid[i][j] = pos1 + pos2;
                    grid[i][j - 1] = 0;
                }
            }

        }

        //loops through and gets rid of all zeros if any
        for (int i = 0; i < GRID_SIZE; i++) { //loops to next row
            for (int c = 0; c < GRID_SIZE - 1; c++) { //loops columns grid-1 times to completely fill
                for (int j = GRID_SIZE - 1; j > 0; j--) //loops to next column
                {
                    int pos1 = grid[i][j];
                    int pos2 = grid[i][j - 1];

                    if (pos1 == 0 && pos2 != 0) //switches tile to empty spot
                    {
                        grid[i][j] = pos2;
                        grid[i][j - 1] = pos1;
                    }
                }
            }
        }
    }

    /*
    * Name: isGameOver
    * Purpose: checks to see if there are any possible moves
    * Parameters: none
    * Return: boolean overBool - returns true if the game is over
    * */
    public boolean isGameOver() {

        boolean overBool = false;
        if (!(canMove(Direction.UP) || canMove(Direction.DOWN)
                || canMove(Direction.LEFT) || canMove(Direction.RIGHT))) {
            overBool = true;
        }
        return overBool;
    }

    /*
    * Name: canMove
    * Purpose: checks to see if there is a valid move
    * Parameters: Direction direction - the direction of the movement
    * Return: boolean moveBool - returns true when a move is valid
    * */
    public boolean canMove(Direction direction) {
        boolean moveBool = false;

        if (direction == Direction.UP) {
            moveBool = canMoveUp();
        } else if (direction == Direction.DOWN) {
            moveBool = canMoveDown();
        }
        if (direction == Direction.LEFT) {
            moveBool = canMoveLeft();
        } else if (direction == Direction.RIGHT) {
            moveBool = canMoveRight();
        }
        return moveBool;
    }

    /*
    * Name: canMoveUp
    * Purpose: checks to see if the direction up is a valid movement
    * Parameters: none
    * Return: boolean moveBool - returns true if the movement is valid
    * */
    private boolean canMoveUp() {
        boolean moveBool = false;

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int i = GRID_SIZE - 1; i > 0; i--) {
                int pos1 = grid[i][j];
                int pos2 = grid[i - 1][j];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    moveBool = true;
                } else if (pos1 != 0 && pos2 == 0) {
                    moveBool = true;
                }
            }
        }
        return moveBool;
    }

    /*
   * Name: canMoveDown
   * Purpose: checks to see if the direction down is a valid movement
   * Parameters: none
   * Return: boolean moveBool - returns true if the movement is valid
   * */
    private boolean canMoveDown() {
        boolean moveBool = false;

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int i = 0; i < GRID_SIZE - 1; i++) {
                int pos1 = grid[i][j];
                int pos2 = grid[i + 1][j];

                ////checks if adjacent tiles are the same VALUE
                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    moveBool = true;
                    //checks if 0 is in front of a tile
                } else if (pos1 != 0 && pos2 == 0)
                {
                    moveBool = true;
                }
            }
        }
        return moveBool;
    }

    /*
   * Name: canMoveLeft
   * Purpose: checks to see if the direction left is a valid movement
   * Parameters: none
   * Return: boolean moveBool - returns true if the movement is valid
   * */
    private boolean canMoveLeft() {
        boolean moveBool = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = GRID_SIZE - 1; j > 0; j--) {
                int pos1 = grid[i][j];
                int pos2 = grid[i][j - 1];
                //checks if two non zero tiles are next to each other
                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    moveBool = true;
                }
                //checks if there is a zero in the direction of movement
                if (pos1 != 0 && pos2 == 0) { //
                    moveBool = true;
                }
            }
        }
        return moveBool;
    }

    /*
   * Name: canMoveRight
   * Purpose: checks to see if the direction right is a valid movement
   * Parameters: none
   * Return: boolean moveBool - returns true if the movement is valid
   * */
    private boolean canMoveRight() {
        boolean moveBool = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE - 1; j++) {
                int pos1 = grid[i][j];
                int pos2 = grid[i][j + 1];

                if (pos1 == pos2 && pos1 != 0 && pos2 != 0) {
                    moveBool = true;
                }

                if (pos1 != 0 && pos2 == 0) {
                    moveBool = true;
                }
            }
        }
        return moveBool;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}

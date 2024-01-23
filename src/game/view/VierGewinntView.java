package game.view;

import java.util.List;

import game.controller.IVierGewinntController;
import game.controller.VierGewinntController ;
import game.model.Move;
import processing.core.PApplet;

/**
 * Class implementing the view for a "Vier Gewinnt" (Connect Four) game using the Processing library.
 * This class is responsible for displaying the game to the user.
 */
public class VierGewinntView extends PApplet implements IVierGewinntView {
    /**
     * The game controller associated with this view.
     */
    IVierGewinntController controller;

    /**
     * Main method to launch the game view using Processing.
     *
     * @param args Command-line arguments (not used in this context).
     */
    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new VierGewinntView());
    }

    /**
     * Sets the size of the game board.
     */
    @Override
    public void settings() {
        super.size(700, 800);
    }

    /**
     * Initializes the game controller and sets up the start screen.
     */
    @Override
    public void setup() {
        background(0);
        this.controller = new VierGewinntController(this);
        controller.startScreen();
    }

    /**
     * Draws the game tiles on the screen based on the provided game board.
     *
     * @param board A 2D array representing the game board.
     */
    @Override
    public void drawTiles(int[][] board) {
        fill(100, 100, 100);
        for (int j = 0; j < board[0].length; j++) {
            rect(0, 0, 700, 100);
        }
        fill(255);
        for (int i = 1; i < board[0].length; i++) {
            for (int j = 0; j < 7; j++) {
                rect((float) j * 100, (float) i * 100, 100, 100);
            }
        }

    }

    /**
     * Displays help or instructions to the user.
     */
    @Override
    public void displayHelp() {
        fill(100, 100, 100);
        rect(0, 0, 700, 800);
        fill(200, 200, 0);
        textSize(50);
        text("Welcome to connect four", 10, 50);
        fill(200);
        textSize(30);
        text("""
                The four arrow keys are used to play this game.
                To move a chip, press the left and right arrow keys
                To play a chip, press the down arrow key
                To undo a move, press the up arrow key


                 HAVE FUN
                Press
                 1.    To Play with a friend
                 OR
                 2.    To Play with AI
                """, 10, 200);
    }

    /**
     * Displays the start screen of the game.
     */
    @Override
    public void displayStart() {
        fill(100, 100, 100);
        rect(0, 0, 700, 800);
        fill(200, 200, 0);
        textSize(50);

        text("Welcome to connect four", 10, 50);
        fill(200);

        textSize(30);
        text("""
                Press
                 1.    To Play with a friend
                 OR
                 2.    To Play with AI
                OR
                 3. For help""", 10, 200);
    }

    /**
     * Logs an event or message for the user.
     *
     * @param event The event or message to be logged.
     */
    @Override
    public void log(String event) {
        fill(255, 0, 0);
        rect(0, 700, 700, 100);
        fill(70, 70, 70);
        textSize(20);
        text(String.format("Logger info%n%s:", event), 10, 720);
        System.out.println(event);
    }

    /**
     * Displays the winning moves on the game board.
     *
     * @param moves A list of moves representing the winning combination.
     */
    @Override
    public void displayWinningMoves(List<Move> moves) {
        fill(0, 0, 0); // winning moves color
        for (Move move : moves) {
            ellipse((move.column() * 100) + 50, ((6 - move.row()) * 100) + 50, 20, 20);
        }
    }

    /**
     * Draws game pieces on the screen based on the provided game board.
     *
     * @param board A 2D array representing the game board.
     */
    @Override
    public void drawPieces(int[][] board) {
        for (int i = 5, w = 1; i >= 0; i--, w++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 1) {
                    fill(255, 0, 0);
                    ellipse((j * 100) + 50, (w * 100) + 50, 60, 60);
                }
                if (board[i][j] == 2) {
                    fill(255, 255, 0);
                    ellipse((j * 100) + 50, (w * 100) + 50, 60, 60);
                }
            }
        }
    }

    /**
     * Displays the game board along with game-related information.
     *
     * @param board        A 2D array representing the game board.
     * @param isPlayerTurn `true` if it's the player's turn, `false` otherwise.
     * @param position     The current position in the game.
     */
    @Override
    public void displayBoard(int[][] board, boolean isPlayerTurn, int position) {
        this.drawTiles(board);
        this.drawPieces(board);
        this.drawPiecePosition(position, isPlayerTurn);
    }

    /**
     * Draws a game piece at a specific position on the game board.
     *
     * @param position     The position where the piece should be drawn.
     * @param isPlayerTurn `true` if it's the player's turn, `false` otherwise.
     */
    @Override
    public void drawPiecePosition(int position, boolean isPlayerTurn) {
        if (isPlayerTurn) {
            fill(150, 25, 0);
        } else {
            fill(255, 255, 0);
        }
        ellipse((position * 100) + 50, 50, 60, 60);
    }

    /**
     * Overrides the `draw` method to register pressed keys and refresh the screen when needed.
     */
    @Override
    public void draw() {
        // needed to register pressed keys and refresh screen when needed
    }

    /**
     * Handles key press events and forwards them to the game controller.
     */
    @Override
    public void keyPressed() {
        if (key == CODED) {
            controller.handleKeyPressed(keyCode);
        }
        controller.handleKeyPressed(key);

    }

    /**
     * Displays the winner screen with appropriate messages.
     *
     * @param winner The winner (1 for red, 2 for blue, 0 for a draw).
     */
    @Override
    public void displayWinner(int winner) {
        fill(0, 20, 20);
        rect(100, 300, 500, 200);
        fill(0, 200, 0);
        textSize(20);
        if (winner == 0) {
            text("GAME OVER IT ENDED IN A DRAW", 200, 350);
            text("Press Enter To Restart", 200, 400);
            return;
        }

        text(String.format("GAME OVER PLAYER %d WON GAME", winner), 200, 350);
        text("WINNER", 200, 400);

        if (winner == 1) {
            fill(200, 0, 0);
        }
        if (winner == 2) {
            fill(255, 255, 0);
        }
        ellipse(350, 400, 60, 60);
        text("Press Enter To Restart Or 's' To Show board", 200, 450);
    }

}


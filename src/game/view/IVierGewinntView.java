package game.view;
import game.model.Move;

import java.util.List;

/**
 * Interface representing the view of a "Vier Gewinnt" (Connect Four) game.
 * Implementations of this interface are responsible for displaying the game to the user.
 */
public interface IVierGewinntView {

    /**
     * Draws the game tiles on the screen based on the provided game board.
     *
     * @param board A 2D array representing the game board.
     */
    void drawTiles(int[][] board);

    /**
     * Displays help or instructions to the user.
     */
    void displayHelp();

    /**
     * Displays the start screen of the game.
     */
    void displayStart();

    /**
     * Logs an event or message for the user.
     *
     * @param event The event or message to be logged.
     */
    void log(String event);

    /**
     * Displays the winning moves on the game board.
     *
     * @param moves A list of moves representing the winning combination.
     */
    void displayWinningMoves(List<Move> moves);

    /**
     * Draws game pieces on the screen based on the provided game board.
     *
     * @param board A 2D array representing the game board.
     */
    void drawPieces(int[][] board);

    /**
     * Displays the game board along with game-related information.
     *
     * @param board        A 2D array representing the game board.
     * @param isPlayerTurn `true` if it's the player's turn, `false` otherwise.
     * @param position     The current position in the game.
     */
    void displayBoard(int[][] board, boolean isPlayerTurn, int position);

    /**
     * Draws a game piece at a specific position on the game board.
     *
     * @param position     The position where the piece should be drawn.
     * @param isPlayerTurn `true` if it's the player's turn, `false` otherwise.
     */
    void drawPiecePosition(int position, boolean isPlayerTurn);

    /**
     * Displays the winner of the game.
     *
     * @param winner The identifier of the winning player (or 0 for a draw).
     */
    void displayWinner(int winner);
}

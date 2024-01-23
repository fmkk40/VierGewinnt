package game.model;

import game.model.exceptions.InvalidMoveException;
import game.model.exceptions.NoMoreMovesException;
import game.model.exceptions.NoMoreUndoMovesException;

import java.util.List;
/**
 * Interface representing the model of a "Vier Gewinnt" (Connect Four) game.
 * Implementations of this interface are responsible for managing the game state and logic.
 */
public interface IVierGewinntModel {

        /**
         * Checks if it is currently the player's turn.
         *
         * @return `true` if it's the player's turn, `false` otherwise.
         */
        boolean isPlayerTurn();

        /**
         * Retrieves the opposing player's identifier.
         *
         * @return The identifier of the opposing player.
         */
        int getOpposingPlayer();

        /**
         * Retrieves the current state of the game board as a 2D array.
         *
         * @return A 2D array representing the game board.
         */
        int[][] getBoard();

        /**
         * Attempts to play a move in the specified column.
         *
         * @param col The column in which the move should be played.
         * @return The move that was played.
         * @throws InvalidMoveException If the move is invalid.
         */
        Move play(int col) throws InvalidMoveException;

        /**
         * Attempts to undo the last move played.
         *
         * @return The move that was undone.
         * @throws NoMoreUndoMovesException If there are no more moves to undo.
         */
        Move undo() throws NoMoreUndoMovesException;

        /**
         * Checks if the game is over.
         *
         * @return `true` if the game is over, `false` otherwise.
         */
        boolean isGameOver();

        /**
         * Determines the list of moves that resulted in a winning combination.
         *
         * @return A list of moves that contributed to a win, or an empty list if no win is detected.
         */
        List<Move> gameWon();

        /**
         * Attempts to play a random move.
         *
         * @return The random move that was played.
         * @throws InvalidMoveException If the move is invalid.
         * @throws NoMoreMovesException If there are no more valid moves left.
         */
        Move playRandom() throws InvalidMoveException, NoMoreMovesException;
}

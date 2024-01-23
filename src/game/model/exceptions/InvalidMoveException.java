package game.model.exceptions;

/**
 * Custom exception class representing an invalid move in the game.
 * This exception is thrown when a player attempts to make an invalid move.
 */
public class InvalidMoveException extends Exception {

    /**
     * Constructs an `InvalidMoveException` with a specific column number.
     *
     * @param column The column where the invalid move was attempted.
     */
    public InvalidMoveException(int column) {
        super(String.format("Move @ column %d is invalid. Please try again.", column));
    }
}


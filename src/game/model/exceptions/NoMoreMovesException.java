package game.model.exceptions;

/**
 * Custom exception class representing the situation where there are no more valid moves left in the game.
 * This exception is thrown when the game has reached a state where no more moves can be made.
 */
public class NoMoreMovesException extends Exception {

    /**
     * Constructs a `NoMoreMovesException` with a message indicating that there are no more moves left.
     */
    public NoMoreMovesException() {
        super("No more moves left.");
    }
}
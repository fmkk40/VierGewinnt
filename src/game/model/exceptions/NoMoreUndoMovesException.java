package game.model.exceptions;

/**
 * Custom exception class representing the situation where there are no more moves to undo in the game.
 * This exception is thrown when there are no moves on the board that can be undone.
 */
public class NoMoreUndoMovesException extends Exception {

    /**
     * Constructs a `NoMoreUndoMovesException` with a message indicating that there are no moves on the board to undo.
     */
    public NoMoreUndoMovesException() {
        super("There are no moves on the board to undo.");
    }
}

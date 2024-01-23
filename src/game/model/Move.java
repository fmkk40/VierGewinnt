package game.model;

/**
 * A record representing a move in a game, defined by its row and column coordinates.
 *
 * @param row    The row coordinate of the move.
 * @param column The column coordinate of the move.
 */
public record Move(int row, int column) {

    /**
     * Returns a string representation of the move in the format "[row, column]".
     *
     * @return A string representation of the move.
     */
    @Override
    public String toString() {
        return String.format("[%d, %d]", this.row, this.column);
    }
}


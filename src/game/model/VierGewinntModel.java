package game.model;

import game.model.exceptions.InvalidMoveException;
import game.model.exceptions.NoMoreMovesException;
import game.model.exceptions.NoMoreUndoMovesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class ConnectFourModel
 */
public class VierGewinntModel implements IVierGewinntModel {

    /**
     * the game board. contains all the moves played.
     */
    private final int[][] board;

    public int getHeight() {
        return height;
    }

    /**
     * the height of the board
     */
    private final int height;

    public int getWidth() {
        return width;
    }

    /**
     * the width of the board
     */
    private final int width;
    /**
     * is used to check if it is player 1's turn
     */
    private boolean isPlayerTurn; // 1
    /**
     * needed to generate random numbers.
     */
    private final Random r = new Random();
    /**
     * contains all moves played.
     */
    private final List<Integer> moves;

    /**
     * initialises the game as a 6*7 board.
     */
    public VierGewinntModel() {
        moves = new ArrayList<>();
        this.height = 6;
        this.width = 7;
        this.board = new int[height][width];
        this.isPlayerTurn = true;
    }

    /**
     * checks if it is player 1s turn
     *
     * @return true if its player 1s turn
     */
    @Override
    public boolean isPlayerTurn(){
        return isPlayerTurn;
    }


    /**
     * Returns the number
     */
    @Override
    public int getOpposingPlayer() {
        return isPlayerTurn ? 2 : 1;
    }

    /**
     * getter method for board
     *
     * @return the game board
     */
    @Override
    public int[][] getBoard() {
        return board;
    }

    /**
     * Checks if there is a slot free in a column for a move to be made.
     * If there is a slot free, it means that a player can be able to place a "chip"
     * in that column
     *
     * @param col the column to be validated
     * @return true if the column has an empty slot else false
     */
    private boolean isValidMove(int col) {
        return this.board[5][col] == 0;
    }

    /**
     * plays a move by first checking if the move is a valid move.
     * If this is not the case an {@code InvalidMoveException} is thrown
     * After the move is played, the {@code isPlayerTurn} variable is toggled.
     *
     * @param col the column to play
     * @return the move played consisting of the column of row.
     * @throws InvalidMoveException if the move to be played is invalid.
     */
    @Override
    public Move play(int col) throws InvalidMoveException {
        if (col >= width) {
            throw new InvalidMoveException(col);
        }
        int row = this.findFreeSlot(col);
        if (row == -1) {
            throw new InvalidMoveException(col);
        }
        moves.add(col);
        if (this.isPlayerTurn) {
            this.board[row][col] = 1;
        } else {
            this.board[row][col] = 2;
        }
        this.isPlayerTurn = !this.isPlayerTurn;
        return new Move(row, col);
    }

    /**
     * If there are any moves on the board, the most resent move is reverted.
     */
    @Override
    public Move undo() throws NoMoreUndoMovesException {
        if (!this.moves.isEmpty()) {
            int lastMovePosition = this.moves.size() - 1;
            int lastColumnPlayed = moves.get(lastMovePosition); // col 0
            int freeSlot = findFreeSlot(lastColumnPlayed);
            int prevRow = freeSlot == -1 ? 5 : freeSlot - 1;
            this.board[prevRow][lastColumnPlayed] = 0;
            this.moves.remove(lastMovePosition);
            this.isPlayerTurn = !this.isPlayerTurn;
            return new Move(prevRow, lastColumnPlayed);
        }
        throw new NoMoreUndoMovesException();
    }

    /**
     * Checks a column and returns a playable free slot position.
     *
     * @param col the column to be checked
     * @return a free slot position in a column if none is available, -1 is returned.
     */
    private int findFreeSlot(int col) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if there are no more playable slots on the board.
     *
     * @return true if there are no more available slots else false
     */
    @Override
    public boolean isGameOver() {
        for (int[] rows : board) {
            if (rows[height] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks diagonally in ascending position if the previous player (player that
     * just played) has won the game
     *
     * @return an empty list if there are no winning positions else a list
     * containing the winning moves.
     */
    private List<Move> checkDescendingDiagonal() {
        int previousPlayer = this.getOpposingPlayer();
        List<Move> winningMoves = new ArrayList<>();
        for (int i = 3; i < height; i++) {
            for (int j = 0; j < width - 3; j++) {
                if (this.board[i][j] == previousPlayer) {
                    if (this.board[i - 1][j + 1] == previousPlayer) {
                        if (this.board[i - 2][j + 2] == previousPlayer) {
                            if (this.board[i - 3][j + 3] == previousPlayer) {
                                winningMoves.add(new Move(i, j));
                                winningMoves.add(new Move(i - 1, j + 1));
                                winningMoves.add(new Move(i - 2, j + 2));
                                winningMoves.add(new Move(i - 3, j + 3));
                            }
                        }
                    }
                }
            }
        }
        return winningMoves;
    }

    /**
     * Checks diagonally in descending position if the previous player (player that
     * just played) has won the game
     *
     * @return an empty list if there is no winning positions else a list
     * containing the winning moves.
     */
    private List<Move> checkAscendingDiagonal() {
        int previousPlayer = this.getOpposingPlayer();
        List<Move> winningMoves = new ArrayList<>();
        for (int i = 3; i < height; i++) {
            for (int j = 3; j < width; j++) {
                if (this.board[i][j] == previousPlayer) {
                    if (this.board[i - 1][j - 1] == previousPlayer) {
                        if (this.board[i - 2][j - 2] == previousPlayer) {
                            if (this.board[i - 3][j - 3] == previousPlayer) {
                                winningMoves.add(new Move(i, j));
                                winningMoves.add(new Move(i - 1, j - 1));
                                winningMoves.add(new Move(i - 2, j - 2));
                                winningMoves.add(new Move(i - 3, j - 3));
                            }
                        }
                    }
                }
            }
        }
        return winningMoves;
    }

    /**
     * Checks horizontally if the previous player (player that
     * just played) has won the game
     *
     * @return an empty list if there is no winning positions else a list
     * containing the winning moves.
     */
    private List<Move> checkHorizontal() {
        int previousPlayer = this.getOpposingPlayer();
        List<Move> winningMoves = new ArrayList<>();
        for (int j = 0; j < width - 3; j++) {
            for (int i = 0; i < height; i++) {
                if (this.board[i][j] == previousPlayer) {
                    if (this.board[i][j + 1] == previousPlayer) {
                        if (this.board[i][j + 2] == previousPlayer) {
                            if (this.board[i][j + 3] == previousPlayer) {
                                winningMoves.add(new Move(i, j));
                                winningMoves.add(new Move(i, j + 1));
                                winningMoves.add(new Move(i, j + 2));
                                winningMoves.add(new Move(i, j + 3));
                            }
                        }
                    }
                }
            }
        }
        return winningMoves;
    }

    /**
     * Checks vertically if the previous player (player that just played) has won
     * the game
     *
     * @return an empty list if there is no winning positions else a list
     * containing the winning moves.
     */
    private List<Move> checkVertical() {
        int previousPlayer = this.getOpposingPlayer();
        List<Move> winningMoves = new ArrayList<>();
        for (int i = 0; i < height - 3; i++) {
            for (int j = 0; j < width; j++) {
                if (this.board[i][j] == previousPlayer) {
                    if (this.board[i + 1][j] == previousPlayer) {
                        if (this.board[i + 2][j] == previousPlayer) {
                            if (this.board[i + 3][j] == previousPlayer) {
                                winningMoves.add(new Move(i, j));
                                winningMoves.add(new Move(i + 1, j));
                                winningMoves.add(new Move(i + 2, j));
                                winningMoves.add(new Move(i + 3, j));
                            }
                        }
                    }
                }
            }
        }
        return winningMoves;
    }

    /**
     * Checks if the previous player has won the game
     *
     * @return if game won, a list containing the winning moves is returned else an
     * empty list is returned
     */
    @Override
    public List<Move> gameWon() {
        List<Move> winningMoves = new ArrayList<>();

        winningMoves.addAll(checkDescendingDiagonal());
        winningMoves.addAll(checkAscendingDiagonal());
        winningMoves.addAll(checkHorizontal());
        winningMoves.addAll(checkVertical());

        return winningMoves;
    }

    /**
     * Analyses the board and returns player columns as an array.
     *
     * @return a list of all columns with at least one free slot
     */
    private List<Integer> validMoves() {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < board[0].length; i++) {
            if (isValidMove(i)) {
                results.add(i);
            }
        }
        return results;
    }

    /**
     * This finds all columns with at least one free slot and chooses a column at
     * random to play
     *
     * @return the random move played
     * @throws InvalidMoveException if the move chosen at random is invalid
     * @throws NoMoreMovesException if there are no available moves to play.
     */
    @Override
    public Move playRandom() throws InvalidMoveException, NoMoreMovesException {
        List<Integer> validMoves = validMoves();
        if (!validMoves.isEmpty()) {
            int randomColumn = validMoves.get(r.nextInt(validMoves.size()));
            return this.play(randomColumn);
        }
        throw new NoMoreMovesException();
    }

}

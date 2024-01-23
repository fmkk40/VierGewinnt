package test;

import game.model.Move;
import game.model.VierGewinntModel;
import game.model.exceptions.InvalidMoveException;
import game.model.exceptions.NoMoreMovesException;
import game.model.exceptions.NoMoreUndoMovesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VierGewinntModelTest {

    private VierGewinntModel model;

    @BeforeEach
    public void setUp() {
        model = new VierGewinntModel();
    }

    @Test
    public void testInitialConditions() {
        int[][] board = model.getBoard();
        assertEquals(6, board.length);
        assertEquals(7, board[0].length);
        assertTrue(model.isPlayerTurn());
    }

    @Test
    public void testValidMove() throws InvalidMoveException {
        int columnToPlay = 0;
        model.play(columnToPlay);
        assertFalse(model.isPlayerTurn());
    }

    @Test
    public void testInvalidMove() {
        int invalidColumn = 7;
        assertThrows(InvalidMoveException.class, () -> model.play(invalidColumn));
    }

    @Test
    public void testExceptionMessage() {
        Exception exception = assertThrows(NoMoreMovesException.class, () -> {
            throw new NoMoreMovesException();
        });
        assertEquals("No more moves left.", exception.getMessage());
    }

    @Test
    public void testUndoMove() throws InvalidMoveException, NoMoreUndoMovesException {
        int columnToPlay = 0;
        model.play(columnToPlay);
        int[][] boardBefore = model.getBoard();
        model.undo();
        int[][] boardAfter = model.getBoard();
        assertTrue(model.isPlayerTurn());
        assertArrayEquals(boardBefore, boardAfter);
    }

    @Test
    public void testNoMoreUndoMoves() {
        assertThrows(NoMoreUndoMovesException.class, () -> model.undo());
    }

    @Test
    public void testIsGameOverNoMoves() {
        assertFalse(model.isGameOver());
    }

    @Test
    public void testIsGameOver() throws InvalidMoveException {
        // Fill the entire board to make it a draw
        for (int col = 0; col < model.getWidth(); col++) {
            for (int row = 0; row < model.getHeight(); row++) {
                model.play(col);
            }
        }

        assertTrue(model.isGameOver());
    }


    @Test
    public void testGameWonHorizontal() throws InvalidMoveException {
        for (int i = 0; i < 4; i++) {
            model.play(i);
            model.play(i);
        }
        assertEquals(4, model.gameWon().size());
    }

    @Test
    public void testGameWonVertical() throws InvalidMoveException {
        for (int i = 0; i < 4; i++) {
            model.play(0);
            model.play(1);
        }
        assertEquals(4, model.gameWon().size());
    }
    @Test
    public void testPlayRandom() {
        assertDoesNotThrow(() -> model.playRandom());
    }

    @Test
    public void testPlayRandomWithException() {
        // Fill all columns to make them invalid moves
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                try {
                    model.play(col);
                } catch (InvalidMoveException e) {
                    // Ignore invalid move exceptions
                }
            }
        }

        // Now, there are no more valid moves
        assertThrows(NoMoreMovesException.class, () -> model.playRandom());
    }


    @Test
    public void testToString() {
        Move move = new Move(3, 5);
        String expected = "[3, 5]";
        assertEquals( expected, move.toString());
    }

    @Test
    public void testRowGetter() {
        Move move = new Move(3, 5);
        assertEquals(3, move.row());
    }

    @Test
    public void testColumnGetter() {
        Move move = new Move(3, 5);
        assertEquals(5, move.column());
    }

    @Test
    public void testPlayInvalidMoveRow() {
        int validCol = 0;
        try {
            validCol = 0;
            // Fill up the entire column to make it invalid
            for (int i = 0; i < model.getHeight(); i++) {
                model.play(validCol);
            }
            // Attempt to play in the same column again (should be invalid)
            model.play(validCol);
        } catch (InvalidMoveException e) {
            assertEquals("Move @ column " + validCol + " is invalid. Please try again.", e.getMessage());}
    }

    //new
    @Test
    public void testDescendingDiagonalWin() throws InvalidMoveException {
        // Simulate a descending diagonal win (top right to bottom left)
        model.play(3); // Player 1
        model.play(4); // Player 2
        model.play(4); // Player 1
        model.play(5); // Player 2
        model.play(2); // Player 1
        model.play(5); // Player 2
        model.play(5); // Player 1
        model.play(6); // Player 2
        model.play(6); // Player 1
        model.play(6); // Player 2
        model.play(6); // Player 1 - This should create a winning descending diagonal

        List<Move> winningMoves = model.gameWon();
        assertFalse(winningMoves.isEmpty());
    }

    @Test
    public void testAscendingDiagonalWin() throws InvalidMoveException {
        // Simulate an ascending diagonal win (bottom left to top right)
        model.play(3); // Player 1
        model.play(2); // Player 2
        model.play(2); // Player 1
        model.play(1); // Player 2
        model.play(1); // Player 1
        model.play(0); // Player 2
        model.play(1); // Player 1
        model.play(0); // Player 2
        model.play(0); // Player 1
        model.play(4); // Player 2
        model.play(0); // Player 1 - This should create a winning ascending diagonal

        List<Move> winningMoves = model.gameWon();
        assertFalse(winningMoves.isEmpty());
    }


}

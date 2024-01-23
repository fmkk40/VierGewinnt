package game.controller;

import game.model.VierGewinntModel;
import game.model.IVierGewinntModel;
import game.model.Move;
import game.model.exceptions.InvalidMoveException;
import game.model.exceptions.NoMoreMovesException;
import game.model.exceptions.NoMoreUndoMovesException;
import game.view.IVierGewinntView;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the "Vier Gewinnt" (Connect Four) game.
 * This class implements the {@link IVierGewinntController} interface and is responsible for managing
 * the game's logic and user interactions.
 */
public class VierGewinntController implements IVierGewinntController {
    /**
     * the default position of the coin
     */
    private static final int DEFAULT_POSITION = 3;
    /**
     * the color of the first player
     */
    private static final String PLAYER1COLOR = "RED";
    /**
     * the color of the second player or the AI.
     */
    private static final String PLAYER2COLOR = "Yellow";
    /**
     * the game view
     */
    private final IVierGewinntView view;
    /**
     * manages the state of the game
     */
    GameState state = GameState.GAME_CONFIGURATION;
    /**
     * the game model
     */
    private IVierGewinntModel model;
    /**
     * true if playing with AI.
     */
    private boolean playWithAI;
    /**
     * the current position of the coin to be played
     */
    private int position = DEFAULT_POSITION;
    /**
     * counts the total number of moves made.
     * i.e. the number of coins on the board.
     */
    private int moves = 0;
    /**
     * contains a list of the winningMoves if the game is won
     */
    private List<Move> winningMoves;

    /**
     * The last move played in the game.
     */
    private Move playMove = null;

    /**
     * A string containing log messages related to gameplay.
     */
    private String playMsg = "";

    /**
     * initialize view
     *
     * @param view the view to use to display model information
     */
    public VierGewinntController(IVierGewinntView view) {
        this.view = view;
    }

    /**
     * sets up the view to display the start screen
     */
    @Override
    public void startScreen() {
        this.state = GameState.GAME_CONFIGURATION;
        view.displayStart();
    }

    /**
     * Sets up the game state and Initialises the model.
     * It also displays the startup board of the game with the help of the view
     *
     * @param playWithAI true if the game is to be played with AI else false.
     */
    private void simulateStart(boolean playWithAI) {
        model = new VierGewinntModel();
        moves = 0;
        winningMoves = new ArrayList<>();
        this.playWithAI = playWithAI;
        if (this.playWithAI && !model.isPlayerTurn()) {
            // AI starts
            try {
                model.playRandom();
                logMessage("Game Started");
                moves++;
            } catch (InvalidMoveException | NoMoreMovesException e) {
                logMessage(e.getMessage());
            }
        }
        state = GameState.GAME_STARTED;
        this.display();
    }

    /**
     * Used to log message in the view
     *
     * @param msg the message to be logged
     */
    private void logMessage(String msg) {
        view.log(msg);
    }

    /**
     * Returns a log message to be logged unto the screen after a move has been
     * played
     *
     * @param move the moved played
     * @return a string containing information about the color of the player and the
     * move the player just made
     */
    private String logMove(Move move) {
        String prevPlayerColor = this.model.isPlayerTurn() ? PLAYER2COLOR : PLAYER1COLOR;
        return (String.format("Player %s played %s%n", prevPlayerColor, move));
    }

    /**
     * Starts a new AI thread responsible for making AI moves in the game.
     */
    public void startAIThread() {
        AIThread aiTask = new AIThread(this);
        Thread aiThread = new Thread(aiTask);
        aiThread.start();
    }

    /**
     * Makes an AI move in the game.
     * This method handles AI move generation and updates game state accordingly.
     *
     */
    public void aiMakeMove() {
        try {
            Thread.sleep(1);
            playMove = model.playRandom();
        }
        catch (InvalidMoveException e) {
            logMessage(e.getMessage());
        } catch (NoMoreMovesException e) {
            state = GameState.GAME_OVER;
            logMessage(e.getMessage());
            view.displayWinner(0);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logMessage(e.getMessage() + "\nAI thread was interrupted.");
        }
    }


    /**
     * plays a move based on the column position passed.
     * if playing with AI, it automatically plays an AI move after a player is done
     * Messages of the moves played and exceptions caught are also logged to the
     * view
     *
     * @param position the column/move to be played
     */
    private void play(int position) {
        try {
        playMove = model.play(position);
        moves++;
        playMsg += this.logMove(playMove);
        this.position = DEFAULT_POSITION;
        this.display();
        winningMoves = model.gameWon();
        if (!winningMoves.isEmpty()) {
            state = GameState.GAME_OVER;
            int winner = model.getOpposingPlayer();
            view.displayWinningMoves(winningMoves);
            view.displayWinner(winner);
            playMsg += String.format("Player %d won", winner);
            logMessage(playMsg);
            return;
        }
        if (model.isGameOver()) {
            state = GameState.GAME_OVER;
            view.displayWinner(0);
            playMsg += "Game ended in a draw";
            logMessage(playMsg);
            return;
        }
        } catch (InvalidMoveException e) {
            logMessage(e.getMessage());
            this.display();
            return;
        }
        if (playWithAI) {
                startAIThread();
                this.position = DEFAULT_POSITION;
                this.display();
                winningMoves = model.gameWon();

                if (!winningMoves.isEmpty()) {
                    state = GameState.GAME_OVER;
                    int winner = model.getOpposingPlayer();
                    view.displayWinningMoves(winningMoves);
                    view.displayWinner(winner);
                    playMsg += String.format("Player %d won", winner);
                    logMessage(playMsg);
                    return;
                }
        }
        this.position = DEFAULT_POSITION;
        logMessage(playMsg);
        this.display();
    }

    /**
     * with the help of the view, the current state of the board is displayed.
     */
    private void display() {
        view.displayBoard(model.getBoard(), model.isPlayerTurn(), position);
    }

    /**
     * Handles the keys pressed by a user.
     * Based on the state of the game, the needed method is called which corresponds
     * to the key pressed
     *
     * @param keyCode the code of the key pressed
     */
    @Override
    public void handleKeyPressed(int keyCode) {
        switch (state) {
            case GAME_STARTED -> {
                switch (keyCode) {
                    case PConstants.UP -> this.undo();
                    case PConstants.DOWN, PConstants.ENTER -> this.play(this.position);
                    case PConstants.LEFT -> this.move(-1);
                    case PConstants.RIGHT -> this.move(1);
                    default -> { // do nothing
                    }
                }
            }
            case GAME_OVER -> {
                switch (keyCode) {
                    case 's' -> {
                        this.display();
                        view.displayWinningMoves(winningMoves);
                    }
                    case PConstants.ENTER -> this.startScreen();
                    default -> { // do nothing
                    }
                }
            }
            case GAME_CONFIGURATION -> {
                switch (keyCode) {
                    case '1' -> this.simulateStart(false);
                    case '2' -> this.simulateStart(true);
                    case '3' -> this.view.displayHelp();
                    default -> {// do nothing
                    }
                }
            }
        }
    }

    /**
     * calculates and sets the position current player wants to set his coin for the
     * next move. the position is always >= 0 and <=6
     *
     * @param direction the direction to be moved -1 is left +1 is right
     */
    private void move(int direction) {
        position += direction;
        if (position == -1) {
            position = 6;
        }
        if (position == 7) {
            position = 0;
        }
        this.display();
    }

    /**
     * this reverts a move made.
     * if playing with AI, AI and player moves are reverted.
     */
    private void undo() {
        String msg = "";
        try {
            if (this.playWithAI) {
                if (moves == 1) {
                    // AI just started game.
                    try {
                        msg += logUndoMove(model.undo());
                        moves--;
                        msg += model.playRandom();
                        moves++;
                    } catch (InvalidMoveException | NoMoreMovesException e) {
                        logMessage(e.getMessage());
                    }
                } else {
                    msg += logUndoMove(model.undo());
                    moves--;
                    msg += logUndoMove(model.undo());
                    moves--;
                }
            } else {
                msg += logUndoMove(model.undo());
                moves--;
            }
            logMessage(msg);
        } catch (NoMoreUndoMovesException e) {
            logMessage(e.getMessage());
        }
        this.display();
    }

    /**
     * prepares message to be logged for a reverted move
     *
     * @param move the reverted move
     * @return the message to be logged
     */
    private String logUndoMove(Move move) {
        String prevPlayerColor = this.model.isPlayerTurn() ? PLAYER2COLOR : PLAYER1COLOR;
        return String.format("Player %s removed move %s%n", prevPlayerColor, move);
    }
}


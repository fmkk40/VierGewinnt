package game.controller;

/**
 * Enumeration representing the possible states of a game.
 */
public enum GameState {
    /**
     * The game is in configuration mode, where players can set up the game.
     */
    GAME_CONFIGURATION,

    /**
     * The game has ended, and a winner or a draw has been determined.
     */
    GAME_OVER,

    /**
     * The game has started and is currently in progress.
     */
    GAME_STARTED,
}



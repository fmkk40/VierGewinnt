package game.controller;
/**
 * This interface defines the contract for a controller in a "Vier Gewinnt" (Connect Four) game.
 * Implementations of this interface are responsible for controlling the game flow and user interactions.
 */
public interface IVierGewinntController {

    /**
     * Displays the start screen of the game.
     */
    void startScreen();

    /**
     * Handles a key press event in the game.
     *
     * @param keyCode The code representing the key that was pressed.
     */
    void handleKeyPressed(int keyCode);
}

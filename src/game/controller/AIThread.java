package game.controller;

/**
 * AIThread is a Runnable that encapsulates the execution logic for the AI's move
 * in the VierGewinnt game. When run in a separate thread, it allows the AI
 * computations to be processed independently from the main GUI thread, thereby
 * enhancing application responsiveness.
 */
public class AIThread implements Runnable {
    /**
     * Controller instance to manage the game logic.
     */
    private final VierGewinntController controller;

    /**
     * Constructs an AIThread with a specified VierGewinntController.
     *
     * @param controller The controller responsible for handling the AI logic.
     */
    public AIThread(VierGewinntController controller) {
        this.controller = controller;
    }

    /**
     * The entry point for the thread. This method is invoked when the thread
     * is started and it triggers the AI move logic in the game controller.
     */
    @Override
    public void run() {
        controller.aiMakeMove();
    }
}

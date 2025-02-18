package ruibot;

/**
 * Represents an exception that is thrown when the task is empty.
 */
public class EmptyTaskException extends Exception {
    public EmptyTaskException() {
        super("Task cannot be empty.");
    }
}

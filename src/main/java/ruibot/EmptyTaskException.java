package ruibot;

public class EmptyTaskException extends Exception {
    public EmptyTaskException() {
        super("Task cannot be empty.");
    }
}

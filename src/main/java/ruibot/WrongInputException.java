package ruibot;

/**
 * Represents an exception that is thrown when the input by user is wrong.
 */
public class WrongInputException extends Exception {
    public WrongInputException() {
        super("I don't understand what that means.");
    }
}

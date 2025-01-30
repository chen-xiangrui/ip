package ruibot;

/**
 * The Parser class functions to read the input from user and make sense of it.
 */
public class Parser {

    /**
     * Read the input from the user and categorise it into a command type.
     * @param input The input by the user.
     * @return The Command type of the input.
     */
    public static String read(String input) {
        return input.split(" ")[0];
    }
}

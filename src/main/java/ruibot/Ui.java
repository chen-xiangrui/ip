package ruibot;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the creating and handling of texts displayed on GUI.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Initialises the Ui object.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the welcome message.
     *
     * @return String containing welcome message.
     */
    public String welcomeMessage() {
        return "Hello! I'm ruibot.RuiBot\n"
            + "What can I do for you?\n";
    }

    /**
     * Returns the goodbye message.
     *
     * @return String containing the goodbye message.
     */
    public String goodbyeMessage() {
        return "Bye. Hope to see you again soon!\n";
    }

    /**
     * Returns the input by user.
     *
     * @return String containing input by user.
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }
}

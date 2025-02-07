package ruibot;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String welcomeMessage() {
        return "Hello! I'm ruibot.RuiBot\n"
            + "What can I do for you?\n";
    }

    public String goodbyeMessage() {
        return "Bye. Hope to see you again soon!\n";
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }
}

package ruibot;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void welcomeMessage() {
        String logo = "____________________________________________________________\n"
            + "Hello! I'm ruibot.RuiBot\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n";

        System.out.println(logo);
    }

    public void goodbyeMessage() {
        System.out.println("____________________________________________________________\n"
            + "Bye. Hope to see you again soon!\n"
            + "____________________________________________________________\n");
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }
}

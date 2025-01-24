import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
            + "Hello! I'm RuiBot\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n";

        System.out.println(logo);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n"
                        + "Bye. Hope to see you again soon!\n"
                        + "____________________________________________________________\n");
                break;
            } else {
                System.out.println("____________________________________________________________\n"
                        + input + "\n"
                        + "____________________________________________________________\n");
            }
        }
    }
}
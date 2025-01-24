import java.util.Scanner;
import java.util.ArrayList;

public class RuiBot {
    ArrayList<Task> itemsList;

    public RuiBot() {
        this.itemsList = new ArrayList<>();
    }
    public void addItems(String input) {
        Task task;
        String details[];
        String item;

        if (input.startsWith("todo")) {
            item = input.substring(5);
            task = new ToDo(item);
        } else if (input.startsWith("deadline")) {
            details = input.substring(9).split(" /by ");
            item = details[0];
            String endDate = details[1];
            task = new Deadline(item, endDate);
        } else {
            details = input.substring(6).split(" /from ");
            item = details[0];
            String startDate = details[1].split(" /to ")[0];
            String endDate = details[1].split(" /to ")[1];
            task = new Event(item, startDate, endDate);
        }

        this.itemsList.add(task);
        
        System.out.println("____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + task.taskString() + "\n"
                + "Now you have " + this.itemsList.size() + " tasks in the list.\n"
                + "____________________________________________________________\n");
    }

    public void printList() {
        int itemsNum = this.itemsList.size();

        System.out.println("____________________________________________________________\n"
                + "Here are the tasks in your list:");

        for (int i = 0; i < itemsNum; i++) {
            System.out.println(i + 1 + ". " + this.itemsList.get(i).taskString() + "\n");
        }

        System.out.println("____________________________________________________________\n");
    }

    public void markItem(int index) {
        this.itemsList.get(index - 1).changeStatus();

        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + this.itemsList.get(index - 1).taskString() + "\n"
                + "____________________________________________________________\n");
    }

    public void unmarkItem(int index) {
        this.itemsList.get(index - 1).changeStatus();

        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + this.itemsList.get(index - 1).taskString() + "\n"
                + "____________________________________________________________\n");
    }

    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
            + "Hello! I'm RuiBot\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n";

        RuiBot ruibot = new RuiBot();

        System.out.println(logo);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n"
                        + "Bye. Hope to see you again soon!\n"
                        + "____________________________________________________________\n");
                break;
            } else if (input.equals("list")) {
                ruibot.printList();
            } else if (input.startsWith("mark")) {
                ruibot.markItem(Integer.parseInt(input.substring(5)));
            } else if (input.startsWith("unmark")) {
                ruibot.unmarkItem(Integer.parseInt(input.substring(7)));
            } else {
                ruibot.addItems(input);
            }
        }
    }
}
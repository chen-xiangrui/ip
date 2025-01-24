import java.util.Scanner;
import java.util.ArrayList;

public class RuiBot {
    ArrayList<Task> itemsList;

    public RuiBot() {
        this.itemsList = new ArrayList<>();
    }
    public void addItems(String item) {
        Task task = new Task(item);
        this.itemsList.add(task);
        
        System.out.println("____________________________________________________________\n"
                + "added: " + task.name + "\n"
                + "____________________________________________________________\n");
    }

    public void printList() {
        int itemsNum = this.itemsList.size();

        System.out.println("____________________________________________________________\n");

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
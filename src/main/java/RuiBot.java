import java.util.Scanner;
import java.util.ArrayList;

public class RuiBot {
    ArrayList<Task> itemsList;

    public RuiBot() {
        this.itemsList = new ArrayList<>();
    }
    public void addItem(String input) throws EmptyTaskException {
        Task task;
        String details[];
        String item;

        if (input.startsWith("todo")) {
            if (input.length() <= 5) {
                throw new EmptyTaskException();
            }
            item = input.substring(5);
            task = new ToDo(item);
        } else if (input.startsWith("deadline")) {
            if (input.length() <= 9) {
                throw new EmptyTaskException();
            }
            details = input.substring(9).split(" /by ");
            item = details[0];
            String endDate = details[1];
            task = new Deadline(item, endDate);
        } else {
            if (input.length() <= 6) {
                throw new EmptyTaskException();
            }
            details = input.substring(6).split(" /from ");
            item = details[0];
            if (item.isEmpty()) {
                throw new EmptyTaskException();
            }
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

    public void deleteItem(int index) {
        Task removedItem = this.itemsList.remove(index - 1);

        System.out.println("____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + removedItem.taskString() + "\n"
                + "Now you have " + this.itemsList.size() + " tasks in the list.\n"
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
            try {
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
                } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                    ruibot.addItem(input);
                } else if (input.startsWith("delete")) {
                    ruibot.deleteItem(Integer.parseInt(input.substring(7)));
                } else {
                    throw new WrongInputException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("____________________________________________________________\n"
                        + "OOPS!! No such item." + "\n"
                        + "____________________________________________________________\n");
            } catch (Exception e) {
                System.out.println("____________________________________________________________\n"
                        + "OOPS!! " + e.getMessage() + "\n"
                        + "____________________________________________________________\n");
            }
        }
    }
}

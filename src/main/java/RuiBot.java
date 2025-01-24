import java.util.Scanner;
import java.util.ArrayList;

public class RuiBot {
    ArrayList<String> itemsList;

    public RuiBot() {
        this.itemsList = new ArrayList<>();
    }
    public void addItems(String item) {
        this.itemsList.add(item);
        
        System.out.println("____________________________________________________________\n"
                + "added: " + item + "\n"
                + "____________________________________________________________\n");
    }

    public void printList() {
        int itemsNum = this.itemsList.size();

        System.out.println("____________________________________________________________\n");

        for (int i = 0; i < itemsNum; i++) {
            System.out.println(i + 1 + ". " + this.itemsList.get(i) + "\n");
        }

        System.out.println("____________________________________________________________\n");
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
            } else {
                ruibot.addItems(input);
            }
        }
    }
}
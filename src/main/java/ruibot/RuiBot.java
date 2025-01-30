package ruibot;

public class RuiBot {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public RuiBot(String filePath) throws EmptyTaskException {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.load());
    }

    public static void main(String[] args) {
        try {
            RuiBot ruibot = new RuiBot("./data/ruibot.txt");
            ruibot.ui.welcomeMessage();
            while (true) {
                try {
                    String input = ruibot.ui.readCommand();
                    String command = Parser.read(input);
                    if (command.equals("bye")) {
                        ruibot.ui.goodbyeMessage();
                        break;
                    } else if (command.equals("list")) {
                        ruibot.tasks.printList();
                    } else if (command.equals("mark")) {
                        int index = Integer.parseInt(input.substring(5));
                        ruibot.tasks.markItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.equals("unmark")) {
                        int index = Integer.parseInt(input.substring(7));
                        ruibot.tasks.unmarkItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.equals("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                        ruibot.tasks.addItem(input, false, false);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.equals("delete")) {
                        int index = Integer.parseInt(input.substring(7));
                        ruibot.tasks.deleteItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.equals("find")) {
                        ruibot.tasks.find(input.substring(5));
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
        } catch (Exception e) {
            System.out.println("____________________________________________________________\n"
                    + "OOPS!! " + e.getMessage() + "\n"
                    + "____________________________________________________________\n");
        }
    }
}

package ruibot;

/**
 * The RuiBot class is the entry point for execution of the task bot.
 * It combines the UI, storage of the tasks and the functionalities of the bot together as one.
 */
public class RuiBot {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Constructor to initialise the RuiBot with the filepath of ruibot.txt.
     * @param filePath The filepath of the ruibot.txt storing the tasks.
     * @throws EmptyTaskException if the task is empty.
     */
    public RuiBot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String output;
        try {
            String command = Parser.read(input);
            if (command.equals("list")) {
                output = this.tasks.returnList();
            } else if (command.equals("mark")) {
                int index = Integer.parseInt(input.substring(5));
                output = this.tasks.markItem(index);
                this.storage.save(this.tasks.getTasks());
            } else if (command.equals("unmark")) {
                int index = Integer.parseInt(input.substring(7));
                output = this.tasks.unmarkItem(index);
                this.storage.save(this.tasks.getTasks());
            } else if (command.equals("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                output = this.tasks.addItem(input, false, false);
                this.storage.save(this.tasks.getTasks());
            } else if (command.equals("delete")) {
                int index = Integer.parseInt(input.substring(7));
                output = this.tasks.deleteItem(index);
                this.storage.save(this.tasks.getTasks());
            } else if (command.equals("find")) {
                output = this.tasks.find(input.substring(5));
            } else {
                throw new WrongInputException();
            }
        } catch (IndexOutOfBoundsException e) {
            output = "OOPS!! No such item.";
        } catch (Exception e) {
            output = "OOPS!! " + e.getMessage();
        }
        return output;
    }

//    public static void main(String[] args) {
//        try {
//            RuiBot ruibot = new RuiBot("./data/ruibot.txt");
//            ruibot.ui.welcomeMessage();
//            while (true) {
//                try {
//                    String input = ruibot.ui.readCommand();
//                    String command = Parser.read(input);
//                    if (command.equals("bye")) {
//                        ruibot.ui.goodbyeMessage();
//                        break;
//                    } else if (command.equals("list")) {
//                        ruibot.tasks.printList();
//                    } else if (command.equals("mark")) {
//                        int index = Integer.parseInt(input.substring(5));
//                        ruibot.tasks.markItem(index);
//                        ruibot.storage.save(ruibot.tasks.getTasks());
//                    } else if (command.equals("unmark")) {
//                        int index = Integer.parseInt(input.substring(7));
//                        ruibot.tasks.unmarkItem(index);
//                        ruibot.storage.save(ruibot.tasks.getTasks());
//                    } else if (command.equals("todo") || input.startsWith("deadline") || input.startsWith("event")) {
//                        ruibot.tasks.addItem(input, false, false);
//                        ruibot.storage.save(ruibot.tasks.getTasks());
//                    } else if (command.equals("delete")) {
//                        int index = Integer.parseInt(input.substring(7));
//                        ruibot.tasks.deleteItem(index);
//                        ruibot.storage.save(ruibot.tasks.getTasks());
//                    } else if (command.equals("find")) {
//                        ruibot.tasks.find(input.substring(5));
//                    } else {
//                        throw new WrongInputException();
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("____________________________________________________________\n"
//                            + "OOPS!! No such item." + "\n"
//                            + "____________________________________________________________\n");
//                } catch (Exception e) {
//                    System.out.println("____________________________________________________________\n"
//                            + "OOPS!! " + e.getMessage() + "\n"
//                            + "____________________________________________________________\n");
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("____________________________________________________________\n"
//                    + "OOPS!! " + e.getMessage() + "\n"
//                    + "____________________________________________________________\n");
//        }
//    }
}

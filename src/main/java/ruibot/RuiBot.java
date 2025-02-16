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
            } else if (command.equals("schedule")) {
                output = this.tasks.schedule(input.substring(9));
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

}

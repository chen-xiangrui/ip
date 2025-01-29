import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RuiBot {
    ArrayList<Task> itemsList;
    static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private TaskList tasks;
    private Storage storage;
    private Ui ui;

//    public RuiBot() {
//        this.itemsList = new ArrayList<>();
//    }

    public RuiBot(String filePath) throws EmptyTaskException {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.load());
    }
//    public void addItem(String input, boolean isCompleted, boolean isStart) throws EmptyTaskException {
//        Task task;
//        String details[];
//        String item;
//
//        if (input.startsWith("todo")) {
//            if (input.length() <= 5) {
//                throw new EmptyTaskException();
//            }
//            item = input.substring(5);
//            task = new ToDo(item, isCompleted);
//        } else if (input.startsWith("deadline")) {
//            if (input.length() <= 9) {
//                throw new EmptyTaskException();
//            }
//            details = input.substring(9).split(" /by ");
//            item = details[0];
//            String endDate = details[1];
//            String formattedEndDate = LocalDateTime.parse(endDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
//            task = new Deadline(item, isCompleted, formattedEndDate);
//        } else {
//            if (input.length() <= 6) {
//                throw new EmptyTaskException();
//            }
//            details = input.substring(6).split(" /from ");
//            item = details[0];
//            if (item.isEmpty()) {
//                throw new EmptyTaskException();
//            }
//            String startDate = details[1].split(" /to ")[0];
//            String formattedStartDate = LocalDateTime.parse(startDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
//            String endDate = details[1].split(" /to ")[1];
//            String formattedEndDate = LocalDateTime.parse(endDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
//            task = new Event(item, isCompleted, formattedStartDate, formattedEndDate);
//        }
//
//        this.itemsList.add(task);
//
//        if (!isStart) {
//            System.out.println("____________________________________________________________\n"
//                    + "Got it. I've added this task:\n"
//                    + task.taskString() + "\n"
//                    + "Now you have " + this.itemsList.size() + " tasks in the list.\n"
//                    + "____________________________________________________________\n");
//        }
//
//        this.updateTextFile();
//    }
//
//    public void printList() {
//        int itemsNum = this.itemsList.size();
//
//        System.out.println("____________________________________________________________\n"
//                + "Here are the tasks in your list:");
//
//        for (int i = 0; i < itemsNum; i++) {
//            System.out.println(i + 1 + ". " + this.itemsList.get(i).taskString() + "\n");
//        }
//
//        System.out.println("____________________________________________________________\n");
//    }
//
//    public void markItem(int index) {
//        this.itemsList.get(index - 1).changeStatus();
//
//        System.out.println("____________________________________________________________\n"
//                + "Nice! I've marked this task as done:\n"
//                + this.itemsList.get(index - 1).taskString() + "\n"
//                + "____________________________________________________________\n");
//
//        this.updateTextFile();
//    }
//
//    public void unmarkItem(int index) {
//        this.itemsList.get(index - 1).changeStatus();
//
//        System.out.println("____________________________________________________________\n"
//                + "OK, I've marked this task as not done yet:\n"
//                + this.itemsList.get(index - 1).taskString() + "\n"
//                + "____________________________________________________________\n");
//
//        this.updateTextFile();
//    }
//
//    public void deleteItem(int index) {
//        Task removedItem = this.itemsList.remove(index - 1);
//
//        System.out.println("____________________________________________________________\n"
//                + "Noted. I've removed this task:\n"
//                + removedItem.taskString() + "\n"
//                + "Now you have " + this.itemsList.size() + " tasks in the list.\n"
//                + "____________________________________________________________\n");
//
//        this.updateTextFile();
//    }
//
//    public void updateTextFile() {
//        try {
//            FileWriter fw = new FileWriter("./data/ruibot.txt", false);
//            for (Task task : this.itemsList) {
//                fw.write(task.taskString() + System.lineSeparator());
//            }
//            fw.close();
//        } catch (IOException e) {
//            System.out.println("An error occurred: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
//        String logo = "____________________________________________________________\n"
//            + "Hello! I'm RuiBot\n"
//            + "What can I do for you?\n"
//            + "____________________________________________________________\n";
//
//        System.out.println(logo);
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
                    } else if (command.startsWith("mark")) {
                        int index = Integer.parseInt(input.substring(5));
                        ruibot.tasks.markItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.startsWith("unmark")) {
                        int index = Integer.parseInt(input.substring(7));
                        ruibot.tasks.unmarkItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                        ruibot.tasks.addItem(input, false, false);
                        ruibot.storage.save(ruibot.tasks.getTasks());
                    } else if (command.startsWith("delete")) {
                        int index = Integer.parseInt(input.substring(7));
                        ruibot.tasks.deleteItem(index);
                        ruibot.storage.save(ruibot.tasks.getTasks());
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



//        Scanner scanner = new Scanner(System.in);

//        try {
//            String filepath = "./data/ruibot.txt";
//            File file = new File(filepath);
//            file.createNewFile();
//            Scanner fileScanner = new Scanner(file);
//            ArrayList<String> lines = new ArrayList<>();
//            while (fileScanner.hasNext()) {
//                String line = fileScanner.nextLine();
//                lines.add(line);
//            }
//
//            for (String line : lines) {
//                String item;
//                boolean isCompleted = (line.charAt(5) == 'X');
//                if (line.charAt(1) == 'T') {
//                    item = "todo " + line.substring(8);
//                } else if (line.charAt(1) == 'D') {
//                    String remaining_line = line.substring(8);
//                    String[] split_line = remaining_line.split(" \\(by: ");
//                    String name = split_line[0];
//                    String endDate = split_line[1].split("\\)")[0];
//                    String formattedEndDate = LocalDateTime.parse(endDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
//                    item = "deadline " + name + " /by " + formattedEndDate;
//                } else {
//                    String remaining_line = line.substring(8);
//                    String[] split_line = remaining_line.split(" \\(from: ");
//                    String name = split_line[0];
//                    String[] dates = split_line[1].split(" to: ");
//                    String startDate = dates[0];
//                    String formattedStartDate = LocalDateTime.parse(startDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
//                    String endDate = dates[1].split("\\)")[0];
//                    String formattedEndDate = LocalDateTime.parse(endDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
//                    item = "event " + name + " /from " + formattedStartDate + " /to " + formattedEndDate;
//                }
//                ruibot.addItem(item, isCompleted, true);
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("____________________________________________________________\n"
//                    + "OOPS!! " + e.getMessage() + "\n"
//                    + "____________________________________________________________\n");
//        }

//        while (true) {
//            try {
//                String input = scanner.nextLine();
//                if (input.equals("bye")) {
//                    System.out.println("____________________________________________________________\n"
//                            + "Bye. Hope to see you again soon!\n"
//                            + "____________________________________________________________\n");
//                    break;
//                } else if (input.equals("list")) {
//                    ruibot.printList();
//                } else if (input.startsWith("mark")) {
//                    ruibot.markItem(Integer.parseInt(input.substring(5)));
//                } else if (input.startsWith("unmark")) {
//                    ruibot.unmarkItem(Integer.parseInt(input.substring(7)));
//                } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
//                    ruibot.addItem(input, false, false);
//                } else if (input.startsWith("delete")) {
//                    ruibot.deleteItem(Integer.parseInt(input.substring(7)));
//                } else {
//                    throw new WrongInputException();
//                }
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("____________________________________________________________\n"
//                        + "OOPS!! No such item." + "\n"
//                        + "____________________________________________________________\n");
//            } catch (Exception e) {
//                System.out.println("____________________________________________________________\n"
//                        + "OOPS!! " + e.getMessage() + "\n"
//                        + "____________________________________________________________\n");
//            }
//        }
    }
}

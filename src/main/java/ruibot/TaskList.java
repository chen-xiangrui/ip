package ruibot;

import ruibot.tasks.Task;
import ruibot.tasks.ToDo;
import ruibot.tasks.Deadline;
import ruibot.tasks.Event;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public TaskList(ArrayList<String> lines) throws EmptyTaskException {
        this.tasks = new ArrayList<>();
        for (String line : lines) {
            String item;
            boolean isCompleted = (line.charAt(5) == 'X');
            if (line.charAt(1) == 'T') {
                item = "todo " + line.substring(8);
            } else if (line.charAt(1) == 'D') {
                String remaining_line = line.substring(8);
                String[] split_line = remaining_line.split(" \\(by: ");
                String name = split_line[0];
                String endDate = split_line[1].split("\\)")[0];
                String formattedEndDate = LocalDateTime.parse(endDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
                item = "deadline " + name + " /by " + formattedEndDate;
            } else {
                String remaining_line = line.substring(8);
                String[] split_line = remaining_line.split(" \\(from: ");
                String name = split_line[0];
                String[] dates = split_line[1].split(" to: ");
                String startDate = dates[0];
                String formattedStartDate = LocalDateTime.parse(startDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
                String endDate = dates[1].split("\\)")[0];
                String formattedEndDate = LocalDateTime.parse(endDate, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
                item = "event " + name + " /from " + formattedStartDate + " /to " + formattedEndDate;
            }
            this.addItem(item, isCompleted, true);
        }
    }

    public void addItem(String input, boolean isCompleted, boolean isStart) throws EmptyTaskException {
        Task task;
        String details[];
        String item;

        if (input.startsWith("todo")) {
            if (input.length() <= 5) {
                throw new EmptyTaskException();
            }
            item = input.substring(5);
            task = new ToDo(item, isCompleted);
        } else if (input.startsWith("deadline")) {
            if (input.length() <= 9) {
                throw new EmptyTaskException();
            }
            details = input.substring(9).split(" /by ");
            item = details[0];
            String endDate = details[1];
            String formattedEndDate = LocalDateTime.parse(endDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
            task = new Deadline(item, isCompleted, formattedEndDate);
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
            String formattedStartDate = LocalDateTime.parse(startDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
            String endDate = details[1].split(" /to ")[1];
            String formattedEndDate = LocalDateTime.parse(endDate, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
            task = new Event(item, isCompleted, formattedStartDate, formattedEndDate);
        }

        this.tasks.add(task);

        if (!isStart) {
            System.out.println("____________________________________________________________\n"
                    + "Got it. I've added this task:\n"
                    + task.taskString() + "\n"
                    + "Now you have " + this.tasks.size() + " tasks in the list.\n"
                    + "____________________________________________________________\n");
        }
    }

    public void printList() {
        int itemsNum = this.tasks.size();

        System.out.println("____________________________________________________________\n"
                + "Here are the tasks in your list:");

        for (int i = 0; i < itemsNum; i++) {
            System.out.println(i + 1 + ". " + this.tasks.get(i).taskString() + "\n");
        }

        System.out.println("____________________________________________________________\n");
    }

    public void markItem(int index) {
        this.tasks.get(index - 1).changeStatus();

        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + this.tasks.get(index - 1).taskString() + "\n"
                + "____________________________________________________________\n");
    }

    public void unmarkItem(int index) {
        this.tasks.get(index - 1).changeStatus();

        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + this.tasks.get(index - 1).taskString() + "\n"
                + "____________________________________________________________\n");
    }

    public void deleteItem(int index) {
        Task removedItem = this.tasks.remove(index - 1);

        System.out.println("____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + removedItem.taskString() + "\n"
                + "Now you have " + this.tasks.size() + " tasks in the list.\n"
                + "____________________________________________________________\n");
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }
}

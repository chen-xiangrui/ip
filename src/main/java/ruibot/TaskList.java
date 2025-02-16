package ruibot;

import ruibot.tasks.Task;
import ruibot.tasks.ToDo;
import ruibot.tasks.Deadline;
import ruibot.tasks.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
* The TaskList class reperesents the list of tasks of RuiBot.
* Its functionalities include adding, marking, unmarking, deleting tasks, as well as printing a list of tasks.
*/
public class TaskList {
    private ArrayList<Task> tasks;
    static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
    * Constructor to initialise the TaskList with list of string inputs from user.
    * @param lines List of strings with each string containing input of a task by user.
    * @throws EmptyTaskException if task is empty.
    */
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

    /**
     * Adds task to list of tasks.
     * @param input Input of a task by the user.
     * @param isCompleted Indicates whether the task has been completed.
     * @param isStart Indicates whether this method is being called when the RuiBot is first started.
     * @throws EmptyTaskException if the task is empty.
     */
    public String addItem(String input, boolean isCompleted, boolean isStart) throws EmptyTaskException {
        assert input != null : "Input should not be null";
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
            return "Got it. I've added this task:\n"
                    + task.taskString() + "\n"
                    + "Now you have " + this.tasks.size() + " tasks in the list.\n";
        } else {
            return "";
        }
    }

    /**
     * Prints the list of tasks.
     */
    public String returnList() {
        int itemsNum = this.tasks.size();

        String result = "Here are the tasks in your list:\n";

        for (int i = 0; i < itemsNum; i++) {
            result += (i + 1) + ". " + this.tasks.get(i).taskString() + "\n";
        }

        return result;
    }

    /**
     * Marks a task as completed.
     * @param index The index of the task in the list.
     */
    public String markItem(int index) throws WrongInputException {
        assert index > 0 && index <= this.tasks.size() : "Index is invalid";
        if (this.tasks.get(index - 1).isTaskCompleted()) {
            throw new WrongInputException();
        }

        this.tasks.get(index - 1).changeStatus();

        return "Nice! I've marked this task as done:\n"
                + this.tasks.get(index - 1).taskString() + "\n";
    }

    /**
     * Unmarks a task that was previously marked as completed.
     * @param index The index of the task in the list.
     */
    public String unmarkItem(int index) throws WrongInputException {
        assert index > 0 && index <= this.tasks.size() : "Index is invalid";
        if (!this.tasks.get(index - 1).isTaskCompleted()) {
            throw new WrongInputException();
        }

        this.tasks.get(index - 1).changeStatus();

        return "OK, I've marked this task as not done yet:\n"
                + this.tasks.get(index - 1).taskString() + "\n";
    }

    /**
     * Deletes a task from the list of tasks.
     * @param index The index of the task in the list.
     */
    public String deleteItem(int index) {
        Task removedItem = this.tasks.remove(index - 1);

        return "Noted. I've removed this task:\n"
                + removedItem.taskString() + "\n"
                + "Now you have " + this.tasks.size() + " tasks in the list.\n";
    }

    /**
     * Provides the list of tasks.
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public String find(String keyword) {
        ArrayList<Task> results = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task.contains(keyword)) {
                results.add(task);
            }
        }

        int itemsNum = results.size();

        String result = "Here are the matching tasks in your list:\n";

        for (int i = 0; i < itemsNum; i++) {
            result += (i + 1) + ". " + results.get(i).taskString() + "\n";
        }

        return result;
    }
}

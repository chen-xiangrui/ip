package ruibot;

import ruibot.tasks.Task;
import ruibot.tasks.ToDo;
import ruibot.tasks.Deadline;
import ruibot.tasks.Event;

import java.time.LocalDate;
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
    static final DateTimeFormatter SCHEDULE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter SCHEDULE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

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
                String formattedEndDate = this.formatDate(endDate, "outputToInput");
                item = "deadline " + name + " /by " + formattedEndDate;
            } else {
                String remaining_line = line.substring(8);
                String[] split_line = remaining_line.split(" \\(from: ");
                String name = split_line[0];
                String[] dates = split_line[1].split(" to: ");
                String startDate = dates[0];
                String formattedStartDate = this.formatDate(startDate, "outputToInput");
                String endDate = dates[1].split("\\)")[0];
                String formattedEndDate = this.formatDate(endDate, "outputToInput");
                item = "event " + name + " /from " + formattedStartDate + " /to " + formattedEndDate;
            }
            this.addItem(item, isCompleted, true);
        }
    }

    /**
     * Adds task to list of tasks.
     *
     * @param input Input of a task by the user.
     * @param isCompleted Indicates whether the task has been completed.
     * @param isStart Indicates whether this method is being called when the RuiBot is first started.
     * @throws EmptyTaskException if the task is empty.
     */
    public String addItem(String input, boolean isCompleted, boolean isStart) throws EmptyTaskException {
        assert input != null : "Input should not be null";
        Task task;

        if (input.startsWith("todo")) {
            task = createToDoTask(input, isCompleted);
        } else if (input.startsWith("deadline")) {
            task = createDeadlineTask(input, isCompleted);
        } else {
            task = createEventTask(input, isCompleted);
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
     * Returns a formatted date based on the date conversion type stated.
     *
     * @param date The date to be formatted.
     * @param conversionType The type of conversion the user wants to apply on the date.
     * @return Formatted date.
     */
    public String formatDate(String date, String conversionType) {
        if (conversionType.equals("inputToOutput")) {
            return LocalDateTime.parse(date, INPUT_FORMATTER).format(OUTPUT_FORMATTER);
        } else if (conversionType.equals("outputToInput")){
            return LocalDateTime.parse(date, OUTPUT_FORMATTER).format(INPUT_FORMATTER);
        } else {
            return LocalDate.parse(date, SCHEDULE_INPUT_FORMATTER).format(SCHEDULE_OUTPUT_FORMATTER);
        }
    }

    /**
     * Returns Task of to do type.
     *
     * @param input Input by user.
     * @param isCompleted Whether the task has been completed.
     * @return Task object.
     * @throws EmptyTaskException If task provided is empty.
     */
    public Task createToDoTask(String input, boolean isCompleted) throws EmptyTaskException {
        String item;

        if (input.length() <= 5) {
            throw new EmptyTaskException();
        }
        item = input.substring(5);
        return new ToDo(item, isCompleted);
    }

    /**
     * Returns Task of deadline type.
     *
     * @param input Input by user.
     * @param isCompleted Whether the task has been completed.
     * @return Task object.
     * @throws EmptyTaskException If task provided is empty.
     */
    public Task createDeadlineTask(String input, boolean isCompleted) throws EmptyTaskException {
        String[] details;
        String item;

        if (input.length() <= 9) {
            throw new EmptyTaskException();
        }
        details = input.substring(9).split(" /by ");
        item = details[0];
        String endDate = details[1];
        String formattedEndDate = this.formatDate(endDate, "inputToOutput");
        return new Deadline(item, isCompleted, formattedEndDate);
    }

    /**
     * Returns Task of event type.
     *
     * @param input Input by user.
     * @param isCompleted Whether the task has been completed.
     * @return Task object.
     * @throws EmptyTaskException If task provided is empty.
     */
    public Task createEventTask(String input, boolean isCompleted) throws EmptyTaskException {
        String[] details;
        String item;

        if (input.length() <= 6) {
            throw new EmptyTaskException();
        }
        details = input.substring(6).split(" /from ");
        item = details[0];
        if (item.isEmpty()) {
            throw new EmptyTaskException();
        }
        String startDate = details[1].split(" /to ")[0];
        String formattedStartDate = this.formatDate(startDate, "inputToOutput");
        String endDate = details[1].split(" /to ")[1];
        String formattedEndDate = this.formatDate(endDate, "inputToOutput");
        return new Event(item, isCompleted, formattedStartDate, formattedEndDate);
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
     *
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
     *
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
     *
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
     *
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

        if (itemsNum == 0) {
            return "OOPS!! No tasks with matching keyword!";
        }

        String result = "Here are the matching tasks in your list:\n";

        for (int i = 0; i < itemsNum; i++) {
            result += (i + 1) + ". " + results.get(i).taskString() + "\n";
        }

        return result;
    }

    /**
     * Returns string of text to be printed.
     * Helps to select tasks to be completed during the given date.
     *
     * @param date Date to check if task is required to complete within.
     * @return String message containing the tasks to be completed on the given date.
     * @throws WrongInputException If the input provided is wrong.
     */
    public String schedule(String date) throws WrongInputException {
        ArrayList<Task> results = new ArrayList<>();
        String convertedDate;

        try {
            convertedDate = this.formatDate(date, "scheduleConversion");
        } catch (Exception e) {
            throw new WrongInputException();
        }

        for (Task task : this.tasks) {
            if (task.containsDate(convertedDate)) {
                results.add(task);
            }
        }

        int itemsNum = results.size();

        if (itemsNum == 0) {
            return "CONGRATS!! No tasks to be completed on " + convertedDate;
        }

        String result = "Here are the tasks to be completed on " + convertedDate + ":\n";

        for (int i = 0; i < itemsNum; i++) {
            result += (i + 1) + ". " + results.get(i).taskString() + "\n";
        }

        return result;
    }
}

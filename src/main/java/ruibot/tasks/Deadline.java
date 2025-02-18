package ruibot.tasks;

/**
 * Represents a task of deadline type which contains an end date.
 */
public class Deadline extends Task {
    String endDate;

    /**
     * Initialises a deadline task.
     *
     * @param name Name of task.
     * @param isCompleted Whether the task is completed.
     * @param endDate End date of the task.
     */
    public Deadline(String name, boolean isCompleted, String endDate) {
        super(name, isCompleted);
        this.endDate = endDate;
    }

    /**
     * Provides structure of the string about the task that is being displayed.
     *
     * @return String containing information about the task.
     */
    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[D] " + mark + " " + this.name + " (by: " + this.endDate + ")";
    }

    /**
     * Checks if task is set to be completed during the given date.
     *
     * @param date Date of task.
     * @return Boolean of whether task is to be completed during the given date.
     */
    public boolean containsDate(String date) {
        return endDate.contains(date);
    }
}

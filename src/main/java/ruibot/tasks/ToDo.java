package ruibot.tasks;

/**
 * Represents a task of to do type which contains a start date and an end date.
 */
public class ToDo extends Task {

    /**
     * Initialises a task.
     *
     * @param name Name of task.
     * @param isCompleted Whether the task is completed.
     */
    public ToDo(String name, boolean isCompleted) {
        super(name, isCompleted);
    }

    /**
     * Provides structure of the string about the task that is being displayed.
     *
     * @return String containing information about the task.
     */
    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[T] " + mark + " " + this.name;
    }

    /**
     * Checks if task is set to be completed during the given date.
     *
     * @param date Date of task.
     * @return Boolean of whether task is to be completed during the given date.
     */
    public boolean containsDate(String date) {
        return false;
    }
}

package ruibot.tasks;

/**
 * Represents a task of event type which contains a start date and an end date.
 */
public class Event extends Task {
    String startDate;
    String endDate;

    /**
     * Initialises an event task.
     *
     * @param name Name of task.
     * @param isCompleted Whether the task is completed.
     * @param startDate Start date of the task.
     * @param endDate End date of the task.
     */
    public Event(String name, boolean isCompleted, String startDate, String endDate) {
        super(name, isCompleted);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Provides structure of the string about the task that is being displayed.
     *
     * @return String containing information about the task.
     */
    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[E] " + mark + " " + this.name + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }

    /**
     * Checks if task is set to be completed during the given date.
     *
     * @param date Date of task.
     * @return Boolean of whether task is to be completed during the given date.
     */
    public boolean containsDate(String date) {
        return (startDate.contains(date) || endDate.contains(date));
    }
}

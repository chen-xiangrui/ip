package ruibot.tasks;

/**
 * Represents a task.
 */
public abstract class Task {
    String name;
    boolean isCompleted;

    /**
     * Initialises a task.
     *
     * @param name Name of task.
     * @param isCompleted Whether the task is completed.
     */
    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    /**
     * Checks if task is completed.
     *
     * @return Boolean of whether the task is completed.
     */
    public boolean isTaskCompleted() {
        return this.isCompleted;
    }

    /**
     * Flips the task's completed status.
     */
    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    /**
     * Provides structure of the string about the task that is being displayed.
     *
     * @return String containing information about the task.
     */
    public abstract String taskString();

    /**
     * Checks whether the task's name contains the given name.
     *
     * @param keyword String keyword.
     * @return Boolean of whether the task's name contains the given name.
     */
    public boolean contains(String keyword) {
        return this.name.contains(keyword);
    }

    /**
     * Checks if task is set to be completed during the given date.
     *
     * @param date Date of task.
     * @return Boolean of whether task is to be completed during the given date.
     */
    public abstract boolean containsDate(String date);
}
